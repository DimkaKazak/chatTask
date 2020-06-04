package chat.server;

import chat.filter.*;
import chat.filter.interfaces.Filter;
import constant.Writing;
import context.ContextManager;
import org.apache.log4j.Logger;
import xml.data.Message;
import xml.marshaller.XmlMarshaller;
import xml.unmarshaller.XmlUnmarshaller;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * MultiThreadedSocketServer for our app. It uses socket-connection.
 */
public class MultiThreadedSocketServer {
    private final static Logger LOGGER = Logger.getLogger(MultiThreadedSocketServer.class);

    private static XmlMarshaller xmlMarshaller;
    private static XmlUnmarshaller xmlUnmarshaller;

    private static void initMarshalling() throws JAXBException{

        JAXBContext context = JAXBContext.newInstance(Message.class);
        xmlMarshaller = new XmlMarshaller(context);
        xmlUnmarshaller = new XmlUnmarshaller(context);

    }

    private final static NickFilter nickFilter = new NickFilter();
    private final static List<Filter> filterList = new LinkedList<>();

    static {
        filterList.add(new EmojiFilter());
        filterList.add(new SwearWordsFilter());
        filterList.add(new SpaceFilter());
        filterList.add(new FirstLetterFilter(Writing.names));
        filterList.add(new FirstLetterFilter(Writing.capitals));
        filterList.add(new FirstLetterFilter(Writing.countries));
    }

    private static boolean isServerOn = false;

    public static void main(String[] args) {
        if (!isServerOn){
            new MultiThreadedSocketServer().startServer();
            isServerOn = true;
        }
    }

    private final List<Connection> connections = Collections.synchronizedList(new ArrayList<>());
    private final List<String> chatHistory = Collections.synchronizedList(new ArrayList<>());
    private ServerSocket server;

    public MultiThreadedSocketServer() {
        try {
            server = new ServerSocket( Integer.parseInt(ContextManager.getInstance().getProperty("PORT")) );
            initMarshalling();
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
            LOGGER.info("SERVER IS OFFLINE: CANNOT LAUNCH SERVER");
            closeAll();
        }
    }

    public void startServer(){
        try {
            LOGGER.info("SERVER IS ONLINE");

            while (true) {
                Socket socket = server.accept();

                Connection connection = new Connection(socket);
                connections.add(connection);
                connection.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeAll();
            LOGGER.info("SERVER IS OFFLINE");
        }
    }

    /**
     * Close all connections.
     */
    private void closeAll() {
        try {
            server.close();

            synchronized (connections) {
                for (Connection connection : connections){
                    connection.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("CANNOT CLOSE CONNECTIONS!");
        } finally {
            isServerOn = false;
        }
    }

    public void setDownServer() {
        closeAll();
    }


    /**
     * Main inner class that describes the logic of interaction between user and server based on socket-connection.
     */
    public class Connection extends Thread{
        private BufferedReader in;
        private PrintWriter out;
        private final Socket socket;

        private String name = "";

        public Connection(Socket socket) {
            this.socket = socket;

            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
                close();
            }
        }

        @Override
        public void run() {
            try {
                name = getMessageIn(readXml(in)).getMsg();
                while (!nickFilter.validateNick(name)){
                    out.println(initMessageOut("declined"));
                    name = getMessageIn(readXml(in)).getMsg();
                }

                out.println(initMessageOut("accepted"));
                chatHistory.forEach(historyMsg -> {
                    out.println(initMessageOut(historyMsg));
                });
                sendMsgForAll(name + " присоединился.");


                while (true) {
                    String str = getMessageIn(readXml(in)).getMsg();

                    if (str.equals("exit")) break;
                    String toSend = name + ": " + filterMsg(str);
                    sendMsgForAll(toSend);
                    chatHistory.add(toSend);
                }

                sendMsgForAll(name + ": " + "вышел.");
            } catch (IOException | JAXBException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }

        private void sendMsgForAll(String message) throws JAXBException {
            String msg = initMessageOut(message);
            LOGGER.info(message);
            synchronized (connections){
                for (Connection connection : connections) {
                    connection.out.println( msg );
                }
            }
        }

        public void close(){
            try {
                socket.close();
                in.close();
                out.close();

                connections.remove(this);
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error("CANNOT CLOSE CONNECTION!");
            }
        }

        private String readXml(BufferedReader in) throws IOException {
            StringBuilder xml = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                if(line.isEmpty()) break;
                xml.append(line);
            }
            return xml.toString();
        }

        private Message getMessageIn(String msg) throws JAXBException {
            return (Message) xmlUnmarshaller.getUnmarshalledXml(msg);
        }

        private String initMessageOut(String msg){
            Message messageOut = new Message();
            messageOut.setPort(server.getLocalPort());
            messageOut.setHost(server.getLocalSocketAddress().toString());
            messageOut.setMsg(msg);
            messageOut.setToken("RandomToken");
            messageOut.setDate(new Date());

            try {
                return xmlMarshaller.getXml(messageOut);
            } catch (JAXBException e) {
                e.printStackTrace();
            }

            return "ERROR";
        }
    }

    private static String filterMsg(String message){
        for (Filter filter : filterList){
            message = filter.filter(message);
        }
        return message;
    }

    public List<Connection> getConnections() {
        return connections;
    }

}
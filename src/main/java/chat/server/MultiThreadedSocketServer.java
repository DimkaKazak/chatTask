package chat.server;

import chat.utils.filter.*;
import chat.utils.filter.interfaces.Filter;
import constant.Writing;
import context.ContextManager;
import data.ClientInfo;
import org.apache.log4j.Logger;
import data.Message;
import sql.dao.impl.ClientDAO;
import sql.dao.impl.MessageDAO;
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

import static chat.utils.XmlUtils.*;

/**
 * MultiThreadedSocketServer for our app. It uses socket-connection.
 */
public class MultiThreadedSocketServer {
    private final static Logger LOGGER = Logger.getLogger(MultiThreadedSocketServer.class);

    private static XmlMarshaller xmlMarshaller;
    private static XmlUnmarshaller xmlUnmarshaller;

    private final ClientDAO clientDAO = new ClientDAO();
    private final MessageDAO messageDAO = new MessageDAO();

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
                name = getMessageIn(readXml(in), xmlUnmarshaller).getMsg();
                while (!nickFilter.validateNick(name)){
                    out.println(initMessageOut("declined", MultiThreadedSocketServer.this));
                    name = getMessageIn(readXml(in), xmlUnmarshaller).getMsg();
                }

                out.println(initMessageOut("accepted", MultiThreadedSocketServer.this));

                ClientInfo clientInfo = new ClientInfo(UUID.randomUUID().toString(), name, "");
                clientDAO.create(clientInfo);

                messageDAO.getHistory().forEach(historyMsg -> {
                        out.println(initMessageOut(clientDAO.getById(historyMsg.getClientId()).getName() + " : " +
                                historyMsg.getMsg(), MultiThreadedSocketServer.this));
                        });

                sendMsgForAll(name + " присоединился.");

                while (true) {
                    Message msgIn = getMessageIn(readXml(in), xmlUnmarshaller);
                    msgIn.setId(UUID.randomUUID().toString());
                    msgIn.setClientId(clientInfo.getId());
                    messageDAO.create(msgIn);

                    String str = msgIn.getMsg();
                    if (str.equals("exit")) break;
                    String toSend = name + ": " + filterMsg(str);
                    sendMsgForAll(toSend);
                }

                sendMsgForAll(name + ": " + "вышел.");
            } catch (IOException | JAXBException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }

        private void sendMsgForAll(String message) throws JAXBException {
            String msg = initMessageOut(message, MultiThreadedSocketServer.this);
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

    public XmlUnmarshaller getXmlUnmarshaller() {
        return xmlUnmarshaller;
    }

    public XmlMarshaller getXmlMarshaller() {
        return xmlMarshaller;
    }

    public Logger getLOGGER() {
        return LOGGER;
    }

    public List<Filter> getFilterList() {
        return filterList;
    }

    public NickFilter getNickFilter() {
        return nickFilter;
    }

    public ServerSocket getServer() {
        return server;
    }

    public void setXmlUnmarshaller(XmlUnmarshaller xmlUnmarshaller) {
        MultiThreadedSocketServer.xmlUnmarshaller = xmlUnmarshaller;
    }

    public void setXmlMarshaller(XmlMarshaller xmlMarshaller) {
        MultiThreadedSocketServer.xmlMarshaller = xmlMarshaller;
    }

    public void setServer(ServerSocket server) {
        this.server = server;
    }

    public void setIsServerOn(boolean isServerOn) {
        MultiThreadedSocketServer.isServerOn = isServerOn;
    }

}
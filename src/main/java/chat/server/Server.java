package chat.server;

import chat.filter.*;
import chat.filter.interfaces.Filter;
import context.ContextManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Server for our app. It uses socket-connection.
 */
public class Server {
    private final static Logger LOGGER = Logger.getLogger(Server.class);

    private final static NickFilter nickFilter = new NickFilter();

    private final static List<Filter> filterList = new LinkedList<>();

    static {
        filterList.add(new SwearWordsFilter());
        filterList.add(new EmojiFilter());
        filterList.add(new SpaceFilter());
        filterList.add(new NameFilter());
    }

    public static void main(String[] args) {
        new Server();
    }

    private final List<Connection> connections = Collections.synchronizedList(new ArrayList<>());
    private final List<String> chatHistory = Collections.synchronizedList(new ArrayList<>());
    private ServerSocket server;

    public Server() {
        try {
            server = new ServerSocket( Integer.parseInt(ContextManager.getInstance().getProperty("PORT")) );
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
        }
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

                name = in.readLine();
                while (!nickFilter.validateNick(name)){
                    out.println("declined");
                    name = in.readLine();
                }

                out.println("accepted");
                chatHistory.forEach(historyMsg -> out.println(historyMsg));
                sendMsgForAll(name + " присоединился.");

                String str = "";
                while (true) {
                    str = in.readLine();
                    if (str.equals("exit")) break;

                    str = filterMsg(str);

                    String toSend = name + ": " + str;
                    sendMsgForAll(toSend);
                    chatHistory.add(toSend);
                }

                sendMsgForAll(name + ": " + "вышел.");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }

        private void sendMsgForAll(String message){
            LOGGER.info(message);
            synchronized (connections){
                for (Connection connection : connections) {
                    connection.out.println( message );
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
}

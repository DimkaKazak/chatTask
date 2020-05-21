package chat.server;

import IO.exception.UnableToReadException;
import IO.reader.StreamTextFileReader;
import constant.FileConstants;
import constant.Writing;
import context.ContextManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Server for our app. It uses socket-connection.
 */
public class Server {

    public static void main(String[] args) {
        new Server();
    }

    private final List<Connection> connections = Collections.synchronizedList(new ArrayList<>());
    private ServerSocket server;

    public Server() {
        try {
            server = new ServerSocket( Integer.parseInt(ContextManager.getInstance().getProperty("PORT")) );
            System.out.println("SERVER IS ONLINE");

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
            System.out.println("SERVER IS OFFLINE");
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
            System.err.println("CANNOT CLOSE CONNECTIONS!");
        }
    }


    /**
     * Main inner class that describes the logic of interaction between user and server based on socket-connection.
     */
    public class Connection extends Thread{
        private BufferedReader in;
        private PrintWriter out;
        private Socket socket;

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
                while (!validateNick(name)){
                    out.println("declined");
                    name = in.readLine();
                }

                out.println("accepted");
                sendMsgForAll(name + " присоединился.");

                String str = "";
                while (true) {
                    str = in.readLine();
                    if (str.equals("exit")) break;

                    sendMsgForAll(name + ": " + str);
                }

                sendMsgForAll(name + ": " + "вышел.");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }

        private void sendMsgForAll(String message){
            System.out.println(message);
            synchronized (connections){
                for (Connection connection : connections) {
                    connection.out.println( validateMsg(message) );
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
                System.err.println("CANNOT CLOSE CONNECTION!");
            }
        }
    }

    private boolean validateNick(String nick){
        for (String swearWord : Writing.swearWords){
            if (swearWord.toLowerCase().contains(nick.toLowerCase())){
                return false;
            }
        }
        return true;
    }

    private String validateMsg(String msg){
        String[] splittedMsg = msg.split(" ");
        StringBuilder builder = new StringBuilder("");

        for (String word : splittedMsg){
            for (String swearWord : Writing.swearWords){
                if (swearWord.toLowerCase().contains(word.toLowerCase()) && word.length() > 1){

                    for (int i = 1; i < word.length() - 1; i++){
                        word = word.replace(word.substring(i, i + 1), "*");
                    }

                }
            }

            if (Writing.punctuationMarks.contains(word.toLowerCase())) builder.append(word);
            else builder.append(" ").append(word);
        }

        builder.replace(0, 1, "");
        return builder.toString();
    }

}

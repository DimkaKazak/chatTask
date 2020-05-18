package chat.server;

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
    private final Storage storage = Storage.getInstance();

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
                while (true){
                    name = in.readLine();

                    if( storage.getUsersStorage().containsKey(name) ){
                        out.println("Этот ник уже зарезервирован. Введите другой.");
                    } else {
                        storage.addUser(name);

                        synchronized (connections){
                            for (Connection connection : connections) {
                                connection.out.println(name + " присоединился.");
                            }
                        }

                        out.println("accepted");
                        break;
                    }
                }

                String str = "";
                while (true) {
                    str = in.readLine();
                    storage.addMessage(name, str);

                    if (str.equals("exit")) break;

                    synchronized (connections){
                        for (Connection connection : connections) {
                            connection.out.println(name + ": " + str);
                        }
                    }
                }

                synchronized (connections){
                    for (Connection connection : connections) {
                        connection.out.println(name + " вышел.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }

        public void close(){
            try {
                socket.close();
                in.close();
                out.close();

                connections.remove(this);

                if (connections.isEmpty()){
                    Server.this.closeAll();
                    System.out.println("SERVER IS OFFLINE");
                    System.exit(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("CANNOT CLOSE CONNECTION!");
            }
        }
    }

}

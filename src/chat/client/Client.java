package chat.client;

import constant.PropertyValues;
import context.ContextManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


/**
 *  User of chat room.
 */
public class Client {

    public static void main(String[] args) {
        new Client();
    }

    private final String IP;
    private final int PORT;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public Client(){
        Scanner scan = new Scanner(System.in);

        this.IP = ContextManager.getInstance().getProperty(PropertyValues.HOST.getPropertyName());
        this.PORT = Integer.parseInt(ContextManager.getInstance().getProperty(PropertyValues.PORT.getPropertyName()));

        try {
            try {
                initClient();

                System.out.println("Введите свой ник:");

                out.println(scan.nextLine());

                while (true) {
                    String acceptAnswer = in.readLine();
                    if (acceptAnswer.equals("accepted")){
                        break;
                    } else {
                        System.out.println("Ответ: " + acceptAnswer);
                        out.println(scan.nextLine());
                    }
                }

                Resender resend = new Resender();
                resend.start();

                String str = "";
                while (!str.equals("exit")) {
                    str = scan.nextLine();
                    out.println(str);
                }

            } finally {
                if (socket != null){
                    socket.close();
                }
                in.close();
                out.close();
                System.exit(-1);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Initialize client for chat room. Connects socket and in/out streams.
     *
     * @throws IOException
     */
    private void initClient() throws IOException {
        this.socket = new Socket(this.IP, this.PORT);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    /**
     * This class read in stream and say all information to user when get new message from server.
     */
    private class Resender extends Thread {
        private boolean stop;

        public void setStop() {
            stop = true;
        }

        @Override
        public void run() {
            try {
                while (!stop) {
                    String str = in.readLine();
                    System.out.println(str);
                }
            } catch (IOException e) {
                setStop();
                e.printStackTrace();
            }
        }
    }

}

package chat.client;

import constant.PropertyValues;
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
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

import static chat.utils.XmlUtils.*;

/**
 *  User of chat room.
 */
public class MultiThreadedSocketClient {
    private final static Logger LOGGER = Logger.getLogger(MultiThreadedSocketClient.class);
    private static XmlMarshaller xmlMarshaller;
    private static XmlUnmarshaller xmlUnmarshaller;

    private static void initMarshalling() throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(Message.class);
        xmlMarshaller = new XmlMarshaller(context);
        xmlUnmarshaller = new XmlUnmarshaller(context);

    }

    public static void main(String[] args) {
        new MultiThreadedSocketClient().startClient();
    }

    private final String IP;
    private final int PORT;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private final Scanner scanner;

    public MultiThreadedSocketClient(){
        this.scanner = new Scanner(System.in);

        this.IP = ContextManager.getInstance().getProperty(PropertyValues.HOST.getPropertyName());
        this.PORT = Integer.parseInt(ContextManager.getInstance().getProperty(PropertyValues.PORT.getPropertyName()));

        try {
            initMarshalling();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void startClient(){
        try {
            try {
                initClient();

                System.out.println("Введите свой ник:");
                String msg = initMessageOut(scanner.nextLine(), this.PORT, this.IP, xmlMarshaller);
                out.println(msg);

                isAccepted();

                Resender resend = new Resender();
                resend.start();

                String str = "";
                while (!str.equals("exit")) {
                    str = scanner.nextLine();
                    out.println(initMessageOut(str, this.PORT, this.IP, xmlMarshaller));
                }

            } catch (JAXBException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        } catch (IOException e){
            LOGGER.error("Something went wrong. Reload chat");
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
        LOGGER.info(String.format("Connected to %s:%s", this.IP, this.PORT));
    }

    private void close(){
        try {
            if (socket != null){
                socket.close();
            }
            in.close();
            out.close();
            System.exit(-1);
        } catch (IOException e) {
            LOGGER.error("CANNOT CLOSE CONNECTION!");
            e.printStackTrace();
        }
    }

    private boolean isAccepted() throws IOException, JAXBException {
        while (true) {
            String acceptAnswer = getMessageIn(readXml(in), xmlUnmarshaller).getMsg();
            if (acceptAnswer.equals("accepted")){
                break;
            } else {
                LOGGER.info(String.format("Ответ: %s", acceptAnswer));
                out.println(initMessageOut(scanner.nextLine(), this.PORT, this.IP, xmlMarshaller));
            }
        }
        return true;
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
                    String str = getMessageIn(readXml(in), xmlUnmarshaller).getMsg();
                    LOGGER.info(str);
                }
            } catch (IOException | JAXBException e) {
                setStop();
                e.printStackTrace();
            }
        }
    }

    public PrintWriter getOut() {
        return out;
    }
}
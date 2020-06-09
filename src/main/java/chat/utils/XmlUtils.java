package chat.utils;

import chat.client.MultiThreadedSocketClient;
import chat.server.MultiThreadedSocketServer;
import data.Message;
import xml.unmarshaller.XmlUnmarshaller;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

public class XmlUtils {

    public static String readXml(BufferedReader in) throws IOException {
        StringBuilder xml = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            if(line.isEmpty()) break;
            xml.append(line);
        }
        return xml.toString();
    }

    public static Message getMessageIn(String msg, XmlUnmarshaller xmlUnmarshaller) throws JAXBException {
        return (Message) xmlUnmarshaller.getUnmarshalledXml(msg);
    }

    public static String initMessageOut(String msg, MultiThreadedSocketServer multiThreadedSocketServer){
        Message messageOut = new Message();
        messageOut.setPort(multiThreadedSocketServer.getServer().getLocalPort());
        messageOut.setHost(multiThreadedSocketServer.getServer().getLocalSocketAddress().toString());
        messageOut.setMsg(msg);
        messageOut.setToken(TokenGenerator.getAlphaNumericString(20));
        messageOut.setDate(new Date());

        try {
            return multiThreadedSocketServer.getXmlMarshaller().getXml(messageOut);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return "ERROR";
    }

    public static String initMessageOut(String msg, MultiThreadedSocketClient multiThreadedSocketClient){
        Message messageOut = new Message();
        messageOut.setPort(multiThreadedSocketClient.getPORT());
        messageOut.setHost(multiThreadedSocketClient.getIP());
        messageOut.setMsg(msg);
        messageOut.setToken(TokenGenerator.getAlphaNumericString(20));
        messageOut.setDate(new Date());

        try {
            return multiThreadedSocketClient.getXmlMarshaller().getXml(messageOut);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return "ERROR";
    }
}

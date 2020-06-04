package chat.utils;

import xml.data.Message;
import xml.marshaller.XmlMarshaller;
import xml.unmarshaller.XmlUnmarshaller;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
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

    public static String initMessageOut(String msg, ServerSocket server, XmlMarshaller xmlMarshaller){
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

    public static String initMessageOut(String msg, int port, String host, XmlMarshaller xmlMarshaller){
        Message messageOut = new Message();
        messageOut.setPort(port);
        messageOut.setHost(host);
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

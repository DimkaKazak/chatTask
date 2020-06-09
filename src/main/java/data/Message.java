package data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@JsonAutoDetect
@XmlType(name = "message")
@XmlRootElement
public class Message implements Comparable<Message>{

    public Message(){}

    public Message(String msg){
        this.msg = msg;
        this.date = new Date();
    }

    public Message(String host, int port, String token, String msg, Date date){
        this.host = host;
        this.port = port;
        this.token = token;
        this.msg = msg;
        this.date = date;
    }

    private String host;
    private int port;
    private String token;
    private String msg;
    private Date date;

    @Override
    public int compareTo(Message message) {
        if (getDate() == null || message.getDate() == null)
            return 0;
        return getDate().compareTo(message.getDate());
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @XmlElement(name = "host")
    public String getHost() {
        return host;
    }

    @XmlElement(name = "port")
    public int getPort() {
        return port;
    }

    @XmlElement(name = "date")
    public Date getDate() {
        return date;
    }

    @XmlElement(name = "msg")
    public String getMsg() {
        return msg;
    }

    @XmlElement(name = "token")
    public String getToken() {
        return token;
    }


}

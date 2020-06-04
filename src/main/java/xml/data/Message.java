package xml.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlType(name = "message")
@XmlRootElement
public class Message {

    public Message(){}

    private String host;
    private int port;
    private String token;
    private String msg;
    private Date date;

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

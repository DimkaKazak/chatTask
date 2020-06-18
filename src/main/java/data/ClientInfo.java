package data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@JsonAutoDetect
@XmlType(name = "client")
@XmlRootElement
public class ClientInfo {

    private int id;
    private String name;
    private String password;

    public ClientInfo(){}

    public ClientInfo(int id){
        this.id = id;
    }

    @XmlElement(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

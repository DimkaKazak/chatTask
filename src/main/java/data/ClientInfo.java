package data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@JsonAutoDetect
@XmlType(name = "client")
@XmlRootElement
public class ClientInfo {

    private String id;
    private String name;
    private String password;

    public ClientInfo(){}

    public ClientInfo(String id){
        this.id = id;
    }

    public ClientInfo(String id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;

    }

    @XmlElement(name = "id")
    public String getId() {
        return id;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    @XmlElement(name = "password")
    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

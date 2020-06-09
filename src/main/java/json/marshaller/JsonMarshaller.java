package json.marshaller;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.Message;
import json.base.JsonContext;

import java.io.IOException;
import java.io.StringWriter;

public class JsonMarshaller extends JsonContext {

    public static void main(String[] args) {
        Message msg = new Message();
        msg.setMsg("HELLO");
        msg.setPort(1111);


        try {
            System.out.println(new JsonMarshaller().getJson(msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonMarshaller(){
        super(new ObjectMapper());
    }

    public JsonMarshaller(ObjectMapper objectMapper){
        super(objectMapper);
    }

    public String getJson(Object obj) throws IOException {

        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, obj);
        return writer.toString();

    }

}

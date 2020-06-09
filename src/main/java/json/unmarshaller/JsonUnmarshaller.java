package json.unmarshaller;

import com.fasterxml.jackson.databind.ObjectMapper;
import json.base.JsonContext;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class JsonUnmarshaller extends JsonContext {

    public JsonUnmarshaller(){
        super(new ObjectMapper());
    }

    public JsonUnmarshaller(ObjectMapper objectMapper){
        super(objectMapper);
    }

    public Object getUnmarshalledJson(String json) throws IOException {

        StringReader reader = new StringReader(json);
        return mapper.readValue(reader, Object.class);

    }

}

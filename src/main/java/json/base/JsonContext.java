package json.base;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonContext {

    protected ObjectMapper mapper;

    protected JsonContext(){
        this.mapper = new ObjectMapper();
    }

    protected JsonContext(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }
}

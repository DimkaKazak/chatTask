package xml.base;

import javax.xml.bind.JAXBContext;

public class XmlContext {

    protected JAXBContext context;

    protected XmlContext(JAXBContext context){
        this.context = context;
    }

    public JAXBContext getContext() {
        return context;
    }

    public void setContext(JAXBContext context) {
        this.context = context;
    }
}

package xml.unmarshaller;

import xml.base.XmlContext;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public class XmlUnmarshaller extends XmlContext {

    private Unmarshaller unmarshaller;

    public XmlUnmarshaller(JAXBContext context) throws JAXBException {
        super(context);
        this.unmarshaller = createUnmarshaller();
    }

    private Unmarshaller createUnmarshaller() throws JAXBException {
        return context.createUnmarshaller();
    }

    public Object getUnmarshalledXml(String xmlData) throws JAXBException {
        StringReader reader = new StringReader(xmlData);

        return unmarshaller.unmarshal(reader);
    }

    public Unmarshaller getUnmarshaller() {
        return unmarshaller;
    }

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }
}

package sistema.data;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class XmlPersister {
    private String path;
    private static XmlPersister theInstance;

    public static XmlPersister instance(){
        if (theInstance == null) {
            theInstance = new XmlPersister("data.xml");
        }
        return theInstance;
    }

    public XmlPersister(String p) {
        path = p;
    }

    public data load() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(data.class);
        FileInputStream is = new FileInputStream(path);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        data result = (data) unmarshaller.unmarshal(is);
        is.close();
        return result;
    }

    public void store(data d) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(data.class);
        FileOutputStream os = new FileOutputStream(path);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(d, os);
        os.flush();
        os.close();
    }
}
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.Queue;

public class Dispatched {
    private int dispatchedId;
    private int targetId;


    public Dispatched(int id, File nameFile){
        this.dispatchedId = id;
        this.targetId = getTargetId(nameFile);
    }

    private int getTargetId(File namefileXml){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(namefileXml);
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            return Integer.parseInt(xPath.evaluate("/message/target/@id",document));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getDispatchedId() {
        return dispatchedId;
    }

    public int getTargetId() {
        return targetId;
    }
}

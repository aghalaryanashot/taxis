import com.sun.jmx.remote.internal.ArrayQueue;
import com.sun.org.apache.xpath.internal.jaxp.XPathFactoryImpl;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        String reposInMass = args[0];
        int quantityTaxi = Integer.parseInt(args[1]);
        String reposOutXml = args[2];
        int quantityDispatch = Integer.parseInt(args[3]);

        Client client = new Client(reposInMass);
        Logic logic = new Logic(client,quantityTaxi,reposOutXml,quantityDispatch);
        logic.run();
    }
}



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
        int size=0;
        File files = new File("C:\\Users\\Ps\\Desktop\\t");
        if (files.listFiles()!=null) {
            size = files.listFiles().length;
        }
        BlockingQueue stecFila = new ArrayBlockingQueue<File>(size);
        stecFila.addAll(Arrays.asList(files.listFiles()));

        for (int i=1;i<=100;i++){
            Thread threadTax = null;
            Thread threadDis = new Dispatched(i, (File)stecFila.poll());
            threadDis.start();
            if(i<11){
                threadTax = new Taxi(i,"C:\\Users\\Ps\\Desktop",threadDis.);
                threadTax.start();
            }
            threadTax.se

        }


    }

}



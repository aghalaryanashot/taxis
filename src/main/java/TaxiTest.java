package main.java;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import sun.java2d.pipe.OutlineTextRenderer;
import sun.misc.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.junit.Assert.*;

public class TaxiTest {

    @ClassRule
    public static final TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void saveXml() {
        Dispatched dispatched = new Dispatched(1,2);
        Taxi taxi = new Taxi();
        taxi.saveXml(folder.getRoot().getAbsolutePath(),dispatched);
        File file = folder.getRoot().getAbsoluteFile().listFiles()[0].listFiles()[0];
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            String xml = new String(bytes, StandardCharsets.UTF_8);
            assertEquals(xml,"<?xml version='1.0' encoding='UTF-8'?>\n" +
                    "<message>\n" +
                    "<dispatched id=\"1\"/>\n" +
                    "<target id=\"2\"/>\n" +
                    "<sometags>\n" +
                    "<data> </data>\n" +
                    "<data> </data>\n" +
                    "<data> </data>\n" +
                    "</sometags>\n" +
                    "</message>");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
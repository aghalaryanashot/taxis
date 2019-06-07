package main.java;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class DispatchedTest {

    @ClassRule
    public static final TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void getTargetIdOfXml() {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(folder.newFile("test.xml"), true),
                StandardCharsets.UTF_8)) {
            writer.write("<?xml version='1.0' encoding='UTF-8'?>\n" +
                    "<message>\n" +
                    "<target id=\"" + 3 + "\"/>\n" +
                    "<sometags>\n" +
                    "<data> </data>\n" +
                    "<data> </data>\n" +
                    "<data> </data>\n" +
                    "</sometags>\n" +
                    "</message>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Dispatched dispatched = new Dispatched(1,folder.getRoot().listFiles()[0]);
        assertEquals(dispatched.getTargetId(),3);
    }
}
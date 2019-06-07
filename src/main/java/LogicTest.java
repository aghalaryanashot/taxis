package main.java;

import main.java.Client;
import main.java.Dispatched;
import main.java.Logic;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.assertEquals;

public class LogicTest {

    @ClassRule
    public static final TemporaryFolder folderIn = new TemporaryFolder();
    @ClassRule
    public static final TemporaryFolder folderOut = new TemporaryFolder();
    private int quantityTaxi = 10;
    private int quantityDispatch = 100;
    private Logic logic;
    private static boolean setUpIsDone = true;

    @Before
    public void addObjec() {
        if(setUpIsDone){
            generatyaXml();
            setUpIsDone=false;
        }
        Client client = new Client(folderIn.getRoot().getAbsolutePath());
        logic = new Logic(client,quantityTaxi,folderOut.getRoot().getAbsolutePath(),quantityDispatch);
    }

    @Test
    public void fileLinkedBlockingQueue(){
        File file = new File("test");
        assertEquals(logic.fileLinkedBlockingQueue(folderIn.getRoot()).size(),1000);
        assertEquals(logic.fileLinkedBlockingQueue(file).poll().toString(),"test");
    }

    @Test
    public void createListQueueIsSizeQuantityTaxiTest() {
        assertEquals(logic.getListQueue(quantityTaxi).size(), quantityTaxi);
    }

    @Test
    public void equalesTargrtIdOfFillListQueue() {
        ArrayList<BlockingQueue> listQueue =logic.fillListQueue(logic.getListQueue(quantityTaxi),quantityDispatch,
                logic.fileLinkedBlockingQueue(folderIn.getRoot()));
        assertEquals(((Dispatched) listQueue.get(0).poll()).getTargetId(),1);
        assertEquals(((Dispatched) listQueue.get(1).poll()).getTargetId(),2);
        assertEquals(((Dispatched) listQueue.get(2).poll()).getTargetId(),3);
        assertEquals(((Dispatched) listQueue.get(3).poll()).getTargetId(),4);
        assertEquals(((Dispatched) listQueue.get(4).poll()).getTargetId(),5);
        assertEquals(((Dispatched) listQueue.get(5).poll()).getTargetId(),6);
        assertEquals(((Dispatched) listQueue.get(6).poll()).getTargetId(),7);
        assertEquals(((Dispatched) listQueue.get(7).poll()).getTargetId(),8);
        assertEquals(((Dispatched) listQueue.get(8).poll()).getTargetId(),9);
        assertEquals(((Dispatched) listQueue.get(9).poll()).getTargetId(),10);
    }

    @Test
    public void maxValFileListQueue() {
        ArrayList<BlockingQueue> listQueue = new ArrayList<>();
        LinkedBlockingQueue queue1 = new LinkedBlockingQueue();
        queue1.add("Test1-1");
        queue1.add("Test1-2");
        queue1.add("Test1-3");
        queue1.add("Test1-4");
        queue1.add("Test1-5");
        queue1.add("Test1-6");
        LinkedBlockingQueue queue2 = new LinkedBlockingQueue();
        queue2.add("Test2-1");
        queue2.add("Test2-2");
        queue2.add("Test2-3");
        LinkedBlockingQueue queue3 = new LinkedBlockingQueue();
        queue3.add("Test3-1");
        queue3.add("Test3-2");
        queue3.add("Test3-3");
        queue3.add("Test3-4");
        listQueue.add(queue3);
        listQueue.add(queue2);
        listQueue.add(queue1);
      assertEquals(logic.maxValListQueue(listQueue),6);
    }

    private void generatyaXml() {
        for (int i = 1; i <= 1000; i++) {
            try (Writer writer = new OutputStreamWriter(new FileOutputStream(folderIn.getRoot().getAbsolutePath()+
                    "\\generationXML-" + i + ".xml", true), StandardCharsets.UTF_8)) {
                writer.write("<?xml version='1.0' encoding='UTF-8'?>\n" +
                        "<message>\n" +
                        "<target id=\"" + rnd(1, 10) + "\"/>\n" +
                        "<sometags>\n" +
                        "<data> </data>\n" +
                        "<data> </data>\n" +
                        "<data> </data>\n" +
                        "</sometags>\n" +
                        "</message>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
}
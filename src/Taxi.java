import java.io.*;
import java.nio.charset.StandardCharsets;

public class Taxi extends Thread {
    private int taxiId;

    public Taxi(int id, String rep, Dispatched dispatched){
        this.taxiId = id;
        saveXml(rep, dispatched);
    }

    public void saveXml(String rep, Dispatched dispatched) {
        File file = new File(rep+"\\"+dispatched.getTargetId());
        boolean mkdir = file.mkdir();
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath()+"\\doneXML-" +
                System.currentTimeMillis() + ".xml", true), StandardCharsets.UTF_8)) {
            writer.write("<?xml version='1.0' encoding='UTF-8'?>\n" +
                    "<message>\n" +
                    "<dispatched id=\"" + dispatched.getDispatchedId() + "\"/>\n" +
                    "<target id=\"" + dispatched.getTargetId() + "\"/>\n" +
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

    public int getTaxiId() {
        return taxiId;
    }

}

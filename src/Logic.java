import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Logic {
    private String reposOutXml;
    private int quantityDispatch;
    private int quantityTaxi;
    private ArrayList<BlockingQueue> listQueue;
    private BlockingQueue stecNameFila = new LinkedBlockingQueue<File>();

    public Logic(Client mess,int quantityTaxi,String reposOutXml,int quantityDispatch){
        this.reposOutXml = reposOutXml;
        fileLinkedBlockingQueue(mess.getMesseng());
        this.quantityTaxi=quantityTaxi;
        this.quantityDispatch=quantityDispatch;
        this.listQueue = fillListQueue(getListQueue());
    }

    private void fileLinkedBlockingQueue(File file) {
        if(file.listFiles()!=null){
            stecNameFila.addAll(Arrays.asList(file.listFiles()));
        } else stecNameFila.add(file);
    }

    public void run(){
        createThredTaxi(maxVallistQueue(listQueue),listQueue);
    }


    private ArrayList<BlockingQueue> getListQueue() {
        ArrayList<BlockingQueue> listQueue = new ArrayList<>();
        for (int i = 0; i < quantityTaxi; i++) {
            listQueue.add(new LinkedBlockingQueue<Dispatched>());
        }
        return listQueue;
    }
    private ArrayList<BlockingQueue> fillListQueue(ArrayList<BlockingQueue> listQueue) {
        while (stecNameFila.size()>0) {
            for (int j = 1; j <= quantityDispatch; j++) {
                Dispatched dispatched = new Dispatched(j, (File) stecNameFila.poll());
                listQueue.get(dispatched.getTargetId() - 1).add(dispatched);
                if(stecNameFila.size()<1){
                    break;
                }
            }
        }
        return listQueue;
    }

    private int maxVallistQueue(ArrayList<BlockingQueue> listQueue) {
        int val = 0;
        for (BlockingQueue queue : listQueue) {
            if (queue.size() > val) {
                val = queue.size();
            }
        }
        return val;
    }

    private void createThredTaxi(int val,ArrayList<BlockingQueue> listQueue) {
        while (val > 0) {
            try {
                for (int i = 0; i < quantityTaxi; i++) {
                    if (listQueue.get(i).size() > 0) {
                        new Taxi(i, reposOutXml, (Dispatched) listQueue.get(i).poll()).start();
                    }
                }
                Taxi.taxiSleep();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

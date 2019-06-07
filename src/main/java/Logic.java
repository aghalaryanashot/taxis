package main.java;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Logic {
    private String reposOutXml;
    private int quantityTaxi;
    private ArrayList<BlockingQueue> listQueue;
    private LinkedBlockingQueue stecNameFila;

    public Logic(Client mess, int quantityTaxi, String reposOutXml, int quantityDispatch){
        this.reposOutXml = reposOutXml;
        this.stecNameFila = fileLinkedBlockingQueue(mess.getMesseng());
        this.quantityTaxi = quantityTaxi;
        this.listQueue = fillListQueue(getListQueue(quantityTaxi),quantityDispatch,stecNameFila);
    }

    public LinkedBlockingQueue fileLinkedBlockingQueue(File file) {
        LinkedBlockingQueue filasName = new LinkedBlockingQueue<File>();
        if(file.listFiles()!=null){
            filasName.addAll(Arrays.asList(file.listFiles()));
            return filasName;
        } else filasName.add(file);
        return filasName;
    }

    public void run(){
        createThredTaxi(maxValListQueue(listQueue),listQueue,quantityTaxi);
    }

    public ArrayList<BlockingQueue> getListQueue(int quantityTaxi) {
        ArrayList<BlockingQueue> listQueue = new ArrayList<>();
        for (int i = 0; i < quantityTaxi; i++) {
            listQueue.add(new LinkedBlockingQueue<Dispatched>());
        }
        return listQueue;
    }
    public ArrayList<BlockingQueue> fillListQueue(ArrayList<BlockingQueue> listQueue,
                                                  int quantityDispatch,LinkedBlockingQueue stecNameFila ) {
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

    public int maxValListQueue(ArrayList<BlockingQueue> listQueue) {
        int val = 0;
        for (BlockingQueue queue : listQueue) {
            if (queue.size() > val) {
                val = queue.size();
            }
        }
        return val;
    }

    private void createThredTaxi(int val,ArrayList<BlockingQueue> listQueue,int quantityTaxi ) {
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
            val--;
        }

    }
}

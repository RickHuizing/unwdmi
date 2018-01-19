package threadPooledServer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Lenovo T420 on 17-1-2018.
 * writes a file for a socket
 */
class FileCreator {
    private Map<Integer, MessageContainer> messageMap = new HashMap<>();
    private ConnectionManager connectionManager;

    FileCreator(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    void addMessageMap(Map<Integer, MessageContainer> messageMap){
        this.messageMap = messageMap;
        Runnable task = this::writefile;
        connectionManager.getExecutor().execute(task);
        /*Thread thread = new Thread(task);
        thread.start();*/
    }

    //TODO get the writeline outa here initialising buffered writer takes 100 times longer than writing 60 lines(30 mssgs)
    private void writefile() {
        synchronized (messageMap) {
            messageMap.forEach((id, messageContainer)->{
                //long startTime = System.nanoTime();
                BufferedWriter bw = null;
                try {
                    //Specify the file name and path
                    File txt = new File("C:/temp/bla/" + id + ".txt");

                    if (!txt.exists()) {
                        txt.createNewFile();
                    }

                    java.io.FileWriter fw = new java.io.FileWriter(txt, true);
                    bw = new BufferedWriter(fw);
                    long startTime2=System.nanoTime();
                    for(String mssg : messageContainer.getMessages()) {
                        bw.write(mssg);
                        bw.newLine();
                    }
                    //long estimatedTime2 = System.nanoTime() - startTime2;
                    //System.out.println("took "+estimatedTime2+" to write lines");
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } finally {
                    try {
                        if (bw != null)
                            bw.close();
                    } catch (Exception ex) {
                        System.out.println("Error in closing the BufferedWriter" + ex);
                    }
                }
                //long estimatedTime = System.nanoTime() - startTime;
                //System.out.println("took "+estimatedTime+ " to write 1 file");
            });
        }
    }
}

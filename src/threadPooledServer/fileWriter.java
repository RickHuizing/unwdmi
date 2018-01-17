package threadPooledServer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by Lenovo T420 on 17-1-2018.
 * writes a file for a socket
 */
class fileWriter {
    private List<Message> messages = new ArrayList<>();
    private Random random = new Random();
    private int id = 0;
    fileWriter(){
    }
    void addMessage(Message message) {
        synchronized (messages) {
            messages.add(message);
            //System.out.println("added mssg");
            if (id == 0) {
                id = message.getActiveStation();
            }
            if (messages.size() >= 100) {
                Runnable task = this::writefile;
                Thread thread = new Thread(task);
                thread.start();
            }
        }
    }

    private void writefile() {
        synchronized (messages) {
            BufferedWriter bw = null;
            try {
                //Specify the file name and path
                File txt = new File("C:/temp/"+this.id+".txt");

                if (!txt.exists()) {
                    txt.createNewFile();
                }

                FileWriter fw = new FileWriter(txt, true);
                bw = new BufferedWriter(fw);
                for (Message message : messages) {
                    bw.write(message.getMessage());
                    bw.newLine();
                }
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
        }
    }
}

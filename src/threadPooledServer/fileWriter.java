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
    private List<String> messages = new ArrayList<>();
    private Random random = new Random();
    int id = random.nextInt(1000000);
    //BufferedWriter bufferedWriter = new BufferedWriter;
    fileWriter(){
    }
    void addMessage(String message) {
        synchronized (messages) {
            messages.add(message);
            if (message.contains("</WEATHERDATA>")) {
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
                String mycontent = "This String would be written" +
                        " to the specified File";
                //Specify the file name and path here
                File txt = new File("C:/temp/myfile"+id+".txt");

                if (!txt.exists()) {
                    txt.createNewFile();
                }

                FileWriter fw = new FileWriter(txt);
                bw = new BufferedWriter(fw);
                for (String message : messages) {
                    bw.write(message);
                    bw.newLine();
                }
                //System.out.println("File written Successfully");

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

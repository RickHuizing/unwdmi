package threadPooledServer;

import resources.Constants;
import resources.ExecutorServices;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Lenovo T420 on 17-1-2018.
 * writes a file for a socket
 */
class FileCreator {
    private Map<Integer, MessageContainer> messageMap = new HashMap<>();
    private Map<Integer, BufferedMessageWriter> writerList = new HashMap<>();

    private Boolean writerListInit = false;

    FileCreator() {}

    void writeMessageMap(Map<Integer, MessageContainer> messageMap) {
        this.messageMap = messageMap;
        Runnable task = this::writeFiles;
        ExecutorServices.WRITER_EXECUTOR.execute(task);
    }


    private void writeFiles() {
        synchronized (this) {
            if (!writerListInit) {
                writerListInit();
            }
            messageMap.forEach((id, messageContainer) -> {
                try {
                    BufferedMessageWriter bw = writerList.get(id);
                    for (String mssg : messageContainer.getMessages()) {
                        bw.write(mssg);
                        bw.newLine();
                    }
                    bw.flush();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            });
        }
    }
    //creates a new file and initialises new BufferedMessageWriters for that file
    void writerListInit() {
        synchronized (this) {
            if (writerListInit) {
                killFileWriters();
            }
            writerList.clear();
            messageMap.forEach((id, messageContainer) -> {
                //long startTime = System.nanoTime();
                BufferedMessageWriter bw;
                try {
                    //Specify the file name and path
                    File txt = createFileFromMessage(messageContainer);

                    java.io.FileWriter fw = new java.io.FileWriter(txt, true);
                    bw = new BufferedMessageWriter(fw, id);
                    writerList.put(id, bw);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            });
            writerListInit = true;
        }
    }
    void killFileWriters() {
        writerList.forEach((id, bw)->{
            try {
                if (bw != null)
                    bw.close();
            } catch (Exception ex) {
                System.out.println("Error in closing the BufferedWriter" + ex);
            }
         });
    }
    File createFileFromMessage(MessageContainer messageContainer){
        int id = messageContainer.getStation();
        String path = Constants.FileSettings.PATH + id;
        File directory = new File(path);
        directory.mkdir();
        path = path +"/" + messageContainer.getMsgDate();
        directory = new File(path);
        directory.mkdir();
        String fileName = "/" + messageContainer.getMsgTime() + ".txt";
        File txt = new File(path + fileName);
        try {
            if (!txt.exists()) {
                txt.createNewFile();
            }
        }catch(IOException e){
            System.out.println(e.getMessage()+ "hoi");
        }
        return txt;
    }

    public void setWriterListInit(Boolean writerListInit) {
        this.writerListInit = writerListInit;
    }
}

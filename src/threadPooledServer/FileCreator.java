package threadPooledServer;

import resources.Settings;
import resources.ExecutorServices;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo T420 on 17-1-2018.
 * writes a file for a socket
 */
class FileCreator {
    static long runtime = 0;

    FileCreator(Map<Integer, MessageContainer> messageMap, int aantal) {
        List messages = new ArrayList();
        /*
        messageMap.forEach((id, messageContainer)->{
            messages.add(id.toString());
            messages.add(" ");
        });*/
        messageMap.forEach((id, messageContainer) -> {
            messages.addAll(messageContainer.getMessages());
        });
        String path = createPathFromMessageMap(messageMap);
        BinaryFileWriter bfw = new BinaryFileWriter(messages, path, aantal);
        ExecutorServices.WRITER_EXECUTOR.execute(bfw);
    }

    String createPathFromMessageMap(Map<Integer, MessageContainer> messageMap){
        //long startTime = System.nanoTime();
        Object[] stations = messageMap.keySet().toArray();
        MessageContainer messageContainer = messageMap.get(stations[0]);
        int id = messageContainer.getStation();

        String path = Settings.FileSettings.PATH + id;

        File directory = new File(path);
        directory.mkdir();
        path +="/" + messageContainer.getMsgDate();
        directory = new File(path);
        directory.mkdir();
        String fileName = "/" + messageContainer.getMsgTime() + ".txt";
        return path+fileName;
    }
    String createPathFromMessage(MessageContainer messageContainer){
        //long startTime = System.nanoTime();
        int id = messageContainer.getStation();
        String path = Settings.FileSettings.PATH + id;
        File directory = new File(path);
        if (!directory.exists()) directory.mkdir();
        path +="/" + messageContainer.getMsgDate();
        directory = new File(path);
        if (!directory.exists()) directory.mkdir();
        String fileName = "/" + messageContainer.getMsgTime() + ".txt";
        //runtime += System.nanoTime()-startTime;
        //System.out.println(runtime);
        return path+fileName;
    }
}

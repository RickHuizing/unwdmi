package threadPooledServer;

import resources.Constants;
import resources.ExecutorServices;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo T420 on 17-1-2018.
 * writes a file for a socket
 */
class FileCreator {
    FileCreator(Map<Integer, MessageContainer> messageMap, int aantal) {
        messageMap.forEach((id, messageContainer) -> {
            List messages = messageContainer.getMessages();
            String path = createPathFromMessage(messageContainer);
            BinaryFileWriter bfw = new BinaryFileWriter(messages, path, aantal);
            ExecutorServices.WRITER_EXECUTOR.execute(bfw);
        });
    }

    String createPathFromMessage(MessageContainer messageContainer){
        int id = messageContainer.getStation();
        String path = Constants.FileSettings.PATH + id;
        File directory = new File(path);
        directory.mkdir();
        path +="/" + messageContainer.getMsgDate();
        directory = new File(path);
        directory.mkdir();
        String fileName = "/" + messageContainer.getMsgTime() + ".txt";
        return path+fileName;
    }
}

package threadPooledServer;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Lenovo T420 on 19-1-2018.
 */
public class BinaryFileWriter implements Runnable{
    private List<String> content;
    private String filePath;

    BinaryFileWriter(List<String> content, String filePath) {
        this.content = content;
        this.filePath = filePath;
    }

    /**
     * Write raw data to file using BufferedOutputStream
     */
    public void run() {
        Path fileP = Paths.get(filePath);

        try (BufferedOutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(fileP))) {

            for (String line : content) {
                outputStream.write(line.getBytes());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
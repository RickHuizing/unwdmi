package threadPooledServer;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Lenovo T420 on 19-1-2018.
 */
public class BinaryFileWriter implements Runnable{
    private final Integer messages;
    private List<String> content;
    private List<List<String>> contents;
    private String filePath;

    BinaryFileWriter(List<String> content, String filePath, int messages) {
        this.messages = messages;
        this.content = content;
        this.filePath = filePath;
    }

    /**
     * Write raw data to file using BufferedOutputStream
     */
    public void run() {
        Path fileP = Paths.get(filePath);

        try (BufferedOutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(fileP))) {
            String aantBericht = messages.toString();
            while(aantBericht.length()<3){aantBericht+=" ";}
            outputStream.write(aantBericht.getBytes());
            for (String line : content) {
                outputStream.write(line.getBytes());
            }
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
    }
}
package threadPooledServer;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Lenovo T420 on 17-1-2018.
 */
public class inputReader implements Runnable {
    private BufferedReader bufferedReader = null;
    private fileWriter fileWriter = new fileWriter();

    inputReader(BufferedReader bufferedReader){
        this.bufferedReader = bufferedReader;
    }

    public void run(){
        try {
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                fileWriter.addMessage(inputLine);
                if (inputLine.contains("</WEATHERDATA>")) {
                    fileWriter = new fileWriter();
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    +"xxx" + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}

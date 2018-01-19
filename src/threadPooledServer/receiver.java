package threadPooledServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class receiver implements Runnable{
    private int portNumber;
    private Socket clientSocket = null;
    private BufferedReader bufferedReader = null;
    private ExecutorService threadPool = null;

    public receiver(Socket socket, ExecutorService tp){
        //7789
        this.clientSocket = socket;
        this.portNumber = this.clientSocket.getPort();
        this.threadPool = tp;
    }

    public void run() {
        if(this.bufferedReader==null) {
            try {
                this.bufferedReader = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                System.out.println("Exception caught when trying to listen on port "
                        + portNumber + " or listening for a connection");
                System.out.println(e.getMessage());
            }
        }
        try {
            String inputLine = "";
            int x = 1;
            Boolean closed = false;
            String message = "";
            while (closed == false &&(inputLine = bufferedReader.readLine()) != null) {
                if (inputLine.contains("</WEATHERDATA>")) {
                    //closed = true;
                    message+=inputLine.trim();
                    message+="message number "+x;
                    if(x%10==0) {
                        String path = "c://temp//testFile" + x + "" + portNumber + ".txt";
                       // this.threadPool.execute(
                         //       new FileCreator(path, message));
                        message = "";
                    }
                    x++;
                } else {
                    message += inputLine.trim();
                }
                System.out.println(inputLine);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
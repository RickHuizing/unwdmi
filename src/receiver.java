import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Lenovo T420 on 4-1-2018.
 */
public class receiver {
    public void connect() {
        int portNumber = 7789;
        try (
                ServerSocket serverSocket =
                        new ServerSocket(7789);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;
            int x = 1;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains("</WEATHERDATA>")){
                    System.out.println(x);
                    x++;
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
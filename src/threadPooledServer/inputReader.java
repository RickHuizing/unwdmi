package threadPooledServer;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Lenovo T420 on 17-1-2018.
 */
public class inputReader implements Runnable {
    private BufferedReader bufferedReader = null;
    private fileWriter fileWriter = new fileWriter();
    private ArrayList<String> stations = new ArrayList<>(10);
    private String stringBuffer;
    private Message message;
    int lineCounter =-1;
    int messageCounter = 0;

    inputReader(BufferedReader bufferedReader){
        this.bufferedReader = bufferedReader;
    }

    public void run(){
        try {
            String inputLine;
            if ((inputLine = bufferedReader.readLine()) != null) {
                //System.out.println(inputLine);
                if(inputLine.contains("<MEASUREMENT>")){
                    this.message=new Message();
                    stringBuffer="";
                    lineCounter =0;
                }
                if(lineCounter >0&& lineCounter <15) {
                    substringInputline(inputLine, lineCounter);
                }

                if(inputLine.contains("</MEASUREMENT>")){
                    //System.out.println("found eo msrmnt");
                    fileWriter.addMessage(this.message);
                    stringBuffer="";
                    messageCounter++;
                    lineCounter = 15;
                    if(messageCounter>100){fileWriter = new fileWriter();messageCounter=0;}
                }
                lineCounter++;
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    +"xxx" + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    private void substringInputline(String inputLine, int x) {
        switch(x) {
            case 0:
                break;
            case 1:
                inputLine = inputLine.trim().substring(5);
                stringBuffer = inputLine.substring(0, inputLine.length() - 6);
                this.message.setActiveStation(Integer.parseInt(stringBuffer));
                break;
            case 2:
                inputLine = inputLine.trim().substring(6);
                stringBuffer = inputLine.substring(0, inputLine.length() - 7);
                this.message.setMsgDate(stringBuffer);
                break;
            case 3:
                inputLine = inputLine.trim().substring(6);
                stringBuffer = inputLine.substring(0, inputLine.length() - 7);
                this.message.setMsgTime(stringBuffer);
                break;
            case 4:
                inputLine = inputLine.trim().substring(6);
                stringBuffer = inputLine.substring(0, inputLine.length() - 7);
                break;
            case 5:
                inputLine = inputLine.trim().substring(6);
                stringBuffer = inputLine.substring(0, inputLine.length() - 7);
                break;
            case 6:
                inputLine = inputLine.trim().substring(5);
                stringBuffer = inputLine.substring(0, inputLine.length() - 6);
                break;
            case 7:
                inputLine = inputLine.trim().substring(5);
                stringBuffer = inputLine.substring(0, inputLine.length() - 6);
                break;
            case 8:
                inputLine = inputLine.trim().substring(7);
                stringBuffer = inputLine.substring(0, inputLine.length() - 8);
                break;
            case 9:
                inputLine = inputLine.trim().substring(6);
                stringBuffer = inputLine.substring(0, inputLine.length() - 7);
                break;
            case 10:
                inputLine = inputLine.trim().substring(6);
                stringBuffer = inputLine.substring(0, inputLine.length() - 7);
                break;
            case 11:
                inputLine = inputLine.trim().substring(6);
                stringBuffer = inputLine.substring(0, inputLine.length() - 7);
                break;
            case 12:
                inputLine = inputLine.trim().substring(8);
                stringBuffer = inputLine.substring(0, inputLine.length() - 9);
                break;
            case 13:
                inputLine = inputLine.trim().substring(6);
                stringBuffer = inputLine.substring(0, inputLine.length() - 7);
                break;
            case 14:
                inputLine = inputLine.trim().substring(8);
                stringBuffer = inputLine.substring(0, inputLine.length() - 9);
                break;
        }
        this.message.message+=stringBuffer+" ";
        stringBuffer="";
    }
}

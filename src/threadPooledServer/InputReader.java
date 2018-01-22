package threadPooledServer;

import resources.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


    /**
     * Created by Lenovo T420 on 17-1-2018.
     */
public class InputReader implements Runnable {
    private BufferedReader bufferedReader = null;
    private Map<Integer, MessageContainer> messages = new HashMap<>();

    private int activeStation;
    private String stringBuffer;
    private int lineCounter = -1;
    private int messageCounter = 0;
    private Boolean newFile = true;

    private int day;
    private int minute;
    private int hour;

    InputReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public void run() {
        try {
            String inputLine;
            if ((inputLine = bufferedReader.readLine()) != null) {
                //System.out.println(inputLine);
                if (inputLine.contains("<MEASUREMENT>")) {
                    stringBuffer = "";
                    lineCounter = 0;
                }
                if (lineCounter > 0 && lineCounter < 15) {
                    substringInputLine(inputLine, lineCounter);
                }

                if (inputLine.contains("</MEASUREMENT>")) {
                    stringBuffer = "";
                    lineCounter = 15;
                }
                if(inputLine.contains("</WEATHERDATA>")){
                    messageCounter++;
                    if (this.newFile) {
                        new FileCreator(this.messages, messageCounter);
                        messages = new HashMap<>();
                        this.newFile = false;
                        messageCounter=0;
                    }
                }
            }
            lineCounter++;
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + "xxx" + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    private void substringInputLine(String inputLine, int x) {
        boolean doWrite = true;
        switch (x) {
            case 0:
                break;
            case 1:
                inputLine = inputLine.trim().substring(5);
                int station = Integer.parseInt(inputLine.substring(0, inputLine.length() - 6));
                this.activeStation = station;
                if (!messages.containsKey(station)) {
                    MessageContainer messageContainer = new MessageContainer();
                    messageContainer.setStation((this.activeStation));
                    messages.put(station, messageContainer);
                } else {
                    messages.get(this.activeStation).setNewmssg(true);
                }
                doWrite = false;
                break;
            case 2:
                inputLine = inputLine.trim().substring(6);
                stringBuffer = inputLine.substring(0, inputLine.length() - 7);
                this.messages.get(this.activeStation).setMsgDate(stringBuffer);
                checkDate(stringBuffer);
                doWrite = false;
                break;
            case 3:
                inputLine = inputLine.trim().substring(6);
                stringBuffer = inputLine.substring(0, inputLine.length() - 7);
                this.messages.get(this.activeStation).setMsgTime(stringBuffer);
                stringBuffer = stringBuffer.substring(0, 5);
                checkTime(stringBuffer);
                doWrite = false;
                break;
            case 4:
                inputLine = inputLine.trim().substring(6);
                stringBuffer = inputLine.substring(0, inputLine.length() - 7);
                break;
            case 5:
                this.messages.get(this.activeStation).setNewmssg(false);
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
        if (doWrite) {
            if (stringBuffer.isEmpty()) {
                stringBuffer = "0.00";
            }
            this.messages.get(this.activeStation).addMessage(stringBuffer + " ");
        }
        stringBuffer = "";
    }

    private void checkDate(String date) {
        int day = Integer.parseInt(date.substring(5, 7));
        if (this.day != day) {
            newFile = true;
            this.day = day;
        }
    }

    private void checkTime(String time) {
        int minute = Integer.parseInt(time.substring(3, 5));
        int hour = Integer.parseInt(time.substring(0, 2));
        if (minute - this.minute > Constants.FileSettings.FILE_INTERVAL - 1 || hour != this.hour) {
            this.newFile = true;
            this.minute = minute;
            this.hour = hour;
        }
    }
}


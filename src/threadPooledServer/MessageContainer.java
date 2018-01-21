package threadPooledServer;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo T420 on 17-1-2018.
 */
class MessageContainer {
    private int station;
    private String msgDate;
    private String msgTime;
    private Boolean newmssg =true;

    private List<String> messages = new ArrayList<String>();

    MessageContainer(){}

    void addMessage(String message){
        if(!newmssg){
            int size = messages.size()-1;
            String buf = messages.get(size);
            buf +=message;
            messages.set(size, buf);
        }else {
            messages.add(message);
        }
    }
    void setNewmssg(Boolean newmssg) {
        this.newmssg = newmssg;
    }

    List<String> getMessages() {
        return messages;
    }

    void setStation(int station) {
        this.station = station;
    }

    int getStation() {
        return station;
    }

    void setMsgDate(String msgDate) {
        this.msgDate = msgDate;
    }
    String getMsgDate(){return this.msgDate;}

    void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
        //this.msgTime = this.msgTime.substring(0,5);
        this.msgTime = msgTime.replaceAll(":", "-");
        //System.out.println(Time.valueOf(this.msgTime));
    }
    String getMsgTime(){return this.msgTime;}
}

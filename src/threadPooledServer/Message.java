package threadPooledServer;

/**
 * Created by Lenovo T420 on 17-1-2018.
 */
public class Message {
    private int activeStation;
    private String msgDate;
    private String msgTime;


    String message = "";

    Message(){}

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setActiveStation(int activeStation) {
        this.activeStation = activeStation;
    }

    public int getActiveStation() {
        return activeStation;
    }

    public void setMsgDate(String msgDate) {
        this.msgDate = msgDate;
    }

    public String getMsgDate() {
        return msgDate;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    public String getMsgTime() {
        return msgTime;
    }
}

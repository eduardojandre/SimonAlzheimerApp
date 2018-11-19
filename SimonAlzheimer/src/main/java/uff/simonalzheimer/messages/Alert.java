package uff.simonalzheimer.messages;

import java.io.Serializable;

public class Alert implements Serializable{

    private String timeStamp;
    private String message;

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Alert(String timeStamp, String message) {
        this.timeStamp = timeStamp;
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}

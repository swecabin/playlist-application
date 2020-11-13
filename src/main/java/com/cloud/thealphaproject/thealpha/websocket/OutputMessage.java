package com.cloud.thealphaproject.thealpha.websocket;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OutputMessage {

    private String text;
    private String time;

    public OutputMessage(final String text) {

        this.text = text;
        this.time = new SimpleDateFormat("HH:mm").format(new Date());
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }
}
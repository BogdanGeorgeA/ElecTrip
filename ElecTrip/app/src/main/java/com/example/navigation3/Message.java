package com.example.navigation3;

public class Message {
    private CharSequence text;
    private long timestamp;
    private CharSequence sender;

    public Message(CharSequence text, CharSequence sender) {
        this.text = text;
        this.sender = sender;
        timestamp = System.currentTimeMillis();
    }

    public CharSequence getText() {
        return text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public CharSequence getSender() {
        return sender;
    }

}
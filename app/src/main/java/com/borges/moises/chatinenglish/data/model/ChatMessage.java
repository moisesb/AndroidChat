package com.borges.moises.chatinenglish.data.model;

import java.util.Date;

/**
 * Created by Moisés on 09/07/2016.
 */

public class ChatMessage {
    private long id;
    private final String content;
    private final String receiver;
    private final Date time;
    private final long conversationId;
    private Status status;

    public ChatMessage(String content, String receiver, Date time, long conversationId) {
        this.content = content;
        this.receiver = receiver;
        this.time = time;
        this.conversationId = conversationId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public String getReceiver() {
        return receiver;
    }

    public Date getTime() {
        return time;
    }

    public long getConversationId() {
        return conversationId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

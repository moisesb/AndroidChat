package com.borges.moises.chatinenglish.events;

import com.borges.moises.chatinenglish.data.model.ChatMessage;

import java.util.Date;

/**
 * Created by Moisés on 09/07/2016.
 */

public class IncomingMessageEvent {
    private final String mSender;
    private final String mMessage;
    private ChatMessage mChatMessage;
    private final long mDate;

    public IncomingMessageEvent(String sender, String message, long date) {
        mSender = sender;
        mMessage = message;
        mDate = date;
    }

    public String sender() {
        return mSender;
    }

    public String message() {
        return mMessage;
    }

    public ChatMessage chatMessage() {
        return mChatMessage;
    }

    public Date date() {
        final Date date = new Date();
        date.setTime(mDate);
        return date;
    }
}

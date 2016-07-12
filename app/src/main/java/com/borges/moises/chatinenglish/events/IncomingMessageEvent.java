package com.borges.moises.chatinenglish.events;

import com.borges.moises.chatinenglish.data.model.ChatMessage;

import java.util.Date;

/**
 * Created by Moisés on 09/07/2016.
 */

public class IncomingMessageEvent {
    private ChatMessage mChatMessage;

    public IncomingMessageEvent(ChatMessage chatMessage) {
        mChatMessage = chatMessage;
    }

    public ChatMessage chatMessage() {
        return mChatMessage;
    }
}

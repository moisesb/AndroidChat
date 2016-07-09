package com.borges.moises.chatinenglish.chat;

import android.support.annotation.NonNull;

import com.borges.moises.chatinenglish.data.model.ChatMessage;
import com.borges.moises.chatinenglish.data.model.Status;
import com.borges.moises.chatinenglish.data.model.User;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;

import java.util.Date;

import javax.inject.Inject;

/**
 * Created by Moisés on 03/07/2016.
 */

public class ChatModel {

    private final ChatManager mChatManager;
    private Chat mChat;

    @Inject
    public ChatModel(ChatManager chatManager) {
        mChatManager = chatManager;
    }

    public void sendMessage(String content, User user, SendMessageCallback callback) {
        createChat(user);
        ChatMessage chatMessage = createChatMessage(content, user);
        storeMessage(chatMessage);
        callback.onStore(chatMessage);
        try {
            mChat.sendMessage(content);
            chatMessage.setStatus(Status.SENT);
            callback.onSuccess(chatMessage);
        } catch (SmackException.NotConnectedException e) {
            chatMessage.setStatus(Status.NOT_SEND);
            callback.onError();
        }
    }

    private void storeMessage(ChatMessage chatMessage) {

    }

    @NonNull
    private ChatMessage createChatMessage(String content, User user) {
        Date currentTime = new Date();
        return new ChatMessage(content,user.getUserName(),currentTime, 1);
    }

    private void createChat(User user) {
        if (mChat == null) {
            mChat = mChatManager.createChat(user.getUserName());
        }
    }

    public boolean isMessageValid(String content) {
        return content != null && !content.trim().isEmpty();
    }

    public interface SendMessageCallback {
        void onStore(ChatMessage chatMessage);
        void onSuccess(ChatMessage chatMessage);
        void onError();
    }
}

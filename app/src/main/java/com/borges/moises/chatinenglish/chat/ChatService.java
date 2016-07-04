package com.borges.moises.chatinenglish.chat;

import android.util.Log;

import com.borges.moises.chatinenglish.data.model.Contact;
import com.borges.moises.chatinenglish.mvp.Callback;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

import javax.inject.Inject;

/**
 * Created by Moisés on 03/07/2016.
 */

public class ChatService {

    private final ChatManager mChatManager;
    private Chat mChat;
    @Inject
    public ChatService(ChatManager chatManager) {
        mChatManager = chatManager;
    }

    public void sendMessage(String message, Contact contact, Callback<Void> callback) {
        createChat(contact);
        try {
            mChat.sendMessage(message);
            callback.onSuccess(null);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
            callback.onError();
        }
    }

    private void createChat(Contact contact) {
        if (mChat == null) {
            mChat = mChatManager.createChat(contact.getUserName(), new ChatMessageListener() {
                @Override
                public void processMessage(Chat chat, Message message) {
                    Log.d("ChatApp", message.getBody());
                }
            });
        }
    }
}

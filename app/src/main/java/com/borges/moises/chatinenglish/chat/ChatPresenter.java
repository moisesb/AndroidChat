package com.borges.moises.chatinenglish.chat;

import com.borges.moises.chatinenglish.data.model.Contact;
import com.borges.moises.chatinenglish.mvp.Callback;
import com.borges.moises.chatinenglish.mvp.PresenterMvp;

import javax.inject.Inject;

/**
 * Created by Moisés on 03/07/2016.
 */

public class ChatPresenter implements PresenterMvp<ChatView> {
    private final ChatService mChatService;
    private ChatView mChatView;

    @Inject
    public ChatPresenter(ChatService chatService) {
        mChatService = chatService;
    }

    public void sendMessage(final String message, Contact contact) {
        mChatService.sendMessage(message, contact, new Callback<Void>() {
            @Override
            public void onSuccess(Void data) {
                mChatView.cleanMessageField();
                mChatView.showMessage(message);
            }

            @Override
            public void onError() {
                mChatView.showMessageNotSentError();
            }
        });
    }

    @Override
    public void bindView(ChatView viewMvp) {
        mChatView = viewMvp;
    }

    @Override
    public void unbindView() {
        mChatView = null;
    }
}

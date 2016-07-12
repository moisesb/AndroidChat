package com.borges.moises.chatinenglish.chat;

import com.borges.moises.chatinenglish.data.model.ChatMessage;
import com.borges.moises.chatinenglish.data.model.User;
import com.borges.moises.chatinenglish.events.IncomingMessageEvent;
import com.borges.moises.chatinenglish.mvp.PresenterMvp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

/**
 * Created by Moisés on 03/07/2016.
 */
public class ChatPresenter implements PresenterMvp<ChatView> {
    private final ChatModel mChatModel;
    private ChatView mChatView;

    @Inject
    public ChatPresenter(ChatModel chatModel) {
        mChatModel = chatModel;
    }

    public void sendMessage(final String message, User user) {
        if (mChatView == null) {
            return;
        }
        if (!mChatModel.isMessageValid(message)) {
            mChatView.showEmptyMessageError();
            return;
        }
        mChatModel.sendMessage(message, user, new ChatModel.SendMessageCallback() {
            @Override
            public void onStore(ChatMessage chatMessage) {
                mChatView.cleanMessageField();
                mChatView.showMessage(chatMessage);
            }

            @Override
            public void onSuccess(ChatMessage chatMessage) {
                mChatView.updateMessageStatus(chatMessage);
            }

            @Override
            public void onError() {
                mChatView.showMessageNotSentError();
            }
        });
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void incomingMessage(IncomingMessageEvent event) {
        mChatView.showMessage(event.chatMessage());
    }

    @Override
    public void bindView(ChatView viewMvp) {
        mChatView = viewMvp;
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void unbindView() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        mChatView = null;
    }
}

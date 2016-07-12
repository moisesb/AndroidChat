package com.borges.moises.chatinenglish.receivemessage;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.borges.moises.chatinenglish.App;
import com.borges.moises.chatinenglish.data.model.ChatMessage;
import com.borges.moises.chatinenglish.events.IncomingMessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.offline.OfflineMessageManager;

import java.util.Date;

import javax.inject.Inject;

/**
 * Created by Moisés on 07/07/2016.
 */

public class ReceiveIncomingMessageService extends Service {

    private static final String NAME = "ReceiveIncomingMessagesService";

    @Inject
    ChatManager mChatManager;
    @Inject
    OfflineMessageManager mOfflineMessageManager;

    public ReceiveIncomingMessageService() {
        super();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("ChatApp", "Service Created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        injectDependecies();

        fetchOfflineMessages();
        listenMessages();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ChatApp", "Service destroyed");
    }

    private void injectDependecies() {
        final App app = (App) getApplicationContext();
        app.getNetComponent().inject(this);
    }

    private void fetchOfflineMessages() {
        try {
            Log.d("ChatApp", "count " + mOfflineMessageManager.getMessageCount());
            for (Message message : mOfflineMessageManager.getMessages()) {
                Log.d("ChatApp", "offline " + message);
            }
        } catch (SmackException.NoResponseException |
                XMPPException.XMPPErrorException |
                SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    private void listenMessages() {
        mChatManager.addChatListener(new ChatManagerListener() {
            @Override
            public void chatCreated(Chat chat, boolean createdLocally) {
                if (!createdLocally) {
                    chat.addMessageListener(new ChatMessageListener() {
                        @Override
                        public void processMessage(Chat chat, Message message) {
                            if (message.getBody() != null) {
                                ChatMessage chatMessage = createChatMessage(message, chat);
                                if (chatMessage != null) {
                                    notifyChatMessageReceived(chatMessage);
                                }

                            }
                        }
                    });
                }
            }
        });
    }

    private void notifyChatMessageReceived(ChatMessage chatMessage) {
        EventBus.getDefault().post(new IncomingMessageEvent(chatMessage));
    }

    private ChatMessage createChatMessage(Message message, Chat chat) {
        if (message.getBody() == null) {
            return null;
        }

        Date date = new Date();
        final String content = message.getBody();
        final String receiver = "moisesb";
        ChatMessage chatMessage = new ChatMessage(content, receiver, date, 1);
        return chatMessage;
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, ReceiveIncomingMessageService.class);
        context.startService(intent);
    }
}

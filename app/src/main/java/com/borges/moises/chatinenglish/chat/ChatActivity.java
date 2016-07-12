package com.borges.moises.chatinenglish.chat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.borges.moises.chatinenglish.App;
import com.borges.moises.chatinenglish.R;
import com.borges.moises.chatinenglish.data.model.ChatMessage;
import com.borges.moises.chatinenglish.data.model.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity implements ChatView {

    @BindView(R.id.message)
    EditText mMessageEditText;
    @BindView(R.id.chat_messages)
    RecyclerView mChatRecyclerView;

    @Inject
    ChatPresenter mChatPresenter;

    private static final String CONTACT_ARG = "com.borges.moises.chatinenglish.conversation.contact";
    private User mReceiver;
    private ChatAdapter mAdapter = new ChatAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        if (getIntent().getExtras() != null) {
            mReceiver = (User) getIntent().getExtras().getSerializable(CONTACT_ARG);
        }
        ButterKnife.bind(this);
        injectDependencies();
        setupToolbar();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        mChatRecyclerView.setHasFixedSize(true);
        mChatRecyclerView.setLayoutManager(layoutManager);
        mChatRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mChatPresenter.bindView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mChatPresenter.unbindView();
    }

    @OnClick(R.id.send_button)
    void onSendClick() {
        final String message = mMessageEditText.getText().toString();
        mChatPresenter.sendMessage(message, mReceiver);
    }

    private void injectDependencies() {
        ((App) getApplication()).getNetComponent().inject(this);
    }

    private void setupToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mReceiver.getName());
        }
    }

    public static void start(Context context, User user) {
        Intent intent = new Intent(context, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(CONTACT_ARG, user);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void showMessageNotSentError() {

    }

    @Override
    public void cleanMessageField() {
        mMessageEditText.setText("");
    }

    @Override
    public void showMessage(ChatMessage chatMessage) {
        mAdapter.addMessage(chatMessage);
    }

    @Override
    public void updateMessageStatus(ChatMessage chatMessage) {

    }

    @Override
    public void showEmptyMessageError() {

    }

    static class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

        private List<ChatMessage> messages = new ArrayList<>();

        public ChatAdapter() {
            super();
        }

        public void addMessage(ChatMessage chatMessage) {
            messages.add(0,chatMessage);
            notifyItemInserted(0);
        }

        @Override
        public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View layout = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message, parent, false);
            return new ViewHolder(layout);
        }

        @Override
        public void onBindViewHolder(ChatAdapter.ViewHolder holder, int position) {
            final ChatMessage chatMessage = messages.get(position);
            holder.messageTextView.setText(chatMessage.getContent());
            holder.dateTextView.setText(chatMessage.getTime().toString());
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView messageTextView;
            TextView dateTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                messageTextView = (TextView) itemView.findViewById(R.id.message);
                dateTextView = (TextView) itemView.findViewById(R.id.date);
            }
        }
    }
}

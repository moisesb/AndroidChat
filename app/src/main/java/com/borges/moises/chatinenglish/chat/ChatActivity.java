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
import com.borges.moises.chatinenglish.data.model.Contact;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity implements ChatView {

    @BindView(R.id.message)
    EditText mMessageEditText;
    @BindView(R.id.chat)
    RecyclerView mChatRecyclerView;

    @Inject
    ChatPresenter mChatPresenter;

    private static final String CONTACT_ARG = "com.borges.moises.chatinenglish.conversation.contact";
    private Contact mContact;
    private ChatAdapter mAdapter = new ChatAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        if (getIntent().getExtras() != null) {
            mContact = (Contact) getIntent().getExtras().getSerializable(CONTACT_ARG);
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
        mChatPresenter.sendMessage(message,mContact);
    }

    private void injectDependencies() {
        ((App) getApplication()).getNetComponent().inject(this);
    }

    private void setupToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mContact.getName());
        }
    }

    public static void start(Context context, Contact contact) {
        Intent intent = new Intent(context, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(CONTACT_ARG, contact);
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
    public void showMessage(String message) {
        mAdapter.addMessage(message);
    }


    static class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

        private List<String> messages = new ArrayList<>();

        public ChatAdapter() {
            super();
        }

        public void addMessage(String message) {
            messages.add(0,message);
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
            final String message = messages.get(position);
            holder.messageTextView.setText(message);
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView messageTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                messageTextView = (TextView) itemView.findViewById(R.id.message);
            }
        }
    }
}

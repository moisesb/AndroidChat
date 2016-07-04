package com.borges.moises.chatinenglish.contacts;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.borges.moises.chatinenglish.App;
import com.borges.moises.chatinenglish.R;
import com.borges.moises.chatinenglish.chat.ChatActivity;
import com.borges.moises.chatinenglish.data.model.Contact;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsActivity extends AppCompatActivity implements ContactsView {

    @Inject
    ContactsPresenter mContactsPresenter;
    @BindView(R.id.contacts)
    RecyclerView mRecyclerView;

    private ContactsAdapter mAdapter = new ContactsAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        injectDependencies();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mAdapter.setOnContactClickListener(new OnContactClickListener() {
            @Override
            public void onClick(Contact contact) {
                mContactsPresenter.openConversation(contact);
            }
        });
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void injectDependencies() {
        ((App) getApplication()).getNetComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mContactsPresenter.bindView(this);
        mContactsPresenter.getContacts();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContactsPresenter.unbindView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contacts_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                mContactsPresenter.getContacts();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showLoadingContacts(boolean b) {
        Log.d("ContactsApp", "loading " + b);
    }

    @Override
    public void showContacts(List<Contact> contacts) {
        mAdapter.replaceData(contacts);
    }

    @Override
    public void showNoContacts() {
        Log.d("ContactsApp", "No Contacts");
    }

    @Override
    public void openConversation(Contact contact) {
        ChatActivity.start(this,contact);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context,ContactsActivity.class);
        context.startActivity(intent);
    }

    interface OnContactClickListener {
        void onClick(Contact contact);
    }

    static class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
        private List<Contact> mContacts = new ArrayList<>();
        private OnContactClickListener mOnContactClickListener = new OnContactClickListener() {
            @Override
            public void onClick(Contact contact) {

            }
        };

        public ContactsAdapter() {
            super();
        }

        public void setOnContactClickListener(OnContactClickListener onContactClickListener) {
            mOnContactClickListener = onContactClickListener;
        }

        public void replaceData(@NonNull List<Contact> contacts) {
            mContacts.clear();
            mContacts.addAll(contacts);
            notifyDataSetChanged();
        }

        @Override
        public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View layout = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_contact, parent, false);
            return new ViewHolder(layout);
        }

        @Override
        public void onBindViewHolder(ContactsAdapter.ViewHolder holder, int position) {
            final Contact contact = mContacts.get(position);
            holder.contactName.setText(contact.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnContactClickListener.onClick(contact);
                }
            });

        }

        @Override
        public int getItemCount() {
            return mContacts.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView contactName;

            public ViewHolder(View itemView) {
                super(itemView);
                contactName = (TextView) itemView.findViewById(R.id.contact_name);

            }
        }
    }
}

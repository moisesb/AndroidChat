package com.borges.moises.chatinenglish.contacts;

import android.util.Log;

import com.borges.moises.chatinenglish.data.model.Contact;
import com.borges.moises.chatinenglish.mvp.Callback;
import com.borges.moises.chatinenglish.mvp.PresenterMvp;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Moisés on 03/07/2016.
 */

public class ContactsPresenter implements PresenterMvp<ContactsView> {

    private ContactsView mContactsView;
    private ContactsService mContactsService;

    @Inject
    public ContactsPresenter(ContactsService contactsService) {
        mContactsService = contactsService;
    }

    public void getContacts() {
        if (mContactsView == null) {
            return;
        }
        mContactsView.showLoadingContacts(true);
        mContactsService.fetchContacts(new Callback<List<Contact>>() {
            @Override
            public void onSuccess(List<Contact> contacts) {
                mContactsView.showLoadingContacts(false);

                if (contacts.size() > 0) {
                    mContactsView.showContacts(contacts);
                }else {
                    mContactsView.showNoContacts();
                }
            }

            @Override
            public void onError() {
                mContactsView.showLoadingContacts(false);
                Log.d("Contacts", "fail");
            }
        });
    }

    public void openConversation(Contact contact) {
        if (mContactsView == null) {
            return;
        }
        mContactsView.openConversation(contact);
    }

    @Override
    public void bindView(ContactsView viewMvp) {
        mContactsView = viewMvp;
    }

    @Override
    public void unbindView() {
        mContactsView = null;
    }
}

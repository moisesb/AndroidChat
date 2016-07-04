package com.borges.moises.chatinenglish.contacts;

import com.borges.moises.chatinenglish.data.model.Contact;
import com.borges.moises.chatinenglish.mvp.ViewMvp;

import java.util.List;

/**
 * Created by Moisés on 03/07/2016.
 */

public interface ContactsView extends ViewMvp {
    void showLoadingContacts(boolean b);

    void showContacts(List<Contact> contacts);

    void showNoContacts();

    void openConversation(Contact contact);
}

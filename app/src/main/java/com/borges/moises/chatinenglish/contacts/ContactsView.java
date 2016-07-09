package com.borges.moises.chatinenglish.contacts;

import com.borges.moises.chatinenglish.data.model.User;
import com.borges.moises.chatinenglish.mvp.ViewMvp;

import java.util.List;

/**
 * Created by Moisés on 03/07/2016.
 */

public interface ContactsView extends ViewMvp {
    void showLoadingContacts(boolean b);

    void showContacts(List<User> users);

    void showNoContacts();

    void openConversation(User user);
}

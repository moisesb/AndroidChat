package com.borges.moises.chatinenglish.net;

import com.borges.moises.chatinenglish.chat.ChatActivity;
import com.borges.moises.chatinenglish.contacts.ContactsActivity;
import com.borges.moises.chatinenglish.login.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Moisés on 02/07/2016.
 */
@Singleton
@Component(modules = {NetModule.class})
public interface NetComponent {
    void inject(LoginActivity loginActivity);
    void inject(ContactsActivity contactsActivity);
    void inject(ChatActivity chatActivity);
}

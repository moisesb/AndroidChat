package com.borges.moises.chatinenglish.contacts;

import com.borges.moises.chatinenglish.data.model.User;
import com.borges.moises.chatinenglish.mvp.Callback;

import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Moisés on 03/07/2016.
 */

public class ContactsService {

    private final Roster mRoster;

    @Inject
    public ContactsService(Roster roster) {
        mRoster = roster;
    }

    public void fetchContacts(Callback<List<User>> callback){
        List<User> users = new ArrayList<>();
        for (RosterEntry entry : mRoster.getEntries()) {
            User user = new User();
            user.setName(entry.getName());
            user.setUserName(entry.getUser());
            users.add(user);
        }
        callback.onSuccess(users);
    }
}

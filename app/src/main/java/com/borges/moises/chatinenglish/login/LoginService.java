package com.borges.moises.chatinenglish.login;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.borges.moises.chatinenglish.mvp.Callback;
import com.borges.moises.chatinenglish.net.ServerConfig;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import javax.inject.Inject;

/**
 * Created by Moisés on 02/07/2016.
 */

public class LoginService {

    private final ServerConfig mServerConfig;
    private final AbstractXMPPConnection mConnection;

    @Inject
    public LoginService(AbstractXMPPConnection connection, ServerConfig serverConfig) {
        mConnection = connection;
        mServerConfig = serverConfig;
    }

    void login(final String userName, final String password, final Callback<Void> callback) {
        final AsyncTask<Void, Void, Boolean> asyncTask = new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    connect();
                    mConnection.login(serverUserName(userName),password);
                    return true;
                } catch (XMPPException | SmackException | IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPostExecute(success);
                if (success) {
                    callback.onSuccess(null);
                }else {
                    callback.onError();
                }
            }
        };
        asyncTask.execute();
    }

    private void connect() throws SmackException, IOException, XMPPException {
        if (!mConnection.isConnected()) {
            mConnection.connect();
        }
    }

    @NonNull
    private String serverUserName(String userName) {
        return userName + "@" + mServerConfig.hostName();
    }

    public boolean isUserNameEmpty(String userName) {
        return userName == null || userName.trim().isEmpty();
    }

    public boolean isPasswordInvalid(String password) {
        return password == null || password.trim().isEmpty() || password.length() <= 2;
    }
}

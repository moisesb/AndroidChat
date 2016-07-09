package com.borges.moises.chatinenglish.login;

import android.content.Context;

import com.borges.moises.chatinenglish.mvp.Callback;
import com.borges.moises.chatinenglish.mvp.PresenterMvp;
import com.borges.moises.chatinenglish.receivemessage.ReceiveIncomingMessageService;

import javax.inject.Inject;

/**
 * Created by Moisés on 02/07/2016.
 */

public class LoginPresenter implements PresenterMvp<LoginView> {

    private final LoginService mLoginService;
    private final Context mContext;
    private LoginView mLoginView;

    @Inject
    public LoginPresenter(LoginService loginService, Context context) {
        mLoginService = loginService;
        mContext = context;
    }

    public void login(String userName, String password) {
        if (mLoginView == null) {
            return;
        }

        if (mLoginService.isUserNameEmpty(userName)) {
            mLoginView.showUserNameInvalid();
            return;
        }

        if (mLoginService.isPasswordInvalid(password)) {
            mLoginView.showPasswordInvalid();
            return;
        }

        mLoginView.showProgress(true);
        mLoginService.login(userName, password, new Callback<Void>() {
            @Override
            public void onSuccess(Void data) {
                mLoginView.showProgress(false);
                startReceiveIncomingMessagesService();
                mLoginView.openDashboard();
            }

            @Override
            public void onError() {
                mLoginView.showProgress(false);
                mLoginView.userNameAndPasswordInvalid();
            }
        });
    }

    private void startReceiveIncomingMessagesService() {
        ReceiveIncomingMessageService.start(mContext);
    }

    @Override
    public void bindView(LoginView viewMvp) {
        mLoginView = viewMvp;
    }

    @Override
    public void unbindView() {
        mLoginView = null;
    }
}

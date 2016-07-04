package com.borges.moises.chatinenglish.login;

import android.support.annotation.MainThread;

import com.borges.moises.chatinenglish.mvp.ViewMvp;

/**
 * Created by Moisés on 02/07/2016.
 */

public interface LoginView extends ViewMvp{
    @MainThread
    void showProgress(boolean loading);

    @MainThread
    void userNameAndPasswordInvalid();

    @MainThread
    void openDashboard();

    @MainThread
    void showPasswordInvalid();

    @MainThread
    void showUserNameInvalid();
}

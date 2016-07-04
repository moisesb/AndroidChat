package com.borges.moises.chatinenglish.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.borges.moises.chatinenglish.App;
import com.borges.moises.chatinenglish.R;
import com.borges.moises.chatinenglish.chat.ChatActivity;
import com.borges.moises.chatinenglish.contacts.ContactsActivity;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindString(R.string.error_incorrect_password)
    String mInvalidPassword;
    @BindString(R.string.error_invalid_user_name)
    String mInvalidUserName;

    @BindView(R.id.email)
    AutoCompleteTextView mUserNameTextView;
    @BindView(R.id.password)
    TextView mPasswordTextView;
    @BindView(R.id.login_progress)
    ProgressBar mProgressBar;

    @Inject
    LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        injectDependencies();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoginPresenter.bindView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.unbindView();
    }

    private void injectDependencies() {
        ((App) getApplication()).getNetComponent().inject(this);
    }

    @OnClick(R.id.sign_in_button)
    void onLoginClick() {
        final String userName = mUserNameTextView.getText().toString();
        final String password = mPasswordTextView.getText().toString();
        mLoginPresenter.login(userName, password);
        Log.d("ChatApp", "login");
    }

    @Override
    public void showProgress(boolean loading) {
        mProgressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void userNameAndPasswordInvalid() {
        Log.d("LoginActivity", "username and password invalid");
    }

    @Override
    public void openDashboard() {
        ContactsActivity.start(this);
        finish();
    }

    @Override
    public void showPasswordInvalid() {
        mPasswordTextView.setError(mInvalidPassword);
    }

    @Override
    public void showUserNameInvalid() {
        mUserNameTextView.setError(mInvalidUserName);
    }
}

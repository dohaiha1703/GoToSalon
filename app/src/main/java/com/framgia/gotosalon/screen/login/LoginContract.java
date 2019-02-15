package com.framgia.gotosalon.screen.login;

import android.app.ProgressDialog;

import com.framgia.gotosalon.data.model.Account;

public interface LoginContract {
    interface View {
        void onSignInSuccess();

        void onEmptyFieldEmail();

        void onEmptyFieldPassword();

        void onInvalidEmail();

        void onInvalidPassword();

        void onInvalidEmailForm();

        void onRestoringAccount(Account account);

        void showProgressDialog();

        void hideProgressDialog();
    }

    interface Presenter<View> {
        void setView(LoginContract.View view);

        void signIn(Account account);

        boolean isValidAccount(Account account);

        void saveAccount(Account account);

        void restoreAccount(Account account);
    }
}

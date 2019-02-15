package com.framgia.gotosalon.screen.signup;

import com.framgia.gotosalon.data.model.Account;

public interface SignUpContract {
    interface View {
        void onSignUpSuccess();

        void onEmptyFieldEmail();

        void onEmptyFieldPassword();

        void onEmptyFieldRePassword();

        void onEmptyFieldUsername();

        void onEmailExsist();

        void onShortPassword();

        void onDoNotMatchPassword();

        void onInvalidEmailForm();

        void onErrorWhenSignUp();

        void showProgressDialog();

        void hideProgressDialog();
    }

    interface Presenter<View> {
        void setView(SignUpContract.View view);

        void signUp(Account account);

        boolean validateSignUp(Account account);
    }
}

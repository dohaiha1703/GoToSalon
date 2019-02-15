package com.framgia.gotosalon.screen.login;

import android.support.annotation.NonNull;
import android.util.Patterns;

import com.framgia.gotosalon.data.model.Account;
import com.framgia.gotosalon.data.repository.AuthenicationRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginPresenter implements LoginContract.Presenter {
    private AuthenicationRepository mRepository;
    private LoginContract.View mView;

    public LoginPresenter(AuthenicationRepository repository) {
        mRepository = repository;
    }

    @Override
    public void setView(LoginContract.View view) {
        mView = view;
    }

    @Override
    public void signIn(Account account) {
        if (!isValidAccount(account)) {
            return;
        }
        mView.showProgressDialog();
        mRepository.signInAccount(account.getEmail(), account.getPassword(),
                new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            mView.onSignInSuccess();
                            mView.hideProgressDialog();
                        }
                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseAuthInvalidUserException) {
                            mView.onInvalidEmail();
                            mView.hideProgressDialog();
                        } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            mView.onInvalidPassword();
                            mView.hideProgressDialog();
                        }
                    }
                });
    }

    @Override
    public boolean isValidAccount(Account account) {
        if (account.getEmail().isEmpty()) {
            mView.onEmptyFieldEmail();
            return false;
        }
        if (account.getPassword().isEmpty()) {
            mView.onEmptyFieldPassword();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(account.getEmail()).matches()) {
            mView.onInvalidEmailForm();
            return false;
        }
        return true;
    }

    @Override
    public void saveAccount(Account account) {
        mRepository.saveAccount(account);
    }

    @Override
    public void restoreAccount(Account account) {
        mRepository.restoreAccount(account);
        if (account.isRemember()){
            mView.onRestoringAccount(account);
        }
    }
}

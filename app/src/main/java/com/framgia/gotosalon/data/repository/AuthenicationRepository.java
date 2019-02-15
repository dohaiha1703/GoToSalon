package com.framgia.gotosalon.data.repository;

import com.framgia.gotosalon.data.model.Account;
import com.framgia.gotosalon.data.source.AuthenicationDataSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;

public class AuthenicationRepository implements AuthenicationDataSource.Remote,
        AuthenicationDataSource.Local {
    private AuthenicationDataSource.Remote mRemote;
    private AuthenicationDataSource.Local mLocal;
    private static AuthenicationRepository sInstance;

    private AuthenicationRepository(AuthenicationDataSource.Remote remote,
                                    AuthenicationDataSource.Local local) {
        mRemote = remote;
        mLocal = local;
    }

    public static AuthenicationRepository getInstance(AuthenicationDataSource.Remote remote,
                                                      AuthenicationDataSource.Local local) {
        if (sInstance == null) {
            sInstance = new AuthenicationRepository(remote, local);
        }
        return sInstance;
    }

    @Override
    public void signInAccount(String email, String password, OnCompleteListener completeListener,
                              OnFailureListener failureListener) {
        mRemote.signInAccount(email, password, completeListener, failureListener);
    }

    @Override
    public void signUpAccount(String email, String password, OnCompleteListener completeListener,
                              OnFailureListener failureListener) {
        mRemote.signUpAccount(email, password, completeListener, failureListener);
    }

    @Override
    public void saveAccount(Account account) {
        mLocal.saveAccount(account);
    }

    @Override
    public void restoreAccount(Account account) {
        mLocal.restoreAccount(account);
    }
}

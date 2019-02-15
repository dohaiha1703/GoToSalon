package com.framgia.gotosalon.data.source;

import com.framgia.gotosalon.data.model.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;

public interface AuthenicationDataSource {

    interface Remote {
        void signInAccount(String email, String password, OnCompleteListener completeListener,
                           OnFailureListener failureListener);

        void signUpAccount(String email, String password, OnCompleteListener completeListener,
                           OnFailureListener failureListener);
    }

    interface Local {
        void saveAccount(Account account);

        void restoreAccount(Account account);
    }
}

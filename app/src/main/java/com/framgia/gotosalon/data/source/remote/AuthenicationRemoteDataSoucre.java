package com.framgia.gotosalon.data.source.remote;

import com.framgia.gotosalon.data.source.AuthenicationDataSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;

public class AuthenicationRemoteDataSoucre implements AuthenicationDataSource.Remote {
    private FirebaseAuth mAuth;
    private static AuthenicationRemoteDataSoucre sInstance;

    private AuthenicationRemoteDataSoucre(FirebaseAuth auth) {
        mAuth = auth;
    }

    public static AuthenicationRemoteDataSoucre getInstance(FirebaseAuth auth) {
        if (sInstance == null) {
            sInstance = new AuthenicationRemoteDataSoucre(auth);
        }
        return sInstance;
    }

    @Override
    public void signInAccount(String email, String password,
                              OnCompleteListener completeListener, OnFailureListener failureListener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(completeListener)
                .addOnFailureListener(failureListener);
    }

    @Override
    public void signUpAccount(String email, String password, OnCompleteListener completeListener,
                              OnFailureListener failureListener) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(completeListener)
                .addOnFailureListener(failureListener);
    }
}

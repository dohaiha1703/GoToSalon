package com.framgia.gotosalon.data.source.remote;

import android.net.Uri;

import com.framgia.gotosalon.data.model.Salon;
import com.framgia.gotosalon.data.source.SalonDataSource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SalonRemoteDataSource implements SalonDataSource.Remote {
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;
    private static SalonRemoteDataSource sInstance;

    private SalonRemoteDataSource(DatabaseReference databaseReference, StorageReference storageReference) {
        mDatabaseReference = databaseReference;
        mStorageReference = storageReference;
    }

    public static SalonRemoteDataSource getInstance(DatabaseReference databaseReference,
                                                    StorageReference storageReference) {
        if (sInstance == null) {
            sInstance = new SalonRemoteDataSource(databaseReference, storageReference);
        }
        return sInstance;
    }

    @Override
    public void getSalons(ValueEventListener listener) {
        mDatabaseReference.addValueEventListener(listener);
    }

    @Override
    public void deleteDetailSalon(Salon salon, OnSuccessListener successListener,
                                  OnFailureListener failureListener) {
        mDatabaseReference.child(salon.getSalonId()).removeValue();
    }

    @Override
    public void deleteImageSalon(StorageReference storageReferenceUrl, OnSuccessListener
            successListener, OnFailureListener failureListener) {
        storageReferenceUrl.delete()
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }

    @Override
    public void storageSalonImage(Uri uri, String child,
                                  OnSuccessListener<UploadTask.TaskSnapshot> onSuccessListener,
                                  OnFailureListener failureListener) {
        mStorageReference.child(child).putFile(uri).addOnSuccessListener(onSuccessListener).
                addOnFailureListener(failureListener);
    }

    @Override
    public void publishSalons(Salon salon, OnSuccessListener onSuccessListener,
                              OnFailureListener failureListener) {
        mDatabaseReference.child(salon.getSalonId()).setValue(salon).
                addOnSuccessListener(onSuccessListener).
                addOnFailureListener(failureListener);
    }
}

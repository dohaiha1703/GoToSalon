package com.framgia.gotosalon.data.repository;

import android.net.Uri;

import com.framgia.gotosalon.data.model.Salon;
import com.framgia.gotosalon.data.source.SalonDataSource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SalonRepository implements SalonDataSource.Remote {
    private SalonDataSource.Remote mDataSource;
    private static SalonRepository sInstance;

    private SalonRepository(SalonDataSource.Remote dataSource) {
        mDataSource = dataSource;
    }

    public static SalonRepository getInstance(SalonDataSource.Remote dataSource) {
        if (sInstance == null) {
            sInstance = new SalonRepository(dataSource);
        }
        return sInstance;
    }

    @Override
    public void getSalons(ValueEventListener listener) {
        mDataSource.getSalons(listener);
    }

    @Override
    public void deleteDetailSalon(Salon salon,
                                  OnSuccessListener successListener,
                                  OnFailureListener failureListener) {
        mDataSource.deleteDetailSalon(salon, successListener, failureListener);
    }

    @Override
    public void deleteImageSalon(StorageReference storageReferenceUrl,
                                 OnSuccessListener successListener,
                                 OnFailureListener failureListener) {
        mDataSource.deleteImageSalon(storageReferenceUrl, successListener, failureListener);
    }

    @Override
    public void storageSalonImage(Uri uri, String child,
                                  OnSuccessListener<UploadTask.TaskSnapshot> onSuccessListener,
                                  OnFailureListener failureListener) {
        mDataSource.storageSalonImage(uri, child, onSuccessListener, failureListener);
    }

    @Override
    public void publishSalons(Salon salon, OnSuccessListener onSuccessListener,
                              OnFailureListener failureListener) {
        mDataSource.publishSalons(salon, onSuccessListener, failureListener);
    }
}

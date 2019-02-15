package com.framgia.gotosalon.data.source;

import android.net.Uri;

import com.framgia.gotosalon.data.model.Salon;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public interface SalonDataSource {
    interface Remote {
        void getSalons(ValueEventListener listener);

        void deleteDetailSalon(Salon salon,
                               OnSuccessListener successListener,
                               OnFailureListener failureListener);

        void deleteImageSalon(StorageReference storageReferenceUrl,
                              OnSuccessListener successListener,
                              OnFailureListener failureListener);

        void storageSalonImage(Uri uri, String child,
                               OnSuccessListener<UploadTask.TaskSnapshot> onSuccessListener,
                               OnFailureListener failureListener);

        void publishSalons(Salon salon, OnSuccessListener onSuccessListener,
                           OnFailureListener failureListener);
    }
}

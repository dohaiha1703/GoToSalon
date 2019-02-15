package com.framgia.gotosalon.screen.manager;

import android.support.annotation.NonNull;
import android.util.Log;

import com.framgia.gotosalon.data.model.Salon;
import com.framgia.gotosalon.data.repository.SalonRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManagerPresenter implements ManagerContract.Presenter {
    private SalonRepository mRepository;
    private ManagerContract.View mView;

    public ManagerPresenter(SalonRepository repository) {
        mRepository = repository;
    }

    @Override
    public void setView(ManagerContract.View view) {
        mView = view;
    }

    @Override
    public void getSalons(final String userId) {
        final List<Salon> list = new ArrayList<>();
        mRepository.getSalons(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                mView.onGetSalonProgress();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Salon salon = snapshot.getValue(Salon.class);
                    salon.setSalonId(snapshot.getKey());
                    if (salon.getOwnerKey().equals(userId)) {
                        list.add(salon);
                    }
                }
                Collections.reverse(list);
                mView.onGetSalonsSuccess(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mView.onGetSalonFailed();
            }
        });
    }

    @Override
    public void deleteSalon(StorageReference storageReferenceUrl, final Salon salon) {
        mRepository.deleteImageSalon(storageReferenceUrl, new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
        mRepository.deleteDetailSalon(salon, new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                mView.onDeleteSuccess();
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
}

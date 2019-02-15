package com.framgia.gotosalon.screen.home;

import android.support.annotation.NonNull;

import com.framgia.gotosalon.data.model.Salon;
import com.framgia.gotosalon.data.repository.SalonRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomePresenter implements HomeContract.Presenter {
    private SalonRepository mRepository;
    private HomeContract.View mView;

    public HomePresenter(SalonRepository repository) {
        mRepository = repository;
    }

    @Override
    public void setView(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void getHotSalons() {
        mView.onGetSalonInProgress();
        final List<Salon> salons = new ArrayList<>();
        mRepository.getSalons(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                salons.clear();
                mView.onGetSalonInProgress();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Salon salon = snapshot.getValue(Salon.class);
                    salon.setSalonId(snapshot.getKey());
                    salons.add(salon);
                }
                Collections.sort(salons, new SalonViewsComparator());
                mView.onGetSalonsSucced(salons);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mView.onGetSalonsFailed();
            }
        });
    }

    @Override
    public void getNewSalons() {
        mView.onGetSalonInProgress();
        final List<Salon> salons = new ArrayList<>();
        mRepository.getSalons(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                salons.clear();
                mView.onGetSalonInProgress();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Salon salon = snapshot.getValue(Salon.class);
                    salon.setSalonId(snapshot.getKey());
                    salons.add(salon);
                }
                mView.onGetSalonsSucced(salons);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mView.onGetSalonsFailed();
            }
        });
    }
}

package com.framgia.gotosalon.screen.manager;

import com.framgia.gotosalon.data.model.Salon;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public interface ManagerContract {
    interface View {
        void onGetSalonsSuccess(List<Salon> salons);

        void onGetSalonFailed();

        void onGetSalonProgress();

        void onDeleteSuccess();
    }

    interface Presenter<View> {
        void setView(ManagerContract.View view);

        void getSalons(String userId);

        void deleteSalon(StorageReference storageReferenceUrl, Salon salon);
    }
}

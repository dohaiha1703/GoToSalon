package com.framgia.gotosalon.screen.home;

import com.framgia.gotosalon.data.model.Salon;

import java.util.List;

public interface HomeContract {
    interface View {
        void onGetSalonsSucced(List<Salon> salons);

        void onGetSalonsFailed();

        void onGetSalonInProgress();
    }

    interface Presenter<View> {
        void setView(HomeContract.View view);

        void getNewSalons();

        void getHotSalons();
    }
}

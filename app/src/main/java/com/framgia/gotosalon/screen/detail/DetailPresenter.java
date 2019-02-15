package com.framgia.gotosalon.screen.detail;

import com.framgia.gotosalon.data.model.Salon;

public class DetailPresenter implements DetailContract.Presenter {
    private DetailContract.View mView;

    @Override
    public void setView(DetailContract.View view) {
        mView = view;
    }

    @Override
    public void checkSalonExist(Salon salon) {
        if (salon == null) {
            mView.onSalonNoExist();
            return;
        }
        mView.onSalonExist();
    }
}

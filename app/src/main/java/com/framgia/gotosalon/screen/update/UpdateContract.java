package com.framgia.gotosalon.screen.update;

import android.net.Uri;

import com.framgia.gotosalon.data.model.Salon;

public interface UpdateContract {
    interface View {
        void onUpdateProgress();

        void onUpdateImageSuccess();

        void onUpdateImageFailed();

        void onUpdateSalonSuccess();

        void onUpdateSalonFailed();

        void onValidateSalonName();

        void onValidateSalonAddress();

        void onValidateSalonEmail();

        void onInvalidEmailForm();

        void onValidateSalonPhone();

        void onValidateSalonTime();

    }

    interface Presenter<View> {
        void setView(UpdateContract.View view);

        boolean validateUpdateSalon(Salon salon);

        void updateSalon(Uri uri, String child, Salon salon);
    }
}

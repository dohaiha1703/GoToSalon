package com.framgia.gotosalon.screen.publish;

import android.net.Uri;

import com.framgia.gotosalon.data.model.Salon;

public interface PublishContract {
    interface View {
        void onPublishProgress();

        void onUploadImageSuccess();

        void onUploadImageFailed();

        void onPublishSalonSuccess();

        void onPublishSalonFailed();

        void onValidateSalonName();

        void onValidateSalonAddress();

        void onValidateSalonEmail();

        void onInvalidEmailForm();

        void onValidateSalonPhone();

        void onValidateSalonTime();

        void onValidateSalonImage();
    }

    interface Presenter<View> {
        void setView(PublishContract.View view);

        boolean validatePublishSalon(Salon salon);

        void uploadImageSalon(Uri uri, String child, Salon salon);
    }
}

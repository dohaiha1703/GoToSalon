package com.framgia.gotosalon.screen.update;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Patterns;

import com.framgia.gotosalon.data.model.Salon;
import com.framgia.gotosalon.data.repository.SalonRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;

public class UpdatePresenter implements UpdateContract.Presenter {
    private UpdateContract.View mView;
    private SalonRepository mRepository;

    public UpdatePresenter(SalonRepository repository) {
        mRepository = repository;
    }

    @Override
    public void setView(UpdateContract.View view) {
        mView = view;
    }

    @Override
    public boolean validateUpdateSalon(Salon salon) {
        if (salon.getSalonName().isEmpty()) {
            mView.onValidateSalonName();
            return false;
        }
        if (salon.getSalonAddress().isEmpty()) {
            mView.onValidateSalonAddress();
            return false;
        }
        if (salon.getSalonPhone().isEmpty()) {
            mView.onValidateSalonPhone();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(salon.getSalonEmail()).matches()) {
            mView.onInvalidEmailForm();
            return false;
        }
        if (salon.getSalonEmail().isEmpty()) {
            mView.onValidateSalonEmail();
            return false;
        }
        if (salon.getSalonOpenTime().equals(salon.getSalonCloseTime())) {
            mView.onValidateSalonTime();
            return false;
        }
        return true;
    }

    @Override
    public void updateSalon(Uri uri, String child, final Salon salon) {
        if (!validateUpdateSalon(salon)) {
            return;
        }

        mView.onUpdateProgress();
        if (uri == null) {
            mRepository.publishSalons(salon, new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    mView.onUpdateSalonSuccess();
                }
            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mView.onUpdateSalonFailed();
                }
            });
            return;
        }

        mRepository.storageSalonImage(uri, child, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mView.onUpdateImageSuccess();
                taskSnapshot.getStorage().getDownloadUrl().
                        addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                salon.setImageUrl(uri.toString());
                                mRepository.publishSalons(salon, new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        mView.onUpdateSalonSuccess();
                                    }
                                }, new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        mView.onUpdateSalonFailed();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mView.onUpdateImageFailed();
            }
        });
    }
}

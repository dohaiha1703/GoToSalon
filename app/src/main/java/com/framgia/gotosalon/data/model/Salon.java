package com.framgia.gotosalon.data.model;

import java.io.Serializable;

public class Salon implements Serializable {
    private String mSalonName;
    private String mOwnerKey;
    private String mSalonAddress;
    private String mSalonOpenTime;
    private String mSalonCloseTime;
    private String mSalonEmail;
    private String mSalonPhone;
    private String mSalonId;
    private String mImageUrl;
    private String mSalonDescription;
    private int mSalonView;

    public Salon() {
    }

    public String getSalonName() {
        return mSalonName;
    }

    public void setSalonName(String salonName) {
        mSalonName = salonName;
    }

    public String getOwnerKey() {
        return mOwnerKey;
    }

    public void setOwnerKey(String ownerKey) {
        mOwnerKey = ownerKey;
    }

    public String getSalonAddress() {
        return mSalonAddress;
    }

    public void setSalonAddress(String salonAddress) {
        mSalonAddress = salonAddress;
    }

    public String getSalonOpenTime() {
        return mSalonOpenTime;
    }

    public void setSalonOpenTime(String salonOpenTime) {
        mSalonOpenTime = salonOpenTime;
    }

    public String getSalonCloseTime() {
        return mSalonCloseTime;
    }

    public void setSalonCloseTime(String salonCloseTime) {
        mSalonCloseTime = salonCloseTime;
    }

    public String getSalonEmail() {
        return mSalonEmail;
    }

    public void setSalonEmail(String salonEmail) {
        mSalonEmail = salonEmail;
    }

    public String getSalonPhone() {
        return mSalonPhone;
    }

    public void setSalonPhone(String salonPhone) {
        mSalonPhone = salonPhone;
    }

    public String getSalonId() {
        return mSalonId;
    }

    public void setSalonId(String salonId) {
        mSalonId = salonId;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getSalonDescription() {
        return mSalonDescription;
    }

    public void setSalonDescription(String salonDescription) {
        mSalonDescription = salonDescription;
    }

    public int getSalonView() {
        return mSalonView;
    }

    public void setSalonView(int salonView) {
        mSalonView = salonView;
    }
}

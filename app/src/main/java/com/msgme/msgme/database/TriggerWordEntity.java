package com.msgme.msgme.database;

/**
 * Created by alonm on 8/30/14.
 */
public class TriggerWordEntity {

    private String mLanguage;
    private String mCouponText;
    private String mText;
    private String mImageUrl;

    public TriggerWordEntity(String mLanguage, String mCouponText, String mText, String mImageUrl) {
        this.mLanguage = mLanguage;
        this.mCouponText = mCouponText;
        this.mText = mText;
        this.mImageUrl = mImageUrl;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setmLanguage(String mLanguage) {
        this.mLanguage = mLanguage;
    }

    public String getCouponText() {
        return mCouponText;
    }

    public void setmCouponText(String mCouponText) {
        this.mCouponText = mCouponText;
    }

    public String getText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}

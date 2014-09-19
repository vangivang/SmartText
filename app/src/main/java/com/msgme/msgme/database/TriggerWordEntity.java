package com.msgme.msgme.database;

/**
 * Created by alonm on 8/30/14.
 */
public class TriggerWordEntity {

    private String mLanguage;
    private String mCouponTeaserText;
    private String mTriggerWord;
    private String mImageUrl;

    public TriggerWordEntity(String mLanguage, String mCouponText, String mText, String mImageUrl) {
        this.mLanguage = mLanguage;
        this.mCouponTeaserText = mCouponText;
        this.mTriggerWord = mText;
        this.mImageUrl = mImageUrl;
    }

    public String getLanguage() {
        return mLanguage;
    }
    public String getCouponText() {
        return mCouponTeaserText;
    }
    public String getText() {
        return mTriggerWord;
    }
    public String getImageUrl() {
        return mImageUrl;
    }

}

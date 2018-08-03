package com.example.android.miwok;

/**
 * Created by nocdib on 9/18/16.
 */
public class Word {

    private final int NO_IMAGE_CODE = -1;
    private String mDefaultTranslation, mMiwokTranslation;
    private int mImageResourceID = NO_IMAGE_CODE;

    public Word(String defaultTranslation, String miwokTranslation){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
    }

    public Word(String defaultTranslation, String miwokTranslation, int imageResourceID) {
        this(defaultTranslation,miwokTranslation);
        mImageResourceID = imageResourceID;
    }

    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }

    public int getImageResourceID(){
        return mImageResourceID;
    }

    public String getMiwokTranslation(){
        return mMiwokTranslation;
    }

    public boolean hasImage(){
        return mImageResourceID != NO_IMAGE_CODE;
    }

}

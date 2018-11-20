package com.example.android.miwok;

public class Word {

    /**
     *  Default translation for the word
     */
    private String mDefaultTranslation;


    /** Miwok translation for the word */
    private String mMiwokTranslation;
    private int mAudioResource;
    private int mImageResourceId;

    private static final int NO_IMAGE_PROVIEDED= -1;

    /*
    public Word(String defaultTranslation, String miwokTranslation) {
        mDefaultTranslation=defaultTranslation;
        mMiwokTranslation=miwokTranslation;

    }
*/

    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResource) {
        mDefaultTranslation=defaultTranslation;
        mMiwokTranslation=miwokTranslation;
        mImageResourceId=imageResourceId;
        mAudioResource=audioResource;
    }

    public int getmImageResourceId() {
        return mImageResourceId;
    }



    public String getmDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getmMiwokTranslation() {
        return mMiwokTranslation;
    }

    public boolean hasImage()
    {
        return mImageResourceId  != NO_IMAGE_PROVIEDED;

    }

    public int getAudioResource() {
        return mAudioResource;
    }


}

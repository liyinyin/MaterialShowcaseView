package com.lion.materialshowcaseview;

import android.graphics.Color;
import android.graphics.Typeface;

public class ShowcaseConfig {

    public static final String DEFAULT_MASK_COLOUR = "#dd335075";
    public static final long DEFAULT_FADE_TIME = 300;
    public static final long DEFAULT_DELAY = 0;
    public static final int DEFAULT_RADIUS = 200;
    public static final int DEFAULT_CONTENT_LAYOUT = R.layout.default_showcase_content;
    
    public static final int ARRAW_BUTTON_MARGIN = 15;
    
    public static final double DEFAULT_AUTO_RADIUS_SCALE_FACTOR = 1.4;

    private long mDelay = DEFAULT_DELAY;
    private int mMaskColour;
    private int mContentTextColor;
    private int mDismissTextColor;
    private long mFadeDuration = DEFAULT_FADE_TIME;
    private Typeface mTypeface;
    private float mContentTextSize;
    private float mButtonTextSize;

	public ShowcaseConfig() {
        mMaskColour = Color.parseColor(ShowcaseConfig.DEFAULT_MASK_COLOUR);
        mContentTextColor = Color.parseColor("#ffffff");
        mDismissTextColor = Color.parseColor("#ffffff");
    }

    public long getDelay() {
        return mDelay;
    }

    public void setDelay(long delay) {
        this.mDelay = delay;
    }

    public int getMaskColor() {
        return mMaskColour;
    }

    public void setMaskColor(int maskColor) {
        mMaskColour = maskColor;
    }

    public int getContentTextColor() {
        return mContentTextColor;
    }

    public void setContentTextColor(int mContentTextColor) {
        this.mContentTextColor = mContentTextColor;
    }

    public int getDismissTextColor() {
        return mDismissTextColor;
    }

    public void setDismissTextColor(int dismissTextColor) {
        this.mDismissTextColor = dismissTextColor;
    }

    public long getFadeDuration() {
        return mFadeDuration;
    }

    public void setFadeDuration(long fadeDuration) {
        this.mFadeDuration = fadeDuration;
    }
    
    public Typeface getTypeface() {
		return mTypeface;
	}

	public void setTypeface(Typeface typeface) {
		this.mTypeface = typeface;
	}
	
	public float getContentTextSize() {
		return mContentTextSize;
	}

	public void setContentTextSize(float contentTextSize) {
		this.mContentTextSize = contentTextSize;
	}
	
	public float getButtonTextSize() {
		return mButtonTextSize;
	}

	public void setButtonTextSize(float buttonTextSize) {
		this.mButtonTextSize = buttonTextSize;
	}
}

package com.lion.materialshowcaseview;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.View;

import java.util.LinkedList;
import java.util.Queue;

public class MaterialShowcaseSequence implements IShowcaseSequenceListener {

    PrefsManager mPrefsManager;
    Queue<MaterialShowcaseView> mShowcaseQueue;
    private boolean mSingleUse = false;
    Activity mActivity;
    private ShowcaseConfig mConfig;
    private boolean mOrientationFixed = false;

	public MaterialShowcaseSequence(Activity activity) {
        mActivity = activity;
        mShowcaseQueue = new LinkedList<>();
    }

    public MaterialShowcaseSequence(Activity activity, String sequenceID) {
        this(activity);
        this.singleUse(sequenceID);
    }

    public MaterialShowcaseSequence addSequenceItem(MaterialShowcaseView sequenceItem) {
    	if(mConfig != null){
            sequenceItem.setConfig(mConfig);
        }
    	sequenceItem.setSkipGuideVisibility(View.VISIBLE);
    	sequenceItem.setDismissText(mActivity.getString(R.string.next_step));
    	sequenceItem.removeLockOrientationListener();
        mShowcaseQueue.add(sequenceItem);
        return this;
    }

    public MaterialShowcaseSequence singleUse(String sequenceID) {
        mSingleUse = true;
        mPrefsManager = new PrefsManager(mActivity, sequenceID);
        return this;
    }

    public void start() {

        /**
         * Check if we'e already shot our bolt and bail out if so
         * Otherwise, notify the prefsManager that we're firing now
         */
        if (mSingleUse) {
            if (mPrefsManager.hasFired()) {
                return;
            }
        }

        // do start
        if (mShowcaseQueue.size() > 0) {
        	MaterialShowcaseView sequenceItem = mShowcaseQueue.peek();
        	if (!mOrientationFixed) {
        		sequenceItem.addShowcaseListener(new IShowcaseListener(){
    				@Override
    				public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
    					if (!mActivity.isFinishing()) {
    						int beforeOrientation = mActivity.getResources().getConfiguration().orientation;
    						if (beforeOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
    							mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
    						} else if (beforeOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
    							mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
    						} else  {
                                //noinspection ResourceType
    							mActivity.setRequestedOrientation(beforeOrientation);
    						}
    					}
    					Log.e("mOrientationFixed", Integer.toString(mActivity.getResources().getConfiguration().orientation));
    				}

    				@Override
    				public void onShowcaseDismissed(MaterialShowcaseView showcaseView, MaterialShowcaseView.DismissedType dismissedType) {
    					if (mShowcaseQueue.size() == 0 && !showcaseView.needConnectToSubSequence()) {
    						mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    					}
    				}
            	});
        	}
        	showNextItem();
        }
    }

    private void showNextItem() {

        if (mShowcaseQueue.size() > 0 && !mActivity.isFinishing()){
            MaterialShowcaseView sequenceItem = mShowcaseQueue.remove();
            sequenceItem.addShowcaseListener(this);
            if (mShowcaseQueue.size() == 0 && !sequenceItem.needConnectToSubSequence()) {
            	sequenceItem.setSkipGuideVisibility(View.GONE);
            	fixLastDismissText(sequenceItem);
            }
            sequenceItem.show(mActivity);
        } else {
        	unlockOrientation();
        	
            /**
             * We've reached the end of the sequence, save the fired state
             */
            if (mSingleUse) {
                mPrefsManager.setFired();
            }
        }
    }
    
    private void skipAllItem() {
    	unlockOrientation();
    	
        if (mSingleUse) {
            mPrefsManager.setFired();
        }
        mShowcaseQueue.clear();
    }
    
    private void unlockOrientation() {
    	if (!mOrientationFixed) {
    		mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    	}
    }

    @Override
    public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
        // don't care
    }

    @Override
    public void onShowcaseDismissed(MaterialShowcaseView showcaseView, MaterialShowcaseView.DismissedType dismissedType) {
        showcaseView.removeShowcaseListener(this);
        if (!showcaseView.needConnectToSubSequence()) {
        	showNextItem();
        }
    }
    

	@Override
	public void onShowcaseSkipClicked(MaterialShowcaseView showcaseView) {
		skipAllItem();
	}

    public void setConfig(ShowcaseConfig config) {
        this.mConfig = config;
    }
    
    public void setOrientationFixed(boolean orientationFixed) {
    	this.mOrientationFixed = orientationFixed;
    }
    
    public void fixLastDismissText(MaterialShowcaseView sequenceItem) {
    	String dismissText = sequenceItem.getDismissText();
    	if (sequenceItem.getDismissText() != null && dismissText.equals(mActivity.getString(R.string.next_step))) {
    		sequenceItem.setDismissText(mActivity.getString(R.string.got_it));
    	}
    }
    
    public void connectToSubSequence() {
    	showNextItem();
    }
}

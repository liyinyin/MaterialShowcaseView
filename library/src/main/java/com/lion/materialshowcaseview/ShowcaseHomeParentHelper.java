package com.lion.materialshowcaseview;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

public class ShowcaseHomeParentHelper {
    private static ShowcaseHomeParentHelper mInstance;

    private ShowcaseHomeParentHelper() {

    }

    public static ShowcaseHomeParentHelper getInstance() {
        if (mInstance == null) {
            mInstance = new ShowcaseHomeParentHelper();
        }
        return mInstance;
    }

    public static int getStatusBarHeight(View view) {
        int result = 0;
        int resourceId = view.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = view.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public boolean isActionBarContainerLocationFinished(Activity activity, StatusBarState statusBarState) {
        int resId = activity.getResources().getIdentifier("action_bar_container", "id", activity.getPackageName() );
        View actionBarContainer = activity.getWindow().getDecorView().findViewById(resId);
        if (actionBarContainer == null) {
            resId = activity.getResources().getIdentifier("action_bar_container", "id", "android");
            actionBarContainer = activity.getWindow().getDecorView().findViewById(resId);
            if (actionBarContainer == null) {
                actionBarContainer = (View) activity.findViewById(android.R.id.home).getParent();
                if (actionBarContainer == null) {
                    return false;
                }
            }
        }
        return isActionBarContainerLocationFinished(actionBarContainer, statusBarState);
    }

    private boolean handleKey(Activity activity) {
        ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();
        View showcaseView = rootView.getChildAt(rootView.getChildCount() - 1);
        if (showcaseView != null && showcaseView instanceof MaterialShowcaseView) {
            return true;
        } else {
            return false;
        }
    }

    public boolean handleKeyUp(Activity activity) {
        return handleKey(activity);
    }

    public boolean handleKeyDown(Activity activity) {
        return handleKey(activity);
    }

    public boolean handleBackPressed(Activity activity) {
        return handleKey(activity);
    }

    public boolean isActionBarContainerLocationFinished(View homeParent, StatusBarState statusBarState) {
        int[] location = new int[2];
        homeParent.getLocationInWindow(location);

        if (statusBarState == StatusBarState.SHOWING) {
            if (location[1] == getStatusBarHeight(homeParent)) {
                return true;
            } else {
                return false;
            }
        } else if (statusBarState == StatusBarState.HIDING) {
            if (location[1] == 0) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public enum StatusBarState {
        NONE, SHOWING, HIDING
    }
}

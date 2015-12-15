package com.lion.materialshowcaseview;

public interface IShowcaseListener {
    void onShowcaseDisplayed(MaterialShowcaseView showcaseView);
    void onShowcaseDismissed(MaterialShowcaseView showcaseView, MaterialShowcaseView.DismissedType dismissedType);
}

package com.lion.materialshowcaseviewsample;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Toast;

import com.lion.materialshowcaseview.IShowcaseListener;
import com.lion.materialshowcaseview.MaterialShowcaseSequence;
import com.lion.materialshowcaseview.MaterialShowcaseView;
import com.lion.materialshowcaseview.ShowcaseConfig;
import com.lion.materialshowcaseview.ShowcaseHomeParentHelper;

public class SubSequenceActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String SHOWCASE_ID = "sub sequence example";
    private Button mButtonOne;
    private Button mButtonTwo;

    private MaterialShowcaseSequence sequence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_sequence_example);

        mButtonOne = (Button) findViewById(R.id.btn_one);
        mButtonTwo = (Button) findViewById(R.id.btn_two);

        findViewById(R.id.btn_reset).setOnClickListener(this);

        addOnGlobalLayoutListener(); // Make sure action bar container location finished, then present ShowcaseView
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_reset) {
            MaterialShowcaseView.resetSingleUse(this, SHOWCASE_ID);
            Toast.makeText(this, "Showcase reset", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("deprecation")
    public void addOnGlobalLayoutListener() {
        final View root = getWindow().getDecorView();
        ViewTreeObserver vto = root.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (ShowcaseHomeParentHelper.getInstance().isActionBarContainerLocationFinished(SubSequenceActivity
                        .this, ShowcaseHomeParentHelper.StatusBarState.SHOWING)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        root.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                    presentShowcaseSequence();
                }
            }
        });
    }

    private void showButtonThree() {
        final View view = findViewById(R.id.btn_three);

        AlphaAnimation animation = new AlphaAnimation(0.2f, 1.0f);
        animation.setDuration(1000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.VISIBLE);

                MaterialShowcaseView threeShowcaseView = new MaterialShowcaseView.Builder(SubSequenceActivity.this)
                        .setTarget(findViewById(R.id.btn_three)).setContentText(R.string.sub_button_text).build();
                sequence.addSequenceItem(threeShowcaseView);

                sequence.connectToSubSequence();  // must call this method
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.setInterpolator(new AccelerateInterpolator());
        view.startAnimation(animation);
    }

    private void presentShowcaseSequence() {

        AssetManager mgr = getAssets();
        Typeface tf = Typeface.createFromAsset(mgr, "showcase/xjlFont.fon");

        ShowcaseConfig config = new ShowcaseConfig();
        config.setTypeface(tf);
        if (Util.isZhCN()) {
            config.setContentTextSize(24);
            config.setButtonTextSize(22);
        } else {
            config.setContentTextSize(36);
            config.setButtonTextSize(30);
        }

        sequence = new MaterialShowcaseSequence(this, SHOWCASE_ID);

        sequence.setConfig(config);

        MaterialShowcaseView oneShowcaseView = new MaterialShowcaseView.Builder(this)
                .setTarget(mButtonOne).setContentText(R.string.normal_text).setDelay(500).build();
        MaterialShowcaseView twoShowcaseView = new MaterialShowcaseView.Builder(this)
                .setTarget(mButtonTwo).setContentText(R.string.short_text)
                .setHasSubSequence(true) // must set true, then can show sub sequence.
                .setListener(new IShowcaseListener() {
                    @Override
                    public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {

                    }

                    @Override
                    public void onShowcaseDismissed(MaterialShowcaseView showcaseView, MaterialShowcaseView
                            .DismissedType dismissedType) {
                        if (dismissedType != MaterialShowcaseView.DismissedType.SKIP_CLICKED) {
                            showButtonThree(); // show button three.
                        }
                    }
                }).setDelay(500).build();
        sequence.addSequenceItem(oneShowcaseView);
        sequence.addSequenceItem(twoShowcaseView);

        sequence.start();
    }
}

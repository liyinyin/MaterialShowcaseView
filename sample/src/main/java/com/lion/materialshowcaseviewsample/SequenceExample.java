package com.lion.materialshowcaseviewsample;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lion.materialshowcaseview.MaterialShowcaseSequence;
import com.lion.materialshowcaseview.MaterialShowcaseView;
import com.lion.materialshowcaseview.ShowcaseConfig;

public class SequenceExample extends AppCompatActivity implements View.OnClickListener {

    private static final String SHOWCASE_ID = "sequence example";
    private Button mButtonOne;
    private Button mButtonTwo;
    private Button mButtonThree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence_example);

        mButtonOne = (Button) findViewById(R.id.btn_one);
        mButtonTwo = (Button) findViewById(R.id.btn_two);
        mButtonThree = (Button) findViewById(R.id.btn_three);

        findViewById(R.id.btn_reset).setOnClickListener(this);

        presentShowcaseSequence();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_reset) {
            MaterialShowcaseView.resetSingleUse(this, SHOWCASE_ID);
            Toast.makeText(this, "Showcase reset", Toast.LENGTH_SHORT).show();
        }
    }

    private void presentShowcaseSequence() {

        AssetManager mgr = getAssets();
        Typeface tf = Typeface.createFromAsset(mgr, "showcase/xjlFont.fon");

        ShowcaseConfig config = new ShowcaseConfig();
        config.setTypeface(tf);
        config.setDelay(500); // half second between each showcase view
        if (Util.isZhCN()) {
            config.setContentTextSize(24);
            config.setButtonTextSize(22);
        } else {
            config.setContentTextSize(36);
            config.setButtonTextSize(30);
        }

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, SHOWCASE_ID);

        sequence.setConfig(config);

        MaterialShowcaseView oneShowcaseView = new MaterialShowcaseView.Builder(this)
                .setTarget(mButtonOne).setContentText(R.string.normal_text).build();
        MaterialShowcaseView twoShowcaseView = new MaterialShowcaseView.Builder(this)
                .setTarget(mButtonTwo).setContentText(R.string.short_text).build();
        MaterialShowcaseView threeShowcaseView = new MaterialShowcaseView.Builder(this)
                .setTarget(mButtonThree).setContentText(R.string.long_text).build();
        sequence.addSequenceItem(oneShowcaseView);

        sequence.addSequenceItem(twoShowcaseView);

        sequence.addSequenceItem(threeShowcaseView);

        sequence.start();

    }

}

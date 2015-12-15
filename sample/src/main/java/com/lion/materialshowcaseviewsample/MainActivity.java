package com.lion.materialshowcaseviewsample;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.lion.materialshowcaseview.MaterialShowcaseView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean isFullscreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.btn_simple_example);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_custom_example);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_sequence_example);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_sub_sequence_example);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_reset_all);
        button.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fullscreen_toggle:
                if (isFullscreen) {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                } else {
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams
                            .FLAG_FULLSCREEN);
                }
                isFullscreen = !isFullscreen;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        Intent intent = null;

        switch (v.getId()) {
            case R.id.btn_simple_example:
                intent = new Intent(this, SimpleSingleExample.class);
                break;

            case R.id.btn_custom_example:
                intent = new Intent(this, CustomExample.class);
                break;

            case R.id.btn_sequence_example:
                intent = new Intent(this, SequenceExample.class);
                break;

            case R.id.btn_sub_sequence_example:
                intent = new Intent(this, SubSequenceActivity.class);
                break;

            case R.id.btn_reset_all:
                MaterialShowcaseView.resetAll(this);
                Toast.makeText(this, "All Showcases reset", Toast.LENGTH_SHORT).show();
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}

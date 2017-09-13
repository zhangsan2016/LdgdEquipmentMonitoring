package com.ldgd.equipment.monitoring.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import com.ldgd.equipment.monitoring.R;

public class SingleLampInformationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_single_lamp_information);
    }
}

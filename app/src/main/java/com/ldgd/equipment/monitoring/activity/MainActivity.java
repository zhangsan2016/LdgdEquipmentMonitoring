package com.ldgd.equipment.monitoring.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ldgd.equipment.monitoring.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     /*   Intent intent = new Intent(this,SingleLampInformationActivity.class);
        startActivity(intent);*/
        Intent intent = new Intent(this,SingleLampStateActivity.class);
        startActivity(intent);

    }
}

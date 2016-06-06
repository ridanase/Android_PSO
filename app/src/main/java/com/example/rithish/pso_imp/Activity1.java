package com.example.rithish.pso_imp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Activity1 extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
    }

    public void nextActivity(View view)
    {
        Intent intent = new Intent(Activity1.this, Activity2.class);
        startActivity(intent);
    }
}

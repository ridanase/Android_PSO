package com.example.rithish.pso_imp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Activity4 extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);
        Bundle bundle = getIntent().getExtras();
        TextView tv = (TextView)findViewById(R.id.npValue);
        tv.setTextColor(Color.RED);
        tv.setText(bundle.getCharSequence("Np_Value"));
        TextView tv1 = (TextView)findViewById(R.id.ndValue);
        tv1.setTextColor(Color.RED);
        tv1.setText(bundle.getCharSequence("Nd_Value"));
        TextView tv2 = (TextView)findViewById(R.id.ntValue);
        tv2.setTextColor(Color.RED);
        tv2.setText(bundle.getCharSequence("Nt_Value"));
    }

    public void displayActivity(View v)
    {
        Intent intent = new Intent(Activity4.this, Activity5.class);
        Bundle b = getIntent().getExtras();
        intent.putExtras(b);
        int code = intent.getIntExtra("Code",0);
        System.out.println(code);
        intent.putExtra("Code",code);
        startActivity(intent);
    }
}

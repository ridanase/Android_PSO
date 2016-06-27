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
        /*TextView tv3 = (TextView)findViewById(R.id.c1Value);
        tv3.setTextColor(Color.RED);
        tv3.setText(bundle.getCharSequence("Con1_Value"));
        TextView tv4 = (TextView)findViewById(R.id.c2Value);
        tv4.setTextColor(Color.RED);
        tv4.setText(bundle.getCharSequence("Con2_Value"));
        TextView tv5 = (TextView)findViewById(R.id.chiValue);
        tv5.setTextColor(Color.RED);
        tv5.setText(bundle.getCharSequence("Chi_Value"));*/
    }

    public void displayActivity(View v)
    {
        /*TextView tv = (TextView)findViewById(R.id.npValue);
        String np = tv.getText().toString();

        TextView tv1 = (TextView)findViewById(R.id.ndValue);
        String nd = tv1.getText().toString();

        TextView tv2 = (TextView)findViewById(R.id.ntValue);
        String nt = tv2.getText().toString();

        TextView tv3 = (TextView)findViewById(R.id.c1Value);
        String con1 = tv3.getText().toString();

        TextView tv4 = (TextView)findViewById(R.id.c2Value);
        String con2 = tv4.getText().toString();

        Bundle b = new Bundle();
        b.putString("Np_Value", np);
        b.putString("Nd_Value", nd);
        b.putString("Nt_Value", nt);
        b.putString("Con1_Value", con1);
        b.putString("Con2_Value", con2);*/
        Intent intent = new Intent(Activity4.this, Activity5.class);
        Bundle b = getIntent().getExtras();
        intent.putExtras(b);
        int code = intent.getIntExtra("Code",0);
        System.out.println(code);
        intent.putExtra("Code",code);
        startActivity(intent);
    }

}

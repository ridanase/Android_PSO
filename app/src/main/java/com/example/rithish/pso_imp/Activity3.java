package com.example.rithish.pso_imp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Activity3 extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        //Spinner Element
        dropdown = (Spinner) findViewById(R.id.spinner);

        //Spinner click listener
        dropdown.setOnItemSelectedListener(this);

        //Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select Category");
        categories.add("Rosenbrock Function");
        categories.add("Rastrigin Function");
        categories.add("Griewangk Function");
        categories.add("De Jong's Sphere Function");
        categories.add("Schaffer F6 Function");

        //Creating adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);

        //Drop down layout style - list view with radio button
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Attaching Data adapter to spinner
        dropdown.setAdapter(adapter);

        //Calculating the chi value.
        double chi, phi, num1, num2;
        EditText c1 = (EditText) findViewById(R.id.editText4);
        EditText c2 = (EditText) findViewById(R.id.editText5);
        EditText cons = (EditText) findViewById(R.id.editText6);
        num1 = Double.parseDouble(c1.getText().toString());
        num2 = Double.parseDouble(c2.getText().toString());
        phi = num1 + num2;
        chi = 2.0 / Math.abs(2.0 - phi - Math.sqrt(Math.pow(phi, 2) - 4 * phi));
        cons.setText(Double.toString(chi));
    }


    @Override
    public void onResume() {
        super.onResume();
        dropdown.setSelection(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //On Selecting a Spinner Item
        final Intent intent;
        String np, nd, nt, con1, con2, chi;
        EditText n1 = (EditText)findViewById(R.id.editText);
        np = n1.getText().toString();
        EditText n2 = (EditText)findViewById(R.id.editText2);
        nd = n2.getText().toString();
        EditText n3 = (EditText)findViewById(R.id.editText3);
        nt = n3.getText().toString();
        EditText c1 = (EditText)findViewById(R.id.editText4);
        con1 = c1.getText().toString();
        EditText c2 = (EditText)findViewById(R.id.editText5);
        con2 = c2.getText().toString();
        EditText ch = (EditText)findViewById(R.id.editText6);
        chi = ch.getText().toString();

        switch(position)
        {
            case 1:
                Bundle bundle = new Bundle();
                bundle.putString("Np_Value", np);
                bundle.putString("Nd_Value", nd);
                bundle.putString("Nt_Value", nt);
                bundle.putString("Con1_Value", con1);
                bundle.putString("Con2_Value", con2);
                bundle.putString("Chi_Value", chi);
                Intent myintent = new Intent(Activity3.this, Activity4.class);
                myintent.putExtras(bundle);
                myintent.putExtra("Code",position);
                startActivity(myintent);
                break;
            case 2:
                Bundle bundle1 = new Bundle();
                bundle1.putString("Np_Value", np);
                bundle1.putString("Nd_Value", nd);
                bundle1.putString("Nt_Value", nt);
                bundle1.putString("Con1_Value", con1);
                bundle1.putString("Con2_Value", con2);
                bundle1.putString("Chi_Value", chi);
                Intent myintent1 = new Intent(Activity3.this, Activity4.class);
                myintent1.putExtras(bundle1);
                myintent1.putExtra("Code",position);
                startActivity(myintent1);
                break;
            case 3:
                Bundle bundle2 = new Bundle();
                bundle2.putString("Np_Value", np);
                bundle2.putString("Nd_Value", nd);
                bundle2.putString("Nt_Value", nt);
                bundle2.putString("Con1_Value", con1);
                bundle2.putString("Con2_Value", con2);
                bundle2.putString("Chi_Value", chi);
                Intent myintent2 = new Intent(Activity3.this, Activity4.class);
                myintent2.putExtras(bundle2);
                startActivity(myintent2);
                intent = new Intent(Activity3.this, Activity4.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(Activity3.this, Activity4.class);
                startActivity(intent);
                break;
            case 5:
                intent = new Intent(Activity3.this, Activity4.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){

    }

}


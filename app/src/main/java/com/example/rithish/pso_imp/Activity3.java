package com.example.rithish.pso_imp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

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

        /*//Calculating the chi value.
        double chi, phi, num1, num2;
        EditText c1 = (EditText) findViewById(R.id.editText4);
        EditText c2 = (EditText) findViewById(R.id.editText5);
        EditText cons = (EditText) findViewById(R.id.editText6);
        num1 = Double.parseDouble(c1.getText().toString());
        num2 = Double.parseDouble(c2.getText().toString());
        phi = num1 + num2;
        chi = 2.0 / Math.abs(2.0 - phi - Math.sqrt(Math.pow(phi, 2) - 4 * phi));
        cons.setText(Double.toString(chi));*/
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
        /*EditText c1 = (EditText)findViewById(R.id.editText4);
        con1 = c1.getText().toString();
        EditText c2 = (EditText)findViewById(R.id.editText5);
        con2 = c2.getText().toString();
        EditText ch = (EditText)findViewById(R.id.editText6);
        chi = ch.getText().toString();*/

        switch(position)
        {
            case 1:
                Bundle bundle = new Bundle();
                bundle.putString("Np_Value", np);
                bundle.putString("Nd_Value", nd);
                bundle.putString("Nt_Value", nt);
                /*bundle.putString("Con1_Value", con1);
                bundle.putString("Con2_Value", con2);
                bundle.putString("Chi_Value", chi);*/
                Intent myIntent = new Intent(Activity3.this, Activity4.class);
                myIntent.putExtras(bundle);
                myIntent.putExtra("Code",position);
                startActivity(myIntent);
                break;
            case 2:
                Bundle bundle1 = new Bundle();
                bundle1.putString("Np_Value", np);
                bundle1.putString("Nd_Value", nd);
                bundle1.putString("Nt_Value", nt);
                /*bundle1.putString("Con1_Value", con1);
                bundle1.putString("Con2_Value", con2);
                bundle1.putString("Chi_Value", chi);*/
                Intent myIntent1 = new Intent(Activity3.this, Activity4.class);
                myIntent1.putExtras(bundle1);
                myIntent1.putExtra("Code",position);
                startActivity(myIntent1);
                break;
            case 3:
                Bundle bundle2 = new Bundle();
                bundle2.putString("Np_Value", np);
                bundle2.putString("Nd_Value", nd);
                bundle2.putString("Nt_Value", nt);
                /*bundle2.putString("Con1_Value", con1);
                bundle2.putString("Con2_Value", con2);
                bundle2.putString("Chi_Value", chi);*/
                Intent myIntent2 = new Intent(Activity3.this, Activity4.class);
                myIntent2.putExtras(bundle2);
                myIntent2.putExtra("Code",position);
                startActivity(myIntent2);
                break;
            case 4:
                Bundle bundle3 = new Bundle();
                bundle3.putString("Np_Value", np);
                bundle3.putString("Nd_Value", nd);
                bundle3.putString("Nt_Value", nt);
                Intent myIntent3 = new Intent(Activity3.this, Activity4.class);
                myIntent3.putExtras(bundle3);
                myIntent3.putExtra("Code", position);
                startActivity(myIntent3);
                break;
            case 5:
                Bundle bundle4 = new Bundle();
                bundle4.putString("Np_Value", np);
                bundle4.putString("Nd_Value", nd);
                bundle4.putString("Nt_Value", nt);
                Intent myIntent4 = new Intent(Activity3.this, Activity4.class);
                myIntent4.putExtras(bundle4);
                myIntent4.putExtra("Code", position);
                startActivity(myIntent4);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){

    }

}


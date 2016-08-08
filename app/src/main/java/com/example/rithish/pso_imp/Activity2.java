package com.example.rithish.pso_imp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

public class Activity2 extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        //Spinner Element
        dropdown = (Spinner)findViewById(R.id.spinner);

        //Spinner Drop down elements
        List <String> categories = new ArrayList <String>();
        categories.add("Select Algorithm");
        categories.add("Particle Swarm Optimization");
        //categories.add("Algorithm_2");
        //categories.add("Algorithm_3");

        //Creating adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);

        //Drop down layout style - list view with radio button
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Attaching Data adapter to spinner
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        dropdown.setSelection(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        //On Selecting a Spinner Item
        final Intent intent;

        switch(position)
        {
            case 1:
                intent = new Intent(Activity2.this, Activity3.class);
                startActivity(intent);
                break;
            /*case 2:
                intent = new Intent(Activity2.this, Activity3.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(Activity2.this, Activity3.class);
                startActivity(intent);
                break;*/
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}

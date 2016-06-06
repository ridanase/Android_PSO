package com.example.rithish.pso_imp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class Activity5 extends Activity {

    static TextView tv;
    static TextView ttv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5);
        Intent i = getIntent();
        int position = i.getIntExtra("Code", 0);
        System.out.println(position);
        if(position == 1)
        {
            tv = (TextView)findViewById(R.id.bestValue);
            ttv = (TextView)findViewById(R.id.iteration);
            //Calling Rosenbrock Fitness Function
            func1();
            tv.setTextColor(Color.RED);
            ttv.setTextColor(Color.RED);
            //tv.setText("Success");
            //tv.setText(arrList.toString());
        }
        else
        {
            TextView tv = (TextView)findViewById(R.id.bestValue);
            System.out.println("Select the appropriate fitness function");
            tv.setTextColor(Color.RED);
            //tv.setText("Select the appropriate fitness function");
        }


    }

    public static double fitness(double[] x) {
        double retValue = 0;
        for (int i = 0; i < x.length; i++) {
            retValue = retValue + Math.pow(x[i], 2);
        }

        return retValue;
    }

    // Defining Rosenbrock Fitness Function
    public void func1() {
        double w = 0.9,
                r1 = 0.0, r2 = 0.0,
                xMin = -5.12, xMax = 5.12,
                vMin = 0, vMax = 1,
                wMin = 0.4, wMax = 0.9,
                nInfinite = Double.NEGATIVE_INFINITY,
                gBestValue = nInfinite;

        Bundle bundle = getIntent().getExtras();
        // # of particles
        int Np = Integer.parseInt(bundle.getString("Np_Value"));

        // # of dimensions
        int Nd = Integer.parseInt(bundle.getString("Nd_Value"));

        // # of Time Steps
        int Nt = Integer.parseInt(bundle.getString("Nt_Value"));

        double c1 = Double.parseDouble(bundle.getString("Con1_Value"));
        double c2 = Double.parseDouble(bundle.getString("Con2_Value"));

        double phi = c1 + c2;

        double chi = 2.0 / Math.abs(2.0 - phi - Math.sqrt(Math.pow(phi, 2) - 4 * phi));

        double[] pBestValue = new double[Np],
                gBestPosition = new double[Nd],
                bestFitnessHistory = new double[Nt],
                M = new double[Np];

        double[][] pBestPosition = new double[Np][Nd],
                R = new double[Np][Nd],
                V = new double[Np][Nd];

        ArrayList <Integer> arrList = new ArrayList<Integer>();
        ArrayList <Double> arrList1 = new ArrayList<Double>();

        Random rand = new Random();

        for (int p = 0; p < Np; p++) {
            pBestValue[p] = nInfinite;
        }

        for (int p = 0; p < Np; p++) {
            for (int i = 0; i < Nd; i++) {
                R[p][i] = xMin + (xMax - xMin) * rand.nextDouble();
                V[p][i] = vMin + (vMax - vMin) * rand.nextDouble();

                if (rand.nextDouble() < 0.5) {
                    V[p][i] = -V[p][i];
                    R[p][i] = -R[p][i];
                }
            }
        }

        for (int p = 0; p < Np; p++) {
            M[p] = fitness(R[p]);
            M[p] = -M[p];
        }

        for (int j = 0; j < Nt; j++) {
            for (int p = 0; p < Np; p++) {
                for (int i = 0; i < Nd; i++) {
                    R[p][i] = R[p][i] + V[p][i];

                    if (R[p][i] > xMax) {
                        R[p][i] = xMax;
                    } else if (R[p][i] < xMin) {
                        R[p][i] = xMin;
                    }
                }
            }

            for (int p = 0; p < Np; p++) {

                M[p] = fitness(R[p]);
                M[p] = -M[p];

                if (M[p] > pBestValue[p]) {

                    pBestValue[p] = M[p];
                    for (int i = 0; i < Nd; i++) {
                        pBestPosition[p][i] = R[p][i];
                    }
                }

                if (M[p] > gBestValue) {

                    gBestValue = M[p];
                    for (int i = 0; i < Nd; i++) {
                        gBestPosition[i] = R[p][i];
                    }
                }

            }
            bestFitnessHistory[j] = gBestValue;

            w = wMax - ((wMax - wMin) / Nt) * j;
            for (int p = 0; p < Np; p++) {
                for (int i = 0; i < Nd; i++) {
                    r1 = rand.nextDouble();
                    r2 = rand.nextDouble();
                    V[p][i] = chi * w * (V[p][i] + r1 * c1 * (pBestPosition[p][i] - R[p][i]) + r2 * c2 * (gBestPosition[i] - R[p][i]));

                    // classic Velocity update formulate
                    if (V[p][i] > vMax) {
                        V[p][i] = vMax;
                    } else if (V[p][i] < vMin) {
                        V[p][i] = vMin;
                    }
                }
            }
            //output global best value at current timestep
            /*tv5.setTextColor(Color.RED);
            tv5.setText("Iteration:" + j);
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            System.out.println(arrayList.size());*/
            arrList.add(j);
            arrList1.add(gBestValue);
            System.out.println("iteration: " + j + " BestValue " + gBestValue);
        }
        System.out.println(arrList.toString());
        tv.setText("Iteration: " + arrList.toString());
        ttv.setText("Best Value: " + arrList1.toString());
    }

    /*public static double fitness1(double[] x){
        double retValue = 0;
        for (int i = 0; i < x.length; i++)
        {
            retValue = retValue + (100 * (x[i+1] - Math.pow(x[i], 2)) + Math.pow (1 - x[i], 2));
        }
        return retValue;
    }*/
}
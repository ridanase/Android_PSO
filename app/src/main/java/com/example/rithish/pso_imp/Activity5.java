package com.example.rithish.pso_imp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;
import java.util.ArrayList;
import java.util.Random;

public class Activity5 extends Activity {
    private static TextView tv;
    private static PointsGraphSeries series;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5);
        Intent i = getIntent();
        int position = i.getIntExtra("Code", 0);
        tv = (TextView) findViewById(R.id.bestValue);
        if (position == 1) {
            //Calling Rosenbrock Fitness Function
            tv.setTextColor(Color.RED);
            func1();
        } else if (position == 2) {
            //Calling Rastrigin Fitness Function
            tv.setTextColor(Color.RED);
            func2();
        } else if (position == 3) {
            //Calling Griewangk Fitness Function
            tv.setTextColor(Color.RED);
            func3();
        } else if (position == 4) {
            //Calling De Jong's Sphere Fitness Function
            tv.setTextColor(Color.RED);
            func4();
        } else {
            //Calling Schaffer F6 Function
            tv.setTextColor(Color.RED);
            func5();
        }
    }

    public void navigateActivity(View view)
    {
        Intent intent = new Intent(Activity5.this, Activity3.class);
        startActivity(intent);
    }

    //Implementing Rosenbrock Fitness Function
    public static double fitness1(double[] x){
        double retValue = 0;
        int n = x.length - 1;
        for (int i = 0; i < n; i++)
        {
            retValue = retValue + (100 * Math.pow((x[i+1] - Math.pow(x[i], 2)), 2) + Math.pow ((1 - x[i]), 2));
        }
        return retValue;
    }

    // Calling Rosenbrock Fitness Function
    public void func1() {
        double w = 0.9,
                r1 = 0.0, r2 = 0.0, c1 = 2.05, c2 = 2.05,
                xMin = -5.12, xMax = 5.12,
                vMin = 0, vMax = 1,
                wMin = 0.4, wMax = 0.9,
                nInfinite = Double.NEGATIVE_INFINITY,
                gBestValue = nInfinite;

        Bundle bundle = getIntent().getExtras();
        GraphView graph = (GraphView) findViewById(R.id.graph);

        // # of particles
        int Np = Integer.parseInt(bundle.getString("Np_Value"));
        // # of dimensions
        int Nd = Integer.parseInt(bundle.getString("Nd_Value"));
        // # of Time Steps
        int Nt = Integer.parseInt(bundle.getString("Nt_Value"));

        double phi = c1 + c2;
        double chi = 2.0 / Math.abs(2.0 - phi - Math.sqrt(Math.pow(phi, 2) - 4 * phi));
        double[] pBestValue = new double[Np],
                gBestPosition = new double[Nd],
                bestFitnessHistory = new double[Nt],
                M = new double[Np];

        double[][] pBestPosition = new double[Np][Nd],
                R = new double[Np][Nd],
                V = new double[Np][Nd];

        ArrayList<Integer> arrList = new ArrayList<Integer>();
        ArrayList<Double> arrList1 = new ArrayList<Double>();
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
            M[p] = fitness1(R[p]);
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
                M[p] = fitness1(R[p]);
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
            graph.setTitle("Fitness Chart");
            series = new PointsGraphSeries<DataPoint>(new DataPoint[]{
                        new DataPoint(j, gBestValue)});
            graph.addSeries(series);
            arrList.add(j);
            arrList1.add(gBestValue);
        }
        series.setColor(Color.RED);
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Solution");
        gridLabel.setVerticalAxisTitle("Fitness");
        Double d = arrList1.get(arrList1.size() - 1);
        tv.setText(String.format("Best Value: %.4f ", d));
    }

    // Implementing Rastrigin Function.
    public static double fitness2(double[] x){
        double retValue = 0;
        int n = x.length;
        for (int i = 0; i < n; i++)
        {
            retValue = (retValue + ((10 * n) + ((Math.pow(x[i], 2) - 10 * Math.cos(2 * Math.PI * x[i] )))));
        }
        return retValue;
    }

    //Calling Rastrigin Function.
    public void func2()
    {
        double w = 0.9,
                r1 = 0.0, r2 = 0.0, c1 = 2.05, c2 = 2.05,
                xMin = -5.12, xMax = 5.12,
                vMin = 0, vMax = 1,
                wMin = 0.4, wMax = 0.9,
                nInfinite = Double.NEGATIVE_INFINITY,
                gBestValue = nInfinite;

        Bundle bundle = getIntent().getExtras();
        GraphView graph = (GraphView) findViewById(R.id.graph);

        // # of particles
        int Np = Integer.parseInt(bundle.getString("Np_Value"));
        // # of dimensions
        int Nd = Integer.parseInt(bundle.getString("Nd_Value"));
        // # of Time Steps
        int Nt = Integer.parseInt(bundle.getString("Nt_Value"));

        double phi = c1 + c2;
        double chi = 2.0 / Math.abs(2.0 - phi - Math.sqrt(Math.pow(phi, 2) - 4 * phi));
        double[] pBestValue = new double[Np],
                gBestPosition = new double[Nd],
                bestFitnessHistory = new double[Nt],
                M = new double[Np];

        double[][] pBestPosition = new double[Np][Nd],
                R = new double[Np][Nd],
                V = new double[Np][Nd];

        ArrayList<Integer> arrList = new ArrayList<Integer>();
        ArrayList<Double> arrList1 = new ArrayList<Double>();

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
            M[p] = fitness2(R[p]);
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
                M[p] = fitness2(R[p]);
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
            graph.setTitle("Fitness Chart");
            series = new PointsGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(j, gBestValue)
            });
            graph.addSeries(series);
            arrList.add(j);
            arrList1.add(gBestValue);
        }
        series.setColor(Color.RED);
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Solution");
        gridLabel.setVerticalAxisTitle("Fitness");
        tv.setText(String.format("Best Value: %.4f ", arrList1.get(arrList1.size() - 1)));
    }

    //Implementing Griewangk Fitness Function
    public static double fitness3(double[] x){
        double retValue, sumValue = 0, prodValue = 0;

        for(int i=0; i<x.length; i++){
            sumValue = sumValue + Math.pow(x[i], 2);
            prodValue = prodValue * (Math.cos(x[i]/Math.sqrt(i))+ 1);
        }
        retValue = 1 + sumValue * (1.0/4000.0) - prodValue;
        return retValue;
    }

    public void func3()
    {
        double w = 0.9,
                r1 = 0.0, r2 = 0.0, c1 = 2.05, c2 = 2.05,
                xMin = -5.12, xMax = 5.12,
                vMin = 0, vMax = 1,
                wMin = 0.4, wMax = 0.9,
                nInfinite = Double.NEGATIVE_INFINITY,
                gBestValue = nInfinite;

        Bundle bundle = getIntent().getExtras();
        GraphView graph = (GraphView) findViewById(R.id.graph);

        // # of particles
        int Np = Integer.parseInt(bundle.getString("Np_Value"));
        // # of dimensions
        int Nd = Integer.parseInt(bundle.getString("Nd_Value"));
        // # of Time Steps
        int Nt = Integer.parseInt(bundle.getString("Nt_Value"));

        double phi = c1 + c2;
        double chi = 2.0 / Math.abs(2.0 - phi - Math.sqrt(Math.pow(phi, 2) - 4 * phi));
        double[] pBestValue = new double[Np],
                gBestPosition = new double[Nd],
                bestFitnessHistory = new double[Nt],
                M = new double[Np];

        double[][] pBestPosition = new double[Np][Nd],
                R = new double[Np][Nd],
                V = new double[Np][Nd];

        ArrayList<Integer> arrList = new ArrayList<Integer>();
        ArrayList<Double> arrList1 = new ArrayList<Double>();

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
            M[p] = fitness3(R[p]);
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
                M[p] = fitness3(R[p]);
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
            graph.setTitle("Fitness Chart");
            series = new PointsGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(j, gBestValue)
            });
            graph.addSeries(series);
            arrList.add(j);
            arrList1.add(gBestValue);
        }
        series.setColor(Color.RED);
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Solution");
        gridLabel.setVerticalAxisTitle("Fitness");
        tv.setText(String.format("Best Value: %.4f ", arrList1.get(arrList1.size() - 1)));
    }

    //Implementing De Jong's Sphere Fitness Function
    public static double fitness4(double[] x){
        double retValue = 0;

        for(int i=0; i<x.length; i++){
            retValue = retValue + Math.pow(x[i], 2);
        }

        return retValue;
    }

    //Calling De Jong's Sphere Fitness Function
    public void func4()
    {
        double w = 0.9,
                r1 = 0.0, r2 = 0.0, c1 = 2.05, c2 = 2.05,
                xMin = -5.12, xMax = 5.12,
                vMin = 0, vMax = 1,
                wMin = 0.4, wMax = 0.9,
                nInfinite = Double.NEGATIVE_INFINITY,
                gBestValue = nInfinite;

        Bundle bundle = getIntent().getExtras();
        GraphView graph = (GraphView) findViewById(R.id.graph);

        // # of particles
        int Np = Integer.parseInt(bundle.getString("Np_Value"));
        // # of dimensions
        int Nd = Integer.parseInt(bundle.getString("Nd_Value"));
        // # of Time Steps
        int Nt = Integer.parseInt(bundle.getString("Nt_Value"));

        double phi = c1 + c2;
        double chi = 2.0 / Math.abs(2.0 - phi - Math.sqrt(Math.pow(phi, 2) - 4 * phi));
        double[] pBestValue = new double[Np],
                gBestPosition = new double[Nd],
                bestFitnessHistory = new double[Nt],
                M = new double[Np];

        double[][] pBestPosition = new double[Np][Nd],
                R = new double[Np][Nd],
                V = new double[Np][Nd];

        ArrayList<Integer> arrList = new ArrayList<Integer>();
        ArrayList<Double> arrList1 = new ArrayList<Double>();

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
            M[p] = fitness4(R[p]);
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
                M[p] = fitness4(R[p]);
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
            graph.setTitle("Fitness Chart");
            series = new PointsGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(j, gBestValue)
            });
            graph.addSeries(series);
            arrList.add(j);
            arrList1.add(gBestValue);
        }
        series.setColor(Color.RED);
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Solution");
        gridLabel.setVerticalAxisTitle("Fitness");
        tv.setText(String.format("Best Value: %.4f ", arrList1.get(arrList1.size() - 1)));
    }

    //Implementing Schaffer F6 Fitness Function
    public static double fitness5(double[] x){
        double retValue = 0;
        for(int i=0; i<x.length - 1; i++){
            double z = Math.sin(Math.sqrt(Math.pow(x[i], 2)) + Math.pow(x[i+1], 2));
            double r = 1 + 0.001 * (Math.pow(x[i], 2) + Math.pow(x[i+1], 2));
            retValue = retValue + 0.5 + ((z * z) - 0.5) / (r * r);
        }
        return retValue;
    }

    //Calling Schaffer F6 Fitness Function
    public void func5()
    {
        double w = 0.9,
                r1 = 0.0, r2 = 0.0, c1 = 2.05, c2 = 2.05,
                xMin = -5.12, xMax = 5.12,
                vMin = 0, vMax = 1,
                wMin = 0.4, wMax = 0.9,
                nInfinite = Double.NEGATIVE_INFINITY,
                gBestValue = nInfinite;

        Bundle bundle = getIntent().getExtras();
        GraphView graph = (GraphView) findViewById(R.id.graph);

        // # of particles
        int Np = Integer.parseInt(bundle.getString("Np_Value"));
        // # of dimensions
        int Nd = Integer.parseInt(bundle.getString("Nd_Value"));
        // # of Time Steps
        int Nt = Integer.parseInt(bundle.getString("Nt_Value"));

        double phi = c1 + c2;
        double chi = 2.0 / Math.abs(2.0 - phi - Math.sqrt(Math.pow(phi, 2) - 4 * phi));
        double[] pBestValue = new double[Np],
                gBestPosition = new double[Nd],
                bestFitnessHistory = new double[Nt],
                M = new double[Np];

        double[][] pBestPosition = new double[Np][Nd],
                R = new double[Np][Nd],
                V = new double[Np][Nd];

        ArrayList<Integer> arrList = new ArrayList<Integer>();
        ArrayList<Double> arrList1 = new ArrayList<Double>();

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
            M[p] = fitness5(R[p]);
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
                M[p] = fitness5(R[p]);
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
            graph.setTitle("Fitness Chart");
            series = new PointsGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(j, gBestValue)
            });
            graph.addSeries(series);
            arrList.add(j);
            arrList1.add(gBestValue);
        }
        series.setColor(Color.RED);
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Solution");
        gridLabel.setVerticalAxisTitle("Fitness");
        tv.setText(String.format("Best Value: %.4f ", arrList1.get(arrList1.size() - 1)));
    }
}
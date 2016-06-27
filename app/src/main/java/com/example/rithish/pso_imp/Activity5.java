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

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;

public class Activity5 extends Activity {
    private static TextView tv;
    private static TextView ttv;
    //private static LineGraphSeries<DataPoint> series;
    private static PointsGraphSeries series;
    private static GraphView graph;
    private static Viewport viewport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5);
        Intent i = getIntent();
        int position = i.getIntExtra("Code", 0);
        System.out.println(position);
        tv = (TextView)findViewById(R.id.bestValue);
        if(position == 1)
        {
            //tv = (TextView)findViewById(R.id.bestValue);
            //Calling Rosenbrock Fitness Function
            tv.setTextColor(Color.RED);
            func1();
            //ttv.setTextColor(Color.RED);
            //tv.setText("Success");
            //tv.setText(arrList.toString());
        }
        else if (position == 2)
        {
            //tv = (TextView)findViewById(R.id.bestValue);
            //Calling Rastrigin Fitness Function
            tv.setTextColor(Color.RED);
            func2();
        }

        else if (position == 3)
        {
            //Calling Griewangk Fitness Function
            System.out.println("Please select appropriate fitness function");
        }

        else if (position == 4)
        {
            //Calling De Jong's Sphere Fitness Function
        }
        else
        {
            //Calling Schaffer F6 Function
        }
        //graph = (GraphView)findViewById(R.id.graph);
        //viewport = graph.getViewport();
        /*GraphView graph = (   GraphView)findViewById(R.id.graph);
        for (int j = 0; j < 10; j++)
        {
                    series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                    new DataPoint(0, 1),
                    new DataPoint(1, 5),
                    new DataPoint(2, 3),
                    new DataPoint(3, 2),
                    new DataPoint(4, 6)
            });
        }
        graph.addSeries(series);
*/    }

    public static double fitness(double[] x) {
        double retValue = 0;
        for (int i = 0; i < x.length; i++) {
            retValue = retValue + Math.pow(x[i], 2);
        }
        return retValue;
    }

    /*public static DataPoint[] generateData()
    {
        int count = 30;
        Random mRand = new Random();
        DataPoint[] values = new DataPoint[count];
        for(int i = 0; i < count; i++)
        {
            double x = i;
            double f = mRand.nextDouble()*0.15+0.3;
            double y = Math.sin(i*f+2) + mRand.nextDouble()*0.3;
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        return values;
    }*/

    public static double fitness1(double[] x){
        double retValue = 0;
        int n = x.length - 1;
        for (int i = 0; i < n; i++)
        {
            System.out.println("I Value:" +i);
            System.out.println("Length:" +x.length);
            retValue = retValue + (100 * Math.pow((x[i+1] - Math.pow(x[i], 2)), 2) + Math.pow ((1 - x[i]), 2));
            System.out.println("Return Value:" +retValue);
        }
        return retValue;
    }

    // Defining Rosenbrock Fitness Function
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

        /*double c1 = Double.parseDouble(bundle.getString("Con1_Value"));
        double c2 = Double.parseDouble(bundle.getString("Con2_Value"));*/

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
        ArrayList<DataPoint> arrList2 = new ArrayList<DataPoint>();
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

            /*for(int k = 0; k < Nt; k++)
            {
                DataPoint[] values = new DataPoint[Nt];
                double x = k;
                System.out.println(gBestValue);
                double y = gBestValue;
                DataPoint v = new DataPoint(x, y);
                values[k] = v;
                System.out.println(values);
                arrList2.add(v);
            }*/
            /*DataPoint values[] = new DataPoint[Nt];
            DataPoint v = new DataPoint(j, gBestValue);
            System.out.println(v.toString());
            values[j] = v;
            series = new LineGraphSeries<DataPoint>(values);
            graph.addSeries(series);*/
            graph.setTitle("Fitness Chart");
            series = new PointsGraphSeries<DataPoint>(new DataPoint[]{
                        new DataPoint(j, gBestValue)
                });
                graph.addSeries(series);

             //output global best value at current timestep
            /*tv5.setTextColor(Color.RED);
            tv5.setText("Iteration:" + j);
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            System.out.println(arrayList.size());*/
            arrList.add(j);
            arrList1.add(gBestValue);
            System.out.println("iteration: " + j + " BestValue " + gBestValue);
            /*graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setMinX(gBestValue);
            graph.getViewport().setMinY(j);*/
        }
        series.setColor(Color.RED);
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Solution");
        gridLabel.setVerticalAxisTitle("Fitness");
        /*NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(0);
        nf.setMinimumFractionDigits(2);
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf, nf));*/
        //graph.getLegendRenderer().setVisible(true);
        //graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        System.out.println(arrList.toString());
        /*tv.setText("Iteration: " + arrList.toString());*/
        tv.setText("Best Value: " + arrList1.get(arrList1.size() - 1));
    }

    // Implemented Rastrigin Function.
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

        /*double c1 = Double.parseDouble(bundle.getString("Con1_Value"));
        double c2 = Double.parseDouble(bundle.getString("Con2_Value"));*/

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
        ArrayList<DataPoint> arrList2 = new ArrayList<DataPoint>();
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

            /*for(int k = 0; k < Nt; k++)
            {
                DataPoint[] values = new DataPoint[Nt];
                double x = k;
                System.out.println(gBestValue);
                double y = gBestValue;
                DataPoint v = new DataPoint(x, y);
                values[k] = v;
                System.out.println(values);
                arrList2.add(v);
            }*/
            /*DataPoint values[] = new DataPoint[Nt];
            DataPoint v = new DataPoint(j, gBestValue);
            System.out.println(v.toString());
            values[j] = v;
            series = new LineGraphSeries<DataPoint>(values);
            graph.addSeries(series);*/
            graph.setTitle("Fitness Chart");
            series = new PointsGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(j, gBestValue)
            });
            graph.addSeries(series);

            //output global best value at current timestep
            /*tv5.setTextColor(Color.RED);
            tv5.setText("Iteration:" + j);
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            System.out.println(arrayList.size());*/
            arrList.add(j);
            arrList1.add(gBestValue);
            System.out.println("iteration: " + j + " BestValue " + gBestValue);
            /*graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setMinX(gBestValue);
            graph.getViewport().setMinY(j);*/
        }
        series.setColor(Color.RED);
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Solution");
        gridLabel.setVerticalAxisTitle("Fitness");
        /*NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(0);
        nf.setMinimumFractionDigits(2);
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf, nf));*/
        //graph.getLegendRenderer().setVisible(true);
        //graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        System.out.println(arrList.toString());
        /*tv.setText("Iteration: " + arrList.toString());*/
        tv.setText("Best Value: " + arrList1.get(arrList1.size() - 1));
    }
}
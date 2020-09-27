package com.example.thingsspeak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    TextView tvTempValue;
    //TextView tvError;
    //TextView tvDateValue;

    TextView tvHumValue;
    TextView tvHumDateValue;

    GraphView graphView1;
    GraphView graphView2;

    public static final int NUM = 50;
    int[] resultsTemp;
    int[] resultsHumidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTempValue = findViewById(R.id.tv_temp_value);
        //tvError = findViewById(R.id.tv_error);
        //tvDateValue = findViewById(R.id.tv_temp_date_value);

        tvHumValue = findViewById(R.id.tv_hum_value);
        tvHumDateValue = findViewById(R.id.tv_hum_date_value);

        graphView1 = (GraphView) findViewById(R.id.graph_view1);
        graphView1.setTitle("Температура");

        graphView2 = (GraphView) findViewById(R.id.graph_view2);
        graphView2.setTitle("Влажность");

        resultsTemp = new int[NUM];
        resultsHumidity = new int[NUM];
        for(int i=0;i<NUM;i++){
            resultsTemp[i] = 0;
            resultsHumidity[i] = 0;
        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        dataSource()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        Observable<Integer> observable = Observable.just(1,2,3);
        observable.subscribe(
                integer -> Log.i("MyTag", String.valueOf(integer)),
                throwable -> {/*handle error*/}
                );
        /*observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e("MyTag",String.valueOf(integer));
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });*/
        //Observer<Integer> dispose = observable.subscribe();
        ////////////////////////////////////////////////////////////////////////////////////////////


    }

    public Observable<Integer> dataSource(){
        return Observable.create(subscriber -> {
            getRequest();
        });
    }

    public void getRequest(){



        /*String url = "https://api.thingspeak.com/channels/1010014/feeds.json?api_key=VORVMNWZBM5VZDET&results=50";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray feeds = response.getJSONArray("feeds");

                            int res = feeds.length();
                            JSONObject eachData = feeds.getJSONObject(res-1);
                            String  address = eachData.getString("field1");
                            tvTempValue.setText(address);

                            String  address2 = eachData.getString("field2");
                            tvHumValue.setText(address2);

                            String  addressDate2 = eachData.getString("created_at");
                            tvHumDateValue.setText(addressDate2);

                            ////////////////////////////////////////////////////////////////////////

                            for(int i=0;i<NUM;i++){
                                JSONObject eachData2 = feeds.getJSONObject(res-NUM+i);
                                String temp = eachData2.getString("field1");
                                if(temp == "null")
                                    resultsTemp[i] = 0;
                                else resultsTemp[i] = Integer.parseInt(temp);
                                temp = eachData2.getString("field2");
                                if(temp == "null")
                                    resultsHumidity[i] = 0;
                                else resultsHumidity[i] = Integer.parseInt(temp);
                            }
                            ////////////////////////////////////////////////////////////////////////

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(getApplicationContext(),R.string.error_response,Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);*/
    }

    public void graphData(){
        DataPoint[] dataPoints1 = new DataPoint[resultsTemp.length];
        for(int i =0;i<resultsTemp.length;i++){
            dataPoints1[i] = new DataPoint(i, resultsTemp[i]);
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints1);
        graphView1.removeAllSeries();
        graphView1.addSeries(series);

        DataPoint[] dataPoints2 = new DataPoint[resultsHumidity.length];
        for(int i =0;i<resultsHumidity.length;i++){
            dataPoints2[i] = new DataPoint(i, resultsHumidity[i]);
        }
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(dataPoints2);
        graphView2.removeAllSeries();
        graphView2.addSeries(series2);
    }

    public void onBtnRequestClick(View view) {

        String temp = "Waiting for response...";
        tvTempValue.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.green));
        tvTempValue.setText(temp);

        //tvDateValue.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.green));
        //tvDateValue.setText(temp);

        tvHumValue.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.green));
        tvHumValue.setText(temp);

        tvHumDateValue.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.green));
        tvHumDateValue.setText(temp);

        for(int i=0;i<NUM;i++){
            resultsTemp[i] = 0;
            resultsHumidity[i] = 0;
        }

        //tvError.setText("Test");

        String url = "https://api.thingspeak.com/channels/1010014/feeds.json?api_key=VORVMNWZBM5VZDET&results=50";
        //String url = "https://api.thingspeak.com/channels/1010014/fields/1.json?api_key=VORVMNWZBM5VZDET&results=2";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        //tvTempValue.setText("Response: " + response.toString());

                        try {
                            JSONArray feeds = response.getJSONArray("feeds");

                            int res = feeds.length();
                            JSONObject eachData = feeds.getJSONObject(res-1);
                            String  address = eachData.getString("field1");
                            tvTempValue.setText(address);

                            //String  addressDate = eachData.getString("created_at");
                            //tvDateValue.setText(addressDate);

                            /*for(int i=0; i<feeds.length();i++) {
                                JSONObject jo = feeds.getJSONObject(i);
                                String res = jo.getString("1");
                                tvTempValue.setText(res);
                            }*/

                            String  address2 = eachData.getString("field2");
                            tvHumValue.setText(address2);

                            String  addressDate2 = eachData.getString("created_at");
                            tvHumDateValue.setText(addressDate2);

                            ////////////////////////////////////////////////////////////////////////

                            for(int i=0;i<NUM;i++){
                                JSONObject eachData2 = feeds.getJSONObject(res-NUM+i);
                                String temp = eachData2.getString("field1");
                                if(temp == "null")
                                    resultsTemp[i] = 0;
                                else resultsTemp[i] = Integer.parseInt(temp);
                                temp = eachData2.getString("field2");
                                if(temp == "null")
                                    resultsHumidity[i] = 0;
                                else resultsHumidity[i] = Integer.parseInt(temp);
                            }
                            ////////////////////////////////////////////////////////////////////////

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //tvError.setText("Error Response");
                        tvTempValue.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.red));
                        tvTempValue.setText(R.string.error_response);
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

        //if(tvTempValue.getText().equals(temp))
        //    tvError.setText("Can not get response!");
        //else tvError.setText("Ok");

    }

    public void onBtnGraphPlotClick(View view) {
        DataPoint[] dataPoints1 = new DataPoint[resultsTemp.length];
        for(int i =0;i<resultsTemp.length;i++){
            dataPoints1[i] = new DataPoint(i, resultsTemp[i]);
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints1);
        graphView1.removeAllSeries();
        graphView1.addSeries(series);

        DataPoint[] dataPoints2 = new DataPoint[resultsHumidity.length];
        for(int i =0;i<resultsHumidity.length;i++){
            dataPoints2[i] = new DataPoint(i, resultsHumidity[i]);
        }
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(dataPoints2);
        graphView2.removeAllSeries();
        graphView2.addSeries(series2);
    }
}

package com.example.chs.postmates_android;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        PostmatesAPI api = new PostmatesAPI();
        api.postDeliveryQuote(new DeliveryQuote("20 McAllister St, San Francisco, CA" ,
                "101 Market St, San Francisco, CA"), new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String respStr = response.body().string();
                try{
                    JSONObject quote = new JSONObject(respStr);
                    Log.d("Quote: " , quote.toString());
                }
                catch (JSONException e){

                }
            }
        });

        /*
        api.getAllDeliveries(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String respStr = response.body().string();
                try{
                    JSONObject allDeliveries = new JSONObject(respStr);
                    Log.d("Deliveries List: " , allDeliveries.toString());
                }
                catch (JSONException e){

                }
            }
        });
        */

        /*
        api.postDelivery(null, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String respStr = response.body().string();
                try{
                    JSONObject postDelivResp = new JSONObject(respStr);
                    Log.d("Post Delivery: " , postDelivResp.toString());
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
        */

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

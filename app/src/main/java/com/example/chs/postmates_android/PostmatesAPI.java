package com.example.chs.postmates_android;

import android.util.Log;

import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.net.Proxy;
import java.util.HashMap;

import javax.security.auth.callback.Callback;
import okio.BufferedSink;

/**
 * Created by snaheth on 10/10/15.
 */
public class PostmatesAPI {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String baseUrl = "https://api.postmates.com/v1/";

    private static final String customerId = "cus_KBJleyUPBKQz3-";
    private static final String authKey = "f5c0691e-d66b-485a-8d52-145c2d38942f";

    private OkHttpClient client;

    public PostmatesAPI(){
        client = new OkHttpClient();
        client.setAuthenticator(new Authenticator() {
            @Override
            public Request authenticate(Proxy proxy, Response response) throws IOException {
                String basicAuth = Credentials.basic(authKey, "");
                return response.request().newBuilder().header("Authorization", basicAuth).build();
            }

            @Override
            public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
                return null;
            }
        });
    }

    //POST https://api.postmates.com/v1/customers/:customer_id/delivery_quotes
    public void postDeliveryQuote(DeliveryQuote quote, com.squareup.okhttp.Callback callback){
        String url = baseUrl + "customers/" + customerId + "/delivery_quotes";
        String json = "{ \"dropoff_address\" : " +  "\"" + quote.getDropoffAddress() + "\"" +" , \"pickup_address\" : "
                + "\"" + quote.getPickupAddress() + "\"" + "}";
        Log.v("JSON" , json);

        try{
            post(url, json, callback);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //POST https://api.postmates.com/v1/customers/:customer_id/deliveries
    public void postDelivery(Delivery delivery, com.squareup.okhttp.Callback callback){
       String url = baseUrl + "customers/" + customerId + "/deliveries";
       JSONObject postParams = new JSONObject(delivery.hashMapRepresentation());
       try{
           post(url, postParams.toString(), callback);
       }
       catch (IOException e){
           e.printStackTrace();
       }
    }

    //GET https://api.postmates.com/v1/customers/:customer_id/deliveries
    public void getAllDeliveries(com.squareup.okhttp.Callback callback){
        String url = baseUrl + "customers/" + customerId + "/deliveries";

        try{
            get(url, callback);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public Call get(String url, com.squareup.okhttp.Callback callback) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public Call post(String url, String json, com.squareup.okhttp.Callback callback) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }
}

package com.smarthome;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.smarthome.Data.Request;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyAsync extends AsyncTask<String, Void, Integer> {

    private Context mContext;

    private static final String TAG = MyAsync.class.getSimpleName();

    public MyAsync() {
    }

    public MyAsync(Context context) {
        //mContext = context;
    }

    //Execute this before the request is made
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(String... params) {

        HttpURLConnection urlConnection;

        try {
            //1. Open a new URL connection
            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            //urlConnection.setDoOutput(true);
/*
            //2. Defines a HTTP request type
            urlConnection.setRequestMethod("GET"); // or POST

            //3. Defines a HTTP header
            urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            //urlConnection.setRequestProperty("Content-length",//);
            //urlConnection.setRequestProperty("Content-language", "en-US");

            //4. Defines a HTTP body
            //urlConnection.setFixedLengthStreamingMode(contentLength);
            //Post data in JSON format
           *//* JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("id","value");
            }catch (JSONException e){
                e.printStackTrace();
            }*//*
            Request request = new Request("dbcall", 104, "");
            Gson gson = new Gson();
            String requestJson = gson.toJson(request);
            Log.i(TAG, requestJson);

            //5. Make the request
            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            writeStream(out, requestJson);

            //6. Read response
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            readStream(in);*/

            //Get response code from the request: 200/500
            int responseCode = urlConnection.getResponseCode();
            Log.i(TAG, Integer.toString(responseCode));

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return -1;
    }

    private void writeStream(OutputStream os, String json) {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));

        try {
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    private String readStream(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder builder = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return builder.toString();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        Log.i(TAG, "Response code: " + integer);
    }


    @Override
    protected void onCancelled(Integer integer) {
        super.onCancelled(integer);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}

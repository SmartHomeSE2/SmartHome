package com.smarthome;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

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

public class NetRequest extends AsyncTask<String, Void, Integer> {

    private static final String TAG = NetRequest.class.getSimpleName();

    public NetRequest() {
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
            //1. Open a new URL connection]
            String urlPrefix = "http://83.248.250.216:8000/";
            String query = params[0];
            URL url = new URL(urlPrefix + query);
            urlConnection = (HttpURLConnection) url.openConnection();
            Log.i(TAG, "urlConnection: " + urlConnection.toString());

            //2. Defines a HTTP request type
            urlConnection.setRequestMethod("POST"); //by default if setDoOutput(true) has been called

            //3. Defines a HTTP header
            urlConnection.setRequestProperty("Charset", "UTF-8");
            urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Content-language", "en-US");
            /*urlConnection.setConnectTimeout(TIMEOUT_CONNECT);
            urlConnection.setReadTimeout(TIMEOUT_READ);*/

            //4. Defines a HTTP body
            JSONObject cred = new JSONObject();
            try {
                //loginUser
                cred.put("email", "something@something.com");
                cred.put("password", "hej");

                //checkDevice
                //cred.put("id","109");

                //toggleDevice
                /*cred.put("id","109");
                cred.put("value","0");*/

                //setTemp: Device issue, input value = Celsius + 100, Celsius range -40 ~ 40
                /*cred.put("id", "110");
                cred.put("value", "100");*/

                //registerUser: Database issue
                /*cred.put("userName", "df");
                cred.put("email", "mobile@mobile.com");
                cred.put("password", "hi");*/
                Log.i(TAG, "requestBody: " + cred);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            urlConnection.connect(); //Operations like getInputStream, getOutputStream, will implicitly invoke connection.connect(), if necessary.

            //5. Write to the request
            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            writeStream(out, cred.toString());


            //6. Read response
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String response = readStream(in);
            Log.i(TAG, "response: " + response);

            //Get response code from the request: 200/500
            int responseCode = urlConnection.getResponseCode();
            Log.i(TAG, "responseCode: " + Integer.toString(responseCode));

            urlConnection.disconnect();
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

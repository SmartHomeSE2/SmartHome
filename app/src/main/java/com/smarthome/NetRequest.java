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

        HttpURLConnection urlConnection = null;

        try {
            //1. Obtain a HttpURLConnection object
            String urlPrefix = "http://83.248.250.216:8000/";
            String path = params[0];
            URL url = new URL(urlPrefix + path);
            urlConnection = (HttpURLConnection) url.openConnection();
            Log.i(TAG, "urlConnection: " + urlConnection.toString());

            //2. Defines a HTTP request type and header
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Charset", "UTF-8");
            urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Content-language", "en-US");
            /*urlConnection.setConnectTimeout(TIMEOUT_CONNECT);
            urlConnection.setReadTimeout(TIMEOUT_READ);*/

            //3. Defines a HTTP body
            JSONObject requestJason = setRequestBody(params);
            if (requestJason == null) {
                return -1;
            }

            //4. Write to the request
            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            writeStream(out, requestJason.toString());

            //5. Get response code (200/500) and read response
            int responseCode = urlConnection.getResponseCode();
            Log.i(TAG, "responseCode: " + Integer.toString(responseCode));
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String response = readStream(in);
            Log.i(TAG, "response: " + response);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            //6. Close the connection
            if (urlConnection != null) urlConnection.disconnect();
        }

        return -1;
    }

    private JSONObject setRequestBody(String... params) {
        JSONObject object = new JSONObject();
        String path = params[0];
        try {
            switch (path) {
                case "loginUser":
                    String email_login = params[1];
                    String password_login = params[2];
                    object.put("email", email_login);
                    object.put("password", password_login);
                    break;
                case "checkDevice":
                    String id_check = params[1];
                    object.put("id", id_check);
                    break;
                case "toggleDevice":
                    String id_toggle = params[1];
                    String value_toggle = params[2];
                    object.put("id", id_toggle);
                    object.put("value", value_toggle);
                    break;
                case "registerUser":
                    String userName = params[1];
                    String email_register = params[2];
                    String password_register = params[3];
                    object.put("userName", userName);
                    object.put("email", email_register);
                    object.put("password", password_register);
                case "setTemp":
                    //setTemp id can be 108 or 110
                    //Input value = Celsius + 100, Celsius range -40 ~ 40
                    String id_setTemp = params[1];
                    String value_setTemp = String.valueOf(Integer.valueOf(params[2]) + 100);
                    object.put("id", id_setTemp);
                    object.put("value", value_setTemp);
                    break;
                default:
                    return null;
            }
            Log.i(TAG, "requestBody: " + object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    private void writeStream(OutputStream os, String json) {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));

        try {
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String readStream(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line;
        StringBuilder builder = new StringBuilder();
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

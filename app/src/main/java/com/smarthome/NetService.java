package com.smarthome;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.smarthome.Data.NetResponse;

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

public class NetService extends AsyncTask<String, Void, NetResponse> {

    private static final String TAG = NetService.class.getSimpleName();

    private NetResponseListener mResponseListener;

    public NetService() {
    }

    public NetService(Context context) {
        mResponseListener = (NetResponseListener) context;
    }

    public NetService(NetResponseListener listener) {
        mResponseListener = listener;
    }

    //Execute this before the request is made
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected NetResponse doInBackground(String... params) {

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
            String requestJason = setRequestBody(params);
            Log.i(TAG, "requestBody: " + requestJason);
            if (requestJason == null) {
                Log.i(TAG, "JASON is null");
                return null;
            }

            //4. Write to the request
            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            writeStream(out, requestJason);

            //5. Get response code (200/500) and read response
            int responseCode = urlConnection.getResponseCode();
            Log.i(TAG, "responseCode: " + Integer.toString(responseCode));
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String response = readStream(in);
            Log.i(TAG, "response: " + response);

            NetResponse netResponse = new NetResponse();
            netResponse.setResponseCode(responseCode);
            netResponse.setResponse(response);
            return netResponse;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            //6. Close the connection
            if (urlConnection != null) urlConnection.disconnect();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(NetResponse netResponse) {
        super.onPostExecute(netResponse);
        if (mResponseListener != null) {
            mResponseListener.onFinishNetRequest(netResponse);
        } else {
            Log.i(TAG, "listener is null");
        }
    }

    @Override
    protected void onCancelled(NetResponse netResponse) {
        super.onCancelled(netResponse);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }


    private String setRequestBody(String... params) {
        JSONObject object = new JSONObject();
        String path = params[0];
        try {
            switch (path) {
                case Constants.LOGIN_USER:
                    String email_login = params[1];
                    String password_login = params[2];
                    object.put("email", email_login);
                    object.put("password", password_login);
                    break;
                case Constants.CHECK_DEVICE:
                    String id_check = params[1];
                    object.put("id", id_check);
                    break;
                case Constants.TOGGLE_DEVICE:
                    String id_toggle = params[1];
                    object.put("id", id_toggle);
                    break;
                case Constants.REGISTER_USER:
                    String userName = params[1];
                    String email_register = params[2];
                    String password_register = params[3];
                    object.put("userName", userName);
                    object.put("email", email_register);
                    object.put("password", password_register);
                    break;
                case Constants.SET_TEMP:
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "requestBody: " + object);
        return object.toString();
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
                builder.append(line);
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

    public interface NetResponseListener {
        void onFinishNetRequest(NetResponse netResponse);
    }

    public void setNetResponseListener(NetResponseListener listener) {
        mResponseListener = listener;
    }
}
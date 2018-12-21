package com.smarthome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText edit_email_login;
    private EditText edit_password_login;
    private Button button_sign_in_login;
    private CheckBox checkBox_rememberMe;
    private TextView text_register_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(TAG, "onCreate");

        edit_email_login = findViewById(R.id.edit_email_login);
        edit_password_login = findViewById(R.id.edit_password_login);

        button_sign_in_login = findViewById(R.id.button_sign_in_login);
        button_sign_in_login.setOnClickListener(this);

        text_register_login = findViewById(R.id.text_register_login);
        text_register_login.setOnClickListener(this);

        checkBox_rememberMe = findViewById(R.id.checkBox_remember_login);
        checkBox_rememberMe.setOnClickListener(this);

        // Lode data from sharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("Login credential", MODE_PRIVATE);
        if (sharedPreferences != null) {
            String saved_email = sharedPreferences.getString("email", null);
            String saved_password = sharedPreferences.getString("password", null);
            edit_email_login.setText(saved_email);
            edit_password_login.setText(saved_password);
            checkBox_rememberMe.setChecked(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //
    }

    @Override
    public void onClick(View v) {
        if (v == text_register_login) {
            Log.i(TAG, "register clicked");
            RegisterFragment registerFragment = new RegisterFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.layout_login_content_login, registerFragment).addToBackStack(null).commit();
        }

        if (v == button_sign_in_login) {
            Log.i(TAG, "Login clicked");

            String email = edit_email_login.getText().toString();
            String password = edit_password_login.getText().toString();

            if (StringUtils.isNoneBlank(email, password)) {
                login(email, password);
            } else {
                // Print email/password empty/null message for user
                Log.i(TAG, "Empty email/pass");
            }

            //myAsync.execute("checkDevice");
            //myAsync.execute("toggleDevice");
            //myAsync.execute("setTemp"); wait for Device's team to tackle it
            //myAsync.execute("registerUser");
        }

        if (v == checkBox_rememberMe) {
            if (checkBox_rememberMe.isChecked()) {
                String email = edit_email_login.getText().toString();
                String password = edit_password_login.getText().toString();
                if (StringUtils.isNoneBlank(email, password)) {
                    SharedPreferences.Editor editor = getSharedPreferences("Login credential", MODE_PRIVATE).edit();
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.apply();
                } else {
                    // Print email/password empty/null message for user
                    Log.i(TAG, "Empty email/pass to remember");
                }

            }
        }
    }

    private void login(String email, String password) {
        NetRequest request = new NetRequest(new NetRequest.NetResponseListener() {
            @Override
            public void onFinishNetRequest(NetResponse netResponse) {
                int responseCode = netResponse.getResponseCode();
                String response = netResponse.getResponse();
                Log.i(TAG, "responseCode: " + String.valueOf(responseCode));
                Log.i(TAG, "response: " + response);

                if (responseCode == 200 && response.equals("exists")) {
                    startDashBoard();
                } else {
                    // Print out error hint to user
                    Log.i(TAG, "Wrong login credentials");
                }
            }
        });
        request.execute("loginUser", email, password);
    }

    private void startDashBoard() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

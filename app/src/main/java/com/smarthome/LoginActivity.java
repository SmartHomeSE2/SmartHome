package com.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private EditText edit_email_login;
    private EditText edit_password_login;
    private Button button_sign_in_login;
    private TextView text_register_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(TAG, "onCreate");

        edit_email_login = findViewById(R.id.edit_email_login);
        edit_password_login = findViewById(R.id.edit_password_login);

        text_register_login = findViewById(R.id.text_register_login);
        text_register_login.setOnClickListener(this);

        button_sign_in_login = findViewById(R.id.button_sign_in_login);
        button_sign_in_login.setOnClickListener(this);
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
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);

            String email = edit_email_login.getText().toString();
            String password = edit_password_login.getText().toString();

            if (StringUtils.isNoneBlank(email, password)) {
                login(email, password);
            }

            //myAsync.execute("checkDevice");
            //myAsync.execute("toggleDevice");
            //myAsync.execute("setTemp"); wait for Device's team to tackle it
            //myAsync.execute("registerUser");
        }
    }

    private void login(String email, String password) {
        boolean isSuccessful = false;

        NetRequest request = new NetRequest();
        request.execute("loginUser", email, password);
    }

    private boolean isAdressValid(String address) {
        // email address = accountName + sign + domainName
        // Find the sign '@'
        int pos = address.indexOf("@");
        //If the address does not contain an '@', it's not valid
        if (pos == -1) return false;

        // http://chillyfacts.com/check-an-email-exist-using-java-java-email-verification-and-validation/
        return true;
    }
}

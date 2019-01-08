package com.smarthome;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smarthome.Data.NetResponse;

import org.apache.commons.lang3.StringUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = RegisterFragment.class.getSimpleName();

    private EditText edit_userName;
    private EditText edit_email;
    private EditText edit_password;
    private EditText edit_repeat_password;
    private Button btn_register;
    private TextView action_login;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        edit_userName = view.findViewById(R.id.edit_username_register);
        edit_email = view.findViewById(R.id.edit_email_register);
        edit_password = view.findViewById(R.id.edit_password_register);
        edit_repeat_password = view.findViewById(R.id.edit_repeatPassword_register);

        btn_register = view.findViewById(R.id.button_create_account_register);
        btn_register.setOnClickListener(this);

        action_login = view.findViewById(R.id.text_login_register);
        action_login.setOnClickListener(this);

        return view;
    }


    private boolean isAddressValid(String address) {
        return (StringUtils.isNotBlank(address) && Patterns.EMAIL_ADDRESS.matcher(address).matches());
    }

    @Override
    public void onClick(View view) {
        if (view == btn_register) {
            Log.i(TAG, "button clicked");
            String userName = edit_userName.getText().toString();
            String email = edit_email.getText().toString();
            String password = edit_password.getText().toString();
            String repeat_password = edit_repeat_password.getText().toString();

            if (StringUtils.isNoneBlank(userName, email, password, repeat_password) && isAddressValid(email) && password.equals(repeat_password)) {
                Log.i(TAG, "internet call");


                NetService registerUser = new NetService(new NetService.NetResponseListener() {
                    @Override
                    public void onFinishNetRequest(NetResponse netResponse) {
                        if (netResponse != null) {
                            int responseCode = netResponse.getResponseCode();
                            String response = netResponse.getResponse();
                            Log.i(TAG, "responseCode: " + String.valueOf(responseCode));
                            Log.i(TAG, "response: " + response);

                            if (responseCode == 200 && response.equals("New user created")) {
                                startLoginActivity();
                                Toast.makeText(getContext(), "New user created successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Register user failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Error on server, please try later", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                registerUser.execute(Constants.REGISTER_USER, userName, email, password);
            }
        }

        if (view == action_login) {
            startLoginActivity();
        }
    }

    private void startLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}

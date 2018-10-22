package com.smarthome;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DashboardActivity";
    private Button button_manage_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        button_manage_item = findViewById(R.id.button_manage_item);
        button_manage_item.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == button_manage_item) {
            Log.i(TAG, "button clicked");
            DeviceListFragment deviceListFragment = new DeviceListFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.layout_content_dashboard, deviceListFragment).addToBackStack(null).commit();
        }
    }
}

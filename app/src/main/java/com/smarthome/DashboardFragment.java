package com.smarthome;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.Toast;

import com.smarthome.Data.Device;
import com.smarthome.Data.NetResponse;

import java.util.ArrayList;

=======
import android.widget.Button;
import android.widget.TextView;

// Todo: Fix response delay problem
>>>>>>> 5e26d55a8b8a825b5ef95a7bb63d388c95ccb927
/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements DashboardAdapter.OnItemClickListener {
    // Log TAG
    private static final String TAG = DashboardFragment.class.getSimpleName();

<<<<<<< HEAD
    // Default Values
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Device> devices = new ArrayList<>(15);
    private RecyclerView.LayoutManager layoutManager;
    //private ArrayList<Integer> spinnerData;
=======
    private static final String TAG = DashboardFragment.class.getSimpleName();
>>>>>>> 5e26d55a8b8a825b5ef95a7bb63d388c95ccb927
    private OnFragmentInteractionListener mListener;
    private NetResponse mNetResponse=null;

<<<<<<< HEAD
=======
    private Button fireAlarm, burglarAlarm, leakageAlarm, tempRoom, tempAttic, tempOutdoor, lightIndoor, lightOutdoor, stove, window, fan;
    private TextView status_fireAlarm, status_burglarAlarm, status_leakageAlarm, status_tempRoom, status_tempAttic, status_tempOutdoor, status_lightIndoor, status_lightOutdoor, status_stove, status_window, status_fan;

>>>>>>> 5e26d55a8b8a825b5ef95a7bb63d388c95ccb927
    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //Set title and icon
        if (mListener != null) {
            mListener.onFragmentInteraction("Smart Home", R.drawable.ic_home_black_24dp);
        }
        setHasOptionsMenu(true);

<<<<<<< HEAD
        //RecyclerView
        recyclerView = rootView.findViewById(R.id.recycler_dashboard);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        initialize();
        updateDashboard(devices);
        adapter = new DashboardAdapter(getContext(), devices.subList(0, 11));
        ((DashboardAdapter) adapter).SetOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
=======
        // Button
        fireAlarm = rootView.findViewById(R.id.btn_fireAlarm);
        burglarAlarm = rootView.findViewById(R.id.btn_burglarAlarm);
        leakageAlarm = rootView.findViewById(R.id.btn_leakageAlarm);
        tempRoom = rootView.findViewById(R.id.btn_roomTemp);
        tempAttic = rootView.findViewById(R.id.btn_atticTemp);
        tempOutdoor = rootView.findViewById(R.id.btn_outdoorTemp);
        lightIndoor = rootView.findViewById(R.id.btn_indoorLight);
        lightOutdoor = rootView.findViewById(R.id.btn_outdoorLight);
        fan = rootView.findViewById(R.id.btn_fan);
        stove = rootView.findViewById(R.id.btn_stove);
        window = rootView.findViewById(R.id.btn_window);

        fireAlarm.setOnClickListener(this);
        burglarAlarm.setOnClickListener(this);
        leakageAlarm.setOnClickListener(this);
        tempRoom.setOnClickListener(this);
        tempOutdoor.setOnClickListener(this);
        tempAttic.setOnClickListener(this);
        lightOutdoor.setOnClickListener(this);
        lightIndoor.setOnClickListener(this);
        fan.setOnClickListener(this);
        stove.setOnClickListener(this);
        window.setOnClickListener(this);

        // TextView
        status_fireAlarm = rootView.findViewById(R.id.status_fire);
        status_leakageAlarm = rootView.findViewById(R.id.status_leakage);
        status_burglarAlarm = rootView.findViewById(R.id.status_burglar);
        status_tempAttic = rootView.findViewById(R.id.status_atticTemp);
        status_tempOutdoor = rootView.findViewById(R.id.status_outdoorTemp);
        status_tempRoom = rootView.findViewById(R.id.status_roomTemp);
        status_lightIndoor = rootView.findViewById(R.id.status_indoorLight);
        status_lightOutdoor = rootView.findViewById(R.id.status_outdoorLight);
        status_fan = rootView.findViewById(R.id.status_fan);
        status_stove = rootView.findViewById(R.id.status_stove);
        status_window = rootView.findViewById(R.id.status_window);
>>>>>>> 5e26d55a8b8a825b5ef95a7bb63d388c95ccb927

        // Return view
        return rootView;
    }

<<<<<<< HEAD
=======
    @Override
    public void onClick(View v) {
        NetRequest request = new NetRequest(new NetRequest.NetResponseListener() {
            @Override
            public void onFinishNetRequest(NetResponse netResponse) {
                //mNetResponse = netResponse;
                int responseCode = netResponse.getResponseCode();
                String response = netResponse.getResponse();
                Log.i(TAG, "responseCode: " + String.valueOf(responseCode));
                Log.i(TAG, "response: " + response);

                if (responseCode == 200 && response.equals("exists")) {
                } else {
                    // Print out error hint to user
                    Log.i(TAG, "Wrong login credentials");
                }
            }
        });

        if (v == fireAlarm) {
            request.execute("checkDevice", "97");
            //status_fireAlarm.setText(mNetResponse.getResponse().equals("0") ? "OFF" : "ON");
        }
        if (v == burglarAlarm) {
            request.execute("checkDevice", "98");
            status_burglarAlarm.setText(mNetResponse.getResponse().equals("0") ? "OFF" : "ON");
        }
        if (v == leakageAlarm) {
            request.execute("checkDevice", "99");
            status_leakageAlarm.setText(mNetResponse.getResponse().equals("0") ? "OFF" : "ON");
        }
        if (v == tempAttic) {
            request.execute("checkDevice", "101");
            status_tempAttic.setText(mNetResponse.getResponse());
        }
        if (v == tempOutdoor) {
            request.execute("checkDevice", "102");
            status_tempOutdoor.setText(mNetResponse.getResponse());
        }
        if (v == tempRoom) {
            request.execute("checkDevice", "100");
            status_tempRoom.setText(mNetResponse.getResponse());
        }
        if (v == lightIndoor) {

        }
        if (v == lightOutdoor) {

        }
        if (v == fan) {

        }
        if (v == stove) {
            request.execute("checkDevice", "104");
            status_stove.setText(mNetResponse.getResponse().equals("0") ? "OFF" : "ON");
        }
        if (v == window) {
            request.execute("checkDevice", "105");
            status_window.setText(mNetResponse.getResponse().equals("0") ? "OFF" : "ON");
        }
       // Log.i(TAG, mNetResponse.toString());

    }

>>>>>>> 5e26d55a8b8a825b5ef95a7bb63d388c95ccb927
    ///////////////////////////////////// Menu Handling ////////////////////////////////////////////
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_dashboard, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.i(TAG, "home button pressed");
                if (getActivity() != null) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    if (fm.getBackStackEntryCount() > 0) {
                        fm.popBackStack();
                    }
                }
                return true;
            case R.id.menu_refresh:
                updateDashboard(devices);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    ////////////////////////////////// Toolbar Handling ////////////////////////////////////////////
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    ////////////////////////////////// Listener Handling ////////////////////////////////////////////
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String title, int icon);
    }


    @Override
    public void onSwitchToggle(final View view, final boolean isChecked, final int position) {

        // turn on/off indoor light
        // turn on/off outdoor light
        // turn on/off fan
        // enable/disable burglar alarm
        final Device toggledDevice = devices.get(position);

        // turn on/off indoor light, outdoor light, fan
        NetService request = new NetService(new NetService.NetResponseListener() {
            @Override
            public void onFinishNetRequest(NetResponse netResponse) {
                if (netResponse != null) {
                    String response = netResponse.getResponse();
                    toggledDevice.setValue(response);

                    updateDashboard(devices);
                } else {
                    Toast.makeText(getContext(), "Server error, please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // enable/disable burglar alarm
        NetService enableBurglarAlarm = new NetService(new NetService.NetResponseListener() {
            @Override
            public void onFinishNetRequest(NetResponse netResponse) {
                if (netResponse != null) {
                    String response = netResponse.getResponse();
                    toggledDevice.setTarget(response);

                    updateDashboard(devices);
                } else {
                    Toast.makeText(getContext(), "Server error, please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (toggledDevice.getName().equals(Constants.BURGLAR_ALARM)) {
            boolean isEnable = devices.get(position).getTarget().equals("1");
            if ((isChecked && !isEnable) || (!isChecked && isEnable)) {
                Log.i(TAG, "isChecked: " + isChecked);
                Log.i(TAG, "isEnable: " + isEnable);
                enableBurglarAlarm.execute(Constants.TOGGLE_DEVICE, String.valueOf(111));
            }
        } else {
            boolean isOn = devices.get(position).getValue().equals("1");
            if ((isChecked && !isOn) || (!isChecked && isOn)) {
                Log.i(TAG, "isChecked: " + isChecked);
                Log.i(TAG, "isOn: " + isOn);
                request.execute(Constants.TOGGLE_DEVICE, String.valueOf(toggledDevice.getId()));
            }
        }
    }

    @Override
    public void onItemSelected(final String targetTemp, int position) {
        // Only getting temp set response
        // setTemp target indoor/attic
        /*final Device device = devices.get(position);
        NetService request = new NetService(new NetService.NetResponseListener() {
            @Override
            public void onFinishNetRequest(NetResponse netResponse) {
                if (netResponse != null) {
                    Log.i(TAG, "response: " + netResponse.getResponse());
                    if (netResponse.getResponse().equals("temp set")) {
                        device.setTarget(targetTemp);
                    }
                } else {
                    Toast.makeText(getContext(), "Server error, please try again later", Toast.LENGTH_SHORT).show();
                }

                adapter.notifyDataSetChanged();
            }
        });

        request.execute(Constants.SET_TEMP, String.valueOf(device.getId()), String.valueOf(targetTemp));*/
    }

    ///////////////////////////////////// Common Methods ///////////////////////////////////////////
    private void initialize() {
        devices.clear();
        Device lightIndoor = new Device(107, Constants.INDOOR_LIGHT, "", Device.CONTROL_TYPE);
        Device lightOutdoor = new Device(103, Constants.OUTDOOR_LIGHT, "", Device.CONTROL_TYPE);
        Device fan = new Device(109, Constants.FAN, "", Device.CONTROL_TYPE);
        Device tempIndoor = new Device(100, Constants.INDOOR_TEMPERATURE, "", "", Device.TEMP_IN_TYPE);
        Device tempAttic = new Device(101, Constants.ATTIC_TEMPERATURE, "", "", Device.TEMP_IN_TYPE);
        Device tempOutdoor = new Device(102, Constants.OUTDOOR_TEMPERATURE, "", Device.DISPLAY_TYPE);
        Device oven = new Device(104, Constants.STOVE, "", Device.DISPLAY_TYPE);
        Device window = new Device(105, Constants.WINDOW, "", Device.DISPLAY_TYPE);
        Device alarmBurglar = new Device(98, Constants.BURGLAR_ALARM, "", "", Device.BURGLAR_ALARM_TYPE);
        Device alarmFire = new Device(97, Constants.FIRE_ALARM, "", Device.DISPLAY_TYPE);
        Device alarmLeak = new Device(99, Constants.LEAKAGE_ALARM, "", Device.DISPLAY_TYPE);

        // 106 power consumption,  108 Target temperature_room, 110 Target temperature_attic, 111 Enable burglar alarm
        Device powerConsumption = new Device(106, Constants.POWER_CONSUMPTION, "");
        Device target_roomTemp = new Device(108, Constants.TARGET_ROOM_TEMPERATURE, "");
        Device target_atticTemp = new Device(110, Constants.TARGET_ATTIC_TEMPERATURE, "");
        Device enable_burglarAlarm = new Device(111, Constants.ENABLE_BURGLAR_ALARM, "");

        devices.add(lightIndoor);
        devices.add(lightOutdoor);
        devices.add(fan);
        devices.add(tempIndoor);
        devices.add(tempAttic);
        devices.add(tempOutdoor);
        devices.add(oven);
        devices.add(window);
        devices.add(alarmBurglar);
        devices.add(alarmFire);
        devices.add(alarmLeak);
        devices.add(powerConsumption);
        devices.add(target_roomTemp);
        devices.add(target_atticTemp);
        devices.add(enable_burglarAlarm);
    }

    private void updateDashboard(final ArrayList<Device> devices) {

        Log.i(TAG, "devices size: " + devices.size());
        for (final Device device : devices) {
            final String deviceName = device.getName();
            final int id = device.getId();

            // Check all device
            NetService request = new NetService(new NetService.NetResponseListener() {
                @Override
                public void onFinishNetRequest(NetResponse netResponse) {
                    if (netResponse != null) {
                        int responseCode = netResponse.getResponseCode();
                        String response = netResponse.getResponse();
                        Log.i(TAG, deviceName + "-Response: " + response);
                        if (responseCode == 200) {
                            device.setValue(netResponse.getResponse());

                            if (deviceName.equals(Constants.TARGET_ROOM_TEMPERATURE)) {
                                devices.get(3).setTarget(response); // 100 tempIndoor
                            }
                            if (deviceName.equals(Constants.TARGET_ATTIC_TEMPERATURE)) {
                                devices.get(4).setTarget(response); // 101 tempAttic
                            }
                            if (deviceName.equals(Constants.ENABLE_BURGLAR_ALARM)) {
                                devices.get(8).setTarget(response); // 98 alarmBurglar
                            }

                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getContext(), "Error on server, please try later", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            request.execute(Constants.CHECK_DEVICE, String.valueOf(id));
        }
    }
}

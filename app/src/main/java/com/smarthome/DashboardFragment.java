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
import android.widget.Toast;

import com.smarthome.Data.Device;
import com.smarthome.Data.NetResponse;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements DashboardAdapter.OnItemClickListener {
    // Log TAG
    private static final String TAG = DashboardFragment.class.getSimpleName();

    // Default Values
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Device> devices = new ArrayList<>(15);
    private RecyclerView.LayoutManager layoutManager;
    //private ArrayList<Integer> spinnerData;
    private OnFragmentInteractionListener mListener;

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

        //RecyclerView
        recyclerView = rootView.findViewById(R.id.recycler_dashboard);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        initialize();
        updateDashboard(devices);
        adapter = new DashboardAdapter(getContext(), devices.subList(0, 11));
        ((DashboardAdapter) adapter).SetOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        // Return view
        return rootView;
    }

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

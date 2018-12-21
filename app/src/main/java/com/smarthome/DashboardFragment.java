package com.smarthome;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

// Todo: Fix response delay problem
/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = DashboardFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private NetResponse mNetResponse;

    private Button fireAlarm, burglarAlarm, leakageAlarm, tempRoom, tempAttic, tempOutdoor, lightIndoor, lightOutdoor, stove, window, fan;
    private TextView status_fireAlarm, status_burglarAlarm, status_leakageAlarm, status_tempRoom, status_tempAttic, status_tempOutdoor, status_lightIndoor, status_lightOutdoor, status_stove, status_window, status_fan;

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

        // Return view
        return rootView;
    }

    @Override
    public void onClick(View v) {
        NetRequest request = new NetRequest(new NetRequest.NetResponseListener() {
            @Override
            public void onFinishNetRequest(NetResponse netResponse) {
                mNetResponse = netResponse;
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

    ///////////////////////////////////// Menu Handling ////////////////////////////////////////////
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_dashboard, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_manage_items:
                gotoManageItemsFragment();
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String title, int icon);
    }

    ///////////////////////////////////// Common Methods ///////////////////////////////////////////
    private void gotoManageItemsFragment() {
        ManageItemsFragment manageItemsFragment = new ManageItemsFragment();
        if (getActivity() != null) {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.layout_fragment_container, manageItemsFragment).addToBackStack(null).commit();
        }
    }
}

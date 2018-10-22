package com.smarthome;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceListFragment extends Fragment {
    private static final String TAG = "DeviceListFragment";

    public DeviceListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(TAG, "Device list onCreate");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_device_list, container, false);

        // Setup RecyclerView
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView_manage_items);
        Log.i(TAG,"recyclerview found");
        recyclerView.setAdapter(new ManageItemsAdapter());
        Log.i(TAG,"adapter");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.i(TAG,"layoutmanager");


        // Return the view
        return rootView;
    }
}

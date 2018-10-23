package com.smarthome;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "DashboardFragment";

    private Button button_manage_item;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Button
        button_manage_item = rootView.findViewById(R.id.button_manage_item_dashboard);
        button_manage_item.setOnClickListener(this);

        // Return view
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v == button_manage_item) {
            Log.i(TAG, "button clicked");
            ManageItemsFragment manageItemsFragment = new ManageItemsFragment();
            if (getActivity() != null) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.layout_fragment_container, manageItemsFragment).addToBackStack(null).commit();
            }
        }
    }
}

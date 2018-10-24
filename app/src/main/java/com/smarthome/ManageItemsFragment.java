package com.smarthome;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ManageItemsFragment extends Fragment {
    private static final String TAG = "ManageItemsFragment";

    private OnFragmentInteractionListener mListener;

    public ManageItemsFragment() {
        //Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(TAG, "Device list onCreate");
        //Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_manage_items, container, false);

        //Set title and icon
        if (mListener != null) {
            mListener.onFragmentInteraction("Manage Items", R.drawable.ic_keyboard_arrow_left_black_24dp);
        }
        setHasOptionsMenu(true);

        //Setup RecyclerView
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView_manage_items);
        Log.i(TAG, "recyclerview found");

        ManageItemsAdapter adapter = new ManageItemsAdapter();
        recyclerView.setAdapter(adapter);
        Log.i(TAG, "adapter");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        Log.i(TAG, "layout manager");

        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(itemDecoration);

        //Return the view
        return rootView;
    }

    ///////////////////////////////////// Menu handling ////////////////////////////////////////////
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    ////////////////////////////////// Toolbar handling ////////////////////////////////////////////
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
}

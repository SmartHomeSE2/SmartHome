package com.smarthome;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
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

    private int[] device_images = {R.drawable.ic_lightbulb_outline_black_24dp, R.drawable.ic_lightbulb_outline_black_24dp, R.drawable.ic_lightbulb_outline_black_24dp,
            R.drawable.ic_lightbulb_outline_black_24dp, R.drawable.ic_lightbulb_outline_black_24dp, R.drawable.ic_lightbulb_outline_black_24dp, R.drawable.ic_lightbulb_outline_black_24dp,
            R.drawable.ic_lightbulb_outline_black_24dp, R.drawable.ic_lightbulb_outline_black_24dp, R.drawable.ic_lightbulb_outline_black_24dp, R.drawable.ic_lightbulb_outline_black_24dp,
            R.drawable.ic_lightbulb_outline_black_24dp, R.drawable.ic_lightbulb_outline_black_24dp, R.drawable.ic_lightbulb_outline_black_24dp,};
    private String[] device_names = {"Fire alarm", "Burglar alarm", "Leakage alarm", "Temperature room", "Temperature attic", "Outside light", "Stove", "Window", "Power consumption", "Inside light", "Target room temp", "Target attic temp", "Fan"};

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

        final ManageItemsAdapter adapter = new ManageItemsAdapter(device_names, device_images);
        adapter.setOnItemClickListener(new ManageItemsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, boolean isChecked, int position) {
                Log.i(TAG, device_names[position] + " isChecked: " + isChecked);

                //Todo a custom ViewGroup that implements a simple FrameLayout along with the ability to control children
                if (isChecked) {
                    // Add view
                } else {
                    // Remove view
                }
            }
        });
        recyclerView.setAdapter(adapter);
        Log.i(TAG, "adapter");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        Log.i(TAG, "layout manager");

        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        Drawable divider = ResourcesCompat.getDrawable(getResources(), R.drawable.recyclerview_divider, null);
        if (divider != null) {
            itemDecoration.setDrawable(divider);
        }
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

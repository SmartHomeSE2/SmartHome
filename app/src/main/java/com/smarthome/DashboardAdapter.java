package com.smarthome;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

public class DashboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String[] names, status, target;

    public DashboardAdapter(String[] deviceNames, String[] deviceStatus, String[] deviceTarget) {
        names = deviceNames;
        status = deviceStatus;
        target = deviceTarget;
    }

    /////////////////////////////////// Custom ViewHolders /////////////////////////////////////////

    // ViewHolder for Lights
    class ViewHolderLight extends RecyclerView.ViewHolder {
        public TextView title, isOn;
        public Switch toggleSwitch;

        public ViewHolderLight(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_light);
            isOn = itemView.findViewById(R.id.toggle_light);
            toggleSwitch = itemView.findViewById(R.id.switch_light);
        }

    }
    // ViewHolder for Outdoor Temperatures
    class ViewHolderTempOut extends RecyclerView.ViewHolder {
        public TextView title, actualTempOut;

        public ViewHolderTempOut(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_temp_out);
            actualTempOut = itemView.findViewById(R.id.actual_temp_out);
        }
    }
    // ViewHolder for Indoor Temperatures
    class ViewHolderTempIn extends RecyclerView.ViewHolder {
        public TextView title, actualTempIn, targetTempIn;

        public ViewHolderTempIn(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_temp_in);
            actualTempIn = itemView.findViewById(R.id.actual_temp_in);
            targetTempIn = itemView.findViewById(R.id.target_temp_in);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // Inflate XML Layouts and return ViewHolders
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case 0:
            case 1:
                View light = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_light, viewGroup, false);
                return new ViewHolderLight(light);
            case 2:
                View tempOut = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_temp_out, viewGroup, false);
                return new ViewHolderTempOut(tempOut);
            case 3:
            case 4:
                View tempIn = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_temp_in, viewGroup, false);
                return new ViewHolderTempIn(tempIn);
        }
        return null;
    }

    // Set Information within ViewHolders
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder deviceViewHolder, int i) {
        switch (deviceViewHolder.getItemViewType()) {
            case 0:
            case 1:
                ViewHolderLight viewHolderLight = (ViewHolderLight) deviceViewHolder;
                viewHolderLight.title.setText(names[i]);
                viewHolderLight.isOn.setText(status[i]);
                break;
            case 2:
                ViewHolderTempOut viewHolderTempOut = (ViewHolderTempOut) deviceViewHolder;
                viewHolderTempOut.title.setText(names[i]);
                viewHolderTempOut.actualTempOut.setText(status[i]);
            case 3:
            case 4:
                ViewHolderTempIn viewHolderTempIn = (ViewHolderTempIn) deviceViewHolder;
                viewHolderTempIn.title.setText(names[i]);
                viewHolderTempIn.actualTempIn.setText(status[i]);
                viewHolderTempIn.targetTempIn.setText(target[i]);
        }

    }

    @Override
    public int getItemCount() {
        return names.length;
    }
}

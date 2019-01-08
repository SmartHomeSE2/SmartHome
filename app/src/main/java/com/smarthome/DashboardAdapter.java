package com.smarthome;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.smarthome.Data.Device;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // Log TAG
    private String TAG = DashboardAdapter.class.getSimpleName();

    private List<Device> devices;
    private OnItemClickListener mItemClickListener;

    public DashboardAdapter(List<Device> devices) {
        this.devices = devices;
    }

    // Inflate XML Layouts and return ViewHolders
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case Device.TOGGLE_TYPE:
                View light = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_toggle, viewGroup, false);
                return new ViewHolderControl(light);
            case Device.CHECK_TYPE:
                View alarm = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_check, viewGroup, false);
                return new ViewHolderDisplay(alarm);
            case Device.BURGLAR_ALARM_TYPE:
                View burglary = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_burglary, viewGroup, false);
                return new ViewHolderBurgle(burglary);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return devices.get(position).getViewType();
    }

    // Set Information within ViewHolders
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder deviceViewHolder, int position) {
        Device device = devices.get(position);

        // Toggleable devices
        if (deviceViewHolder instanceof ViewHolderControl) {
            ViewHolderControl holder = (ViewHolderControl) deviceViewHolder;

            holder.title.setText(device.getName());

            if (device.getName().equals(Constants.FAN)) {
                holder.image.setImageResource(R.drawable.ic_fan);
            }

            if (device.getValue().equals("0")) {
                holder.status.setText("isOff");
            } else if (device.getValue().equals("1")) {
                holder.status.setText("isOn");
            }
            holder.control.setChecked(device.getValue().equals("1"));
        }

        // Checkable devices
        if (deviceViewHolder instanceof ViewHolderDisplay) {
            ViewHolderDisplay holder = (ViewHolderDisplay) deviceViewHolder;
            String deviceValue = device.getValue();
            String deviceName = device.getName();

            holder.title.setText(deviceName);
            switch (deviceName) {
                case Constants.FIRE_ALARM:
                    holder.displayImage.setImageResource(R.drawable.ic_fire_black_24dp);
                    holder.status.setText(deviceValue.equals("1") ? "Alert!" : "Clear");
                    break;
                case Constants.LEAKAGE_ALARM:
                    holder.displayImage.setImageResource(R.drawable.ic_leakage_alarm);
                    holder.status.setText(deviceValue.equals("1") ? "Alert!" : "Clear");
                    break;
                case Constants.STOVE:
                    holder.displayImage.setImageResource(R.drawable.ic_stove);
                    holder.status.setText(deviceValue.equals("1") ? "isOn" : "isOff");
                    break;
                case Constants.WINDOW:
                    holder.displayImage.setImageResource(R.drawable.ic_window);
                    holder.status.setText(deviceValue.equals("1") ? "isOn" : "isOff");
                    break;
                case Constants.INDOOR_TEMPERATURE:
                case Constants.ATTIC_TEMPERATURE:
                    holder.displayImage.setImageResource(R.drawable.ic_ac_unit_black_24dp);
                    String temp = deviceValue + "°C";
                    holder.status.setText(temp);
                    break;
                case Constants.OUTDOOR_LIGHT:
                    holder.displayImage.setImageResource(R.drawable.ic_lightbulb_outline_black_24dp);
                    if (deviceValue.equals("1")) {
                        holder.status.setText("isOn");
                    } else {
                        holder.status.setText("isOff");
                    }
                    break;

            }
        }

        // Burglar alarm
        if (deviceViewHolder instanceof ViewHolderBurgle) {
            ViewHolderBurgle holder = (ViewHolderBurgle) deviceViewHolder;
            holder.title.setText(device.getName());
            holder.burgleImage.setImageResource(R.drawable.ic_burglar_alarm);

            if (device.getIsEnable().equals("1")) {
                holder.status.setText(device.getValue().equals("1") ? "Alert!" : "Clear");
                holder.enableBurgleAlarm.setChecked(true);
            } else {
                holder.status.setText("Disable");
                holder.enableBurgleAlarm.setChecked(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        return devices == null ? 0 : devices.size();
    }

    /////////////////////////////////// Custom ViewHolders /////////////////////////////////////////
    // ViewHolder for toggleable devices
    class ViewHolderControl extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        public ImageView image;
        public TextView title, status;
        public Switch control;

        public ViewHolderControl(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_control);
            title = itemView.findViewById(R.id.text_control);
            status = itemView.findViewById(R.id.toggle_control);
            control = itemView.findViewById(R.id.switch_control);
            control.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            int position = getAdapterPosition();
            boolean isOn = devices.get(position).getValue().equals("1");
            if ((isChecked && !isOn) || (!isChecked && isOn)) {

                if (mItemClickListener != null) {
                    mItemClickListener.onSwitchToggle(isChecked, position);
                    Log.i("xixi", devices.get(position).getName() + " toggled: " + isChecked);
                    Log.i("xixi", "device target: " + devices.get(position).getIsEnable());

                }
            }
        }
    }

    // ViewHolder for Checkable devices
    class ViewHolderDisplay extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, status;
        public ImageView displayImage;
        public Button check;

        public ViewHolderDisplay(View itemView) {
            super(itemView);
            displayImage = itemView.findViewById(R.id.image_display);
            title = itemView.findViewById(R.id.title_display);
            status = itemView.findViewById(R.id.text_display_status);
            check = itemView.findViewById(R.id.button_check_display_device);
            check.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(position);
            }
        }
    }

    // ViewHolder for Burglar Alarm
    class ViewHolderBurgle extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
        public TextView title, status;
        public Switch enableBurgleAlarm;
        public ImageView burgleImage;
        public Button check;

        public ViewHolderBurgle(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_burgle);
            status = itemView.findViewById(R.id.status_burglary);
            burgleImage = itemView.findViewById(R.id.image_burglary);

            check = itemView.findViewById(R.id.button_check_burglar_alarm);
            check.setOnClickListener(this);

            enableBurgleAlarm = itemView.findViewById(R.id.switch_burgle);
            enableBurgleAlarm.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
            int position = getAdapterPosition();
            boolean isEnable = devices.get(position).getIsEnable().equals("1");
            if ((isCheck && !isEnable) || (!isCheck && isEnable)) {

                if (mItemClickListener != null) {
                    Log.i("xixi", "Burglar enable: " + devices.get(position).getIsEnable());
                    mItemClickListener.onSwitchToggle(isCheck, getAdapterPosition());
                }
            }

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(position);
            }
        }
    }

    // Click listener
    public interface OnItemClickListener {
        void onSwitchToggle(boolean isChecked, int position);

        void onItemClick(int position);

    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public Device getClickedItem(int position) {
        return devices.get(position);
    }
}
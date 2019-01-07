package com.smarthome;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.smarthome.Data.Device;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // Log TAG
    private String TAG = DashboardAdapter.class.getSimpleName();

    private Context mContext;
    private List<Device> devices;
    private OnItemClickListener mItemClickListener;

    public DashboardAdapter(Context context, List<Device> devices) {
        mContext = context;
        this.devices = devices;
    }

    // Inflate XML Layouts and return ViewHolders
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case Device.CONTROL_TYPE:
                View light = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_control, viewGroup, false);
                return new ViewHolderControl(light);
            case Device.TEMP_IN_TYPE:
                View tempIn = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_temp_in, viewGroup, false);
                return new ViewHolderTempIn(tempIn);
            case Device.DISPLAY_TYPE:
                View alarm = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_display, viewGroup, false);
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

        // Control devices
        if (deviceViewHolder instanceof ViewHolderControl) {
            ViewHolderControl holder = (ViewHolderControl) deviceViewHolder;
            holder.title.setText(device.getName());
            if (device.getName().equals(Constants.FAN)) {
                holder.image.setImageResource(R.drawable.ic_fan);
            }

            if (device.getValue().equals("0")) {
                holder.status.setText("isOff");
                holder.control.setChecked(false);
            } else if (device.getValue().equals("1")) {
                holder.status.setText("isOn");
                holder.control.setChecked(true);
            }
        }
        // Temperature
        if (deviceViewHolder instanceof ViewHolderTempIn) {
            ViewHolderTempIn holder = (ViewHolderTempIn) deviceViewHolder;
            holder.title.setText(device.getName());

            String temp = device.getValue() + "°C";
            holder.actualTempIn.setText(temp);
            /*String target = String.valueOf(Integer.parseInt(device.getTarget()) - 100);
            if (!target.trim().isEmpty() && Integer.parseInt(target) > 0) {
                holder.targetTempIn.setSelection(Integer.parseInt(target));
            }*/
        }

        // Display devices
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
                case Constants.OUTDOOR_TEMPERATURE:
                    holder.displayImage.setImageResource(R.drawable.ic_ac_unit_black_24dp);
                    String temp = deviceValue + "°C";
                    holder.status.setText(temp);
                    break;
                case Constants.ATTIC_TEMPERATURE:
                    holder.displayImage.setImageResource(R.drawable.ic_ac_unit_black_24dp);
                    holder.status.setText(deviceValue);
                    break;
            }
        }

        // Burglar alarm
        if (deviceViewHolder instanceof ViewHolderBurgle) {
            ViewHolderBurgle holder = (ViewHolderBurgle) deviceViewHolder;
            holder.title.setText(device.getName());
            holder.burgleImage.setImageResource(R.drawable.ic_burglar_alarm);

            if (device.getTarget().equals("1")) { // value 1 represents enable
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
// ViewHolder for Lights
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
            if (mItemClickListener != null) {
                mItemClickListener.onSwitchToggle(compoundButton, isChecked, getAdapterPosition());
                Log.i(TAG, devices.get(getAdapterPosition()).getName() + " toggled: " + isChecked);
            }
        }
    }

    // ViewHolder for Indoor Temperatures
    class ViewHolderTempIn extends RecyclerView.ViewHolder {
        public TextView title, actualTempIn;
        public Spinner targetTempIn;

        public ViewHolderTempIn(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_temp_in);
            actualTempIn = itemView.findViewById(R.id.actual_temp_in);
            targetTempIn = itemView.findViewById(R.id.spinner_targetTemp);
            ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(mContext, R.array.spinner_data, android.R.layout.simple_spinner_dropdown_item);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            targetTempIn.setAdapter(spinnerAdapter);

            targetTempIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (getAdapterPosition() == 3 || getAdapterPosition() == 4) {
                        if (mItemClickListener != null) {
                            mItemClickListener.onItemSelected(adapterView.getItemAtPosition(i).toString(), getAdapterPosition());
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    // ViewHolder for Alarms
    class ViewHolderDisplay extends RecyclerView.ViewHolder {
        public TextView title, status;
        public ImageView displayImage;

        public ViewHolderDisplay(View itemView) {
            super(itemView);
            displayImage = itemView.findViewById(R.id.image_display);
            title = itemView.findViewById(R.id.title_display);
            status = itemView.findViewById(R.id.text_display_status);
        }
    }

    // ViewHolder for Burglary Alarm
    class ViewHolderBurgle extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        public TextView title, status;
        public Switch enableBurgleAlarm;
        public ImageView burgleImage;

        public ViewHolderBurgle(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_burgle);
            status = itemView.findViewById(R.id.status_burglary);
            burgleImage = itemView.findViewById(R.id.image_burglary);

            enableBurgleAlarm = itemView.findViewById(R.id.switch_burgle);
            enableBurgleAlarm.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (mItemClickListener != null) {
                mItemClickListener.onSwitchToggle(compoundButton, b, getAdapterPosition());
            }
        }
    }

    // Click listener
    public interface OnItemClickListener {
        void onSwitchToggle(View view, boolean isChecked, int position);

        void onItemSelected(String targetTemp, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public Device getClickedItem(int position) {
        return devices.get(position);
    }
}
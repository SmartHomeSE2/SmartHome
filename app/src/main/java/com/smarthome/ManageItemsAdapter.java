package com.smarthome;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class ManageItemsAdapter extends RecyclerView.Adapter<ManageItemsAdapter.MyViewHolder> {

    private OnItemClickListener mItemClickListener;

    @NonNull
    @Override
    public ManageItemsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_device, viewGroup, false);
        return new ManageItemsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageItemsAdapter.MyViewHolder myViewHolder, int i) {
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ImageView image_item = itemView.findViewById(R.id.icon_item);
            TextView name_item = itemView.findViewById(R.id.name_item);
            Switch switch_item = itemView.findViewById(R.id.switch_item);
            switch_item.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(buttonView, isChecked, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, boolean isChecked, int position);

    }

    void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}

package com.example.myapplication;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
@SuppressLint("MissingPermission")

public class RecyclerViewAdapterFlipper extends RecyclerView.Adapter<RecyclerViewAdapterFlipper.ViewHolder> {
     private LeDeviceListAdapter mData;
        private LayoutInflater mInflater;
        private ItemClickListener mClickListener;

        // data is passed into the constructor
        RecyclerViewAdapterFlipper(Context context, LeDeviceListAdapter data) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
        }

        // inflates the row layout from xml when needed
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.flipper_row, parent, false);
            return new ViewHolder(view);
        }


    // binds the data to the TextView in each row
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String animal = mData.getDevice(position).getName();
            System.out.println(animal);
            holder.myTextView.setText(animal);
        }

        // total number of rows
        @Override
        public int getItemCount() {
            return mData.getCount();
        }


        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView myTextView;

            ViewHolder(View itemView) {
                super(itemView);
                myTextView = itemView.findViewById(R.id.tvFlipperName);
                itemView.setOnClickListener(this);
                myTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent Friendlyname = new Intent(itemView.getContext(), MACView.class).putExtra("flipper", myTextView.getText().toString());
                        int position = getAdapterPosition();
                        String currentMAC = mData.getDevice(position).getAddress();
                        Friendlyname.putExtra("Mac", currentMAC);
                        Friendlyname.putExtra("auth", "Basic QUlEYmE2YWY0ZGRiYTlmYWQ0M2FjOWY4NTNkOThlY2FlZWQ6MjY4YzRkNzk2ZWMxMGY0ZTBhOWFiMDc0YzFlZGNkZDA=");
                        itemView.getContext().startActivity(Friendlyname);
                    }
                });
            }

            @Override
            public void onClick(View view) {
                if (mClickListener != null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        BluetoothDevice clickedDevice = mData.getDevice(position);
                        String deviceAddress = clickedDevice.getAddress();
                        String deviceName = clickedDevice.getName();

                        Intent MACLookUp = new Intent(itemView.getContext(), MACView.class);
                        MACLookUp.putExtra("flipper", deviceName);
                        MACLookUp.putExtra("deviceAddress", deviceAddress);
                        itemView.getContext().startActivity(MACLookUp);
                    }
                }
                   // mClickListener.onItemClick(view, getAdapterPosition());
            }
        }

        // convenience method for getting data at click position

        // allows clicks events to be caught
        void setClickListener(ItemClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }

        // parent activity will implement this method to respond to click events
        public interface ItemClickListener {
            void onItemClick(View view, int position);
        }
    }

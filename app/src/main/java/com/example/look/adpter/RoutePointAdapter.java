package com.example.look.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.look.R;

public class RoutePointAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public RoutePointAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vm_item_2_layout, parent, false);
        // return new RoutePointHolder(rootView);
        return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_machine_page, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class RoutePointHolder extends RecyclerView.ViewHolder {
        public TextView tvAddress;
        public TextView tvDistance;

        public RoutePointHolder(View v) {
            super(v);
            tvAddress = v.findViewById(R.id.tv_address);
            tvDistance = v.findViewById(R.id.tv_distance);
        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public EmptyViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.iv_001);
            textView = v.findViewById(R.id.tv_001);
        }
    }
}

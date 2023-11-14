package com.example.look.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.look.R;
import com.example.look.bean.Person;

import java.util.ArrayList;
import java.util.List;

public class LineAddressAdapter extends RecyclerView.Adapter<LineAddressAdapter.LineViewHolder> {

    public List<Person> mDatas = new ArrayList<>();

    public LineAddressAdapter(List<Person> toolBeans) {
        this.mDatas = toolBeans;
    }

    @NonNull
    @Override
    public LineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address_distance, parent, false);
        return new LineViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull LineViewHolder holder, int position) {
        Person bean = mDatas.get(position);
        holder.tvAddress.setText(bean.getName());
        holder.tvDistance.setText(bean.getAge());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public class LineViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAddress;
        public TextView tvDistance;

        public LineViewHolder(View v) {
            super(v);
            tvAddress = v.findViewById(R.id.tv_address);
            tvDistance = v.findViewById(R.id.tv_distance);
        }
    }
}

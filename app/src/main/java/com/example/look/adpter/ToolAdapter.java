package com.example.look.adpter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.look.R;

import java.util.Map;

public class ToolAdapter extends RecyclerView.Adapter<ToolAdapter.ToolViewHolder> {

    public Map<Integer, String> toolMap;
    public OnToolClickListener onToolClickListener;


    public ToolAdapter(Map<Integer, String> toolMap) {
        this.toolMap = toolMap;
    }

    @NonNull
    @Override
    public ToolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_tool, null);
        return new ToolViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ToolViewHolder holder, final int position) {
        holder.btContent.setText(toolMap.get(position));
        if (onToolClickListener != null) {
            holder.btContent.setOnClickListener(view -> onToolClickListener.onToolClick(holder.getBindingAdapterPosition()));
        }
    }

    @Override
    public int getItemCount() {
        return toolMap.size();
    }

    public interface OnToolClickListener {
        void onToolClick(int tag);
    }

    public static class ToolViewHolder extends RecyclerView.ViewHolder {

        public Button btContent;

        public ToolViewHolder(View v) {
            super(v);
            btContent = v.findViewById(R.id.bt_content);
        }
    }
}

package com.example.look.adpter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.look.R;
import com.example.look.bean.ToolBean;

import java.util.List;

public class ToolAdapter extends RecyclerView.Adapter<ToolAdapter.ToolViewHolder> {

    public List<ToolBean> toolBeans;
    public OnToolClickListener onToolClickListener;


    public ToolAdapter(List<ToolBean> toolBeans) {
        this.toolBeans = toolBeans;
    }

    @NonNull
    @Override
    public ToolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_tool, null);
        return new ToolViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ToolViewHolder holder, final int position) {
        ToolBean bean = toolBeans.get(position);
        holder.btContent.setText(bean.txt);
        if (onToolClickListener != null) {
            holder.btContent.setOnClickListener(view -> onToolClickListener.onToolClick(bean.tag));
        }
    }

    @Override
    public int getItemCount() {
        return toolBeans.size();
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

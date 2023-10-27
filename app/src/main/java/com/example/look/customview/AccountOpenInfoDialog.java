package com.example.look.customview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.look.R;

public class AccountOpenInfoDialog extends DialogFragment {

    private ListView listView;
    private final BaseAdapter adapter;
    private AdapterView.OnItemClickListener mClickListener;
    public AccountOpenInfoCallBack mCallBack;

    public interface AccountOpenInfoCallBack {
        // 0-no 1-ok
        void call(int result, BaseAdapter adapter);
    }

    public AccountOpenInfoDialog setCallBack(AccountOpenInfoCallBack callBack) {
        this.mCallBack = callBack;
        return this;
    }

    public AccountOpenInfoDialog(BaseAdapter adapter) {
        this.adapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_account_open_info, container, false);
        listView = view.findViewById(R.id.dialog_list_info);
        listView.setAdapter(adapter);
        if (mClickListener != null)
            listView.setOnItemClickListener(mClickListener);
        initView(view,adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    void initView(View view,BaseAdapter adapter) {
        TextView tvCancel = view.findViewById(R.id.tv_cancel666);
        TextView tvConfirm = view.findViewById(R.id.tv_confirm666);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mCallBack.call(0,adapter);
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mCallBack.call(1,adapter);
            }
        });
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    public ListView getListView() {
        return listView;
    }

    public BaseAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void show(@NonNull FragmentManager manager, String tag) {
        super.show(manager, tag);
    }
}

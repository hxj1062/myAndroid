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

public class FranchiseUserDialog extends DialogFragment {

    private final BaseAdapter adapter;
    private AdapterView.OnItemClickListener mClickListener;
    public AccountOpenInfoCallBack mCallBack;

    public FranchiseUserDialog(BaseAdapter adapter) {
        this.adapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_franchise_user, container, false);
        ListView mListView = view.findViewById(R.id.dialog_list_info);
        mListView.setAdapter(adapter);
        if (mClickListener != null) {
            mListView.setOnItemClickListener(mClickListener);
        }
        initView(view);
        return view;
    }

    void initView(View view) {
        TextView tvCancel = view.findViewById(R.id.tv_cancel666);
        TextView tvConfirm = view.findViewById(R.id.tv_confirm666);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mCallBack.call(0);
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mCallBack.call(1);
            }
        });
    }

    @Override
    public void show(@NonNull FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    public interface AccountOpenInfoCallBack {
        // 0-no 1-ok
        void call(int result);
    }

    public FranchiseUserDialog setCallBack(AccountOpenInfoCallBack callBack) {
        this.mCallBack = callBack;
        return this;
    }
}

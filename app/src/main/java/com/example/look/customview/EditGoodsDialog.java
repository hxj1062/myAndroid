package com.example.look.customview;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.look.R;

import java.lang.ref.WeakReference;

public class EditGoodsDialog extends DialogFragment {


    public EdtGoodsInfoCallBack mCallBack;
    private WeakReference<Window> weakWindow;


    public EditGoodsDialog() {

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                // 使用弱引用保存窗口
                weakWindow = new WeakReference<>(window);
                WindowManager.LayoutParams params = window.getAttributes();
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                params.width = (int) (displayMetrics.widthPixels * 0.85);
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setBackgroundDrawableResource(R.drawable.shape_edit_goods);
                window.setAttributes(params);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_edt_goods, container, false);
        initView(view);
        return view;
    }

    void initView(View view) {
        ImageView ivCanImg = view.findViewById(R.id.iv_canImg);
//        GlideLoad.with(this).load(product.productUrl)
//                .into(iv_store_img)
        TextView tvGoodsName = view.findViewById(R.id.tv_goodsName);
        TextView tvGoodsId = view.findViewById(R.id.tv_goodsId);
        TextView tvGoodsPrice = view.findViewById(R.id.tv_goodsPrice);
        EditText etGoodsName = view.findViewById(R.id.edt_stock);

        etGoodsName.getText().toString();
        TextView tvCancel = view.findViewById(R.id.tv_canGoods);
        TextView tvConfirm = view.findViewById(R.id.tv_confirmGoods);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
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

    public interface EdtGoodsInfoCallBack {
        // 0-no 1-ok
        void call(int result);
    }

    public EditGoodsDialog setCallBack(EdtGoodsInfoCallBack callBack) {
        this.mCallBack = callBack;
        return this;
    }

    @Override
    public void onDestroyView() {
        // 在视图销毁前释放资源
        Dialog dialog = getDialog();
        if (dialog != null && getRetainInstance()) {
            // 避免 DialogFragment 重建时出现 IllegalStateException
            dialog.setDismissMessage(null);
        }

        // 清除窗口引用
        weakWindow = null;
        super.onDestroyView();
    }
}


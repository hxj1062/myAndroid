package com.example.look.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.look.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wl on 2018/6/28.
 */

public class BottomListDlg {
    private FragmentActivity mContext;
    private View bottomContentView;
    private BottomSheetDialog bsDlg;
    private RecyclerView rvBottom;

    public BottomListDlg(FragmentActivity mContext) {
        this.mContext = mContext;
    }


    public void show(List<BottomData> datas) {
        View contentView = createContentView(mContext, datas);
        bsDlg = new BottomSheetDialog(mContext);
        bsDlg.setContentView(contentView);
        bsDlg.show();
        rvBottom.setAdapter(new BottomListAdapter(R.layout.bottom_dlg_list_item, datas));
    }


    private View createContentView(Context context, List<BottomData> datas) {
        bottomContentView = LayoutInflater.from(context).inflate(R.layout.dlg_list_layout, null);

        rvBottom = bottomContentView.findViewById(R.id.rv_bottom_list);
        rvBottom.setLayoutManager(new LinearLayoutManager(context));

        TextView tvCancel = bottomContentView.findViewById(R.id.bottom_tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bsDlg != null) {
                    bsDlg.dismiss();
                }
            }
        });
        return bottomContentView;
    }


    public static class BottomData implements Serializable {
        public int id;
        public String text;
        public String tag;

        public BottomData(int id, String text) {
            this.id = id;
            this.text = text;
        }

        public BottomData(int id, String text, String tag) {
            this.id = id;
            this.text = text;
            this.tag = tag;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        /**
         * @param pos  当前选中的list下标
         * @param bean 当前选中的数据bean
         */
        void onItemClick(int pos, BottomData bean);
    }

    protected class BottomListAdapter extends BaseQuickAdapter<BottomData, BaseViewHolder> {

        public BottomListAdapter(int layoutResId, @Nullable List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final BottomData item) {
            helper.setText(R.id.bottom_item_tv, item.text);
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(helper.getAdapterPosition(), item);
                    }
                    bsDlg.dismiss();
                }
            });
        }
    }
}

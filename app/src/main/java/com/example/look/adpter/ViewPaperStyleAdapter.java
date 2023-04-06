package com.example.look.adpter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class ViewPaperStyleAdapter extends PagerAdapter {

    List<String> mStrings ;
    List<View> mViews ;

    public ViewPaperStyleAdapter(List<String> strings, List<View> views) {
        mStrings = strings;
        mViews = views;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(mViews.get(position));
        return mViews.get(position) ;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mViews.get(position));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mStrings.get(position);
    }
}

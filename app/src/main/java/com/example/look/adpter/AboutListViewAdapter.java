package com.example.look.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.look.R;
import com.example.look.bean.Person;

import java.util.List;


public class AboutListViewAdapter extends BaseAdapter {

    private List<Person> data;
    private Context context;

    public AboutListViewAdapter(Context context, List<Person> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_about_list, parent, false);
        ((TextView) convertView.findViewById(R.id.name_txt)).setText(data.get(position).getName());
        ((TextView) convertView.findViewById(R.id.age_txt)).setText(data.get(position).getAge() + "");
        return convertView;
    }
}

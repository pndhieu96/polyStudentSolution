package com.example.hieudev.polystudentsolution.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hieudev.polystudentsolution.R;

import java.util.ArrayList;

public class customThongTinAdapter extends BaseAdapter{

    private ArrayList<String> strings;
    private LayoutInflater inflater;
    private Context context;

    public customThongTinAdapter(Context context, ArrayList<String> strings){
        this.context = context;
        this.strings = strings;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public Object getItem(int position) {
        return strings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View converView, ViewGroup viewGroup){
        ViewHolder holder;
        if(converView == null){
            converView = inflater.inflate(R.layout.custom_thongtin_fragment, null);
            holder = new ViewHolder();
            holder.tv = (TextView) converView.findViewById(R.id.textView3);
            converView.setTag(holder);
        }else{
            holder = (ViewHolder) converView.getTag();
        }
        String string = this.strings.get(position);
        holder.tv.setText(string);
        return converView;
    }

    static class ViewHolder {
        TextView tv;
    }
}

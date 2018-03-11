package com.example.hieudev.polystudentsolution.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.LichHoc;

import java.util.List;


public class customHomeLichHocAdapter extends BaseAdapter {

    private List<LichHoc> lichHocs;
    private LayoutInflater inflater;
    private Context context;

    public customHomeLichHocAdapter(Context context, List<LichHoc> lichHocs){
        this.context = context;
        this.lichHocs = lichHocs;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.lichHocs.size();
    }

    @Override
    public Object getItem(int position) {
        return this.lichHocs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        customHomeLichHocAdapter.ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.home_lichhoc_listview, null);
            holder = new customHomeLichHocAdapter.ViewHolder();
            holder.tvHomeLVMon = (TextView) convertView.findViewById(R.id.tvHomeLVMon);
            holder.tvHomeLVThoiGian = (TextView) convertView.findViewById(R.id.tvHomeLVThoiGian);
            convertView.setTag(holder);
        }else{
            holder = (customHomeLichHocAdapter.ViewHolder) convertView.getTag();
        }

        LichHoc lichHoc = this.lichHocs.get(position);
        holder.tvHomeLVMon.setText(lichHoc.getMon());
        holder.tvHomeLVThoiGian.setText(lichHoc.getThoiGian()+" - "+"Phòng: "+lichHoc.getPhong());
        return convertView;
    }

    static class ViewHolder {
        TextView tvHomeLVMon;
        TextView tvHomeLVThoiGian;
    }
}

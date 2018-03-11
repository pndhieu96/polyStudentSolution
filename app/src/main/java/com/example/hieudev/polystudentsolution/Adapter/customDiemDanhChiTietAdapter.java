package com.example.hieudev.polystudentsolution.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.DiemDanh;

import java.util.ArrayList;

public class customDiemDanhChiTietAdapter extends BaseAdapter{
    private ArrayList<DiemDanh> diemDanhs;
    private LayoutInflater inflater;
    private Context context;

    public customDiemDanhChiTietAdapter(Context context, ArrayList<DiemDanh> diemDanhs){
        this.context = context;
        this.diemDanhs = diemDanhs;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return diemDanhs.size();
    }

    @Override
    public Object getItem(int position) {
        return diemDanhs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View converView, ViewGroup viewGroup){
        customDiemDanhChiTietAdapter.ViewHolder holder;
        if(converView == null){
            converView = inflater.inflate(R.layout.custom_diemdanhchitiet_listview, null);
            holder = new customDiemDanhChiTietAdapter.ViewHolder();
            holder.tvDDCTNgay = (TextView) converView.findViewById(R.id.tvDDCTNgay);
            holder.tvDDCTCa = (TextView) converView.findViewById(R.id.tvDDCTca);
            holder.tvDDCTTrangThai = (TextView) converView.findViewById(R.id.tvDDCTTrangThai);
            converView.setTag(holder);
        }else{
            holder = (customDiemDanhChiTietAdapter.ViewHolder) converView.getTag();
        }

        DiemDanh diemDanh = this.diemDanhs.get(position);
        holder.tvDDCTNgay.setText(diemDanh.getNgay());
        holder.tvDDCTCa.setText("Ca " +diemDanh.getCa());
        if(diemDanh.getTrangThai().equals("Absent")){
            holder.tvDDCTTrangThai.setTextColor(Color.RED);
        }else {
            holder.tvDDCTTrangThai.setTextColor(Color.BLUE);
        }
        holder.tvDDCTTrangThai.setText(diemDanh.getTrangThai());
        return converView;
    }

    static class ViewHolder {
        TextView tvDDCTNgay;
        TextView tvDDCTCa;
        TextView tvDDCTTrangThai;
    }
}

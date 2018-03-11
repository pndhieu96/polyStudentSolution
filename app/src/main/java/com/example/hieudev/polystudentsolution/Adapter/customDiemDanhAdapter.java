package com.example.hieudev.polystudentsolution.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.DiemDanhThongKe;

import java.util.ArrayList;

public class customDiemDanhAdapter extends BaseAdapter{

    private ArrayList<DiemDanhThongKe> diemDanhThongKes;
    private LayoutInflater inflater;
    private Context context;

    public customDiemDanhAdapter(Context context, ArrayList<DiemDanhThongKe> diemDanhThongKes){
        this.context = context;
        this.diemDanhThongKes = diemDanhThongKes;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return diemDanhThongKes.size();
    }

    @Override
    public Object getItem(int position) {
        return diemDanhThongKes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View converView, ViewGroup viewGroup){
        ViewHolder holder;
        if(converView == null){
            converView = inflater.inflate(R.layout.custom_diemdanh_dialog, null);
            holder = new ViewHolder();
            holder.tvDdMon = (TextView) converView.findViewById(R.id.tvMonDd);
            holder.tvDdTongSo = (TextView) converView.findViewById(R.id.tvMonDdTongSo);
            holder.tvDdVang = (TextView) converView.findViewById(R.id.tvMonDdvang);
            converView.setTag(holder);
        }else{
            holder = (ViewHolder) converView.getTag();
        }

        DiemDanhThongKe diemDanhThongKe = this.diemDanhThongKes.get(position);
        holder.tvDdMon.setText(diemDanhThongKe.getTenMon());
        holder.tvDdVang.setText("Vắng: " + diemDanhThongKe.getBuoiVang() + " ( " + diemDanhThongKe.getPhanTramVang() +"% Tổng số )");
        holder.tvDdTongSo.setText("Tổng số: "+diemDanhThongKe.getTongSoBuoi());

        return converView;
    }

    static class ViewHolder {
        TextView tvDdMon;
        TextView tvDdVang;
        TextView tvDdTongSo;
    }
}

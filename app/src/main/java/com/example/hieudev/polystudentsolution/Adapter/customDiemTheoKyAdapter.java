package com.example.hieudev.polystudentsolution.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.DiemThongKeTheoKy;

import java.util.ArrayList;

public class customDiemTheoKyAdapter extends BaseAdapter{
    private ArrayList<DiemThongKeTheoKy> diemThongKeTheoKies;
    private LayoutInflater inflater;
    private Context context;

    public customDiemTheoKyAdapter(Context context, ArrayList<DiemThongKeTheoKy> diemThongKeTheoKies){
        this.context = context;
        this.diemThongKeTheoKies = diemThongKeTheoKies;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return diemThongKeTheoKies.size();
    }

    @Override
    public Object getItem(int position) {
        return diemThongKeTheoKies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View converView, ViewGroup viewGroup){
        customDiemTheoKyAdapter.ViewHolder holder;
        if(converView == null){
            converView = inflater.inflate(R.layout.custom_diemtheoky_listview, null);
            holder = new customDiemTheoKyAdapter.ViewHolder();
            holder.tvDTKMon = (TextView) converView.findViewById(R.id.tvDTKMon);
            holder.tvDTKTrungBinh = (TextView) converView.findViewById(R.id.tvDTKTrungBinh);
            holder.tvDTKTrangThai = (TextView) converView.findViewById(R.id.tvDTKTrangThai);
            converView.setTag(holder);
        }else{
            holder = (customDiemTheoKyAdapter.ViewHolder) converView.getTag();
        }

        DiemThongKeTheoKy diemThongKeTheoKy = diemThongKeTheoKies.get(position);
        holder.tvDTKMon.setText(diemThongKeTheoKy.getTenMon());
        holder.tvDTKTrungBinh.setText("Trung bình: "+diemThongKeTheoKy.getDiemTrungBinh());
        holder.tvDTKTrangThai.setText("Trạng thái: "+diemThongKeTheoKy.getTrangThai());
        return converView;
    }

    static class ViewHolder {
        TextView tvDTKMon;
        TextView tvDTKTrungBinh;
        TextView tvDTKTrangThai;
    }
}

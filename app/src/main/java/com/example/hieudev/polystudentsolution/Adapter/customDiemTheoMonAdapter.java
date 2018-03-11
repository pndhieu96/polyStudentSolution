package com.example.hieudev.polystudentsolution.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.DiemTheoMon;

import java.util.ArrayList;

public class customDiemTheoMonAdapter extends BaseAdapter {
    private ArrayList<DiemTheoMon> diemTheoMons;
    private LayoutInflater inflater;
    private Context context;

    public customDiemTheoMonAdapter(Context context, ArrayList<DiemTheoMon> diemTheoMons){
        this.context = context;
        this.diemTheoMons = diemTheoMons;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return diemTheoMons.size();
    }

    @Override
    public Object getItem(int position) {
        return diemTheoMons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View converView, ViewGroup viewGroup){
        customDiemTheoMonAdapter.ViewHolder holder;
        if(converView == null){
            converView = inflater.inflate(R.layout.custom_diemtheomon_listview, null);
            holder = new customDiemTheoMonAdapter.ViewHolder();
            holder.tvDTMHocKy = (TextView) converView.findViewById(R.id.tvDTMHocKy);
            holder.tvDTMMon = (TextView) converView.findViewById(R.id.tvDTMMon);
            holder.tvDTMMaMon = (TextView) converView.findViewById(R.id.tvDTMMaMon);
            holder.tvDTMSoTinChi = (TextView) converView.findViewById(R.id.tvDTMSoTinChi);
            holder.tvDTMTrangThai = (TextView) converView.findViewById(R.id.tvDTMTrangThai);
            holder.tvDTMDiem = (TextView) converView.findViewById(R.id.tvDTMDiem);
            converView.setTag(holder);
        }else{
            holder = (customDiemTheoMonAdapter.ViewHolder) converView.getTag();
        }

        DiemTheoMon diemTheoMon = diemTheoMons.get(position);
        holder.tvDTMHocKy.setText(diemTheoMon.getHocKy());
        holder.tvDTMMon.setText(diemTheoMon.getMon());
        holder.tvDTMMaMon.setText("Mã môn: "+diemTheoMon.getMaMon());
        holder.tvDTMSoTinChi.setText("Số tín chỉ: "+diemTheoMon.getSoTinChi());
        holder.tvDTMTrangThai.setText("Trạng thái: "+diemTheoMon.getTrangThai());
        holder.tvDTMDiem.setText("Điểm Trung Bình: "+diemTheoMon.getDiem());
        return converView;
    }

    static class ViewHolder {
        TextView tvDTMMon;
        TextView tvDTMHocKy;
        TextView tvDTMMaMon;
        TextView tvDTMSoTinChi;
        TextView tvDTMTrangThai;
        TextView tvDTMDiem;
    }
}


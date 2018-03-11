package com.example.hieudev.polystudentsolution.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.DiemDanhThongKe;

import java.util.List;

public class customHomeDiemDanhAdapter extends BaseAdapter {

    private List<DiemDanhThongKe> diemDanhThongKes;
    private LayoutInflater inflater;
    private Context context;

    public customHomeDiemDanhAdapter(Context context, List<DiemDanhThongKe> diemDanhThongKes){
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
            converView = inflater.inflate(R.layout.home_diemdanh_listview, null);
            holder = new ViewHolder();
            holder.tvDdMon1 = (TextView) converView.findViewById(R.id.tvHomeMonDd);
            holder.tvDdTongSo1 = (TextView) converView.findViewById(R.id.tvHomeMonDdTongSo);
            holder.tvDdVang1 = (TextView) converView.findViewById(R.id.tvHomeMonDdvang);
            converView.setTag(holder);
        }else{
            holder = (ViewHolder) converView.getTag();
        }

        DiemDanhThongKe diemDanhThongKe = this.diemDanhThongKes.get(position);
        String tenMon = diemDanhThongKe.getTenMon();
        String tenMonMang[] = tenMon.split("-");
        holder.tvDdMon1.setText(tenMonMang[0]);
        holder.tvDdVang1.setText("Vắng: " + diemDanhThongKe.getBuoiVang() + " ( " + diemDanhThongKe.getPhanTramVang() +"% Tổng số )");
        holder.tvDdTongSo1.setText("Tổng số: "+diemDanhThongKe.getTongSoBuoi());

        return converView;
    }

    static class ViewHolder {
        TextView tvDdMon1;
        TextView tvDdVang1;
        TextView tvDdTongSo1;
    }
}

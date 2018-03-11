package com.example.hieudev.polystudentsolution.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.DiemTheoKy;

import java.util.ArrayList;

public class customDiemTheoKyChiTietAdapter extends BaseAdapter{
    private ArrayList<DiemTheoKy> diemTheoKies;
    private LayoutInflater inflater;
    private Context context;

    public customDiemTheoKyChiTietAdapter(Context context, ArrayList<DiemTheoKy> diemTheoKies){
        this.context = context;
        this.diemTheoKies = diemTheoKies;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return diemTheoKies.size();
    }

    @Override
    public Object getItem(int position) {
        return diemTheoKies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View converView, ViewGroup viewGroup){
        customDiemTheoKyChiTietAdapter.ViewHolder holder;
        if(converView == null){
            converView = inflater.inflate(R.layout.custom_diemtheokychitiet_adapter, null);
            holder = new customDiemTheoKyChiTietAdapter.ViewHolder();
            holder.tvDTKCTMON = (TextView) converView.findViewById(R.id.tvDTKCTTen);
            holder.tvDTKCTDIEM = (TextView) converView.findViewById(R.id.tvDTKCTDiem);
            holder.tvDTKCTRONGSO = (TextView) converView.findViewById(R.id.tvDTKCTTrongSo);
            converView.setTag(holder);
        }else{
            holder = (customDiemTheoKyChiTietAdapter.ViewHolder) converView.getTag();
        }

        DiemTheoKy diemTheoKy = this.diemTheoKies.get(position);
        holder.tvDTKCTMON.setText(diemTheoKy.getTenDauDiem());
        holder.tvDTKCTDIEM.setText(diemTheoKy.getDiem()+"");
        holder.tvDTKCTRONGSO.setText(diemTheoKy.getTrongSo()+"");
        return converView;
    }

    static class ViewHolder {
        TextView tvDTKCTMON;
        TextView tvDTKCTRONGSO;
        TextView tvDTKCTDIEM;
    }
}

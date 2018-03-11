package com.example.hieudev.polystudentsolution.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.LichHoc;

import java.util.List;

public class customLichHocAdapter extends RecyclerView.Adapter<customLichHocAdapter.customLichHocViewHolder>{

    private List<LichHoc> lichHocList;

    public customLichHocAdapter(List<LichHoc> lichHocList){
        this.lichHocList = lichHocList;
    }

    @Override
    public customLichHocAdapter.customLichHocViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lichhoc_cardview, parent, false);
        return new customLichHocViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(customLichHocAdapter.customLichHocViewHolder holder, int position) {
        LichHoc lichHoc = lichHocList.get(position);
        holder.tenMon.setText(lichHoc.getMon());
        holder.tenNgay.setText(lichHoc.getNgay());
        holder.tenLop.setText("Lớp: " + lichHoc.getLop());
        holder.tenGiangVien.setText("Giảng viên: " + lichHoc.getGiangVien());
        holder.tenPhong.setText("Phòng: " + lichHoc.getPhong());
        String thoigian = lichHoc.getThoiGian();
        String[] cahocs = thoigian.split("\\(");
        holder.tenCaHoc.setText(cahocs[0]);
        String gios = thoigian.substring(8,30);
        String[] gio = gios.split("đến");
        holder.tenGio.setText(gio[0].substring(1,gio[0].length() -4) + "-" +gio[1].substring(0,gio[1].length() -3));

    }

    @Override
    public int getItemCount() {
        return lichHocList.size();
    }

    public static class customLichHocViewHolder extends RecyclerView.ViewHolder{

        TextView tenMon;
        TextView tenNgay;
        TextView tenLop;
        TextView tenGiangVien;
        TextView tenPhong;
        TextView tenCaHoc;
        TextView tenGio;

        public customLichHocViewHolder(View itemView) {
            super(itemView);
            tenMon = (TextView) itemView.findViewById(R.id.tvMon);
            tenNgay = (TextView) itemView.findViewById(R.id.tvNgay);
            tenLop = (TextView) itemView.findViewById(R.id.tvlop);
            tenGiangVien = (TextView) itemView.findViewById(R.id.tvGiangVien);
            tenPhong = (TextView) itemView.findViewById(R.id.tvPhong);
            tenCaHoc = (TextView) itemView.findViewById(R.id.tvCaHoc);
            tenGio = (TextView) itemView.findViewById(R.id.tvThoiGian);
        }
    }
}
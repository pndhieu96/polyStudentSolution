package com.example.hieudev.polystudentsolution.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hieudev.polystudentsolution.Dialog.SetThongTinMonDangHoc;
import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.DiemDanh;
import com.example.hieudev.polystudentsolution.RealmObject.DiemDanhThongKe;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHoc;
import com.example.hieudev.polystudentsolution.ViewActivity.MonDangHocActivity;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class customDeadlineAdapter extends RecyclerView.Adapter<customDeadlineAdapter.customDeadlineViewHolder>{
    private List<MonDangHoc> monDangHocs;
    private List<Integer> buoiVangs;
    private Realm realm;

    public customDeadlineAdapter(List<MonDangHoc> monDangHocs){
        this.monDangHocs = monDangHocs;
        try{
            realm = Realm.getDefaultInstance();

        }catch (Exception e){

            // Get a Realm instance for this thread
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);

        }
    }

    @Override
    public customDeadlineAdapter.customDeadlineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.deadline_cardview, parent, false);
        return new customDeadlineAdapter.customDeadlineViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(customDeadlineAdapter.customDeadlineViewHolder holder,int position) {
        MonDangHoc monDangHoc = monDangHocs.get(position);
        holder.tvDeadlineMon.setText(monDangHoc.getTenMon());
        if(monDangHoc.getDiemMucTieu() != 0.0f){
            holder.tvDeadlineDiem.setText("Điểm mục tiêu: " + monDangHoc.getDiemMucTieu() +"");
        }else{
            holder.tvDeadlineDiem.setText("Điểm mục tiêu: " + "../..");
        }
        if(monDangHoc.getSoBuoiChoPhep() != 0){
            holder.tvDeadlineBuoi.setText("Số buổi nghỉ cho phép: " + monDangHoc.getSoBuoiChoPhep() +"");
        }else{
            holder.tvDeadlineBuoi.setText("Số buổi nghỉ cho phép: "+"../..");
        }

        RealmResults<DiemDanhThongKe> diemDanhThongKes = realm.where(DiemDanhThongKe.class).contains("tenMon", monDangHoc.getTenMon()).findAll();
        if(diemDanhThongKes.size()>0) {
            int buoivang = diemDanhThongKes.get(0).getBuoiVang();
            if (monDangHoc.getSoBuoiChoPhep() != 0) {
                float phanTramVang = Math.round(((float) buoivang / monDangHoc.getSoBuoiChoPhep()) * 100);
                System.out.println(phanTramVang + "phanTramVang");
                if (phanTramVang <= 100) {
                    holder.tvDeadlineNghi.setText("Số buổi vắng: " + buoivang + " ( " + phanTramVang + "% buổi cho phép )");
                } else {
                    holder.tvDeadlineNghi.setText("Bạn đã nghỉ quá số buổi cho phép");
                }
            } else {
                holder.tvDeadlineNghi.setText("Số buổi vắng: " + buoivang + " ( 0" + "% buổi cho phép )");
            }
        }
        final int position1 = position;

        holder.deadlineRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), MonDangHocActivity.class);
                i.putExtra("TENMON", monDangHocs.get(position1).getTenMon());
                v.getContext().startActivity(i);
            }
        });

        holder.deadlineRelativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent(v.getContext(), SetThongTinMonDangHoc.class);
                i.putExtra("TENMON", monDangHocs.get(position1).getTenMon());
                v.getContext().startActivity(i);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return monDangHocs.size();
    }


    public static class customDeadlineViewHolder extends RecyclerView.ViewHolder{
        TextView tvDeadlineMon;
        TextView tvDeadlineDiem;
        TextView tvDeadlineBuoi;
        TextView tvDeadlineNghi;
        RelativeLayout deadlineRelativeLayout;

        public customDeadlineViewHolder(View itemView) {
            super(itemView);
            tvDeadlineMon = (TextView) itemView.findViewById(R.id.tvDeadlineMon);
            tvDeadlineDiem = (TextView) itemView.findViewById(R.id.tvDeadlineDiem);
            tvDeadlineBuoi = (TextView) itemView.findViewById(R.id.tvDeadlineBuoi);
            tvDeadlineNghi = (TextView) itemView.findViewById(R.id.tvDeadlineNghi);
            deadlineRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.deadlineRelativeLayout);
        }
    }
}
package com.example.hieudev.polystudentsolution.Adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hieudev.polystudentsolution.Dialog.BaiTapDetail;
import com.example.hieudev.polystudentsolution.Dialog.ChinhSuaBaiTap;
import com.example.hieudev.polystudentsolution.Dialog.ChinhSuaGhiNho;
import com.example.hieudev.polystudentsolution.Dialog.GhiNhoDetail;
import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHocBaiTap;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHocGhiNho;
import java.util.List;

public class customBaiTapAdapter extends RecyclerView.Adapter<customBaiTapAdapter.customBaiTapAdapterViewHolder>{
    private List<MonDangHocBaiTap> monDangHocs;
    private String tenMon;

    public customBaiTapAdapter(List<MonDangHocBaiTap> monDangHocs,String tenMon){
        this.monDangHocs = monDangHocs;
        this.tenMon = tenMon;
    }

    @Override
    public customBaiTapAdapter.customBaiTapAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mondanghocbaitap_cardview, parent, false);
        return new customBaiTapAdapter.customBaiTapAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(customBaiTapAdapter.customBaiTapAdapterViewHolder holder,int position) {
        MonDangHocBaiTap monDangHocBaiTap = monDangHocs.get(position);
        if (monDangHocBaiTap.getTieude().length() >= 29){
            String title = monDangHocBaiTap.getTieude().substring(0,29);
            holder.txtTitle.setText(title+"...");
        }else{
            holder.txtTitle.setText(monDangHocBaiTap.getTieude()+" ");
        }
        if(monDangHocBaiTap.getNoiDung().length() >= 50){
            String subTitle= monDangHocBaiTap.getNoiDung().substring(0,50);
            holder.txtSubtitle.setText(subTitle+"...");
        }else{
            holder.txtSubtitle.setText(monDangHocBaiTap.getNoiDung()+" ");
        }
        holder.tvNgay.setText(monDangHocBaiTap.getDealineNgay()+"/"+monDangHocBaiTap.getDealineThang());
        holder.tvGio.setText(monDangHocBaiTap.getDealineGio()+":"+monDangHocBaiTap.getDealinePhut());

        final int position1 = position;
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent(v.getContext(), ChinhSuaBaiTap.class);
                i.putExtra("POSITION", position1);
                i.putExtra("TENMON", tenMon);
                v.getContext().startActivity(i);
                return true;
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), BaiTapDetail.class);
                i.putExtra("POSITION", position1);
                i.putExtra("TENMON", tenMon);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return monDangHocs.size();
    }


    public static class customBaiTapAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle, txtSubtitle, tvNgay, tvGio;
        CardView cardView;

        public customBaiTapAdapterViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.tvBaiTapTitle);
            txtSubtitle = (TextView) itemView.findViewById(R.id.tvBaiTapNoiDung);
            tvNgay = (TextView) itemView.findViewById(R.id.tvBaiTapNgayThang);
            tvGio = (TextView) itemView.findViewById(R.id.tvBaiTapThoiGian);
            cardView = (CardView) itemView.findViewById(R.id.baiTapCardView);
        }
    }
}

package com.example.hieudev.polystudentsolution.Adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hieudev.polystudentsolution.Dialog.ChinhSuaGhiNho;
import com.example.hieudev.polystudentsolution.Dialog.GhiNhoDetail;
import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.MonDangHocGhiNho;
import java.util.List;

public class customGhiNhoAdapter extends RecyclerView.Adapter<customGhiNhoAdapter.customGhiNhoAdapterViewHolder>{
    private List<MonDangHocGhiNho> monDangHocs;
    private String tenMon;

    public customGhiNhoAdapter(List<MonDangHocGhiNho> monDangHocs,String tenMon){
        this.monDangHocs = monDangHocs;
        this.tenMon = tenMon;
    }

    @Override
    public customGhiNhoAdapter.customGhiNhoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mondanghocghinho_cardview, parent, false);
        return new customGhiNhoAdapter.customGhiNhoAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(customGhiNhoAdapter.customGhiNhoAdapterViewHolder holder,int position) {
        MonDangHocGhiNho monDangHocGhiNho = monDangHocs.get(position);
        if (monDangHocGhiNho.getTieude().length() >= 34){
            String title = monDangHocGhiNho.getTieude().substring(0,34);
            holder.txtTitle.setText(title+"...");
        }else{
            holder.txtTitle.setText(monDangHocGhiNho.getTieude()+"");
        }
        if(monDangHocGhiNho.getNoiDung().length() >= 90){
            String subTitle= monDangHocGhiNho.getNoiDung().substring(0,90);
            holder.txtSubtitle.setText(subTitle+"...");
        }else{
            holder.txtSubtitle.setText(monDangHocGhiNho.getNoiDung()+"");
        }

        final int position1 = position;
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent(v.getContext(), ChinhSuaGhiNho.class);
                i.putExtra("POSITION", position1);
                i.putExtra("TENMON", tenMon);
                v.getContext().startActivity(i);
                return true;
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), GhiNhoDetail.class);
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


    public static class customGhiNhoAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle;
        TextView txtSubtitle;
        CardView cardView;

        public customGhiNhoAdapterViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.tvGhiNhoTitle);
            txtSubtitle = (TextView) itemView.findViewById(R.id.tvGhiNhoDes);
            cardView = (CardView) itemView.findViewById(R.id.cVMonDangHocGhiNho);
        }
    }
}

package com.example.hieudev.polystudentsolution.Adapter;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.DiemDanhThongKe;
import com.example.hieudev.polystudentsolution.RealmObject.DiemThongKe;
import com.example.hieudev.polystudentsolution.RealmObject.LichHoc;

import java.util.ArrayList;
import java.util.List;

public class customHomeAdapter extends RecyclerView.Adapter<customHomeAdapter.customHomeViewHolder>{

    ArrayList<DiemThongKe> diemThongKe;
    ArrayList<LichHoc> lichHocHomNay;
    ArrayList<LichHoc> lichHocNgayMai;
    ArrayList<DiemDanhThongKe> diemDanhThongKes;
    String homNay, ngayMai;

    static final int TYPE_THONGKE = 0;
    static final int TYPE_LHHOMNAY = 1;
    static final int TYPE_LHNGAYMAI = 2;
    static final int TYPE_DIEMDANHVANG = 3;

    public customHomeAdapter(List<DiemThongKe> diemThongKe, ArrayList<LichHoc> lichHocHomNay, ArrayList<LichHoc> lichHocNgayMai, ArrayList<DiemDanhThongKe> diemDanhThongKes, String homNay, String ngayMai){
        this.diemThongKe = (ArrayList<DiemThongKe>) diemThongKe;
        this.lichHocHomNay = lichHocHomNay;
        this.lichHocNgayMai = lichHocNgayMai;
        this.diemDanhThongKes = diemDanhThongKes;
        this.homNay = homNay;
        this.ngayMai = ngayMai;
    }

    @Override
    public int getItemViewType(int position) {
        switch(position){
            case 0:
                return  TYPE_THONGKE;
            case 1:
                return  TYPE_LHHOMNAY;
            case 2:
                return  TYPE_LHNGAYMAI;
            case 3:
                return  TYPE_DIEMDANHVANG;
            default:
                return  TYPE_LHHOMNAY;
        }
    }

    @Override
    public customHomeAdapter.customHomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){
            case TYPE_THONGKE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_cardview_diem, parent, false);
                return new customHomeAdapter.customHomeViewHolder(view);
            case TYPE_LHHOMNAY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_cardview_lichhoc, parent, false);
                return new customHomeAdapter.customHomeViewHolder(view);
            case TYPE_LHNGAYMAI:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_cardview_lichhoc, parent, false);
                return new customHomeAdapter.customHomeViewHolder(view);
            case TYPE_DIEMDANHVANG:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_cardview_diemdanh, parent, false);
                return new customHomeAdapter.customHomeViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(customHomeAdapter.customHomeViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case TYPE_THONGKE:
                if(diemThongKe.size()>0) {
                    DiemThongKe diemThongKe1 = diemThongKe.get(position);
                    holder.tenHomeKhoa.setText(diemThongKe1.getKhoa() + "");
                    holder.tenHomeMonDat.setText("Môn đạt: " + diemThongKe1.getTongMonDat() + "");
                    holder.tenHomeMonHocLai.setText("Môn học lại: " + diemThongKe1.getTongHocLai() + "");
                    holder.tenHomeMonDangHoc.setText("Môn đang học: " + diemThongKe1.getTongDangHoc() + "");
                    holder.tenHomeMonChuaHoc.setText("Môn chưa học: " + diemThongKe1.getChuaHoc() + "");
                    holder.tenHomeMonTb.setText("Điểm trung bình: " + diemThongKe1.getDiemTrungBinh() + "");
                    holder.tenHomeMonPercentage.setText("Percentage: " + (int) diemThongKe1.getPhanTram() + "%");
                    holder.tenHomePassed.setText("Passed/Total: " + diemThongKe1.getPassed() + "");
                }
                break;
            case TYPE_LHHOMNAY:
                holder.tenHomeLichHocTitle.setText("Ca học hôm nay");
                holder.tvHomeLichHocNgay.setText("(" + homNay + ")");
                if(lichHocHomNay.size() > 0){
                    holder.tenHomeLichHocLv.setAdapter(new customHomeLichHocAdapter(holder.tenHomeLichHocLv.getContext(), lichHocHomNay));
                    holder.tenHomeLichHocLv.getLayoutParams().height = customHomeAdapter.calculateHeight(holder.tenHomeLichHocLv);
                }else{
                    TextView tvHomNayKo = new TextView(holder.LnLayoutHomeLV.getContext());
                    tvHomNayKo.setText("Hôm nay không có ca học nào !");
                    tvHomNayKo.setPadding(10, 10, 10, 10);
                    tvHomNayKo.setTextColor(Color.parseColor("#000000"));
                    tvHomNayKo.setTextSize(17);
                    tvHomNayKo.setGravity(Gravity.CENTER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        tvHomNayKo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    }
                    tvHomNayKo.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.FILL_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

                    holder.LnLayoutHomeLV.addView(tvHomNayKo);
                }
                break;

            case TYPE_LHNGAYMAI:
                holder.tenHomeLichHocTitle.setText("Ca học ngày mai");
                holder.tvHomeLichHocNgay.setText("(" + ngayMai + ")");
                if(lichHocNgayMai.size() > 0){
                    holder.tenHomeLichHocLv.setAdapter(new customHomeLichHocAdapter(holder.tenHomeLichHocLv.getContext(), lichHocNgayMai));
                    holder.tenHomeLichHocLv.getLayoutParams().height = customHomeAdapter.calculateHeight(holder.tenHomeLichHocLv);
                }else{
                    TextView tvHomNayKo = new TextView(holder.LnLayoutHomeLV.getContext());
                    tvHomNayKo.setText("Ngày mai không có ca học nào !");
                    tvHomNayKo.setPadding(10, 10, 10, 10);
                    tvHomNayKo.setTextColor(Color.parseColor("#000000"));
                    tvHomNayKo.setTextSize(17);
                    tvHomNayKo.setGravity(Gravity.CENTER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        tvHomNayKo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    }
                    tvHomNayKo.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.FILL_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

                    holder.LnLayoutHomeLV.addView(tvHomNayKo);
                }
                break;

            case TYPE_DIEMDANHVANG:
                holder.tenHomeDiemDanhTitle.setText("Các môn điểm danh vắng");
                if(diemDanhThongKes.size() > 0){
                    int h = 100;
                    holder.tenHomeDiemDanhLv.setAdapter(new customHomeDiemDanhAdapter(holder.tenHomeDiemDanhLv.getContext(), diemDanhThongKes));
                    holder.tenHomeDiemDanhLv.getLayoutParams().height = customHomeAdapter.calculateHeight(holder.tenHomeDiemDanhLv);
                }else{
                    TextView tvHomNayKo = new TextView(holder.lnLayoutHomeDiemDanhLv.getContext());
                    tvHomNayKo.setText("Bạn đi học đầy đủ.");
                    tvHomNayKo.setPadding(10, 10, 10, 10);
                    tvHomNayKo.setTextColor(Color.parseColor("#000000"));
                    tvHomNayKo.setTextSize(17);
                    tvHomNayKo.setGravity(Gravity.CENTER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        tvHomNayKo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    }
                    tvHomNayKo.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.FILL_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

                    holder.lnLayoutHomeDiemDanhLv.addView(tvHomNayKo);
                }
                break;

        }
    }

    public static class customHomeViewHolder extends RecyclerView.ViewHolder{

        TextView tenHomeKhoa;
        TextView tenHomeMonDat;
        TextView tenHomeMonHocLai;
        TextView tenHomeMonDangHoc;
        TextView tenHomeMonChuaHoc;
        TextView tenHomeMonTb;
        TextView tenHomeMonPercentage;
        TextView tenHomePassed;

        TextView tenHomeLichHocTitle;
        ListView tenHomeLichHocLv;
        LinearLayout LnLayoutHomeLV;
        TextView tvHomeLichHocNgay;

        TextView tenHomeDiemDanhTitle;
        ListView tenHomeDiemDanhLv;
        LinearLayout lnLayoutHomeDiemDanhLv;

        public customHomeViewHolder(View itemView) {
            super(itemView);
            tenHomeKhoa = (TextView) itemView.findViewById(R.id.tvHomeKhoa);
            tenHomeMonDat = (TextView) itemView.findViewById(R.id.tvHomeMonDat);
            tenHomeMonHocLai = (TextView) itemView.findViewById(R.id.tvHomeMonHocLai);
            tenHomeMonDangHoc = (TextView) itemView.findViewById(R.id.tvHomeMonDangHoc);
            tenHomeMonChuaHoc = (TextView) itemView.findViewById(R.id.tvHomeMonChuaHoc);
            tenHomeMonTb = (TextView) itemView.findViewById(R.id.tvHomeMonTb);
            tenHomeMonPercentage = (TextView) itemView.findViewById(R.id.tvHomeMonPercentage);
            tenHomePassed = (TextView) itemView.findViewById(R.id.tvHomePassed);

            tenHomeLichHocTitle = (TextView) itemView.findViewById(R.id.tvHomeLichHocTitle);
            tenHomeLichHocLv = (ListView) itemView.findViewById(R.id.lvHomeLichHoc);
            LnLayoutHomeLV = (LinearLayout) itemView.findViewById(R.id.LnLayoutHomeLV);
            tvHomeLichHocNgay = (TextView) itemView.findViewById(R.id.tvHomeLichHocNgay);

            tenHomeDiemDanhTitle = (TextView) itemView.findViewById(R.id.tvHomeDiemDanh);
            tenHomeDiemDanhLv = (ListView) itemView.findViewById(R.id.lvHomeDiemDanh);
            lnLayoutHomeDiemDanhLv = (LinearLayout) itemView.findViewById(R.id.LnLayoutDiemDanhLv);
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    private static int calculateHeight(ListView list) {

        int height = 0;

        for (int i = 0; i < list.getCount(); i++) {
            View childView = list.getAdapter().getView(i, null, list);
            childView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            height+= childView.getMeasuredHeight();
        }

        //dividers height
        height += list.getDividerHeight() * list.getCount();

        return height;
    }
}

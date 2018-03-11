package com.example.hieudev.polystudentsolution.HandleActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.RealmObject.DiemDanh;
import com.example.hieudev.polystudentsolution.RealmObject.DiemDanhThongKe;
import com.example.hieudev.polystudentsolution.RealmObject.DiemTheoKy;
import com.example.hieudev.polystudentsolution.RealmObject.DiemTheoMon;
import com.example.hieudev.polystudentsolution.RealmObject.DiemThongKe;
import com.example.hieudev.polystudentsolution.RealmObject.DiemThongKeTheoKy;
import com.example.hieudev.polystudentsolution.RealmObject.LichHoc;
import com.example.hieudev.polystudentsolution.RealmObject.TermId;
import com.example.hieudev.polystudentsolution.RealmObject.ThongTin;
import com.example.hieudev.polystudentsolution.ViewActivity.ChooseCampusActivity;
import com.example.hieudev.polystudentsolution.ViewActivity.MainActivity;
import com.kuassivi.view.ProgressProfileView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class GetDataActivity extends AppCompatActivity {

    WebView getInfo, getLichHoc, getLichThi, getDiemDanh, getDiemTheoKy, getDiemTheoMon;
    ProgressProfileView profileViewLoadding;
    TextView loadText;
    int timeLichHoc = 0;
    int timeDiemTheoKy = 0;
    int timeDiemDanh = 0;
    String user;
    private int progressstatus = 0;
    boolean checkGetDataDiemDanh = false;
    boolean checkStartActivity = false;
    Realm realm;

    int showCount,termId,coutProgressstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);
        getInfo = (WebView) findViewById(R.id.getInfo);
        getLichHoc = (WebView) findViewById(R.id.getLichHoc);
        getLichThi = (WebView) findViewById(R.id.getLichThi);
        getDiemDanh = (WebView) findViewById(R.id.getDiemDanh);
        getDiemTheoKy = (WebView) findViewById(R.id.getDiemTheoKy);
        getDiemTheoMon = (WebView) findViewById(R.id.getBangDiem);
        profileViewLoadding = (ProgressProfileView) findViewById(R.id.profileLoadValue);
        loadText = (TextView) findViewById(R.id.textViewLoadValue);
        try{
            realm = Realm.getDefaultInstance();

        }catch (Exception e){

            // Get a Realm instance for this thread
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);
        }

        getSharedPreferencesSettings();
        if(this.user.equals("")) {
            this.coutProgressstatus = 18;
            GetInfo();
        }else {
            this.coutProgressstatus = 22;
            getDataLichHoc();
            getDataDiemTheoMon();
            GetDiemTheoKy();
            GetDataDiemDanh();
        }
    }

    // handler
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                getValueError();
            }else if( msg.what == 1){
                try{
                    realm = Realm.getDefaultInstance();

                }catch (Exception e){

                    // Get a Realm instance for this thread
                    RealmConfiguration config = new RealmConfiguration.Builder()
                            .deleteRealmIfMigrationNeeded()
                            .build();
                    realm = Realm.getInstance(config);

                }
                RealmResults<ThongTin> thongTins = realm.where(ThongTin.class).findAll();
                if(thongTins.size() > 0){
                    GetDataActivity.this.saveUser(thongTins.get(0).getTenDangNhap());
                    GetDataActivity.this.user = thongTins.get(0).getTenDangNhap();
                }
                getDataLichHoc();
                getDataDiemTheoMon();
                GetDiemTheoKy();
                GetDataDiemDanh();
            }
        }
    };

    // javascriptInterface
    public class MyJavaScriptInterface{
        @JavascriptInterface
        public void getInfo(final String html){
            try {
                System.out.println(Jsoup.parse(html).select("div[id=personal_info]").select("div[id=thumbnail]").select("img").attr("src"));
                try{
                    realm = Realm.getDefaultInstance();

                }catch (Exception e){

                    // Get a Realm instance for this thread
                    RealmConfiguration config = new RealmConfiguration.Builder()
                            .deleteRealmIfMigrationNeeded()
                            .build();
                    realm = Realm.getInstance(config);

                }
                final RealmResults<ThongTin> thongTins = realm.where(ThongTin.class).findAll();
                if(!thongTins.isEmpty()){
                    realm.executeTransaction(new Realm.Transaction(){
                        @Override
                        public void execute(Realm realm) {
                            thongTins.deleteAllFromRealm();
                        }
                    });
                    setInfo(html);
                }else{
                    setInfo(html);
                }
            }catch (Throwable t){
                t.printStackTrace();
                getValueError();
            }
        }

        @JavascriptInterface
        public void getLichHoc(final String html){
            try{
                try{
                    realm = Realm.getDefaultInstance();

                }catch (Exception e){

                    // Get a Realm instance for this thread
                    RealmConfiguration config = new RealmConfiguration.Builder()
                            .deleteRealmIfMigrationNeeded()
                            .build();
                    realm = Realm.getInstance(config);

                }
                final RealmResults<LichHoc> lichHocs = realm.where(LichHoc.class).findAll();
                if(!lichHocs.isEmpty()){
                    realm.executeTransaction(new Realm.Transaction(){
                        @Override
                        public void execute(Realm realm) {
                            lichHocs.deleteAllFromRealm();
                        }
                    });
                    setLichHoc(html,realm);
                }else {
                    setLichHoc(html, realm);
                }
            }catch (Throwable t){
                t.printStackTrace();
                getValueError();
            }
        }

        @JavascriptInterface
        public void getDiemTheoMon(String html) {
            try {
                try{
                    realm = Realm.getDefaultInstance();

                }catch (Exception e){

                    // Get a Realm instance for this thread
                    RealmConfiguration config = new RealmConfiguration.Builder()
                            .deleteRealmIfMigrationNeeded()
                            .build();
                    realm = Realm.getInstance(config);

                }
                final RealmResults<DiemTheoMon> diemTheoMons = realm.where(DiemTheoMon.class).findAll();
                final RealmResults<DiemThongKe> diemThongKes = realm.where(DiemThongKe.class).findAll();
                if(!diemTheoMons.isEmpty() || !diemThongKes.isEmpty()){
                    realm.executeTransaction(new Realm.Transaction(){
                        @Override
                        public void execute(Realm realm) {
                            diemTheoMons.deleteAllFromRealm();
                            diemThongKes.deleteAllFromRealm();
                        }
                    });
                    setDiemTheoMon(html,realm);
                }else {
                    setDiemTheoMon(html, realm);
                }
            } catch (Exception t) {
                t.printStackTrace();
                getValueError();
            }
        }

        @JavascriptInterface
        public void getDiemTheoKy(String html){
            try{
                try{
                    realm = Realm.getDefaultInstance();

                }catch (Exception e){

                    // Get a Realm instance for this thread
                    RealmConfiguration config = new RealmConfiguration.Builder()
                            .deleteRealmIfMigrationNeeded()
                            .build();
                    realm = Realm.getInstance(config);

                }
                final RealmResults<TermId> termIds = realm.where(TermId.class).findAll();

                if(!termIds.isEmpty()){
                    realm.executeTransaction(new Realm.Transaction(){
                        @Override
                        public void execute(Realm realm) {
                            termIds.deleteAllFromRealm();
                        }
                    });
                    setOptionValue(html,realm);
                }else{
                    setOptionValue(html,realm);
                }

                final RealmResults<DiemThongKeTheoKy> diemThongKeTheoKies = realm.where(DiemThongKeTheoKy.class).findAll();
                if(!diemThongKeTheoKies.isEmpty()){
                    realm.executeTransaction(new Realm.Transaction(){
                        @Override
                        public void execute(Realm realm) {
                            diemThongKeTheoKies.deleteAllFromRealm();
                        }
                    });
                    setDiemTheoKy(html,realm);
                }else{
                    setDiemTheoKy(html,realm);
                }
            } catch (Throwable t){
                t.printStackTrace();
                getValueError();
            }
        }

        @JavascriptInterface
        public void getDataDiemDanh(String html){
            final Elements Subheader = Jsoup.parse(html).select("h5.subheader");
            final Elements tbody = Jsoup.parse(html).select("table").select("tbody");
            final Elements tfoot = Jsoup.parse(html).select("table").select("tfoot");
            try{
                realm = Realm.getDefaultInstance();
            }catch (Exception e){

                // Get a Realm instance for this thread
                RealmConfiguration config = new RealmConfiguration.Builder()
                        .deleteRealmIfMigrationNeeded()
                        .build();
                realm = Realm.getInstance(config);
            }
            final RealmResults<DiemDanhThongKe> diemDanhThongKes = realm.where(DiemDanhThongKe.class).findAll();
            if(!diemDanhThongKes.isEmpty()){
                realm.executeTransaction(new Realm.Transaction(){
                    @Override
                    public void execute(Realm realm) {
                        diemDanhThongKes.deleteAllFromRealm();
                    }
                });
                setDataDiemDanh(html, realm);
            }else{
                setDataDiemDanh(html, realm);
            }
        }
    }
    // end javascriptInterface


    // Lấy thông tin từ website
    public void GetDiemTheoKy(){
        this.getDiemTheoKy.setVisibility(View.GONE);
        this.getDiemTheoKy.getSettings().setJavaScriptEnabled(true);
        this.getDiemTheoKy.getSettings().setLoadsImagesAutomatically(false);
        this.getDiemTheoKy.getSettings().setBlockNetworkImage(true);
        this.getDiemTheoKy.addJavascriptInterface(new MyJavaScriptInterface(), "HtmlViewer");
        this.getDiemTheoKy.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(GetDataActivity.this.timeDiemTheoKy == 0){
                    GetDataActivity.this.getDiemTheoKy.loadUrl("javascript:var select = document.getElementById('term_id');select.innerHTML = '';var option = document.createElement('OPTION');option.setAttribute('value',"+ GetDataActivity.this.termId +");select.appendChild(option);select.form.submit();void (0);  ");
                }else if (GetDataActivity.this.timeDiemTheoKy == 1){
                    GetDataActivity.this.getDiemTheoKy.loadUrl("javascript:$(document).ready(function(){$(\"table\").DataTable( {\"paging\":false,\"ordering\":false,\"bDestroy\": true,\"info\":false});});");
                    GetDataActivity.this.getDiemTheoKy.loadUrl("javascript:window.HtmlViewer.getDiemTheoKy('<body>'+document.getElementsByTagName('body')[0].innerHTML+'</body>');");
                    String string = "Lấy dữ liệu điểm theo kỳ";
                    setProgressstatus(string);
                    checkOk();
                }
                GetDataActivity getDataActivity = GetDataActivity.this;
                getDataActivity.timeDiemTheoKy ++;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (request.equals("http://ap.poly.edu.vn/choose_campus.php?noredirect=1&msg=")) {
                    getValueError();
                }
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.equals("http://ap.poly.edu.vn/choose_campus.php?noredirect=1&msg=")) {
                    getValueError();
                }
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                getValueError();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                getValueError();
            }
        });
        this.getDiemTheoKy.loadUrl("http://ap.poly.edu.vn/students/index.php?router=diemtheoky&login=" + this.user);
    }

    public void GetDataDiemDanh(){
        this.getDiemDanh.setVisibility(View.GONE);
        this.getDiemDanh.getSettings().setJavaScriptEnabled(true);
        this.getDiemDanh.getSettings().setLoadsImagesAutomatically(false);
        this.getDiemDanh.getSettings().setBlockNetworkImage(true);
        this.getDiemDanh.addJavascriptInterface(new MyJavaScriptInterface(), "HtmlViewer");
        this.getDiemDanh.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(GetDataActivity.this.timeDiemDanh == 0){
                    GetDataActivity.this.getDiemDanh.loadUrl("javascript:var select = document.getElementById('term_id');select.innerHTML = '';var option = document.createElement('OPTION');option.setAttribute('value',"+ GetDataActivity.this.termId +");select.appendChild(option);select.form.submit();void (0);  ");
                }else if (GetDataActivity.this.timeDiemDanh == 1){
                    GetDataActivity.this.getDiemDanh.loadUrl("javascript:$(document).ready(function(){$(\"table\").DataTable( {\"paging\":false,\"ordering\":false,\"bDestroy\": true,\"info\":false});});");
                    GetDataActivity.this.getDiemDanh.loadUrl("javascript:window.HtmlViewer.getDataDiemDanh('<body>'+document.getElementsByTagName('body')[0].innerHTML+'</body>');");
                    String string = "Lấy dữ liệu điểm danh";
                    setProgressstatus(string);
                }
                GetDataActivity getDataActivity = GetDataActivity.this;
                getDataActivity.timeDiemDanh ++;
                checkOk();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (request.equals("http://ap.poly.edu.vn/choose_campus.php?noredirect=1&msg=")) {
                    getValueError();
                }
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.equals("http://ap.poly.edu.vn/choose_campus.php?noredirect=1&msg=")) {
                    getValueError();
                }
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                getValueError();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                getValueError();
            }
        });
        this.getDiemDanh.loadUrl("http://ap.poly.edu.vn/students/index.php?router=diemdanh&login=" + this.user);
    }

    public void GetInfo() {
        this.getInfo.setVisibility(View.GONE);
        this.getInfo.getSettings().setJavaScriptEnabled(true);
        this.getInfo.getSettings().setLoadsImagesAutomatically(false);
        this.getInfo.getSettings().setBlockNetworkImage(false);
        this.getInfo.addJavascriptInterface(new MyJavaScriptInterface(), "HtmlViewer");
        this.getInfo.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                GetDataActivity.this.getInfo.loadUrl("javascript:window.HtmlViewer.getInfo('<body>'+document.getElementsByTagName('body')[0].innerHTML+'</body>');");
                String string = "Lấy thông tin học viên";
                setProgressstatus(string);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (request.equals("http://ap.poly.edu.vn/choose_campus.php?noredirect=1&msg=")) {
                    getValueError();
                }
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.equals("http://ap.poly.edu.vn/choose_campus.php?noredirect=1&msg=")) {
                    getValueError();
                }
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                getValueError();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                getValueError();
            }
        });
        this.getInfo.loadUrl("http://ap.poly.edu.vn/students/index.php");
    }

    public void getDataLichHoc(){
        this.getLichHoc.getSettings().setJavaScriptEnabled(true);
        this.getLichHoc.getSettings().setLoadsImagesAutomatically(false);
        this.getLichHoc.getSettings().setBlockNetworkImage(true);
        this.getLichHoc.setVisibility(View.GONE);
        this.getLichHoc.addJavascriptInterface(new MyJavaScriptInterface(), "HtmlViewer");
        this.getLichHoc.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(GetDataActivity.this.timeLichHoc ==0){
                    GetDataActivity.this.getLichHoc.loadUrl("javascript:var select = document.getElementById('num_of_day');select.innerHTML = '';var option = document.createElement('OPTION');option.setAttribute('value',"+ GetDataActivity.this.showCount +");select.appendChild(option);select.form.submit();void (0);  ");
                }else if(GetDataActivity.this.timeLichHoc ==1){
                    GetDataActivity.this.getLichHoc.loadUrl("javascript:window.HtmlViewer.getLichHoc('<body>'+document.getElementsByTagName('body')[0].innerHTML+'</body>');");
                    String string = "Lấy dữ liệu lịch học";
                    setProgressstatus(string);
                }
                GetDataActivity getDataActivity = GetDataActivity.this;
                getDataActivity.timeLichHoc ++;
                checkOk();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (request.equals("http://ap.poly.edu.vn/choose_campus.php?noredirect=1&msg=")) {
                    getValueError();
                }
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.equals("http://ap.poly.edu.vn/choose_campus.php?noredirect=1&msg=")) {
                    getValueError();
                }
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                getValueError();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                getValueError();
            }
        });
        this.getLichHoc.loadUrl("http://ap.poly.edu.vn/activity/lichhoc_tab.php?login=" + this.user);
    }

    public void getDataDiemTheoMon(){
        this.getDiemTheoMon.getSettings().setJavaScriptEnabled(true);
        this.getDiemTheoMon.getSettings().setLoadsImagesAutomatically(false);
        this.getDiemTheoMon.getSettings().setBlockNetworkImage(true);
        this.getDiemTheoMon.setVisibility(View.GONE);
        this.getDiemTheoMon.addJavascriptInterface(new MyJavaScriptInterface(), "HtmlViewer");
        this.getDiemTheoMon.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                GetDataActivity.this.getDiemTheoMon.loadUrl("javascript:$(document).ready(function(){$(\"table\").DataTable( {\"paging\":false,\"ordering\":false,\"bDestroy\": true,\"info\":false});});");
                GetDataActivity.this.getDiemTheoMon.loadUrl("javascript:window.HtmlViewer.getDiemTheoMon('<body>'+document.getElementsByTagName('body')[0].innerHTML+'</body>');");
                String string = "Lấy dữ liệu điểm theo môn";
                setProgressstatus(string);
                checkOk();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (request.equals("http://ap.poly.edu.vn/choose_campus.php?noredirect=1&msg=")) {
                    getValueError();
                }
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.equals("http://ap.poly.edu.vn/choose_campus.php?noredirect=1&msg=")) {
                    getValueError();
                }
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                getValueError();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                getValueError();
            }
        });
        this.getDiemTheoMon.loadUrl("http://ap.poly.edu.vn/students/index.php?router=bangdiem&login=" + this.user);
    }
    // end get thong tin tu website


    // cac method bo sung
    private void setInfo(final String html){
        final Elements elementTD = Jsoup.parse(html).select("div[id=user_details]").select("table").select("tr").select("td");
        try{
            realm = Realm.getDefaultInstance();

        }catch (Exception e){

            // Get a Realm instance for this thread
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);

        }
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                ThongTin hocvien = bgRealm.createObject(ThongTin.class);
                hocvien.setTenDangNhap(((Element) elementTD.get(1)).text());
                hocvien.setHo(((Element) elementTD.get(3)).text());
                hocvien.setTenDem(((Element) elementTD.get(5)).text());
                hocvien.setTen(((Element) elementTD.get(7)).text());
                hocvien.setMaSV(((Element) elementTD.get(9)).text());
                hocvien.setGioiTinh(((Element) elementTD.get(11)).text());
                hocvien.setNgaySinh(((Element) elementTD.get(13)).text());
                hocvien.setEmail(((Element) elementTD.get(15)).text());
                hocvien.setNganhHoc(((Element) elementTD.get(21)).text());
                hocvien.setNoiCap(((Element) elementTD.get(31)).text());
            }
        }, new Realm.Transaction.OnSuccess(){

            @Override
            public void onSuccess() {
                handler.sendEmptyMessage(1);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                handler.sendEmptyMessage(0);
            }
        });
    }

    private void setLichHoc(final String html, Realm realm){
        final Elements elementTR = Jsoup.parse(html).select("table").select("tbody").select("tr");
        for(int i = 0; i < elementTR.size(); i++){
            final Elements elementTD = ((Element) elementTR.get(i)).select("td");
            final int finalI = i;
            realm.executeTransactionAsync(new Realm.Transaction(){
                @Override
                public void execute(Realm realm) {
                    LichHoc lichHoc = realm.createObject(LichHoc.class);
                    lichHoc.setCount(finalI);
                    lichHoc.setNgay(((Element)elementTD.get(0)).text());
                    lichHoc.setPhong(((Element)elementTD.get(1)).text());
                    lichHoc.setGiangDuong(((Element)elementTD.get(2)).text());
                    lichHoc.setMon(((Element)elementTD.get(3)).text());
                    lichHoc.setLop(((Element)elementTD.get(4)).text());
                    lichHoc.setGiangVien(((Element)elementTD.get(5)).text());
                    lichHoc.setThoiGian(((Element)elementTD.get(6)).text());
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    getValueError();
                }
            });
        }
    }

    private void setDiemTheoMon(String html, Realm realm){
        Document doc = Jsoup.parseBodyFragment(html);
        final Elements h4 = doc.getElementsByClass("subheader");
        final String[] strings = ((Element) h4.get(1)).text().split(" ");
        Elements tbody = doc.select("tbody");
        Elements tr = ((Element) tbody.get(0)).select("tr");
        final Elements trp = ((Element) tbody.get(1)).select("tr");

        realm.executeTransactionAsync(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {
                DiemThongKe diemThongKe = realm.createObject(DiemThongKe.class);
                diemThongKe.setKhoa(((Element) h4.get(0)).select("strong").text());
                diemThongKe.setDiemTrungBinh(Float.parseFloat(strings[3].toString()));
                diemThongKe.setPhanTram(Float.parseFloat(strings[5].toString().substring(0, strings[5].length() - 2)));
                diemThongKe.setPassed(strings[7].toString());
                for (int a = 0; a < trp.size(); a++) {
                    final Elements td = ((Element) trp.get(a)).select("td");
                    diemThongKe.setChuaHoc(Integer.parseInt(((Element) td.get(0)).text().toString()));
                    diemThongKe.setTongMonDat(Integer.parseInt(((Element) td.get(1)).text().toString()));
                    diemThongKe.setTongHocLai(Integer.parseInt(((Element) td.get(2)).text().toString()));
                    diemThongKe.setTongDangHoc(Integer.parseInt(((Element) td.get(3)).text().toString()));
                }
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                getValueError();
            }
        });
        for(int i = 0; i < tr.size(); i++){
            final Elements td1 = ((Element) tr.get(i)).select("td");
            realm.executeTransactionAsync(new Realm.Transaction(){
                @Override
                public void execute(Realm realm) {
                    DiemTheoMon diemTheoMon = realm.createObject(DiemTheoMon.class);
                    diemTheoMon.setKyThu(Integer.parseInt(((Element) td1.get(1)).text()));
                    diemTheoMon.setHocKy(((Element) td1.get(2)).text());
                    diemTheoMon.setMon(((Element) td1.get(3)).text());
                    diemTheoMon.setMaMon(((Element) td1.get(4)).text());
                    diemTheoMon.setMaChuyenDoi(((Element) td1.get(5)).text());
                    diemTheoMon.setSoTinChi(Integer.parseInt(((Element) td1.get(6)).text()));
                    if(!((Element) td1.get(7)).text().equalsIgnoreCase("--") ) {
                        diemTheoMon.setDiem(Float.parseFloat(((Element) td1.get(7)).text()));
                    }
                    diemTheoMon.setTrangThai(((Element) td1.get(8)).text());
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    getValueError();
                }
            });
        }
    }

    private void setOptionValue(String html, Realm realm){
        final Elements elementOption = Jsoup.parse(html).select("select#term_id");
        final Elements elementValue = elementOption.select("option");

        for(int i = 0; i < elementValue.size(); i++) {
            final Element elementValues = elementValue.get(i);
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    TermId termId = realm.createObject(TermId.class);
                    termId.setValue(Integer.parseInt(((Element) elementValues).attr("value")));
                    termId.setText(((Element) elementValues).text());
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    getValueError();
                }
            });
        }
    }

    private void setDiemTheoKy(String html, Realm realm){
        final Elements Subheader = Jsoup.parse(html).getElementsByClass("subheader");
        final Elements tbody = Jsoup.parse(html).select("table").select("tbody");
        final Elements tfoot = Jsoup.parse(html).select("table").select("tfoot");

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (int i=0; i<tbody.size(); i++){
                    Elements trMon = ((Element) tbody.get(i)).select("tr");
                    Elements thMon = ((Element) tfoot.get(i)).select("th");
                    DiemThongKeTheoKy diemThongKeTheoKy = realm.createObject(DiemThongKeTheoKy.class);
                    diemThongKeTheoKy.setDiemTrungBinh(Float.parseFloat(((Element) thMon.get(1)).select("font").text()));
                    diemThongKeTheoKy.setTrangThai(((Element) thMon.get(3)).select("font").text());
                    diemThongKeTheoKy.setTenMon(((Element) Subheader.get(i)).text().substring(3));
                    for (int a=0; a<trMon.size(); a++){
                        String string = ((Element) trMon.get(a)).select("td").get(2).text();
                        String strings = string.substring(0, string.indexOf('%'));
                        String stringDiem = ((Element) trMon.get(a)).select("td").get(3).text();
                        DiemTheoKy diemTheoKy = realm.createObject(DiemTheoKy.class);
                        diemTheoKy.setTenDauDiem(((Element) trMon.get(a)).select("td").get(1).text());
                        if(!strings.isEmpty()) {
                            diemTheoKy.setTrongSo(Float.parseFloat(strings));
                        }
                        if(!stringDiem.isEmpty()) {
                            System.out.println(stringDiem +"diem");
                            diemTheoKy.setDiem(Float.parseFloat(((Element) trMon.get(a)).select("td").get(3).text()));
                        }else{
                            diemTheoKy.setDiem(0);
                        }
                        diemThongKeTheoKy.diemTheoKy.add(diemTheoKy);
                    }
                }
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                getValueError();
            }
        });
    }

    private void setDataDiemDanh(String html, Realm realm){
        final Elements Subheader = Jsoup.parse(html).select("h5.subheader");
        final Elements tbody = Jsoup.parse(html).select("table").select("tbody");
        final Elements tfoot = Jsoup.parse(html).select("table").select("tfoot");

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (int i=0; i<tbody.size(); i++){
                    Elements trMon = ((Element) tbody.get(i)).select("tr");
                    Elements thMon = ((Element) tfoot.get(i)).select("th");
                    String string = ((Element) thMon.get(0)).select("font").text();
                    String[] strings = string.split("/");
                    String string1 = strings[1];
                    String[] strings1 = string1.split(" ");
                    DiemDanhThongKe diemDanhThongKe = realm.createObject(DiemDanhThongKe.class);
                    diemDanhThongKe.setTenMon(((Element) Subheader.get(i)).text());
                    diemDanhThongKe.setBuoiVang(Integer.parseInt(strings[0]));
                    diemDanhThongKe.setTongSoBuoi(Integer.parseInt(strings1[0]));
                    diemDanhThongKe.setPhanTramVang(Float.parseFloat(strings1[1]));
                    for (int a=0; a<trMon.size(); a++){
                        DiemDanh diemDanh = realm.createObject(DiemDanh.class);
                        diemDanh.setBuoiHoc(Integer.parseInt(((Element) trMon.get(a)).select("td").get(0).text()));
                        diemDanh.setNgay(((Element) trMon.get(a)).select("td").get(1).text());
                        if(!(((Element) trMon.get(a)).select("td").get(2).text()).isEmpty()){
                            diemDanh.setCa(Integer.parseInt(((Element) trMon.get(a)).select("td").get(2).text()));
                        }else{
                            diemDanh.setCa(0);
                        }
                        diemDanh.setTrangThai(((Element) trMon.get(a)).select("td").get(5).text());
                        diemDanhThongKe.diemDanh.add(diemDanh);
                    }
                }
            }
        }, new Realm.Transaction.OnSuccess(){

            @Override
            public void onSuccess() {
                checkGetDataDiemDanh = true;
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                getValueError();
            }
        });
    }
    // ket thuc method bo sung

    public void getValueError(){
        Toast.makeText(GetDataActivity.this.getApplicationContext(), "Lỗi mạng, kết nối không phù hợp !", Toast.LENGTH_LONG).show();
        CookieSyncManager.createInstance(getApplicationContext());
        CookieManager.getInstance().removeAllCookie();
        SharedPreferences settings = getApplicationContext().getSharedPreferences("mode", getApplicationContext().MODE_PRIVATE);
        settings.edit().clear().commit();
        GetDataActivity.this.startActivity(new Intent(GetDataActivity.this.getApplicationContext(), ChooseCampusActivity.class));
        GetDataActivity.this.finish();
    }

    // SharedPreferences
    public void saveUser(String user) {
        SharedPreferences.Editor edit = getSharedPreferences("mode", Context.MODE_PRIVATE).edit();
        edit.putString("user", String.valueOf(user));
        edit.commit();
    }
    public void getSharedPreferencesSettings(){
        SharedPreferences sharedPreferencesSettings = getSharedPreferences("mode", Context.MODE_PRIVATE);
        int term_Id = sharedPreferencesSettings.getInt("TERM_ID", 20);
        int show_Count = sharedPreferencesSettings.getInt("SHOW_COUNT", 14);
        String tenDangNhap = sharedPreferencesSettings.getString("user", "");
        this.showCount = show_Count;
        this.termId = term_Id;
        this.user = tenDangNhap;
    }

    public void setProgressstatus(String ProgressText){
        GetDataActivity.this.loadText.setText(ProgressText);
        GetDataActivity.this.progressstatus =  GetDataActivity.this.progressstatus + coutProgressstatus;
        System.out.println(progressstatus);
        GetDataActivity.this.profileViewLoadding.setProgress((float) GetDataActivity.this.progressstatus);
    }

    public void checkOk(){
        new CountDownTimer(60000, 5000) {
            public void onTick(long l) {
                if (GetDataActivity.this.progressstatus >= 88 && checkGetDataDiemDanh == true) {
                    if(checkStartActivity == false) {
                        GetDataActivity.this.loadText.setText("Đang lấy thông tin...");
                        GetDataActivity.this.profileViewLoadding.setProgress(100);
                        Intent intent = new Intent(GetDataActivity.this.getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        GetDataActivity.this.startActivity(intent);
                        GetDataActivity.this.finish();
                        checkStartActivity = true;
                    }
                }
            }
            public void onFinish() {
                if (GetDataActivity.this.progressstatus >= 88) {
                    if(checkStartActivity == false) {
                        GetDataActivity.this.loadText.setText("Đang lấy thông tin...");
                        GetDataActivity.this.profileViewLoadding.setProgress(100);
                        Intent intent = new Intent(GetDataActivity.this.getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        GetDataActivity.this.startActivity(intent);
                        GetDataActivity.this.finish();
                        checkStartActivity = true;
                    }
                }
            }
        }.start();
    }
}

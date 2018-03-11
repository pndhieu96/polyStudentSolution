package com.example.hieudev.polystudentsolution.ViewActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Process;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.example.hieudev.polystudentsolution.BuildConfig;
import com.example.hieudev.polystudentsolution.Custom.NetworkInfo;
import com.example.hieudev.polystudentsolution.Dialog.LoginGoogleDialog;
import com.example.hieudev.polystudentsolution.R;

public class ChooseCampusActivity extends Activity {

    Button campusHN, campusDN, campusHCM,campusTN,campusHo;
    String useKeyString;
    WebView campusWebView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_campus);

        campusHN = (Button) findViewById(R.id.buttonCampusHN);
        campusDN = (Button) findViewById(R.id.buttonCampusDN);
        campusHCM = (Button) findViewById(R.id.buttonCampusHCM);
        campusTN = (Button) findViewById(R.id.buttonCampusTN);
        campusHo = (Button) findViewById(R.id.buttonCampusHo);
        campusWebView = (WebView) findViewById(R.id.webviewCampus);

        SharedPreferences pre = this.getApplicationContext().getSharedPreferences("mode", this.getApplication().MODE_PRIVATE);
        useKeyString = pre.getString("user", "");
        if(!useKeyString.equals("")){
            this.getCampus();
        }


        this.campusWebView.setVisibility(View.GONE);
        this.campusWebView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url){
                super.onPageFinished(view, url);
                ChooseCampusActivity.this.progressDialog.dismiss();
                Intent loginIntent = new Intent(ChooseCampusActivity.this, LoginGoogleDialog.class);
                startActivity(loginIntent);
                ChooseCampusActivity.this.finish();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                ChooseCampusActivity.this.progressDialog.dismiss();
                SharedPreferences settings = getApplicationContext().getSharedPreferences("mode", getApplicationContext().MODE_PRIVATE);
                settings.edit().clear().commit();
                Toast.makeText(ChooseCampusActivity.this.getApplicationContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });

        this.campusHN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkInfo.isConnected(ChooseCampusActivity.this.getApplicationContext())){
                    ChooseCampusActivity.this.progressDialog = ProgressDialog.show(ChooseCampusActivity.this, BuildConfig.FLAVOR, "Đang kết nối");
                    ChooseCampusActivity.this.progressDialog.setCancelable(false);
                    ChooseCampusActivity.this.campusWebView.loadUrl("http://ap.poly.edu.vn/choose_campus.php?campus_id=1");
                    ChooseCampusActivity.this.saveCampus(1);
                    return;
                }
                new AlertDialog.Builder(ChooseCampusActivity.this).setMessage("Không có kết nối Internet !").setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface, int id){
                        ChooseCampusActivity.this.moveTaskToBack(true);
                        Process.killProcess(Process.myPid());
                        System.exit(1);
                    }
                }).setCancelable(false).create().show();
            }
        });

        this.campusDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkInfo.isConnected(ChooseCampusActivity.this.getApplicationContext())){
                    ChooseCampusActivity.this.progressDialog = ProgressDialog.show(ChooseCampusActivity.this, BuildConfig.FLAVOR, "Đang kết nối");
                    ChooseCampusActivity.this.progressDialog.setCancelable(false);
                    ChooseCampusActivity.this.campusWebView.loadUrl("http://ap.poly.edu.vn/choose_campus.php?campus_id=2");
                    ChooseCampusActivity.this.saveCampus(2);
                    return;
                }
                new AlertDialog.Builder(ChooseCampusActivity.this).setMessage("Không có kết nối Internet !").setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface, int id){
                        ChooseCampusActivity.this.moveTaskToBack(true);
                        Process.killProcess(Process.myPid());
                        System.exit(1);
                    }
                }).setCancelable(false).create().show();
            }
        });

        this.campusHCM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkInfo.isConnected(ChooseCampusActivity.this.getApplicationContext())){
                    ChooseCampusActivity.this.progressDialog = ProgressDialog.show(ChooseCampusActivity.this, BuildConfig.FLAVOR, "Đang kết nối");
                    ChooseCampusActivity.this.progressDialog.setCancelable(false);
                    ChooseCampusActivity.this.campusWebView.loadUrl("http://ap.poly.edu.vn/choose_campus.php?campus_id=3");
                    ChooseCampusActivity.this.saveCampus(3);
                    return;
                }
                new AlertDialog.Builder(ChooseCampusActivity.this).setMessage("Không có kết nối Internet !").setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface, int id){
                        ChooseCampusActivity.this.moveTaskToBack(true);
                        Process.killProcess(Process.myPid());
                        System.exit(1);
                    }
                }).setCancelable(false).create().show();
            }
        });

        this.campusTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkInfo.isConnected(ChooseCampusActivity.this.getApplicationContext())){
                    ChooseCampusActivity.this.progressDialog = ProgressDialog.show(ChooseCampusActivity.this, BuildConfig.FLAVOR, "Đang kết nối");
                    ChooseCampusActivity.this.progressDialog.setCancelable(false);
                    ChooseCampusActivity.this.campusWebView.loadUrl("http://ap.poly.edu.vn/choose_campus.php?campus_id=6");
                    ChooseCampusActivity.this.saveCampus(6);
                    return;
                }
                new AlertDialog.Builder(ChooseCampusActivity.this).setMessage("Không có kết nối Internet !").setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface, int id){
                        ChooseCampusActivity.this.moveTaskToBack(true);
                        Process.killProcess(Process.myPid());
                        System.exit(1);
                    }
                }).setCancelable(false).create().show();
            }
        });

        this.campusHo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkInfo.isConnected(ChooseCampusActivity.this.getApplicationContext())){
                    ChooseCampusActivity.this.progressDialog = ProgressDialog.show(ChooseCampusActivity.this, BuildConfig.FLAVOR, "Đang kết nối");
                    ChooseCampusActivity.this.progressDialog.setCancelable(false);
                    ChooseCampusActivity.this.campusWebView.loadUrl("http://ap.poly.edu.vn/choose_campus.php?campus_id=8");
                    ChooseCampusActivity.this.saveCampus(8);
                    return;
                }
                new AlertDialog.Builder(ChooseCampusActivity.this).setMessage("Không có kết nối Internet !").setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface, int id){
                        ChooseCampusActivity.this.moveTaskToBack(true);
                        Process.killProcess(Process.myPid());
                        System.exit(1);
                    }
                }).setCancelable(false).create().show();
            }
        });

    }

    public void saveCampus(int campus) {
        SharedPreferences.Editor edit = getSharedPreferences("mode", 0).edit();
        edit.putString("campus", String.valueOf(campus));
        edit.commit();
    }

    public void getCampus() {
        String a = getSharedPreferences("mode", 0).getString("campus", BuildConfig.FLAVOR) + BuildConfig.FLAVOR;
        if (a.equals("1")) {
            ChooseCampusActivity.this.progressDialog = ProgressDialog.show(ChooseCampusActivity.this, BuildConfig.FLAVOR, "Đang kết nối");
            ChooseCampusActivity.this.progressDialog.setCancelable(false);
            this.campusWebView.loadUrl("http://ap.poly.edu.vn/choose_campus.php?campus_id=1");
        }
        if (a.equals("2")) {
            ChooseCampusActivity.this.progressDialog = ProgressDialog.show(ChooseCampusActivity.this, BuildConfig.FLAVOR, "Đang kết nối");
            ChooseCampusActivity.this.progressDialog.setCancelable(false);
            this.campusWebView.loadUrl("http://ap.poly.edu.vn/choose_campus.php?campus_id=2");
        }
        if (a.equals("3")) {
            ChooseCampusActivity.this.progressDialog = ProgressDialog.show(ChooseCampusActivity.this, BuildConfig.FLAVOR, "Đang kết nối");
            ChooseCampusActivity.this.progressDialog.setCancelable(false);
            this.campusWebView.loadUrl("http://ap.poly.edu.vn/choose_campus.php?campus_id=3");
        }
        if (a.equals("6")) {
            ChooseCampusActivity.this.progressDialog = ProgressDialog.show(ChooseCampusActivity.this, BuildConfig.FLAVOR, "Đang kết nối");
            ChooseCampusActivity.this.progressDialog.setCancelable(false);
            this.campusWebView.loadUrl("http://ap.poly.edu.vn/choose_campus.php?campus_id=4");
        }
        if (a.equals("8")) {
            ChooseCampusActivity.this.progressDialog = ProgressDialog.show(ChooseCampusActivity.this, BuildConfig.FLAVOR, "Đang kết nối");
            ChooseCampusActivity.this.progressDialog.setCancelable(false);
            this.campusWebView.loadUrl("http://ap.poly.edu.vn/choose_campus.php?campus_id=8");
        }
    }
}

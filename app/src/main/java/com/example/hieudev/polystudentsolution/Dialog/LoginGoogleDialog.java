package com.example.hieudev.polystudentsolution.Dialog;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Process;
import android.support.v7.app.AlertDialog.Builder;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.hieudev.polystudentsolution.BuildConfig;
import com.example.hieudev.polystudentsolution.HandleActivity.GetDataActivity;
import com.example.hieudev.polystudentsolution.R;
import com.example.hieudev.polystudentsolution.ViewActivity.ChooseCampusActivity;
import com.example.hieudev.polystudentsolution.ViewActivity.MainActivity;

public class LoginGoogleDialog extends AppCompatActivity {

    private WebView loginGgWebview;
    private String useKeyStringLogin;
    private ProgressDialog progressDialogLogin;
    private ProgressDialog progressDialogLogin1;
    private int loginGgCount;
    private boolean loginGgBoolean;
    private boolean loginGGBooleanNoError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.activity_login_google_dialog);
        loginGgWebview = (WebView) findViewById(R.id.loginggWebview);
        SharedPreferences pre = this.getSharedPreferences("mode", this.MODE_PRIVATE);
        useKeyStringLogin = pre.getString("user", "");
        this.loginGgCount = 0;
        this.loginGgBoolean = false;
        if(!useKeyStringLogin.equals("")) {
            this.loginGgWebview.setVisibility(View.GONE);
            this.progressDialogLogin = ProgressDialog.show(LoginGoogleDialog.this, BuildConfig.FLAVOR, "Kiểm tra đăng nhập...");
            this.progressDialogLogin.setCancelable(false);
            this.loginGGBooleanNoError = true;
        }

        // define useragent android like
        String userAgent = "Mozilla/5.0 (Linux; Android 5.1.1; Nexus 5 Build/LMY48B; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/43.0.2357.65 Mobile Safari/537.36";

        this.loginGgWebview.getSettings().setUserAgentString("Android");
        this.loginGgWebview.getSettings().setJavaScriptEnabled(true);
        this.loginGgWebview.getSettings().setLoadsImagesAutomatically(true);
        this.loginGgWebview.getSettings().setBlockNetworkImage(false);
        this.loginGgWebview.getSettings().setSavePassword(false);
        this.loginGgWebview.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                System.out.println(url + "url login on page finished");
                if (url.equals("https://accounts.google.com/ServiceLoginAuth") || url.equals("https://accounts.google.com/AccountLoginInfo") || url.equals("https://accounts.google.com/ServiceLoginAuth#password")) {
                    new Builder(LoginGoogleDialog.this).setMessage("Bạn đã nhập sai tài khoản hoặc mật khẩu !").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    }).create().show();
                }
                if (url.endsWith("offline")) {
                    Toast.makeText(LoginGoogleDialog.this, "Lỗi kết nối !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginGoogleDialog.this, ChooseCampusActivity.class);
                    LoginGoogleDialog.this.finish();
                    LoginGoogleDialog.this.startActivity(intent);
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                loginEroor();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                loginEroor();
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                System.out.println(url + "url shouldOverrideUrlLoading");
                if(loginGGBooleanNoError){
                    new CountDownTimer(30000,30000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            if(loginGGBooleanNoError == true){
                                loginEroor();
                            }
                        }
                    }.start();
                }
                if(url.contains("http://ap.poly.edu.vn/hybridauth/?hauth.done=Google&code=4")) {
                    loginGgCount ++;
                    if(useKeyStringLogin.equals("") && loginGgCount == 1) {
                        progressDialogLogin1 = ProgressDialog.show(LoginGoogleDialog.this, BuildConfig.FLAVOR, "Kiểm tra đăng nhập...");
                        progressDialogLogin1.setCancelable(false);
                        loginGgBoolean = true;
                    }
                }
                if (url.equals("http://ap.poly.edu.vn/students/index.php#") || url.equals("http://ap.poly.edu.vn/students/index.php") || url.equals("http://ap.poly.edu.vn/index.php#")) {
                    LoginGoogleDialog.this.finish();
                    if(!useKeyStringLogin.equals("")) {
                        progressDialogLogin.cancel();
                        loginGGBooleanNoError = false;
                    }
                    if(loginGgBoolean == true){
                        progressDialogLogin1.cancel();
                    }
                    LoginGoogleDialog.this.startActivity(new Intent(LoginGoogleDialog.this, GetDataActivity.class));
                    LoginGoogleDialog.this.finish();
                    return true;
                }
                if (url.equals("http://ap.poly.edu.vn/feedback/#") || url.equals("http://ap.poly.edu.vn/feedback/#") || url.equals("http://ap.poly.edu.vn/feedback/index.php#")) {
                    new Builder(LoginGoogleDialog.this).setMessage("Có thể bạn đang có một feedback, đăng nhập bằng PC điền feedback trước nhé!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Process.killProcess(Process.myPid());
                            System.exit(1);
                        }
                    }).create().show();
                }
                if (!url.equals("http://ap.poly.edu.vn/choose_campus.php?noredirect=1&msg=Login%20failed!#") && !url.equals("http://ap.poly.edu.vn/index.php?msg=You%20are%20not%20connected%20to%20Google%20with%20account%20fpt.edu.vn%20%20or%20your%20session%20has%20expired#")) {
                    return super.shouldOverrideUrlLoading(view, url);
                }
                return true;
            }
        });
        this.loginGgWebview.loadUrl("http://ap.poly.edu.vn/hlogin.php?provider=Google");
    }

    public void loginEroor(){
        Toast.makeText(LoginGoogleDialog.this, "Lỗi đăng nhập, vui lòng thử lại...", Toast.LENGTH_SHORT).show();
        CookieSyncManager.createInstance(getApplicationContext());
        CookieManager.getInstance().removeAllCookie();
        SharedPreferences settings = getSharedPreferences("mode", getApplicationContext().MODE_PRIVATE);
        settings.edit().clear().commit();
        Intent intent = new Intent(LoginGoogleDialog.this, ChooseCampusActivity.class);
        this.startActivity(intent);
        this.finish();
    }
}

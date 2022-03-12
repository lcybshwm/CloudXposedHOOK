package com.CloudHook.kele;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import android.widget.Toast;


import com.CloudHook.myhook.R;
import com.tencent.smtt.sdk.WebViewClient;


public class Main2Activity extends Activity {
    public String modtext;
    public TextView m_textview;


    private com.tencent.smtt.sdk.WebView mWebView;
    public static int versionCode = 3;
    public static String appsinfo = "https://tiandi.lovetom.top/home/";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main2);
        requestPermission();
        Toast.makeText(this, "页面正在加载中...", Toast.LENGTH_SHORT).show();
        mWebView=findViewById(R.id.webview);
        mWebView = (com.tencent.smtt.sdk.WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);// 支持js
        mWebView.setWebViewClient(new WebViewClient());//防止加载网页时调起系统浏览器

        mWebView.loadUrl(appsinfo);
//        Welcome();
//
//        SharedPreferences app2 = getSharedPreferences("appinfo", 0);
//        //获取Editor对象
//        SharedPreferences.Editor edit = app2.edit();
//        //根据要保存的数据的类型，调用对应的put方法,
//        //以键值对的形式添加新值。
//        edit.putString("xposedvalue", "1");
//        //提交新值。必须执行，否则前面的操作都无效。
//        edit.commit();




    }
    public void Welcome(){
        Toast.makeText(this, "天帝强力Hook引擎\n模块当前激活状态:"+is_activate(), Toast.LENGTH_SHORT).show();
    }
    public boolean is_activate(){
        return false;
    }
    public void requestPermission() {
        //权限检查
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }
//    public void getResponse() {
////        String url = Tools.hexStringToString(appsinfo) + "?appcode=" + versionCode;
//        String url = appsinfo + "?appcode=" + versionCode;
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//        final Request request = new Request.Builder()
//                .url(url)
//                .build();
//        final Call call = okHttpClient.newCall(request);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Response response = call.execute();
//                    Message msg = new Message();
//                    msg.what = 1;
//                    msg.obj = response.body().string();
//                    mHandler.sendMessage(msg);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }





}

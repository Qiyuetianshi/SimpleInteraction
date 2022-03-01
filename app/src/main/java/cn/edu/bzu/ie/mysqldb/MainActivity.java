package cn.edu.bzu.ie.mysqldb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * 主界面业务逻辑代码
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_query_count, btn_login,btn_logup;   // 查询用户数量的按钮，登录按钮
    private TextView tv_user_count;   // 用户数量文本框

    private EditText et_uname, et_upass;  // 用户名、密码框

    private UserDao dao;   // 用户数据库操作类
    private Handler mainHandler ;     // 主线程

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            if(msg.what==0){
                int count = (Integer)msg.obj;
                tv_user_count.setText("数据库中的用户数量为："+count);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
//        btn_query_count = findViewById(R.id.btn_query_count);
//        tv_user_count = findViewById(R.id.tv_user_count);

        et_uname = findViewById(R.id.et_uname);
        et_upass = findViewById(R.id.et_upass);

        btn_login = findViewById(R.id.btn_login);
        btn_logup = findViewById(R.id.btn_logup);
        dao = new UserDao();
        mainHandler = new Handler(getMainLooper());   // 获取主线程

//        btn_query_count.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_logup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.btn_query_count:   // 查询数量
//                doQueryCount();
//                break;

            case R.id.btn_login:    // 登录按钮
                doLogin();
                break;

            case R.id.btn_logup:    // 登录按钮
                Intent intent = new Intent(this, UserAddActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    // 执行查询用户数量的方法
    private void doQueryCount(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = MySqlHelp.getUserSize();
                Message msg = Message.obtain();
                msg.what = 0;   // 查询结果
                msg.obj = count;
                // 向主线程发送数据
                handler.sendMessage(msg);
            }
        }).start();
    }
    private boolean checkPackInfo(String packname) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(packname, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    private void dogo(){

//        PackageManager packageManager = getApplicationContext().getPackageManager();
//        List<ApplicationInfo>  a =  packageManager.getInstalledApplications(0);
//
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        ComponentName cn = new ComponentName("packageName", "className");
//        intent.setComponent(cn);
//        startActivity(intent);

        PackageManager packageManager = getPackageManager();
        if (checkPackInfo("com.MobileTicket")) {
            Intent intent = packageManager.getLaunchIntentForPackage("com.MobileTicket");
            startActivity(intent);
        } else {

//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.12306.cn/index/"));
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.12306.cn/index/"));
//            Uri uri = Uri.parse("market://search?q=" + "铁路12306");
//            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
//            try {
//                startActivity(goToMarket);
//            } catch (ActivityNotFoundException e) {
//                e.printStackTrace();
//            }
            Uri uri = Uri.parse("market://details?id=" + "com.MobileTicket");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                this.startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
//            startActivity(intent);
//            Toast.makeText(MainActivity.this, "没有安装" + "com.MobileTicket", 1).show();
        }
    }

    // 执行登录操作
    private void doLogin(){
        final String uname = et_uname.getText().toString().trim();
        final String upass = et_upass.getText().toString().trim();

        if(TextUtils.isEmpty(uname)){
            CommonUtils.showShortMsg(this, "请输入用户名");
            et_uname.requestFocus();
        }else if(TextUtils.isEmpty(upass)){
            CommonUtils.showShortMsg(this, "请输入用户密码");
            et_upass.requestFocus();
        }else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Userinfo item = dao.getUserByUnameAndUpass(uname, upass);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(item == null){
                                CommonUtils.showDlgMsg(MainActivity.this, "用户名或密码错误");
                            }else{
                                //CommonUtils.showDlgMsg(MainActivity.this, "登录成功，进入用户管理");
                                CommonUtils.showLonMsg(MainActivity.this, "登录成功，进入用户管理");
                                // 调用用户管理界面
                                Intent intent = new Intent(MainActivity.this, UserManagerActivity.class);
                                intent.putExtra("patname",uname);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }).start();
        }
    }
}

package cn.edu.bzu.ie.mysqldb;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
/**
 * 主界面业务逻辑代码
 */
public class meActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_on, btn_rex,btn_off;   // 查询用户数量的按钮，登录按钮
    private TextView tv_state;   // 用户数量文本框

    private ImageView btn_me, btn_mes;

    private String proname;

    private UserDao userDao;// 用户数据库操作类
    private Handler mainHandler ;     // 主线程

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            if(msg.what==0){
                int count = (Integer)msg.obj;
                switch (count){
                    case 1:
                        tv_state.setText("在线");
                        break;
                    case 2:
                        tv_state.setText("休息");
                        break;
                    case 3:
                        tv_state.setText("离线");
                        break;
                    default:
                        tv_state.setText("离线");
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        Intent intent =getIntent();
        proname=intent.getStringExtra("proname");
        initView();
        doQueryCount();
    }

    private void initView(){
        btn_mes = findViewById(R.id.btn_mes);
        btn_on = findViewById(R.id.btn_on);
        btn_off = findViewById(R.id.btn_off);
        btn_rex = findViewById(R.id.btn_rex);
        tv_state = findViewById(R.id.tv_state);
        userDao = new UserDao();
        mainHandler = new Handler(getMainLooper());   // 获取主线程

        btn_mes.setOnClickListener(this);
        btn_off.setOnClickListener(this);
        btn_rex.setOnClickListener(this);
        btn_on.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_mes:   // room
                Intent intent = new Intent(meActivity.this, roomActivity.class);
                intent.putExtra("proname",proname);
                startActivity(intent);
                break;
            case R.id.btn_on:    // 登录按钮
                updatestate(1);
                break;
            case R.id.btn_rex:
                updatestate(2);
                break;
            case R.id.btn_off:
                updatestate(3);
                break;

        }
    }

    private void doQueryCount(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = userDao.getUserByUname(proname).getStatement();
                Message msg = Message.obtain();
                msg.what = 0;   // 查询结果
                msg.obj = count;
                handler.sendMessage(msg);
            }
        }).start();
    }


    private void updatestate(int state){
        new Thread(new Runnable() {
            @Override
            public void run() {
                userDao.updatestatement(state,proname);   // 获取所有room
                int count = userDao.getUserByUname(proname).getStatement();
                Message msg = Message.obtain();
                msg.what = 0;   // 查询结果
                msg.obj = count;
                handler.sendMessage(msg);
            }
        }).start();
    }


//    // 执行查询用户数量的方法
//    private void doQueryCount(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int count = MySqlHelp.getUserSize();
//                Message msg = Message.obtain();
//                msg.what = 0;   // 查询结果
//                msg.obj = count;
//                // 向主线程发送数据
//                handler.sendMessage(msg);
//            }
//        }).start();
//    }

//    // 执行登录操作
//    private void doLogin(){
//        final String uname = et_uname.getText().toString().trim();
//        final String upass = et_upass.getText().toString().trim();
//
//        if(TextUtils.isEmpty(uname)){
//            CommonUtils.showShortMsg(this, "请输入用户名");
//            et_uname.requestFocus();
//        }else if(TextUtils.isEmpty(upass)){
//            CommonUtils.showShortMsg(this, "请输入用户密码");
//            et_upass.requestFocus();
//        }else{
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    final Userinfo item = dao.getUserByUnameAndUpass(uname, upass);
//                    mainHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            if(item == null){
//                                CommonUtils.showDlgMsg(meActivity.this, "用户名或密码错误");
//                            }else{
//                                //CommonUtils.showDlgMsg(MainActivity.this, "登录成功，进入用户管理");
//                                CommonUtils.showLonMsg(meActivity.this, "登录成功，进入用户管理");
//                                // 调用用户管理界面
//                                Intent intent = new Intent(meActivity.this, roomActivity.class);
//                                intent.putExtra("proname",uname);
//                                startActivity(intent);
//                            }
//                        }
//                    });
//                }
//            }).start();
//        }
//    }
}

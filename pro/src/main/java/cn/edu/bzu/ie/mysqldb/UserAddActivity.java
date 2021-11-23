package cn.edu.bzu.ie.mysqldb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

/**
 * 添加用户的业务代码
 */
public class UserAddActivity extends AppCompatActivity {
    private EditText et_uname, et_upass,et_state,et_info;

    private UserDao userDao;   // 用户数据操作类实例

    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);

        et_uname = findViewById(R.id.et_uname);
        et_upass = findViewById(R.id.et_upass);

        et_state = findViewById(R.id.et_state);
        et_info = findViewById(R.id.et_info);


        userDao = new UserDao();
        mainHandler = new Handler(getMainLooper());
    }

    // 确定按钮的点击事件处理
    public void btn_ok_click(View view){
        final String uname = et_uname.getText().toString().trim();
        final String upass = et_upass.getText().toString().trim();
        final String state = et_state.getText().toString().trim();
        final String info = et_info.getText().toString().trim();

        if(TextUtils.isEmpty(uname)){
            CommonUtils.showShortMsg(this, "请输入用户名");
            et_uname.requestFocus();
        }else if(TextUtils.isEmpty(upass)){
            CommonUtils.showShortMsg(this, "请输入用户密码");
            et_upass.requestFocus();
        }else if(TextUtils.isEmpty(state)){
            CommonUtils.showShortMsg(this, "请输入用户密码");
            et_upass.requestFocus();
        }else if(TextUtils.isEmpty(info)){
            CommonUtils.showShortMsg(this, "请输入用户密码");
            et_upass.requestFocus();
        }else {
            final Userinfo item = new Userinfo();

            item.setUname(uname);
            item.setUpass(upass);
            item.setInfo(state);
            item.setState(info);
//            item.setCreateDt(CommonUtils.getDateStrFromNow());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    final boolean iRow = userDao.addUser(item);

                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(iRow){
                                CommonUtils.showLonMsg(UserAddActivity.this, "注册成功");
                                setResult(1);   // 使用参数表示当前界面操作成功，并返回管理界面
                                finish();
                            }else{
                                CommonUtils.showDlgMsg(UserAddActivity.this, "用户名已被注册");
                            }
                        }
                    });
                }
            }).start();
        }

    }
}

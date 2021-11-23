package cn.edu.bzu.ie.mysqldb;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 用户管理界面业务逻辑
 */
public class roomActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView btn_home, btn_add;   // 返回图片按钮 ，添加图片按钮

    private UserDao userDao;    // 用户数据库操作实例z

    //    private List<Userinfo> userinfoList;   // 用户数据集合
    private List<room> roominfoList;

    private roominfoAdapter roominfoAdapter;   // 用户信息数据适配器

    private ListView lv_user;   // 用户列表组件

    private Handler mainHandler;   // 主线程

    private String proname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_manager);
        Intent intent =getIntent();
        proname=intent.getStringExtra("proname");
        initView();
        loadUserDb();
    }

    private void initView(){
        btn_home = findViewById(R.id.btn_home);
        btn_add = findViewById(R.id.btn_mes);

        lv_user = findViewById(R.id.lv_user);

        userDao = new UserDao();
        mainHandler = new Handler(getMainLooper());

//        btn_return.setOnClickListener(this);
        btn_home.setOnClickListener(this);
    }

    private void loadUserDb(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                roominfoList = userDao.getAllroom(proname);   // 获取所有room
                Log.i("管理界面的数据", "room数量："+roominfoList.size());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showLvData();
                    }
                });
            }
        }).start();
    }

    // 显示列表数据的方法
    private void showLvData(){
        if(roominfoAdapter==null){   // 首次加载时的操作
            roominfoAdapter = new roominfoAdapter(this, roominfoList);
            lv_user.setAdapter(roominfoAdapter);
        }else{   // 更新数据时的操作
            roominfoAdapter.setroominfoList(roominfoList);
            roominfoAdapter.notifyDataSetChanged();
        }
        // message
        roominfoAdapter.setOnMesBtnClickListener(new OnMesBtnClickListener() {
            @Override
            public void onMesBtnClick(View view, int position) {
                // 修改按钮的操作
                room item = roominfoList.get(position);
                Intent intent = new Intent(roomActivity.this, messageActivity.class);
                intent.putExtra("proname",proname);
                intent.putExtra("roomId",item.getId());
                startActivityForResult(intent, 1);
            }
        });

    }

    /**
     * 执行删除用户的方法
     * @param id 要删除用户的id
     */
    private void doDelUser(final int id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final int iRow = userDao.delUser(id);
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        loadUserDb();  // 重新加载数据
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_home:
                Intent intent = new Intent(roomActivity.this, meActivity.class);
                intent.putExtra("proname",proname);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==1){   // 操作成功
            loadUserDb();   // 重新加载数据
        }
    }
}

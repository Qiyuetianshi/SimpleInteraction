package cn.edu.bzu.ie.mysqldb;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * 用户管理界面业务逻辑
 */
public class UserManagerActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView btn_honme, btn_room;   // 返回图片按钮 ，添加图片按钮
    private Button btn_find;
    private EditText et_mes;
    private LinearLayout foc;

    private UserDao userDao;    // 用户数据库操作实例

//    private List<Userinfo> userinfoList;   // 用户数据集合
    private List<proinfo> userinfoList;

    private LvUserinfoAdapter lvUserinfoAdapter;   // 用户信息数据适配器

    private ListView lv_user;   // 用户列表组件

    private Handler mainHandler;   // 主线程

    private String patname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);
        Intent intent =getIntent();
        patname=intent.getStringExtra("patname");
        Log.i("管理界面的数据", patname);
        initView();
        loadUserDb();
    }

    private void initView(){
        btn_honme = findViewById(R.id.btn_home);
        btn_room = findViewById(R.id.btn_room);
        et_mes = findViewById(R.id.et_mes);
        lv_user = findViewById(R.id.lv_user);
        btn_find = findViewById(R.id.btn_find);
        userDao = new UserDao();
        mainHandler = new Handler(getMainLooper());
        foc = findViewById(R.id.sfa);
        btn_honme.setOnClickListener(this);
        btn_room.setOnClickListener(this);
        btn_find.setOnClickListener(this);
    }

    private void find(){
        final String uname = et_mes.getText().toString().trim();
        if(TextUtils.isEmpty(uname)){
            loadUserDb();
            foc.requestFocus();
        }else{
            foc.requestFocus();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    userinfoList = userDao.getsomeUserList(uname);   // 获取所有的用户数据
                    Log.i("管理界面的数据", "用户数量："+userinfoList.size());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            showLvData();
                        }
                    });
                }
            }).start();
        }
    }

    private void loadUserDb(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                userinfoList = userDao.getAllUserList();   // 获取所有的用户数据
                Log.i("管理界面的数据", "用户数量："+userinfoList.size());
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
        if(lvUserinfoAdapter==null){   // 首次加载时的操作
            lvUserinfoAdapter = new LvUserinfoAdapter(this, userinfoList);
            lv_user.setAdapter(lvUserinfoAdapter);
        }else{   // 更新数据时的操作
            lvUserinfoAdapter.setUserinfoList(userinfoList);
            lvUserinfoAdapter.notifyDataSetChanged();
        }
        // message
        lvUserinfoAdapter.setOnMesBtnClickListener(new OnMesBtnClickListener() {
            @Override
            public void onMesBtnClick(View view, int position) {
                // 修改按钮的操作

                proinfo item = userinfoList.get(position);
                int state = item.getStatement();
                if(state == 1){
                    final room item2  = new room();
                    item2.setProname(item.getUname());
                    item2.setPatname(patname);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final boolean iRow = userDao.creatRoom(item2);
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(iRow){
                                        CommonUtils.showDlgMsg(UserManagerActivity.this, "创建成功");
                                        Intent intent = new Intent(UserManagerActivity.this, roomActivity.class);
                                        intent.putExtra("patname",patname);
                                        startActivityForResult(intent, 1);
                                    }else{
                                        CommonUtils.showDlgMsg(UserManagerActivity.this, "创建咨询失败");
                                    }
                                }
                            });
                        }
                    }).start();
                }
            }
        });

//        // 修改按钮的操作
//        lvUserinfoAdapter.setOnEditBtnClickListener(new OnEditBtnClickListener() {
//            @Override
//            public void onEditBtnClick(View view, int position) {
//                // 修改按钮的操作
//                proinfo item = userinfoList.get(position);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("userEdit", item);
//                Intent intent = new Intent(UserManagerActivity.this, UseEditActivity.class);
//                intent.putExtras(bundle);
//                startActivityForResult(intent, 1);
//            }
//        });

        // 删除按钮的操作
//        lvUserinfoAdapter.setOnDelBtnClickListener(new OnDelBtnClickListener() {
//            @Override
//            public void onDelBtnClick(View view, int position) {
//                //  删除方法
//                final proinfo item = userinfoList.get(position);
//                new AlertDialog.Builder(UserManagerActivity.this)
//                        .setTitle("删除确定")
//                        .setMessage("您确定要删除：id:["+item.getId()+"]，uname:["+
//                                item.getUname()+"]的用户信息吗？")
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                doDelUser(item.getId());
//                            }
//                        })
//                        .setNegativeButton("取消", null)
//                        .create().show();
//            }
//        });
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
            case R.id.btn_room:
                Intent intent = new Intent(UserManagerActivity.this, roomActivity.class);
                intent.putExtra("patname",patname);
                startActivity(intent);
                break;
            case R.id.btn_find:
                find();
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

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
import android.widget.ListView;

import java.util.List;

/**
 * 用户管理界面业务逻辑
 */
public class messageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_send;   // 返回图片按钮 ，添加图片按钮

   private ImageView  btn_home;

    private UserDao userDao;    // 用户数据库操作实例z

    //    private List<Userinfo> userinfoList;   // 用户数据集合
    private List<message> messageinfoList;

    private messageinfoAdatper messageinfoAdatper;   // 用户信息数据适配器

    private EditText et_mes;

    private ListView lv_user;   // 用户列表组件

    private Handler mainHandler;   // 主线程

    private String proname;

    private int roomID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_manager);
        Intent intent =getIntent();
        roomID=intent.getIntExtra("roomId",-1);
        proname=intent.getStringExtra("proname");
        initView();
        loadUserDb();
    }

    private void initView(){
        btn_home = findViewById(R.id.btn_home);

        btn_send= findViewById(R.id.btn_send);

        lv_user = findViewById(R.id.lv_user);

        et_mes = findViewById(R.id.et_mes);

        userDao = new UserDao();

        btn_send.setOnClickListener(this);
        btn_home.setOnClickListener(this);

        mainHandler = new Handler(getMainLooper());

    }

    private void loadUserDb(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                messageinfoList = userDao.getAllmessage(roomID);   // getAllmessage
                userDao.updateroompro(roomID);
                Log.i("管理界面的数据", "message数量："+messageinfoList.size());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showLvData();
                    }
                });
            }
        }).start();
    }

    private void sendmessage(){
        final String mes = et_mes.getText().toString().trim();
        if(TextUtils.isEmpty(mes)){
            CommonUtils.showShortMsg(this, "请输入您需要发送的信息");
            et_mes.requestFocus();
        }else{
            et_mes.setText(null);
            final message item = new message();
            item.setSendname(proname);
            item.setRoomId(roomID);
            item.setMes(mes);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    final boolean iRow = userDao.sendmessage(item);
                    userDao.updateroomstatepat(roomID);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(iRow){
                                loadUserDb();
                            }else{
                                CommonUtils.showDlgMsg(messageActivity.this, "发送失败");
                            }
                        }
                    });
                }
            }).start();
        }
    }

    // 显示列表数据的方法
    private void showLvData(){
        if(messageinfoAdatper==null){   // 首次加载时的操作
            messageinfoAdatper = new messageinfoAdatper(this, messageinfoList);
            lv_user.setAdapter(messageinfoAdatper);
        }else{   // 更新数据时的操作
            messageinfoAdatper.setmessageinfoList(messageinfoList);
            messageinfoAdatper.notifyDataSetChanged();
        }
        // message
        messageinfoAdatper.setOnMesBtnClickListener(new OnMesBtnClickListener() {
            @Override
            public void onMesBtnClick(View view, int position) {
//                // 修改按钮的操作
//                room item = roominfoList.get(position);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("userEdit", item);
//                Intent intent = new Intent(roomActivity.this, messageActivity.class);
//                intent.putExtras(bundle);
//                startActivityForResult(intent, 1);
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
            case R.id.btn_send:
                sendmessage();
                break;
            case R.id.btn_home:
                Intent intent = new Intent(messageActivity.this, roomActivity.class);
                intent.putExtra("proname",proname);
                startActivityForResult(intent, 1);
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

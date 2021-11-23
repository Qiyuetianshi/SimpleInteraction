package cn.edu.bzu.ie.mysqldb;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * 自定义用户数据适配器类
 */
public class LvUserinfoAdapter extends BaseAdapter {
    private Context context;    // 上下文信息
    private List<proinfo> userinfoList;    // 用户信息数据集合

    private OnEditBtnClickListener onEditBtnClickListener;   // 修改按钮点击事件的监听实例
    private OnDelBtnClickListener onDelBtnClickListener;     // 删除按钮 点击事件的监听实例
    private OnMesBtnClickListener OnMesBtnClickListener;

    public LvUserinfoAdapter() {

    }

    public LvUserinfoAdapter(Context context, List<proinfo> userinfoList) {
        this.context = context;
        this.userinfoList = userinfoList;
        Log.i("数据适配器", "用户数量："+userinfoList.size());
    }

    public void setUserinfoList(List<proinfo> userinfoList) {
        this.userinfoList = userinfoList;
    }

//    public void setOnEditBtnClickListener(OnEditBtnClickListener onEditBtnClickListener) {
//        this.onEditBtnClickListener = onEditBtnClickListener;
//    }
//
//    public void setOnDelBtnClickListener(OnDelBtnClickListener onDelBtnClickListener) {
//        this.onDelBtnClickListener = onDelBtnClickListener;
//    }

    public void setOnMesBtnClickListener(OnMesBtnClickListener onMesBtnClickListener) {
        OnMesBtnClickListener = onMesBtnClickListener;
    }

    @Override
    public int getCount() {
        return userinfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return userinfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.user_list_item, null);
            viewHolder = new ViewHolder();

//            viewHolder.tv_id = convertView.findViewById(R.id.tv_id);
            viewHolder.tv_uname = convertView.findViewById(R.id.tv_uname);
            viewHolder.tv_state = convertView.findViewById(R.id.tv_state);
            viewHolder.tv_info = convertView.findViewById(R.id.tv_info);
            viewHolder.btn_mes = convertView.findViewById(R.id.btn_mes);
//            viewHolder.btn_edit = convertView.findViewById(R.id.btn_edit);
//            viewHolder.btn_delete = convertView.findViewById(R.id.btn_delete);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 这里进行数据填充
        proinfo item = userinfoList.get(position);
        viewHolder.tv_uname.setText(item.getUname());
        viewHolder.tv_state.setText(item.getState());
        viewHolder.tv_info.setText(item.getInfo());
        viewHolder.tv_info.setText(item.getInfo());
        if(item.getStatement()==2){
            viewHolder.btn_mes.setText("休息中");
        }
//        // 修改按钮的点击事件
//        viewHolder.btn_edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onEditBtnClickListener.onEditBtnClick(v, position);
//            }
//        });


        // mes
        viewHolder.btn_mes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnMesBtnClickListener.onMesBtnClick(v, position);
            }
        });

//        // 删除按钮
//        viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onDelBtnClickListener.onDelBtnClick(v, position);
//            }
//        });

        return convertView;
    }


    // 自定义内部类
    private class ViewHolder{
        private TextView tv_uname, tv_info, tv_state;
        private Button btn_mes;
//        private ImageView btn_mes, btn_delete;
    }
}

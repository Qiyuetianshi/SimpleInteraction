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
public class messageinfoAdatper extends BaseAdapter {
    private Context context;    // 上下文信息
    private List<message> messageinfoList;    // room信息数据集合

//    private OnEditBtnClickListener onEditBtnClickListener;   // 修改按钮点击事件的监听实例
//    private OnDelBtnClickListener onDelBtnClickListener;     // 删除按钮 点击事件的监听实例
    private OnMesBtnClickListener OnMesBtnClickListener;

    public messageinfoAdatper() {
    }

    public messageinfoAdatper(Context context, List<message> messageinfoList) {
        this.context = context;
        this.messageinfoList = messageinfoList;
    }

    public void setmessageinfoList(List<message> messageinfoList) {
        this.messageinfoList = messageinfoList;
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
        return messageinfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageinfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.message_list_item, null);
            viewHolder = new ViewHolder();

//            viewHolder.tv_id = convertView.findViewById(R.id.tv_id);
            viewHolder.tv_name = convertView.findViewById(R.id.tv_name);
            viewHolder.tv_mes = convertView.findViewById(R.id.tv_mes);
            viewHolder.btn_mes = convertView.findViewById(R.id.btn_mes);
//            viewHolder.btn_delete = convertView.findViewById(R.id.btn_delete);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 这里进行数据填充
        message item = messageinfoList.get(position);
        viewHolder.tv_name.setText(item.getSendname());
        viewHolder.tv_mes.setText(item.getMes());

//        // 修改按钮的点击事件
//        viewHolder.btn_edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onEditBtnClickListener.onEditBtnClick(v, position);
//            }
//        });


        // mes
//        viewHolder.btn_mes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OnMesBtnClickListener.onMesBtnClick(v, position);
//            }
//        });

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
        private TextView tv_name, tv_mes;
        private Button btn_mes;
    }
}

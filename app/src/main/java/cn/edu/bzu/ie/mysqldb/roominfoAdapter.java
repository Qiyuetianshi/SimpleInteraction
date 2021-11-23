package cn.edu.bzu.ie.mysqldb;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 自定义用户数据适配器类
 */
public class roominfoAdapter extends BaseAdapter {
    private Context context;    // 上下文信息
    private List<room> roominfoList;    // room信息数据集合

    private OnEditBtnClickListener onEditBtnClickListener;   // 修改按钮点击事件的监听实例
    private OnDelBtnClickListener onDelBtnClickListener;     // 删除按钮 点击事件的监听实例
    private OnMesBtnClickListener OnMesBtnClickListener;

    public roominfoAdapter() {
    }

    public roominfoAdapter(Context context, List<room> roominfoList) {
        this.context = context;
        this.roominfoList = roominfoList;
    }

    public void setroominfoList(List<room> roominfoList) {
        this.roominfoList = roominfoList;
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
        return roominfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return roominfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.room_list_item, null);
            viewHolder = new ViewHolder();

//            viewHolder.tv_id = convertView.findViewById(R.id.tv_id);
            viewHolder.tv_pro = convertView.findViewById(R.id.tv_pro);
            viewHolder.tv_pat = convertView.findViewById(R.id.tv_pat);
            viewHolder.btn_mes = convertView.findViewById(R.id.btn_mes);
            viewHolder.tv_ctime = convertView.findViewById(R.id.tv_ctime);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 这里进行数据填充
        room item = roominfoList.get(position);

        viewHolder.tv_pro.setText(item.getProname());
        if(item.getIsreadpat()==1){
            viewHolder.tv_pat.setText("new");
        }

//        DateFormat formatter=new SimpleDateFormat("yyyy年MM月dd日 HH:mm"); //模板可以多样

//        viewHolder.tv_ctime.setText(item);

        DateFormat formatter2=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date currentTime2=null;
        try {
            currentTime2=formatter2.parse(item.getCtime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateString=formatter2.format(currentTime2);
        viewHolder.tv_ctime.setText(dateString);
//        }).start();
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
        private TextView tv_pat, tv_pro,tv_ctime;
        private Button btn_mes;
    }
}

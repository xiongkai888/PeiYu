package com.lanmei.peiyu.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.xson.common.utils.StringUtils;

import java.util.List;

/**
 * Created by xkai on 2018/12/27.
 * 订单详情物流
 */

public class LogisticsHelper {

    private Context context;
    private List<String> list;
    private LinearLayout root;

    public LogisticsHelper(Context context, LinearLayout root) {
        this.context = context;
        this.root = root;
    }

    public void setLogisticsPosition(List<String> list, int position) {
        root.removeAllViews();
        this.list = list;
        if (StringUtils.isEmpty(list)) {
            return;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            addView(i, position);
        }

    }

    private void addView(int i, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_logistics, root, false);
        TextView nameTv = (TextView)view.findViewById(R.id.name_tv);
        TextView lineTv = (TextView)view.findViewById(R.id.line_tv);
        ImageView textView = (ImageView)view.findViewById(R.id.pic_iv);
        boolean b = (i<=position);
        nameTv.setText(list.get(i));
        nameTv.setTextColor(context.getResources().getColor(b?R.color.red:R.color.color999));
        lineTv.setBackgroundColor(context.getResources().getColor(b?R.color.red:R.color.divider));
        textView.setImageResource(b?R.mipmap.icon_jindu_done:R.mipmap.icon_jindu);
        root.addView(view, i);
    }


}

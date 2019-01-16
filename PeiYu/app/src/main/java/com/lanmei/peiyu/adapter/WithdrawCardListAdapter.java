package com.lanmei.peiyu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanmei.peiyu.R;
import com.lanmei.peiyu.bean.WithdrawCardListBean;
import com.xson.common.adapter.SwipeRefreshAdapter;
import com.xson.common.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 选择银行卡
 */
public class WithdrawCardListAdapter extends SwipeRefreshAdapter<WithdrawCardListBean> {

    int editType = 0;//0是完成状态，1是编辑状态

    public WithdrawCardListAdapter(Context context) {
        super(context);
    }

    public void setEditType(int editType){
        this.editType = editType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_withdraw_card, parent, false));
    }

    @Override
    public void onBindViewHolder2(RecyclerView.ViewHolder holder, int position) {

        final WithdrawCardListBean bean = getItem(position);
        if (bean == null) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        final String carName = bean.getBanks_name();
        viewHolder.mCardNameTv.setText(carName);
        if ("中国工商银行".equals(carName)){
            viewHolder.mllBg.setBackgroundResource(R.color.colorCC000A);
            viewHolder.mBankIv.setImageResource(R.mipmap.withdraw_gongshang);
        }else if ("中国农业银行".equals(carName)){
            viewHolder.mllBg.setBackgroundResource(R.color.color00D3C4);
            viewHolder.mBankIv.setImageResource(R.mipmap.withdraw_nongye);
        }else if ("中国银行".equals(carName)){
            viewHolder.mllBg.setBackgroundResource(R.color.colorA71E32);
            viewHolder.mBankIv.setImageResource(R.mipmap.withdraw_zhongguo);
        }else if ("中国建设银行".equals(carName)){
            viewHolder.mllBg.setBackgroundResource(R.color.color0066B3);
            viewHolder.mBankIv.setImageResource(R.mipmap.withdraw_jianshe);
        }
        String cardNum = bean.getBanks_no();
        if (!StringUtils.isEmpty(cardNum) && cardNum.length()>4){
            int length = cardNum.length();
            cardNum = cardNum.substring(length-4,length);
            viewHolder.mCardNumTv.setText(cardNum);
        }else {
            viewHolder.mCardNumTv.setText(0);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editType == 0){
                    if (mChooseCardListener != null){
                        mChooseCardListener.chooseCard(bean);
                    }
                }else {//
                    if (mChooseCardListener != null){
                        mChooseCardListener.unBound(bean.getId());
                    }
                }
            }
        });
    }



    ChooseCardListener mChooseCardListener;

    public void setChooseCardListener(ChooseCardListener chooseCardListener){
        mChooseCardListener = chooseCardListener;
    }

    public interface ChooseCardListener{
        void chooseCard(WithdrawCardListBean bean);//选择卡
        void unBound(String cardId);//解除绑定
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.card_name_tv)
        TextView mCardNameTv;
        @InjectView(R.id.card_num_tv)
        TextView mCardNumTv;
        @InjectView(R.id.bank_iv)
        ImageView mBankIv;
        @InjectView(R.id.ll_bg)
        LinearLayout mllBg;
        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}

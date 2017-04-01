package com.home.rw.mvp.ui.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.ContractAfterEntity;
import com.home.rw.mvp.entity.ContractInitialEntity;
import com.home.rw.mvp.ui.adapters.base.BaseListViewAdapter;
import com.home.rw.utils.DrawableUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by cty on 2017/2/6.
 */

public class ContactAdapterAdd extends BaseListViewAdapter {

    private Context context;

    private OnItemClickListener mListener;

    private List<ContractAfterEntity> list;

    public ContactAdapterAdd(Context context, List<ContractAfterEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void setDataSource(ArrayList<ContractAfterEntity> dataSource) {
        this.list = dataSource;
        notifyDataSetChanged();
    }

    /**
     * 传入新的数据 刷新UI的方法
     */
    public void updateListView(List<ContractAfterEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list != null) return list.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list == null)
            return null;

        if (position >= list.size())
            return null;

        return list.get(position);
    }
    //设置item监听事件
    public void setOnItemClickListener(OnItemClickListener mListener){

        this.mListener = mListener;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final ContractAfterEntity mContent = list.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(context).inflate(R.layout.cell_contact_add, parent, false);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.friendname);
            viewHolder.tvLetter = (TextView) convertView.findViewById(R.id.catalog);
            viewHolder.mIvHeader = (SimpleDraweeView)convertView.findViewById(R.id.iv_header);
            viewHolder.mTvHeader = (TextView)convertView.findViewById(R.id.tv_header);
            viewHolder.mSperate = (View)convertView.findViewById(R.id.sperate);
            viewHolder.mBtAdd = (Button) convertView.findViewById(R.id.bt_right_add);
            viewHolder.mTvAdded = (TextView) convertView.findViewById(R.id.tv_right_added);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.mSperate.setVisibility(View.GONE);
            String letterFirst = mContent.getLetter();
            if(!TextUtils.isEmpty(letterFirst)){
                letterFirst = String.valueOf(letterFirst.toUpperCase().charAt(0));
            }
            viewHolder.tvLetter.setText(letterFirst);
        } else {
            viewHolder.mSperate.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setVisibility(View.GONE);
        }

        viewHolder.tvTitle.setText(this.list.get(position).getName());

        if (mContent.getAvatar() == null || mContent.getAvatar().equals("")) {
            viewHolder.mIvHeader.setVisibility(View.INVISIBLE);
            viewHolder.mTvHeader.setVisibility(View.VISIBLE);
            if(!TextUtils.isEmpty(mContent.getName()))
            viewHolder.mTvHeader.setText(mContent.getName().substring(0,1));
            viewHolder.mTvHeader.setBackgroundResource(DrawableUtils.getRandomBackgroundResource(mContent.getName()));
        } else {
            viewHolder.mIvHeader.setVisibility(View.VISIBLE);
            viewHolder.mTvHeader.setVisibility(View.INVISIBLE);
            viewHolder.mIvHeader.setImageURI(mContent.getAvatar());
        }
        if(mContent.isAdded()){
            viewHolder.mBtAdd.setVisibility(View.GONE);
            viewHolder.mTvAdded.setVisibility(View.VISIBLE);
        }else{
            viewHolder.mBtAdd.setVisibility(View.VISIBLE);
            viewHolder.mBtAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(position);
                }
            });
            viewHolder.mTvAdded.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getLetter();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getLetter().toUpperCase().charAt(0);
    }


    final static class ViewHolder {
        /**
         * 首字母
         */
        TextView tvLetter;
        /**
         * 昵称
         */
        TextView tvTitle;
        /**
         * 头像1
         */
        SimpleDraweeView mIvHeader;
        /**
         * 头像2
         */
        TextView mTvHeader;

        /**
         * 添加
         */
        Button mBtAdd;
        /**
         * 已添加
         */
        TextView mTvAdded;

        View mSperate;

    }
}

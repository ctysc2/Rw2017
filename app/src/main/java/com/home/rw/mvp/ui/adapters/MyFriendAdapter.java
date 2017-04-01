package com.home.rw.mvp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.entity.MyFriendEntity;
import com.home.rw.mvp.entity.message.MessageCommonEntity;
import com.home.rw.mvp.ui.adapters.base.BaseRecyclerViewAdapter;
import com.home.rw.utils.DateUtils;
import com.home.rw.utils.DrawableUtils;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2017/2/2.
 */

public class MyFriendAdapter extends BaseRecyclerViewAdapter<MessageCommonEntity> {

    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener mListener;

    public MyFriendAdapter(List<MessageCommonEntity> list, Context context) {
        super(list);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder  holder = null;
        View view = null;
        switch (viewType){
            case TYPE_FOOTER:
                view = inflater.inflate(R.layout.layout_footer_loadmore2, parent, false);
                holder = new FooterViewHolder(view);
                break;
            case TYPE_HEADER:
                holder = new HeaderViewHolder(mHeaderView);
                break;
            case TYPE_ITEM:
                view = inflater.inflate(R.layout.cell_friend_common, parent, false);
                holder = new MyFriendViewHolder(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick((int)v.getTag());
                    }
                });
                break;
            default:
                break;
        }

        return holder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,  int position) {

        if(holder instanceof MyFriendViewHolder) {
            if (mIsShowHeader)
                position = position - 1;

            final int mPosition = position;

            final MyFriendViewHolder mHolder = (MyFriendViewHolder) holder;
            final MessageCommonEntity entity = dataSource.get(mPosition);
            mHolder.itemView.setTag(mPosition);
            String name = entity.getNickname() == null?entity.getRealname():entity.getNickname();
            if (entity.getAvatar() == null || entity.getAvatar().equals("")) {
                mHolder.mIvHeader.setVisibility(View.INVISIBLE);
                mHolder.mTvHeader.setVisibility(View.VISIBLE);
                if(!TextUtils.isEmpty(name))
                    mHolder.mTvHeader.setText(name.substring(0,1));
                mHolder.mTvHeader.setBackgroundResource(DrawableUtils.getRandomBackgroundResource(name));
            } else {
                mHolder.mIvHeader.setVisibility(View.VISIBLE);
                mHolder.mTvHeader.setVisibility(View.INVISIBLE);
                mHolder.mIvHeader.setImageURI(entity.getAvatar());
            }
            mHolder.mTvName.setText(name);
            if(!TextUtils.isEmpty(entity.getLastSpeakingTime())){
                mHolder.mTvRight.setText(DateUtils.getMessageMain(new Date(Long.parseLong(entity.getLastSpeakingTime()))));
            }

            if (mPosition == dataSource.size() - 1) {
                mHolder.mSperate.setVisibility(View.GONE);
            } else {
                mHolder.mSperate.setVisibility(View.VISIBLE);
            }
        }

    }
        //设置item监听事件
    public void setOnItemClickListener(OnItemClickListener mListener){

        this.mListener = mListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (mIsShowFooter && isFooterPosition(position)) {
            return TYPE_FOOTER;
        } else if (mIsShowHeader && isHeaderPosition(position)) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }
    class MyFriendViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_header)
        TextView mTvHeader;

        @BindView(R.id.iv_header)
        SimpleDraweeView mIvHeader;

        @BindView(R.id.tv_name)
        TextView mTvName;

        @BindView(R.id.tv_right)
        TextView mTvRight;

        @BindView(R.id.sperate)
        View mSperate;

        public MyFriendViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

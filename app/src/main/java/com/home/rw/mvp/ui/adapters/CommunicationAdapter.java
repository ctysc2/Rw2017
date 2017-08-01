package com.home.rw.mvp.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.ApprovementListEntity;
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.ui.adapters.base.BaseRecyclerViewAdapter;
import com.home.rw.utils.FrescoUtils;
import com.home.rw.utils.PreferenceUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2017/1/6.
 */

public class CommunicationAdapter extends BaseRecyclerViewAdapter<CommunicationEntity.DataEntity.ResLst> {

    private Context context;
    private LayoutInflater inflater;
    private String entryType = "common";
    private OnItemClickListener mListener;
    private OnItemClickListener mListenerFocus;
    private OnItemClickListener mListenerZan;
    private final int COMPRESS_WIDTH = 200;
    private final int COMPRESS_HEIGH = 120;
    public CommunicationAdapter(ArrayList<CommunicationEntity.DataEntity.ResLst> dataSource, Context context){
        super(dataSource);
        this.dataSource = dataSource;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    public CommunicationAdapter(ArrayList<CommunicationEntity.DataEntity.ResLst> dataSource, String type, Context context){
        super(dataSource);
        this.context = context;
        this.entryType = type;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder  holder = null;
        View view = null;
        switch (viewType){
            case TYPE_FOOTER:
                view = inflater.inflate(R.layout.layout_footer_load_more, parent, false);
                holder = new FooterViewHolder(view);
                break;
            case TYPE_HEADER:
                holder = new HeaderViewHolder(mHeaderView);
                break;
            case TYPE_ITEM:
                view = inflater.inflate(R.layout.cell_communication, parent, false);
                holder = new CommViewHolder(view);

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
    //设置item监听事件
    public void setOnItemClickListener(OnItemClickListener mListener){

        this.mListener = mListener;
    }

    //设置关注监听事件
    public void setOnItemFocusClickListener(OnItemClickListener mListenerFocus){

        this.mListenerFocus = mListenerFocus;
    }

    //设置Zan监听事件
    public void setOnItemZanClickListener(OnItemClickListener mListenerZan){

        this.mListenerZan = mListenerZan;
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

    class CommViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_header)
        SimpleDraweeView mHeader;

        @BindView(R.id.tv_name)
        TextView mName;

        @BindView(R.id.ll_focus)
        LinearLayout mLLFocus;

        @BindView(R.id.tv_facus)
        TextView mFacus;

        @BindView(R.id.iv_focus)
        ImageView mIvFocus;

        @BindView(R.id.tv_zan_num)
        TextView mZanNum;

        @BindView(R.id.iv_zan)
        ImageView mZan;

        @BindView(R.id.tv_content)
        TextView content;

        @BindView(R.id.rl_zanContainer)
        RelativeLayout mZanContainer;

        @BindView(R.id.ll_imgContainer)
        LinearLayout mContainer;

        @BindView(R.id.iv_picture1)
        SimpleDraweeView mPic1;

        @BindView(R.id.iv_picture2)
        SimpleDraweeView mPic2;

        @BindView(R.id.iv_picture3)
        SimpleDraweeView mPic3;

        public CommViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CommViewHolder) {

            if(mIsShowHeader)
                position = position -1;

            final int mPosition = position;
            final CommViewHolder mHolder = (CommViewHolder)holder;

            final CommunicationEntity.DataEntity.ResLst entity = dataSource.get(mPosition);


            mHolder.itemView.setTag(mPosition);
            mHolder.mHeader.setImageURI(entity.getAvatar());
            mHolder.mName.setText(entity.getAuthor());
            mHolder.mZanNum.setText(String.valueOf(entity.getSupportNum()));
            mHolder.content.setText(entity.getContent());
            if (entity.getFocus().equals("0")) {
                mHolder.mLLFocus.setEnabled(true);
                mHolder.mFacus.setText(context.getString(R.string.addFacus));
                mHolder.mIvFocus.setImageResource(R.drawable.icon_qiye_focus);

            } else {
                mHolder.mLLFocus.setEnabled(false);
                mHolder.mFacus.setText(context.getString(R.string.Facused));
                mHolder.mIvFocus.setImageResource(R.drawable.icon_qiye_focused);
            }

            if (entity.getSupport().equals("0")) {
                mHolder.mZan.setImageResource(R.drawable.icon_zanlist);
                mHolder.mZan.setEnabled(true);
                mHolder.mZanContainer.setEnabled(true);

            } else {
                mHolder.mZan.setImageResource(R.drawable.icon_zaned);
                mHolder.mZan.setEnabled(false);
                mHolder.mZanContainer.setEnabled(false);

            }

            if (String.valueOf(PreferenceUtils.getPrefLong(context,"ID",0)).equals(entity.getCreatedBy())
                    || ((entryType != null) && (entryType.equals("other")))) {
                mHolder.mLLFocus.setVisibility(View.GONE);
            } else {
                mHolder.mLLFocus.setVisibility(View.VISIBLE);
            }

            mHolder.mLLFocus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListenerFocus.onItemClick(mPosition);
                }
            });

            mHolder.mZanContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListenerZan.onItemClick(mPosition);
                }
            });


            if(!TextUtils.isEmpty(entity.getImgs())){
                String[] imgs = entity.getImgs().split(",");
                switch (imgs.length) {
                    case 0:
                        mHolder.mContainer.setVisibility(View.GONE);
                        break;
                    case 1:
                        mHolder.mContainer.setVisibility(View.VISIBLE);
                        mHolder.mPic1.setVisibility(View.VISIBLE);
                        mHolder.mPic2.setVisibility(View.INVISIBLE);
                        mHolder.mPic3.setVisibility(View.GONE);
                        FrescoUtils.load(Uri.parse(imgs[0]), mHolder.mPic1, COMPRESS_WIDTH, COMPRESS_HEIGH);
                        break;
                    case 2:
                        mHolder.mContainer.setVisibility(View.VISIBLE);
                        mHolder.mPic1.setVisibility(View.VISIBLE);
                        mHolder.mPic2.setVisibility(View.VISIBLE);
                        mHolder.mPic3.setVisibility(View.GONE);
                        FrescoUtils.load(Uri.parse(imgs[0]), mHolder.mPic1, COMPRESS_WIDTH, COMPRESS_HEIGH);
                        FrescoUtils.load(Uri.parse(imgs[1]), mHolder.mPic2, COMPRESS_WIDTH, COMPRESS_HEIGH);
                        break;
                    case 3:
                        mHolder.mContainer.setVisibility(View.VISIBLE);
                        mHolder.mPic1.setVisibility(View.VISIBLE);
                        mHolder.mPic2.setVisibility(View.VISIBLE);
                        mHolder.mPic3.setVisibility(View.VISIBLE);
                        FrescoUtils.load(Uri.parse(imgs[0]), mHolder.mPic1, COMPRESS_WIDTH, COMPRESS_HEIGH);
                        FrescoUtils.load(Uri.parse(imgs[1]), mHolder.mPic2, COMPRESS_WIDTH, COMPRESS_HEIGH);
                        FrescoUtils.load(Uri.parse(imgs[2]), mHolder.mPic3, COMPRESS_WIDTH, COMPRESS_HEIGH);

                        break;
                    default:
                        break;

                }
            }else{
                mHolder.mContainer.setVisibility(View.GONE);
            }



        }


    }
}

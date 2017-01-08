package com.home.rw.mvp.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
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
import com.home.rw.mvp.entity.CommunicationEntity;
import com.home.rw.mvp.entity.FacusListEntity;
import com.home.rw.utils.FrescoUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2017/1/8.
 */

public class FacusListAdapter extends RecyclerView.Adapter<FacusListAdapter.FacusHolder> {

    private ArrayList<FacusListEntity.DataEntity> dataSource;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener mListener;
    private final int COMPRESS_WIDTH = 200;
    private final int COMPRESS_HEIGH = 200;

    public FacusListAdapter(ArrayList<FacusListEntity.DataEntity> dataSource, Context context){
        this.dataSource = dataSource;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public FacusListAdapter.FacusHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cell_facus_list, parent, false);
        FacusHolder holder = new FacusHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick((int)v.getTag());
            }
        });
        return holder;
    }
    //设置item监听事件
    public void setOnItemClickListener(OnItemClickListener mListener){

        this.mListener = mListener;
    }

    @Override
    public void onBindViewHolder(final FacusHolder holder, final int position) {
        FacusListEntity.DataEntity entity = dataSource.get(position);
        holder.itemView.setTag(position);
        FrescoUtils.load(Uri.parse(entity.getHeader()),holder.mHeader,COMPRESS_WIDTH,COMPRESS_HEIGH);
        holder.mName.setText(entity.getName());
        holder.mNum.setText(""+entity.getNum());
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }


    class FacusHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_header)
        SimpleDraweeView mHeader;

        @BindView(R.id.tv_name)
        TextView mName;

        @BindView(R.id.tv_num)
        TextView mNum;

        public FacusHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

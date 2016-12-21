package com.home.rw.mvp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home.rw.R;
import com.home.rw.mvp.entity.ApprovementListEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cty on 2016/12/20.
 */

public class ApprovementListAdapter extends RecyclerView.Adapter<ApprovementListAdapter.ApprovementViewHolder> {
    private List<ApprovementListEntity> dataSource;
    private Context context;
    private LayoutInflater inflater;

    public ApprovementListAdapter(List<ApprovementListEntity> dataSource, Context context){
        this.dataSource = dataSource;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public ApprovementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cell_approvement, parent, false);
        ApprovementViewHolder holder = new ApprovementViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ApprovementViewHolder holder, int position) {
        if(position == dataSource.size())
            holder.mSperate.setVisibility(View.INVISIBLE);
        else
            holder.mSperate.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    class ApprovementViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_header)
        SimpleDraweeView mHeader;

        @BindView(R.id.tv_name)
        TextView mName;

        @BindView(R.id.tv_date)
        TextView mDate;

        @BindView(R.id.tv_type)
        TextView mType;

        @BindView(R.id.tv_result)
        TextView mResult;

        @BindView(R.id.iv_sperate)
        View mSperate;

        public ApprovementViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

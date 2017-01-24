/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.home.rw.mvp.ui.adapters.base;

import android.support.annotation.AnimRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.home.rw.listener.OnItemClickListener;

import java.util.List;

/**
 * @author cty
 * @version 1.0 2017/1/16
 */
public class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;
    public static final int TYPE_HEADER = 2;
    protected int mLastPosition = -1;
    protected boolean mIsShowHeader;
    protected boolean mIsShowFooter;
    protected List<T> dataSource;
    protected OnItemClickListener mOnItemClickListener;

    public View mHeaderView;

    public void setHeaderView(View headerView){
        this.mHeaderView = headerView;

    }
    public BaseRecyclerViewAdapter(List<T> list) {
        dataSource = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (getItemViewType(position) == TYPE_FOOTER) {
            if (layoutParams != null) {
                if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                    StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView
                            .getLayoutParams();
                    params.setFullSpan(true);
                }
            }
        }
    }


    protected View getView(ViewGroup parent, int layoutId) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
    }

    @Override
    public int getItemCount() {
        if (dataSource == null) {
            return 0;
        }
        int itemSize = dataSource.size();
        if (mIsShowFooter) {
            itemSize += 1;
        }
        if(mIsShowHeader){
            itemSize += 1;
        }
        return itemSize;
    }

    protected void setItemAppearAnimation(RecyclerView.ViewHolder holder, int position, @AnimRes int type) {
        if (position > mLastPosition/* && !isFooterPosition(position)*/) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), type);
            holder.itemView.startAnimation(animation);
            mLastPosition = position;
        }
    }

    protected boolean isFooterPosition(int position) {
        return (getItemCount() - 1) == position;
    }
    protected boolean isHeaderPosition(int position) {
        return 0 == position;
    }
    public void add(int position, T item) {

        if(position>=dataSource.size())
            return;

        dataSource.add(position, item);

        if(mIsShowHeader)
            position = position +1;


        notifyItemInserted(position);
        notifyItemRangeChanged(position,getItemCount()-position);
    }

    public void addMore(List<T> data) {
        dataSource.addAll(data);
        notifyDataSetChanged();
    }

    public void delete(int position) {
        if(position>=dataSource.size())
            return;

        dataSource.remove(position);

        if(mIsShowHeader)
            position = position +1;



        notifyItemRemoved(position);
        notifyItemRangeChanged(position,getItemCount()-position);
    }

    public List<T> getList() {
        return dataSource;
    }

    public void setList(List<T> items) {
        dataSource = items;
    }

    //设置是否显示头布局
    public void setIsShowHeader(boolean isShowHeader){
        this.mIsShowHeader = isShowHeader;
    }
    public void showFooter() {
        mIsShowFooter = true;
        notifyItemInserted(getItemCount());
    }

    public void hideFooter() {
        mIsShowFooter = false;
        notifyItemRemoved(getItemCount());
    }

    protected class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    protected class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}

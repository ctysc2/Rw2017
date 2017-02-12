package com.home.rw.mvp.ui.adapters.base;

import android.widget.BaseAdapter;
import android.widget.SectionIndexer;

import com.home.rw.listener.OnItemClickListener;
import com.home.rw.mvp.entity.ContractAfterEntity;

import java.util.ArrayList;

/**
 * Created by cty on 2017/2/7.
 */

public abstract class BaseListViewAdapter extends BaseAdapter implements SectionIndexer {
    public void setOnItemClickListener(OnItemClickListener mListener){

    }

    public void setDataSource(ArrayList<ContractAfterEntity> dataSource){

    }
}

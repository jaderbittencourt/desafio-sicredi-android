package com.jaderbittencourt.sicredidigital.util;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class DataBindingAdapter<T extends ViewDataBinding, I> extends RecyclerView.Adapter<BindingViewHolder<T>> {

    private LayoutInflater inflater;
    protected List<I> items;
    protected Activity activity;

    protected DataBindingAdapter(Activity activity) {
        inflater = LayoutInflater.from(activity);
        items = new ArrayList<>();
        this.activity = activity;
    }

    @Override
    public BindingViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {

        T binding = DataBindingUtil.inflate(inflater, getItemResource(), parent, false);

        return new BindingViewHolder<>(binding);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<I> items) {

        this.items.clear();
        this.items.addAll(items);

        notifyDataSetChanged();
    }

    @LayoutRes
    protected abstract int getItemResource();
}

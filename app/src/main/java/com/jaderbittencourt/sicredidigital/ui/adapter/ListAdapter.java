package com.jaderbittencourt.sicredidigital.ui.adapter;

import android.app.Activity;

import com.jaderbittencourt.sicredidigital.R;
import com.jaderbittencourt.sicredidigital.databinding.AdapterItemEventBinding;
import com.jaderbittencourt.sicredidigital.model.Event;
import com.jaderbittencourt.sicredidigital.ui.interaction.MainInteraction;
import com.jaderbittencourt.sicredidigital.util.BindingViewHolder;
import com.jaderbittencourt.sicredidigital.util.DataBindingAdapter;
import com.jaderbittencourt.sicredidigital.viewmodel.EventAdapterViewModel;

public class ListAdapter extends DataBindingAdapter<AdapterItemEventBinding, Event> {

    private MainInteraction interaction;

    public ListAdapter(Activity activity, MainInteraction interaction) {
        super(activity);
        this.interaction = interaction;
    }

    @Override
    protected int getItemResource() {
        return R.layout.adapter_item_event;
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<AdapterItemEventBinding> holder, int position) {
        if (holder.binding.getViewModel() == null) {
            EventAdapterViewModel viewModel = new EventAdapterViewModel(activity, interaction);
            holder.binding.setViewModel(viewModel);
        }

        holder.binding.getViewModel().bind(items.get(position), holder.binding.image);
    }
}
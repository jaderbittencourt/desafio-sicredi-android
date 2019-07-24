package com.jaderbittencourt.sicredidigital.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.jaderbittencourt.sicredidigital.App;
import com.jaderbittencourt.sicredidigital.R;
import com.jaderbittencourt.sicredidigital.common.ui.CommonActivity;
import com.jaderbittencourt.sicredidigital.databinding.ActivityMainBinding;
import com.jaderbittencourt.sicredidigital.model.Event;
import com.jaderbittencourt.sicredidigital.ui.adapter.ListAdapter;
import com.jaderbittencourt.sicredidigital.ui.interaction.MainInteraction;
import com.jaderbittencourt.sicredidigital.util.CommonJsonParser;
import com.jaderbittencourt.sicredidigital.util.ConnectivityUtil;
import com.jaderbittencourt.sicredidigital.viewmodel.EventsViewModel;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends CommonActivity implements MainInteraction {

    @Inject
    EventsViewModel eventsViewModel;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        eventsViewModel.setInteraction(this);
        eventsViewModel.setMainInteraction(this);
        getEvents();
    }

    private void injectDependencies() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void loadAdapter(List<Event> events) {
        ListAdapter listAdapter = new ListAdapter(this, this);
        binding.eventsList.setLayoutManager(new LinearLayoutManager(this));
        binding.eventsList.setAdapter(listAdapter);
        listAdapter.setItems(events);
    }

    @Override
    public void openDetails(Event event) {
        Bundle bundle = new Bundle();
        bundle.putString(Event.OBJECT, CommonJsonParser.objectToJson(event));

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventsViewModel.dispose();
    }

    private void getEvents() {
        if (ConnectivityUtil.isConnected(this)) {
            eventsViewModel.getEvents();
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.atention));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.no_internet));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.try_again),
                    (dialog, which) ->{
                        dialog.dismiss();
                        getEvents();
                    });
            alertDialog.show();
        }
    }

}

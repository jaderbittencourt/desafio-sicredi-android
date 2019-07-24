package com.jaderbittencourt.sicredidigital.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import com.jaderbittencourt.sicredidigital.App;
import com.jaderbittencourt.sicredidigital.R;
import com.jaderbittencourt.sicredidigital.common.ui.CommonActivity;
import com.jaderbittencourt.sicredidigital.databinding.ActivityDetailsBinding;
import com.jaderbittencourt.sicredidigital.databinding.CheckinDialogBinding;
import com.jaderbittencourt.sicredidigital.databinding.ParticipantDialogBinding;
import com.jaderbittencourt.sicredidigital.model.CheckInBody;
import com.jaderbittencourt.sicredidigital.model.Event;
import com.jaderbittencourt.sicredidigital.ui.interaction.DetailsInteraction;
import com.jaderbittencourt.sicredidigital.util.CommonJsonParser;
import com.jaderbittencourt.sicredidigital.util.ConnectivityUtil;
import com.jaderbittencourt.sicredidigital.viewmodel.CheckInViewModel;
import com.jaderbittencourt.sicredidigital.viewmodel.DetailsViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class DetailsActivity extends CommonActivity implements DetailsInteraction {

    @Inject
    DetailsViewModel detailsViewModel;
    @Inject
    CheckInViewModel checkInViewModel;

    private ActivityDetailsBinding binding;
    private AlertDialog alertDialog, participantDialog;
    private CheckinDialogBinding checkinDialogBinding;
    private ParticipantDialogBinding participantDialogBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        detailsViewModel.setInteraction(this);
        detailsViewModel.setLoaderInteraction(this);

        checkInViewModel.setInteraction(this);
        checkInViewModel.setLoaderInteraction(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        binding.setViewModel(detailsViewModel);

        checkinDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.checkin_dialog, null, false);
        checkinDialogBinding.setViewModel(checkInViewModel);

        participantDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.participant_dialog, null, false);

        readBundle(getIntent().getExtras());
        prepareDialog();
        prepareParticipantDialog();
    }

    private void injectDependencies() {
        App.getAppComponent().inject(this);
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            String json = bundle.getString(Event.OBJECT, null);
            Event event = CommonJsonParser.jsonToObject(json, Event.class);
            detailsViewModel.bind(event, binding.image, binding.imagesContainer);
        }
    }

    @Override
    public void openMaps(Double latitude, Double longitude) {
        Uri gmmIntentUri = Uri.parse("geo:" + latitude.toString() + "," + longitude.toString());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    @Override
    public void openDialog(String eventId) {
        alertDialog.show();
        checkInViewModel.loadData(eventId);
    }

    @Override
    public void openPeopleDialog(String name, String image) {
        participantDialogBinding.participantName.setText(name);
        Picasso.with(this)
                    .load(image)
                    .placeholder(R.drawable.placeholder)
                    .into(participantDialogBinding.participantImage, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError() {
                            Picasso.with(DetailsActivity.this)
                                    .load(image)
                                    .error(R.drawable.placeholder)
                                    .into(participantDialogBinding.participantImage, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                        }

                                        @Override
                                        public void onError() {
                                            Log.v("Picasso", "Could not fetch image");
                                        }
                                    });
                        }
                    });
        participantDialog.show();
    }

    @Override
    public void share(String shareText) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
    }

    @Override
    public void doCheckin(CheckInBody checkInBody) {
        if (ConnectivityUtil.isConnected(this)) {
           checkInViewModel.doCheckin(checkInBody);
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(R.string.atention);
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.no_internet));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.try_again),
                    (dialog, which) -> {
                        dialog.dismiss();
                        doCheckin(checkInBody);
                    });
            alertDialog.show();
        }
    }

    private void prepareDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(checkinDialogBinding.getRoot())
                .setPositiveButton(R.string.checkin, (dialog, id) -> {
                    checkInViewModel.validate(
                            checkinDialogBinding.name.getText().toString(),
                            checkinDialogBinding.email.getText().toString()
                    );
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                });

        alertDialog = builder.create();
    }

    private void prepareParticipantDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(participantDialogBinding.getRoot())
                .setNeutralButton(R.string.ok, (dialog, id) -> {
                    participantDialog.dismiss();
                });

        participantDialog = builder.create();
    }

}

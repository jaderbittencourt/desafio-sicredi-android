package com.jaderbittencourt.sicredidigital.viewmodel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableField;
import android.text.Editable;

import com.jaderbittencourt.sicredidigital.R;
import com.jaderbittencourt.sicredidigital.api.EventsService;
import com.jaderbittencourt.sicredidigital.common.interaction.LoaderInteraction;
import com.jaderbittencourt.sicredidigital.model.CheckInBody;
import com.jaderbittencourt.sicredidigital.ui.interaction.DetailsInteraction;
import com.jaderbittencourt.sicredidigital.util.ConnectivityUtil;
import com.jaderbittencourt.sicredidigital.util.StringUtil;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class CheckInViewModel {

    private static final String SHARED_PREFS = "digitalSharedPrefs";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    private Context context;
    private final EventsService service;
    private DetailsInteraction interaction;
    private LoaderInteraction loaderInteraction;
    private String eventId;
    private Disposable subscribe;

    public ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> email = new ObservableField<>();

    @Inject
    public CheckInViewModel(Context context, EventsService service) {
        this.context = context;
        this.service = service;
    }

    public void setInteraction(DetailsInteraction interaction) {
        this.interaction = interaction;
    }

    public void setLoaderInteraction(LoaderInteraction loaderInteraction) {
        this.loaderInteraction = loaderInteraction;
    }

    public void loadData(String eventId) {
        this.eventId = eventId;
        SharedPreferences prefs = getSharedPrefs();

        if (prefs.contains(NAME)) {
            name.set(prefs.getString(NAME, ""));
        }

        if (prefs.contains(EMAIL)) {
            email.set(prefs.getString(EMAIL, ""));
        }

    }

    private SharedPreferences getSharedPrefs() {
        return context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
    }

    public void validate(String editName, String editEmail) {
        name.set(editName);
        email.set(editEmail);

        if (StringUtil.isBlank(name.get()) || StringUtil.isBlank(email.get())) {
            loaderInteraction.showMessage(context.getString(R.string.required_fields));
        } else if (!StringUtil.isValidEmail(email.get())) {
            loaderInteraction.showMessage(context.getString(R.string.invalid_email));
        } else {
            SharedPreferences prefs = getSharedPrefs();

            prefs.edit().putString(NAME, name.get()).apply();
            prefs.edit().putString(EMAIL, email.get()).apply();

            CheckInBody checkInBody = new CheckInBody();
            checkInBody.setName(name.get());
            checkInBody.setEmail(email.get());
            checkInBody.setEventId(eventId);

            interaction.doCheckin(checkInBody);
        }
    }

    public void doCheckin(CheckInBody checkInBody) {
        if (ConnectivityUtil.isConnected(context)) {
            loaderInteraction.showLoader();
            subscribe = service.checkin(checkInBody)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        loaderInteraction.hideLoader();
                        loaderInteraction.showMessage(context.getString(R.string.checkin_success));
                    }, throwable -> {
                        loaderInteraction.hideLoader();
                        loaderInteraction.showError(throwable);
                    });
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle(R.string.atention);
            alertDialog.setCancelable(false);
            alertDialog.setMessage(context.getString(R.string.no_internet));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, context.getString(R.string.try_again),
                    (dialog, which) -> {
                        dialog.dismiss();
                        doCheckin(checkInBody);
                    });
            alertDialog.show();
        }

    }
}

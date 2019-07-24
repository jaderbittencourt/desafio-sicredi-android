package com.jaderbittencourt.sicredidigital.common.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jaderbittencourt.sicredidigital.R;
import com.jaderbittencourt.sicredidigital.common.interaction.LoaderInteraction;
import com.jaderbittencourt.sicredidigital.util.AlertUtil;

public class CommonActivity extends AppCompatActivity implements LoaderInteraction {

    protected ProgressDialog progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        progress = new ProgressDialog(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showLoader() {
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setCancelable(false);
        progress.show();
    }

    @Override
    public void showError(Throwable throwable) {
        AlertUtil.showAlert(this, throwable.getMessage());
    }

    @Override
    public void showMessage(String message) {
        AlertUtil.showAlert(this, message);
    }

    @Override
    public void hideLoader() {
        progress.dismiss();
    }

}

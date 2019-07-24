package com.jaderbittencourt.sicredidigital.common.interaction;

public interface LoaderInteraction {

    void showLoader();
    void showMessage(String message);
    void showError(Throwable throwable);
    void hideLoader();
}

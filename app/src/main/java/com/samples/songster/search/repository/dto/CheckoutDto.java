package com.samples.songster.search.repository.dto;

/**
 * Created by chrisbraunschweiler1 on 23/11/15.
 */
public class CheckoutDto {
    private boolean loginRequired;

    public boolean isLoginRequired() {
        return loginRequired;
    }

    public void setLoginRequired(boolean loginRequired) {
        this.loginRequired = loginRequired;
    }
}

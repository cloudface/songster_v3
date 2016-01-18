package com.samples.songster.search.repository.dto;

/**
 * Created by chrisbraunschweiler1 on 23/11/15.
 */
public class AuthorizationDto {
    private boolean purchaseAuthorized;

    public boolean isPurchaseAuthorized() {
        return purchaseAuthorized;
    }

    public void setPurchaseAuthorized(boolean purchaseAuthorized) {
        this.purchaseAuthorized = purchaseAuthorized;
    }
}

package com.gbr.nyan.appdirect;

public class SubscriptionResponse {
    private String accountIdentifier;
    private String errorCode;
    private boolean success;

    public static SubscriptionResponse failure() {
        return new SubscriptionResponse(false);
    }

    public static SubscriptionResponse success() {
        return new SubscriptionResponse(true);
    }

    private SubscriptionResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public SubscriptionResponse withErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public SubscriptionResponse withAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }
}

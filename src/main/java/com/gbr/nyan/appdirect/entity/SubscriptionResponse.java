package com.gbr.nyan.appdirect.entity;

public class SubscriptionResponse {
    private String accountIdentifier;
    private String errorCode;
    private String success;

    public static SubscriptionResponse failure() {
        return new SubscriptionResponse("false");
    }

    public static SubscriptionResponse success() {
        return new SubscriptionResponse("true");
    }

    private SubscriptionResponse(String success) {
        this.success = success;
    }

    public SubscriptionResponse withErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public SubscriptionResponse withAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
        return this;
    }

    public String getSuccess() {
        return success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }
}

package com.example.messagingservice.enumeration;

public enum ResponseStatusType {
    SUCCESS("SUCCESS"),
    FAILURE("FAILURE");

    private String value;

    ResponseStatusType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

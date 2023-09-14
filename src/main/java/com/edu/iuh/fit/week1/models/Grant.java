package com.edu.iuh.fit.week1.models;

public enum Grant {
        DISABLE("0"),
        ENABLE("1");

    private String value;

    Grant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

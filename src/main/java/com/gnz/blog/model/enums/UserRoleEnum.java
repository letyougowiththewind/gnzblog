package com.gnz.blog.model.enums;

public enum UserRoleEnum {
    ADMIN("ADMIN");

    UserRoleEnum(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }
}

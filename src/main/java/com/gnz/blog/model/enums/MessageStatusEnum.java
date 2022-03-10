package com.gnz.blog.model.enums;

public enum MessageStatusEnum {
    UNREAD("未读", 0),
    READ("已读", 1),
    ;
    private String notes;
    private int flag;

    public String getNotes() {
        return notes;
    }

    public int getFlag() {
        return flag;
    }

    MessageStatusEnum(String notes, int flag) {
        this.notes = notes;
        this.flag = flag;
    }
}

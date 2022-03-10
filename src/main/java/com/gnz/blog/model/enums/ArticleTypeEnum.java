package com.gnz.blog.model.enums;

public enum ArticleTypeEnum {
    /**
     * 文章类型：原创
     */
    ORIGINAL("原创", 1),
    /**
     * 文章类型：转载
     */
    REPRINT("转载", 0);;
    private String notes;
    private int flag;

    public String getNotes() {
        return notes;
    }

    public int getFlag() {
        return flag;
    }

    ArticleTypeEnum(String notes, int flag) {
        this.notes = notes;
        this.flag = flag;
    }
}

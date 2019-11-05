package com.lsl.blog.po;

public class BlogQuery {
    private String title;
    private Long TagId;
    private Long typeId;
    private boolean recommen;
    public BlogQuery(){

    }

    public Long getTagId() {
        return TagId;
    }

    public void setTagId(Long tagId) {
        TagId = tagId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public boolean isRecommen() {
        return recommen;
    }

    public void setRecommen(boolean recommen) {
        this.recommen = recommen;
    }
}

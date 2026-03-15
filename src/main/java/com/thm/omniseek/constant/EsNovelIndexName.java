package com.thm.omniseek.constant;


public enum EsNovelIndexName {

    NOVEL_INDEX_NAME("novel_index", "小说索引");

    /**
     * 索引名称
     */
    private final String index;

    /**
     * 简介
     */
    private final String message;

    EsNovelIndexName(String code, String message) {
        this.index = code;
        this.message = message;
    }

    public String getIndex() {
        return index;
    }

    public String getMessage() {
        return message;
    }

}


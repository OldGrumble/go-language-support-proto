/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.epdv.plugin.netbeans.lang.go.lexer;

/**
 *
 * @author peter
 */
public enum Category {

    BLOCK_START("{"),
    BLOCK_END("}"),
    PARA_LEFT("("),
    PARA_RIGHT(")"),
    CHARACTER("character"),
    COMMENT("comment"),
    IDENTIFIER("identifier"),
    KEYWORD("keyword"),
    NUMBER("number"),
    OPERATOR("operator"),
    WS("whitespace");

    private final boolean meta;
    private final String value;

    private Category(String value) {
        this(true, value);
    }

    private Category(boolean meta, String value) {
        this.meta = meta;
        this.value = value;
    }

    public boolean isMeta() {
        return meta;
    }

    public String getName() {
        return meta ? toCategoryName(name()) : value;
    }

    public String getValue() {
        return value;
    }

    private String toCategoryName(String ucName) {
        return ucName.toLowerCase().replace('_', '-');
    }
}

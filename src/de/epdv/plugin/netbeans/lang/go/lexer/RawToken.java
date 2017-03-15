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
public class RawToken {

    public static enum Type {
        LITERAL, STRING;
    }

    private final int id;
    private final Type type;
    private final String value;

    RawToken(int id, String value) {
        this.id = id;
        if (value.charAt(0) == '\'') {
            type = Type.STRING;
        } else {
            type = Type.LITERAL;
        }
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}

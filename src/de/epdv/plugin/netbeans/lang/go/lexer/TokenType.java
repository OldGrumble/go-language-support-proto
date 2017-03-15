/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.epdv.plugin.netbeans.lang.go.lexer;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 *
 * @author peter
 */
class TokenType {

    private static final Map<String, TokenType> TOKEN_TYPES = new TreeMap<>();

    static Collection<TokenType> values() {
        if (TOKEN_TYPES.isEmpty()) {
            initTokenTypes();
        }
        return TOKEN_TYPES.values();
    }

    private static void initTokenTypes() {
        TokenAdapter tokens = TokenAdapter.getInstance();
        TokenConverter tc = TokenConverter.getInstance();
        for (RawToken token : tokens.getRawTokens()) {
            TokenType tt = tc.getTokenTypeFor(token);
            TOKEN_TYPES.put(tt.getName(), tt);
        }
    }

    private final int id;
    private final String name;
    private final Category category;

    TokenType(int id, String name, Category category) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(category);
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.epdv.plugin.netbeans.lang.go.lexer;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author peter
 */
class TokenConverter {

    private static TokenConverter INSTANCE;

    public static TokenConverter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TokenConverter();
        }
        return INSTANCE;
    }

    private final Map<String, Category> namesToCategory = new TreeMap<>();
    private final Map<String, Category> metaToCategory = new TreeMap<>();

    private TokenConverter() {
        namesToCategory.put("INT_LIT", Category.NUMBER);
        namesToCategory.put("FLOAT_LIT", Category.NUMBER);
        namesToCategory.put("IMAGINARY_LIT", Category.NUMBER);
        namesToCategory.put("KEYWORD", Category.KEYWORD);
        namesToCategory.put("IDENTIFIER", Category.IDENTIFIER);
        namesToCategory.put("RUNE_LIT", Category.CHARACTER);
        namesToCategory.put("STRING_LIT", Category.CHARACTER);
        namesToCategory.put("LITTLE_U_VALUE", Category.CHARACTER);
        namesToCategory.put("BIG_U_VALUE", Category.CHARACTER);
        namesToCategory.put("WS", Category.WS);
        namesToCategory.put("COMMENT", Category.COMMENT);
        namesToCategory.put("LINE_COMMENT", Category.COMMENT);
        for (Category cat : Category.values()) {
            metaToCategory.put(cat.getValue(), cat);
        }
    }

    public TokenType getTokenTypeFor(RawToken token) {
        int id = token.getId();
        Category category = getCategoryFor(token);
        String name = getNameFor(token);
        return new TokenType(id, name, category);
    }

    private Category getCategoryFor(RawToken token) {
        switch (token.getType()) {
            case LITERAL:
                return namesToCategory.get(token.getValue());
            case STRING: {
                String s = token.getValue();
                if (s.charAt(0) == '\'' && s.charAt(s.length() - 1) == '\'') {
                    s = s.substring(1, s.length() - 1);
                }
                Category cat = metaToCategory.get(s);
                if (cat == null) {
                    char ch = s.charAt(0);
                    if ((ch >= 'A') && (ch <= 'Z') || (ch >= 'a') && (ch <= 'z')) {
                        return Category.KEYWORD;
                    } else {
                        return Category.OPERATOR;
                    }
                } else {
                    return cat;
                }
            }
            default:
                throw new AssertionError();
        }
    }

    private String getNameFor(RawToken token) {
        switch (token.getType()) {
            case LITERAL:
                return token.getValue();
            case STRING:
                return "T__" + token.getId();
            default:
                throw new AssertionError();
        }
    }
}

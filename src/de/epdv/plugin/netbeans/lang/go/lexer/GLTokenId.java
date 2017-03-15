/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.epdv.plugin.netbeans.lang.go.lexer;

import java.util.Objects;
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.TokenId;

/**
 *
 * @author peter
 */
public class GLTokenId implements TokenId {

    private static final Language<GLTokenId> LANGUAGE = new GLLanguageHierarchy().language();

    public static final Language<GLTokenId> getLanguage() {
        return LANGUAGE;
    }

    private final String name;
    private final Category primaryCategory;
    private final int id;

    public GLTokenId(String name, Category primaryCategory, int id) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(primaryCategory);
        this.name = name;
        this.primaryCategory = primaryCategory;
        this.id = id;
    }

    @Override
    public String primaryCategory() {
        return primaryCategory.getName();
    }

    @Override
    public int ordinal() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return "GLTokenId(" + id + "," + name + "," + (primaryCategory == null ? "" : primaryCategory.getName()) + ')';
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.epdv.plugin.netbeans.lang.go.lexer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

/**
 *
 * @author peter
 */
public class GLLanguageHierarchy extends LanguageHierarchy<GLTokenId> {

    private static final List<GLTokenId> TOKENS = new ArrayList<>();
    private static final Map<Integer, GLTokenId> ID_TO_TOKEN = new HashMap<Integer, GLTokenId>();

    static {
        Collection<TokenType> tokenTypes = TokenType.values();
        for (TokenType tokenType : tokenTypes) {
            TOKENS.add(new GLTokenId(tokenType.getName(), tokenType.getCategory(), tokenType.getId()));
        }
        for (GLTokenId token : TOKENS) {
            ID_TO_TOKEN.put(token.ordinal(), token);
        }
    }

    static synchronized GLTokenId getToken(int id) {
        return ID_TO_TOKEN.get(id);
    }

    public GLLanguageHierarchy() {
    }

    @Override
    protected synchronized Collection<GLTokenId> createTokenIds() {
        return TOKENS;
    }

    @Override
    protected synchronized Lexer<GLTokenId> createLexer(LexerRestartInfo<GLTokenId> info) {
        return new GLLexer(info);
    }

    @Override
    protected String mimeType() {
        return "text/x-golang";
    }
}

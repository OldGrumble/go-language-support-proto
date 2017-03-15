/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.epdv.plugin.netbeans.lang.go.lexer;

import de.epdv.plugin.netbeans.lang.go.antlr.GolangLexer;

import org.antlr.v4.runtime.Token;

import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

/**
 *
 * @author peter
 */
public class GLLexer implements Lexer<GLTokenId> {

    private final LexerRestartInfo<GLTokenId> info;
    private final GolangLexer goLexer;

    public GLLexer(LexerRestartInfo<GLTokenId> info) {
        this.info = info;

        Antlr4CharStream charStream = new Antlr4CharStream(info.input(), "Go Editor");
        goLexer = new GolangLexer(charStream);
    }

    @Override
    public org.netbeans.api.lexer.Token<GLTokenId> nextToken() {
        Token token = goLexer.nextToken();
        if (token.getType() != GolangLexer.EOF) {
            GLTokenId tokenId = GLLanguageHierarchy.getToken(token.getType());
            return info.tokenFactory().createToken(tokenId);
        }
        token.getText();
        return null;
    }

    @Override
    public Object state() {
        return null;
    }

    @Override
    public void release() {
    }
}

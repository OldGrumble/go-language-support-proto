/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.epdv.plugin.netbeans.lang.go.lexer;

import org.netbeans.api.lexer.Language;
import org.netbeans.modules.csl.spi.DefaultLanguageConfig;
import org.netbeans.modules.csl.spi.LanguageRegistration;

/**
 *
 * @author peter
 */
@LanguageRegistration(mimeType = "text/x-golang")
public class GLLanguage extends DefaultLanguageConfig {

    @Override
    public Language<GLTokenId> getLexerLanguage() {
        return GLTokenId.getLanguage();
    }

    @Override
    public String getDisplayName() {
        return "Go Language";
    }
}

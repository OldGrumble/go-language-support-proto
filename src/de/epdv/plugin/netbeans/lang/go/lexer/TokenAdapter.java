/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.epdv.plugin.netbeans.lang.go.lexer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.openide.util.Exceptions;

/**
 *
 * @author peter
 */
public class TokenAdapter {

    private static TokenAdapter INSTANCE;

    public static TokenAdapter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TokenAdapter();
        }
        return INSTANCE;
    }

    private final List<RawToken> rawTokens = new ArrayList<>();

    private TokenAdapter() {
        try {
            InputStream is = getInputStream("antlr/GolangLexer.tokens");
            readTokensList(is);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public List<RawToken> getRawTokens() {
        return Collections.unmodifiableList(rawTokens);
    }

    private void readTokensList(InputStream is) throws IOException {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr, 1024);
        String line, sv;
        int p, id;
        while ((line = br.readLine()) != null) {
            if (!line.startsWith("T__")) {
                p = line.lastIndexOf('=');
                sv = line.substring(0, p);
                id = Integer.parseInt(line.substring(p + 1));
                rawTokens.add(new RawToken(id, sv));
            }
        }
    }

    private InputStream getInputStream(String path) throws IOException {
        URL url;
        if (path.startsWith("/")) {
            url = this.getClass().getResource(path);
        } else {
            String base = this.getClass().getPackage().getName().replace('.', '/');
            url = getURL("/" + base, path);
        }
        return url.openStream();
    }

    private URL getURL(String base, String path) {
        if (path.startsWith("./")) {
            return getURL(base, path.substring(2));
        } else if (path.startsWith("../")) {
            int p = base.lastIndexOf('/');
            return getURL(base.substring(0, p), path.substring(3));
        } else {
            String newPath = base + '/' + path;
            return this.getClass().getResource(newPath);
        }
    }
}

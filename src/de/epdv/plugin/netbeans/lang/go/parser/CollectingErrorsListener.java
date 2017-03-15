/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.epdv.plugin.netbeans.lang.go.parser;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.netbeans.api.annotations.common.NonNull;
import org.openide.filesystems.FileObject;

/**
 *
 * @author peter
 */
public class CollectingErrorsListener implements ANTLRErrorListener {

    private final List<ErrorCollectionEntry> errors = new ArrayList<>();
    private final FileObject file;

    public CollectingErrorsListener(@NonNull FileObject file) {
        this.file = file;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException re) {
        ErrorCollectionEntry ece = new ErrorCollectionEntry(ErrorCollectionEntry.Type.SYNTAX_ERROR, file, recognizer, offendingSymbol, line, charPositionInLine, msg, re);
        append(ece);
    }

    @Override
    public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {
        throw new IllegalStateException("Should not happen:\nAmbiguity.");
//        ErrorCollectionEntry ece = new ErrorCollectionEntry(ErrorCollectionEntry.Type.AMBIGUITY, file, recognizer, dfa, startIndex, stopIndex, exact, ambigAlts, configs);
//        append(ece);
    }

    @Override
    public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, ATNConfigSet configs) {
        throw new IllegalStateException("Should not happen:\nSLL conflict.");
    }

    @Override
    public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, ATNConfigSet configs) {
        throw new IllegalStateException("Should not happen:\nFull-context prediction has a unique result.");
    }

    List<ErrorCollectionEntry> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    private void append(ErrorCollectionEntry ece) {
        errors.add(ece);
    }
}

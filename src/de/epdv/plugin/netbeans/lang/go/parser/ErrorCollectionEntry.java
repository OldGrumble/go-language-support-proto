/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.epdv.plugin.netbeans.lang.go.parser;

import java.util.BitSet;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.atn.ATNSimulator;
import org.antlr.v4.runtime.dfa.DFA;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.modules.csl.api.Severity;
//
//import org.netbeans.modules.csl.api.Error;
import org.netbeans.modules.csl.api.Severity;
//import org.openide.filesystems.FileObject;
import org.netbeans.modules.csl.spi.DefaultError;
import org.openide.filesystems.FileObject;

/**
 *
 * @author peter
 */
class ErrorCollectionEntry extends DefaultError {

    public enum Type {
        AMBIGUITY, SYNTAX_ERROR;
    }

    private static int toAbsolutePosition(int line, int charPositionInLine, Object offendingSymbol) {
        String s = offendingSymbol == null ? "" : String.valueOf(offendingSymbol);
        return toAbsolutePosition(line, charPositionInLine + s.length());
    }

    private static int toAbsolutePosition(int line, int charPositionInLine) {
        return -1;
    }

    private final Type type;
    private int line;
    private String message;
    private RecognitionException exception;
    private Severity s;

    ErrorCollectionEntry(@NonNull Type type, @NonNull FileObject file, Recognizer<?, ? extends ATNSimulator> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException re) {
        super(offendingSymbol == null ? null : String.valueOf(offendingSymbol),
                "Display name",
                msg,
                file,
                toAbsolutePosition(line, charPositionInLine),
                toAbsolutePosition(line, charPositionInLine, offendingSymbol),
                Severity.ERROR);
        this.type = Type.SYNTAX_ERROR;
        this.line = line;
        this.message = msg;
        this.exception = re;
    }

//    ErrorCollectionEntry(Type type, Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {
//        super(dfa.getStates().get(0).offendingSymbol == null ? null : String.valueOf(offendingSymbol),
//                "Display name",
//                msg,
//                file,
//                toAbsolutePosition(dfa, startIndex, stopIndex),
//                toAbsolutePosition(line, charPositionInLine, offendingSymbol),
//                Severity.ERROR);
//        this.type = type;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    int getLine() {
        return line;
    }

    String getMessage() {
        return message;
    }

    RecognitionException getException() {
        return exception;
    }
}

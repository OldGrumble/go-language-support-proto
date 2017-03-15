/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.epdv.plugin.netbeans.lang.go.parser;

import de.epdv.plugin.netbeans.lang.go.antlr.GolangLexer;
import de.epdv.plugin.netbeans.lang.go.antlr.GolangParser;
import java.util.List;

import javax.swing.event.ChangeListener;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;

import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Task;
import org.netbeans.modules.parsing.spi.ParseException;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.SourceModificationEvent;
import org.openide.util.Exceptions;

/**
 *
 * @author peter
 */
public class GLParser extends Parser {

    public static class GLEditorParserResult extends Parser.Result {

        private final GolangParser goParser;
        private final CollectingErrorsListener errors;
        private boolean valid = true;

        GLEditorParserResult(Snapshot snapshot, GolangParser goParser, CollectingErrorsListener errors) {
            super(snapshot);
            this.goParser = goParser;
            this.errors = errors;
        }

        public GolangParser getGolangParser() throws ParseException {
            if (!valid) {
                throw new ParseException();
            }
            return goParser;
        }

        List<ErrorCollectionEntry> getErrors() {
            return errors.getErrors();
        }

        @Override
        protected void invalidate() {
            valid = false;
        }
    }

    private Snapshot snapshot;
    private GolangParser goParser;
    private CollectingErrorsListener errors;

    @Override
    public void parse(Snapshot snapshot, Task task, SourceModificationEvent event) {
        this.snapshot = snapshot;
        ANTLRInputStream input = new ANTLRInputStream(snapshot.getText().toString());
        Lexer lexer = new GolangLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        goParser = new GolangParser(tokens);
        goParser.removeErrorListeners();
        errors = new CollectingErrorsListener(snapshot.getSource().getFileObject());
        goParser.addErrorListener(errors);
        try {
            goParser.sourceFile();
        } catch (RuntimeException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public Result getResult(Task task) {
        return new GLEditorParserResult(snapshot, goParser, errors);
    }

    @Override
    public void cancel() {
    }

    @Override
    public void addChangeListener(ChangeListener changeListener) {
    }

    @Override
    public void removeChangeListener(ChangeListener changeListener) {
    }
}

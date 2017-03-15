/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.epdv.plugin.netbeans.lang.go.parser;


import java.util.ArrayList;
import java.util.List;
import javax.swing.text.Document;
import org.netbeans.modules.parsing.spi.Parser;

//import org.antlr.v4.runtime.RecognitionException;

import org.netbeans.modules.parsing.spi.Parser.Result;
import org.netbeans.modules.parsing.spi.ParserResultTask;
import org.netbeans.modules.parsing.spi.Scheduler;
import org.netbeans.modules.parsing.spi.SchedulerEvent;
import org.netbeans.spi.editor.hints.ErrorDescription;
import org.netbeans.spi.editor.hints.ErrorDescriptionFactory;
import org.netbeans.spi.editor.hints.HintsController;
import org.netbeans.spi.editor.hints.Severity;

import org.openide.util.Exceptions;

/**
 *
 * @author peter
 */
public class SyntaxErrorsHighlightingTask extends ParserResultTask<Parser.Result> {

    public SyntaxErrorsHighlightingTask() {
    }

    @Override
    public void run(Result result, SchedulerEvent event) {
        try {
            GLParser.GLEditorParserResult glResult = (GLParser.GLEditorParserResult)result;
            List<ErrorCollectionEntry> collectedErrors = glResult.getErrors();
            Document document = result.getSnapshot().getSource().getDocument(false);
            List<ErrorDescription> errors = new ArrayList<>();
            for (ErrorCollectionEntry collectedError : collectedErrors) {
//                RecognitionException exception = collectedError.getException();
                String message = collectedError.getMessage();

                int line = collectedError.getLine();
                if (line <= 0) {
                    continue;
                }
                ErrorDescription errorDescription = ErrorDescriptionFactory.createErrorDescription(
                        Severity.ERROR,
                        message,
                        document,
                        line);
                errors.add(errorDescription);
            }
            HintsController.setErrors(document, "golang", errors);
        } catch (RuntimeException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public int getPriority() {
        return 100;
    }

    @Override
    public Class<? extends Scheduler> getSchedulerClass() {
        return Scheduler.EDITOR_SENSITIVE_TASK_SCHEDULER;
    }

    @Override
    public void cancel() {
    }
}

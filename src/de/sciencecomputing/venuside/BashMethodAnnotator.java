package de.sciencecomputing.venuside;

import de.sciencecomputing.venuside.psi.ContextAssignment;

import com.ansorgit.plugins.bash.lang.psi.api.BashPsiElement;
import com.ansorgit.plugins.bash.lang.psi.impl.word.BashWordImpl;
import com.ansorgit.plugins.bash.lang.psi.impl.command.BashSimpleCommandImpl;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BashMethodAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (!(element instanceof BashSimpleCommandImpl))
            return;
        BashSimpleCommandImpl cmd = (BashSimpleCommandImpl) element;
        String cmdName = cmd.getReferencedCommandName();
        if (cmdName == null)
            return;
        String methodPath = DomainTree.depotPath(element.getContainingFile());
        if (methodPath == null) {
            holder.createWarningAnnotation(cmd.getTextRange(), "venus API call might be outside of method");
            return;
        }
        if (cmdName.equals("venus_import_variable"))
            annotateImportVariable(cmd, holder, methodPath);
    }

    private void annotateImportVariable(@NotNull BashSimpleCommandImpl cmd, @NotNull AnnotationHolder holder, @NotNull String methodPath) {
        BashWordImpl varWord = null;
        String contextName = cmd.getContainingFile().getName();
        for (BashPsiElement param: cmd.parameters()) {
            if (!(param instanceof BashWordImpl))
                return; // ???
            BashWordImpl paramWord = (BashWordImpl) param;
            if (!paramWord.isStatic())
                return; // could evaluate to any number of parameters
            if (contextName == null)
                contextName = param.getText();
            else if (param.getText().startsWith("-"))
                continue;
            else if (param.getText().equals("from")) {
                contextName = null;
                holder.createInfoAnnotation(param.getTextRange(), null)
                        .setTextAttributes(DefaultLanguageHighlighterColors.KEYWORD);
            } else
                varWord = paramWord;
        }
        if (varWord == null) {
            holder.createErrorAnnotation(cmd.getTextRange(), "variable import without variable name");
            return;
        } else if (contextName == null) {
            holder.createErrorAnnotation(cmd.getTextRange(), "venus_import_variable ... from without context name");
            return;
        }

        ContextAssignment var = DomainTree.lookupContextVariable(cmd.getProject(),
                methodPath, contextName, varWord.getText());
        if (var == null) {
            Annotation annotation = holder.createWeakWarningAnnotation(varWord.getTextRange(), "context variable might be undefined");
            annotation.setHighlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL);
        } else {
            Annotation annotation = holder.createInfoAnnotation(varWord.getTextRange(), var.getValue());
            annotation.setTextAttributes(DefaultLanguageHighlighterColors.GLOBAL_VARIABLE);
        }
    }
}

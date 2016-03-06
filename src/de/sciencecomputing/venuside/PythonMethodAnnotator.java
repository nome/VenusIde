package de.sciencecomputing.venuside;

import de.sciencecomputing.venuside.psi.ContextAssignment;

import com.jetbrains.python.psi.PyExpression;
import com.jetbrains.python.psi.impl.PyCallExpressionImpl;
import com.jetbrains.python.psi.impl.PyStringLiteralExpressionImpl;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.Annotation;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;


import org.jetbrains.annotations.NotNull;

public class PythonMethodAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (!(element instanceof PyCallExpressionImpl))
            return;
        PyCallExpressionImpl call = (PyCallExpressionImpl) element;
        PyExpression callee = call.getCallee();
        if (callee == null || !callee.getText().equals("venus.import_variable"))
            return;
        PyStringLiteralExpressionImpl varArg = call.getArgument(0, PyStringLiteralExpressionImpl.class);
        if (varArg == null)
            return;
        String varName = varArg.getStringValue();

        String methodPath = DomainTree.depotPath(element.getContainingFile());
        if (methodPath == null) {
            holder.createWarningAnnotation(call.getTextRange(), "venus API call might be outside of method");
            return;
        }
        String contextName = element.getContainingFile().getName();
        ContextAssignment var = DomainTree.lookupContextVariable(element.getProject(),
                methodPath, contextName, varName);
        if (var == null) {
            Annotation annotation = holder.createWeakWarningAnnotation(varArg.getTextRange(), "context variable might be undefined");
            annotation.setHighlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL);
        } else {
            Annotation annotation = holder.createInfoAnnotation(varArg.getTextRange(), var.getValue());
            annotation.setTextAttributes(DefaultLanguageHighlighterColors.GLOBAL_VARIABLE);
        }
    }
}

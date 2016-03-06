package de.sciencecomputing.venuside

import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.psi.PsiElement
import com.jetbrains.python.psi.impl.PyCallExpressionImpl
import com.jetbrains.python.psi.impl.PyStringLiteralExpressionImpl

class PythonMethodAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element !is PyCallExpressionImpl)
            return
        val callee = element.callee
        if (callee == null || callee.text != "venus.import_variable")
            return
        val varArg = element.getArgument(0, PyStringLiteralExpressionImpl::class.java) ?: return
        val varName = varArg.stringValue

        val methodPath = depotPath(element.containingFile)
        if (methodPath == null) {
            holder.createWarningAnnotation(element.textRange, "venus API call might be outside of method")
            return
        }
        val contextName = element.containingFile.name
        val variable = lookupContextVariable(element.getProject(),
                methodPath, contextName, varName)
        if (variable == null)
            holder.createWeakWarningAnnotation(varArg.textRange, "context variable might be undefined")
                .highlightType = ProblemHighlightType.LIKE_UNKNOWN_SYMBOL
        else
            holder.createInfoAnnotation(varArg.textRange, variable.value)
                    .textAttributes = DefaultLanguageHighlighterColors.GLOBAL_VARIABLE
    }
}

package de.sciencecomputing.venuside

import com.ansorgit.plugins.bash.lang.psi.impl.command.BashSimpleCommandImpl
import com.ansorgit.plugins.bash.lang.psi.impl.word.BashWordImpl

import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.psi.PsiElement

class BashMethodAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element !is BashSimpleCommandImpl)
            return
        val cmdName = element.referencedCommandName ?: return
        val methodPath = depotPath(element.containingFile)
        if (methodPath == null) {
            holder.createWarningAnnotation(element.textRange, "venus API call might be outside of method")
            return
        }
        if (cmdName == "venus_import_variable")
            annotateImportVariable(element, holder, methodPath)
    }

    private fun annotateImportVariable(cmd: BashSimpleCommandImpl, holder: AnnotationHolder, methodPath: String) {
        var varWord: BashWordImpl? = null
        var contextName: String? = cmd.containingFile.name
        for (param in cmd.parameters()) {
            if (param !is BashWordImpl)
                return  // ???
            if (!param.isStatic)
                return  // could evaluate to any number of parameters
            if (contextName == null)
                contextName = param.getText()
            else if (param.getText().startsWith("-"))
                continue
            else if (param.getText() == "from") {
                contextName = null
                holder.createInfoAnnotation(param.getTextRange(), null).textAttributes = DefaultLanguageHighlighterColors.KEYWORD
            } else
                varWord = param
        }
        if (varWord == null) {
            holder.createErrorAnnotation(cmd.textRange, "variable import without variable name")
            return
        } else if (contextName == null) {
            holder.createErrorAnnotation(cmd.textRange, "venus_import_variable ... from without context name")
            return
        }

        val variable = lookupContextVariable(cmd.project, methodPath, contextName, varWord.text)
        if (variable == null)
            holder.createWeakWarningAnnotation(varWord.textRange, "context variable might be undefined")
                .highlightType = ProblemHighlightType.LIKE_UNKNOWN_SYMBOL
        else
            holder.createInfoAnnotation(varWord.textRange, variable.value)
                .textAttributes = DefaultLanguageHighlighterColors.GLOBAL_VARIABLE
    }
}

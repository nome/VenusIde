package de.sciencecomputing.venuside.psi

import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiElement
import de.sciencecomputing.venuside.Icons
import javax.swing.Icon

fun getVariable(element: ContextAssignment): String? {
    val varNode = element.node.findChildByType(ContextTypes.VARIABLE) ?: return null
    return varNode.text.replace("\\\\ ".toRegex(), " ")
}

fun getValue(element: ContextAssignment): String? {
    val valNode = element.node.findChildByType(ContextTypes.VALUE) ?: return null
    val value = valNode.text
    if (value.length > 1 && value.startsWith("\"") && value.endsWith("\""))
        return value.substring(1, value.length - 1).replace("\\\\\"".toRegex(), "\"")
    return value
}

fun getVariableIdentifier(element: ContextAssignment): PsiElement? {
    val varNode = element.node.findChildByType(ContextTypes.VARIABLE) ?: return null
    return varNode.psi
}

fun getPresentation(element: ContextAssignment): ItemPresentation {
    return object : ItemPresentation {
        override fun getPresentableText(): String? = getVariable(element)
        override fun getLocationString(): String? = element.containingFile?.name
        override fun getIcon(unused: Boolean): Icon? = Icons.CONTEXT
    }
}
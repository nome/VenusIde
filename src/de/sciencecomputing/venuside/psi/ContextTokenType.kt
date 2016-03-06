package de.sciencecomputing.venuside.psi

import de.sciencecomputing.venuside.ContextLanguage

import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NonNls

class ContextTokenType(@NonNls debugName: String) : IElementType(debugName, ContextLanguage) {
    override fun toString(): String = "ContextTokenType." + super.toString()
}

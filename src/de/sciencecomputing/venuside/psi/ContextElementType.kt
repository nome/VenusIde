package de.sciencecomputing.venuside.psi

import com.intellij.psi.tree.IElementType
import de.sciencecomputing.venuside.ContextLanguage
import org.jetbrains.annotations.NonNls

class ContextElementType(@NonNls debugName: String) : IElementType(debugName, ContextLanguage)


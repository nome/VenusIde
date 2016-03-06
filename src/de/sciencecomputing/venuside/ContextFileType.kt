package de.sciencecomputing.venuside

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object ContextFileType : LanguageFileType(ContextLanguage) {
    override fun getName(): String = "Context file"
    override fun getDescription(): String = "scVENUS context file"
    override fun getDefaultExtension(): String = ""
    override fun getIcon(): Icon? = Icons.CONTEXT
}

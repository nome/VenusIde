package de.sciencecomputing.venuside.psi

import de.sciencecomputing.venuside.ContextFileType
import de.sciencecomputing.venuside.ContextLanguage

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class ContextFile(viewProvider: FileViewProvider) :
        PsiFileBase(viewProvider, ContextLanguage) {
    override fun getFileType(): FileType = ContextFileType
    override fun toString(): String = "Context file"
}
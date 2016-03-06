package de.sciencecomputing.venuside

import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.fileTypes.FileTypeRegistry
import com.intellij.openapi.util.io.ByteSequence
import com.intellij.openapi.vfs.VirtualFile

class ContextFileTypeDetector : FileTypeRegistry.FileTypeDetector {
    override fun detect(file: VirtualFile, firstBytes: ByteSequence, firstCharsIfText: CharSequence?):
            FileType? {
        val path = file.path.split("/".toRegex()).dropLastWhile { it.isEmpty() }
        if (path.size >= 2 && path[path.size - 2] == "context")
            return ContextFileType
        else
            return null
    }

    override fun getVersion(): Int = 1
}
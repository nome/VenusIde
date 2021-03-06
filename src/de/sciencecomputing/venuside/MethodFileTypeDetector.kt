package de.sciencecomputing.venuside

import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.fileTypes.FileTypeRegistry
import com.intellij.openapi.util.io.ByteSequence
import com.intellij.openapi.vfs.VirtualFile

class MethodFileTypeDetector : FileTypeRegistry.FileTypeDetector {
    override fun detect(file: VirtualFile, firstBytes: ByteSequence, firstCharsIfText: CharSequence?): FileType? {
        if (firstCharsIfText == null) return null
        val lineBreak = firstCharsIfText.indexOf('\n')
        if (lineBreak < 0) return null
        val firstLine = firstCharsIfText.subSequence(0, lineBreak)
        if (!firstLine.contains("Interpreter:")) return null

        if (firstLine.contains("python"))
            return FileTypeRegistry.getInstance().findFileTypeByName("Python")
        else if (firstLine.contains("bash"))
            return FileTypeRegistry.getInstance().findFileTypeByName("Bash")

        return null
    }

    override fun getVersion() = 1
}
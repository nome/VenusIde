package de.sciencecomputing.venuside;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.util.io.ByteSequence;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MethodFileTypeDetector implements FileTypeRegistry.FileTypeDetector {
    @Override
    public FileType detect(@NotNull VirtualFile file, @NotNull ByteSequence firstBytes, @Nullable CharSequence firstCharsIfText) {
        if (firstCharsIfText == null) {
            return null;
        }
        final int lineBreak = StringUtil.indexOf(firstCharsIfText, '\n');
        if (lineBreak < 0) {
            return null;
        }
        String firstLine = firstCharsIfText.subSequence(0, lineBreak).toString();
        if (! firstLine.contains("Interpreter:")) {
            return null;
        }
        if (firstLine.contains("python")) {
            return FileTypeRegistry.getInstance().findFileTypeByName("Python");
        } else if (firstLine.contains("bash")) {
            return FileTypeRegistry.getInstance().findFileTypeByName("Bash");
        }
        return null;
    }

    @Override
    public int getVersion() { return 1; }
}

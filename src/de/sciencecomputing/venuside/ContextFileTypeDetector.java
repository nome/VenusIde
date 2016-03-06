package de.sciencecomputing.venuside;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.util.io.ByteSequence;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ContextFileTypeDetector implements FileTypeRegistry.FileTypeDetector {
    @Override
    public FileType detect(@NotNull VirtualFile file, @NotNull ByteSequence firstBytes, @Nullable CharSequence firstCharsIfText) {
        String[] path = file.getPath().split("/");
        if (path.length < 2)
            return null;
        if (path[path.length-2].equals("context"))
            return ContextFileType.INSTANCE;
        else
            return null;
    }

    @Override
    public int getVersion() { return 1; }
}

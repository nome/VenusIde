package de.sciencecomputing.venuside.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;
import de.sciencecomputing.venuside.ContextFileType;
import de.sciencecomputing.venuside.ContextLanguage;

public class ContextFile extends PsiFileBase {
    public ContextFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, ContextLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return ContextFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Context file";
    }
}

package de.sciencecomputing.venuside;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import javax.swing.*;

public class ContextFileType extends LanguageFileType {
    public static final ContextFileType INSTANCE = new ContextFileType();

    private ContextFileType() {
        super(ContextLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Context file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "scVENUS context file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return Icons.CONTEXT;
    }
}

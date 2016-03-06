package de.sciencecomputing.venuside.psi;

import com.intellij.psi.tree.IElementType;
import de.sciencecomputing.venuside.ContextLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.NonNls;

public class ContextElementType extends IElementType {
    public ContextElementType(@NotNull @NonNls String debugName) {
        super(debugName, ContextLanguage.INSTANCE);
    }
}

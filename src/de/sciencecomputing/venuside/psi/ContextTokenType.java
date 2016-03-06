package de.sciencecomputing.venuside.psi;

import com.intellij.psi.tree.IElementType;
import de.sciencecomputing.venuside.ContextLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.NonNls;

public class ContextTokenType extends IElementType {
    public ContextTokenType(@NotNull @NonNls String debugName) {
        super(debugName, ContextLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "ContextTokenType." + super.toString();
    }
}

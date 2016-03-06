package de.sciencecomputing.venuside.psi;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.Nullable;
import java.lang.String;
import javax.swing.*;

import de.sciencecomputing.venuside.Icons;
import de.sciencecomputing.venuside.psi.ContextAssignment;
import de.sciencecomputing.venuside.psi.ContextTypes;

public class ContextPsiUtil {
    public static String getVariable(ContextAssignment element) {
        ASTNode varNode = element.getNode().findChildByType(ContextTypes.VARIABLE);
        if (varNode == null)
            return null;
        return varNode.getText().replaceAll("\\\\ ", " ");
    }

    public static String getValue(ContextAssignment element) {
        ASTNode valNode = element.getNode().findChildByType(ContextTypes.VALUE);
        if (valNode == null)
            return null;
        String value = valNode.getText();
        if (value.length() > 1 && value.startsWith("\"") && value.endsWith("\""))
            return value.substring(1, value.length()-1).replaceAll("\\\\\"", "\"");
        return value;
    }

    public static PsiElement getVariableIdentifier(ContextAssignment element) {
        ASTNode varNode = element.getNode().findChildByType(ContextTypes.VARIABLE);
        if (varNode == null)
            return null;
        return varNode.getPsi();
    }

    public static ItemPresentation getPresentation(final ContextAssignment element) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return getVariable(element);
            }

            @Nullable
            @Override
            public String getLocationString() {
                PsiFile file = element.getContainingFile();
                return file == null ? null : file.getName();
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                return Icons.CONTEXT;
            }
        };
    }
}

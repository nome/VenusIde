package de.sciencecomputing.venuside;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

public class ContextColorSettingsPage implements ColorSettingsPage {
    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
            new AttributesDescriptor("Variable", ContextSyntaxHighlighter.VARIABLE),
            new AttributesDescriptor("Separator", ContextSyntaxHighlighter.SEPARATOR),
            new AttributesDescriptor("Value", ContextSyntaxHighlighter.VALUE),
            new AttributesDescriptor("Comment", ContextSyntaxHighlighter.COMMENT),
    };

    @Nullable
    @Override
    public Icon getIcon() {
        return Icons.CONTEXT;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new ContextSyntaxHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {
        return "# demo context\n" +
                "variable1 = value1\n" +
                "variable2 = multiple words value # and comment\n\n" +
                "variable3 = \"\nthis value\nspans multiple\nlines\"\n";
    }

    @Nullable
    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        return DESCRIPTORS;
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Context";
    }
}
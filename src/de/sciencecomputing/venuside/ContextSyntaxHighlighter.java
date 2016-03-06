package de.sciencecomputing.venuside;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

import org.jetbrains.annotations.NotNull;

import de.sciencecomputing.venuside.psi.ContextTypes;

public class ContextSyntaxHighlighter extends SyntaxHighlighterBase {
    public static final TextAttributesKey SEPARATOR = createTextAttributesKey("CONTEXT_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey VARIABLE = createTextAttributesKey("CONTEXT_VARIABLE", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey VALUE = createTextAttributesKey("CONTEXT_VALUE", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey COMMENT = createTextAttributesKey("CONTEXT_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey BAD_CHARACTER = createTextAttributesKey("CONTEXT_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);

    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] SEPARATOR_KEYS = new TextAttributesKey[]{SEPARATOR};
    private static final TextAttributesKey[] VARIABLE_KEYS = new TextAttributesKey[]{VARIABLE};
    private static final TextAttributesKey[] VALUE_KEYS = new TextAttributesKey[]{VALUE};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new ContextLexerAdapter();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(ContextTypes.SEPARATOR)) {
            return SEPARATOR_KEYS;
        } else if (tokenType.equals(ContextTypes.VARIABLE)) {
            return VARIABLE_KEYS;
        } else if (tokenType.equals(ContextTypes.VALUE)) {
            return VALUE_KEYS;
        } else if (tokenType.equals(ContextTypes.COMMENT)) {
            return COMMENT_KEYS;
        } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return BAD_CHAR_KEYS;
        } else {
            return EMPTY_KEYS;
        }
    }
}
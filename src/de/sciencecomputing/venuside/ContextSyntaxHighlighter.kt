package de.sciencecomputing.venuside

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey

import de.sciencecomputing.venuside.psi.ContextTypes

class ContextSyntaxHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer = ContextLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> =
        when (tokenType) {
            ContextTypes.SEPARATOR  -> SEPARATOR_KEYS
            ContextTypes.VARIABLE   -> VARIABLE_KEYS
            ContextTypes.VALUE      -> VALUE_KEYS
            ContextTypes.COMMENT    -> COMMENT_KEYS
            TokenType.BAD_CHARACTER -> BAD_CHAR_KEYS
            else                    -> EMPTY_KEYS
        }

    companion object {
        val SEPARATOR = createTextAttributesKey("CONTEXT_SEPARATOR",
                DefaultLanguageHighlighterColors.OPERATION_SIGN)
        val VARIABLE = createTextAttributesKey("CONTEXT_VARIABLE",
                DefaultLanguageHighlighterColors.KEYWORD)
        val VALUE = createTextAttributesKey("CONTEXT_VALUE",
                DefaultLanguageHighlighterColors.STRING)
        val COMMENT = createTextAttributesKey("CONTEXT_COMMENT",
                DefaultLanguageHighlighterColors.LINE_COMMENT)
        val BAD_CHARACTER = createTextAttributesKey("CONTEXT_BAD_CHARACTER",
                HighlighterColors.BAD_CHARACTER)

        private val BAD_CHAR_KEYS = arrayOf(BAD_CHARACTER)
        private val SEPARATOR_KEYS = arrayOf(SEPARATOR)
        private val VARIABLE_KEYS = arrayOf(VARIABLE)
        private val VALUE_KEYS = arrayOf(VALUE)
        private val COMMENT_KEYS = arrayOf(COMMENT)
        private val EMPTY_KEYS = arrayOf<TextAttributesKey>()
    }
}
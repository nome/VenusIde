package de.sciencecomputing.venuside

import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage

import javax.swing.Icon

class ContextColorSettingsPage : ColorSettingsPage {
    companion object {
        private val DESCRIPTORS = arrayOf(
                AttributesDescriptor("Variable",  ContextSyntaxHighlighter.VARIABLE),
                AttributesDescriptor("Separator", ContextSyntaxHighlighter.SEPARATOR),
                AttributesDescriptor("Value",     ContextSyntaxHighlighter.VALUE),
                AttributesDescriptor("Comment",   ContextSyntaxHighlighter.COMMENT)
        )
    }

    override fun getDisplayName(): String = "Context"

    override fun getDemoText(): String = """# demo context
variable1 = value1
variable2 = multiple words value # and comment

variable3 = "
this value
spans multiple
lines"
"""

    override fun getIcon(): Icon? = Icons.CONTEXT

    override fun getHighlighter(): SyntaxHighlighter = ContextSyntaxHighlighter()

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey>? = null

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = DESCRIPTORS

    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY
}
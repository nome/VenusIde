package de.sciencecomputing.venuside

import com.intellij.lexer.FlexAdapter
import java.io.Reader

class ContextLexerAdapter : FlexAdapter(ContextLexer(null as Reader?))
package de.sciencecomputing.venuside;

import com.intellij.lexer.FlexAdapter;
import java.io.Reader;

public class ContextLexerAdapter extends FlexAdapter {
    public ContextLexerAdapter() {
        super(new ContextLexer((Reader) null));
    }
}

package de.sciencecomputing.venuside;

import com.intellij.lang.Language;

public class ContextLanguage extends Language {
    public static final ContextLanguage INSTANCE = new ContextLanguage();

    private ContextLanguage() {
        super("context");
    }
}

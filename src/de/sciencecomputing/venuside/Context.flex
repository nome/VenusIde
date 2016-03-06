package de.sciencecomputing.venuside;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import de.sciencecomputing.venuside.psi.ContextTypes;
import com.intellij.psi.TokenType;

%%

%class ContextLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType

CRLF= \n|\r|\r\n
WHITE_SPACE=[\ \t\f]
VAL_DELIM=[^ \n\r\f\\#] | "\\".
VALUE_CHARACTER=[^\n\r\f\\#] | "\\".
END_OF_LINE_COMMENT="#"[^\r\n]*
SEPARATOR="="
VAR_CHARACTER=[^=\ \n\r\t\f\\#] | "\\ "
QUOTE_CHARACTER="\""
QUOTED=[^\"\\] | "\\".

%state WAITING_VALUE

%%

<YYINITIAL> {END_OF_LINE_COMMENT}                           { yybegin(YYINITIAL); return ContextTypes.COMMENT; }
<YYINITIAL> {VAR_CHARACTER}+                                { yybegin(YYINITIAL); return ContextTypes.VARIABLE; }
<YYINITIAL> {SEPARATOR}                                     { yybegin(WAITING_VALUE); return ContextTypes.SEPARATOR; }
<WAITING_VALUE> {WHITE_SPACE}+                              { yybegin(WAITING_VALUE); return TokenType.WHITE_SPACE; }
<WAITING_VALUE> {QUOTE_CHARACTER}{QUOTED}*{QUOTE_CHARACTER} { yybegin(YYINITIAL); return ContextTypes.VALUE; }
<WAITING_VALUE> {VAL_DELIM}{VALUE_CHARACTER}*{VAL_DELIM}    { yybegin(YYINITIAL); return ContextTypes.VALUE; }
{CRLF}+                                                     { yybegin(YYINITIAL); return ContextTypes.CRLF; }
({WHITE_SPACE})+                                            { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
.                                                           { return TokenType.BAD_CHARACTER; }
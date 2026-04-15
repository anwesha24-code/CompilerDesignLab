%{
#include <stdio.h>
%}

%%
"int"|"float"|"double"|"char"|"if"|"else"|"while"|"for"|"return"|"void" 
        { printf("%s -> KEYWORD\n", yytext); }

[0-9]+  { printf("%s -> LITERAL\n", yytext); }

[a-zA-Z_][a-zA-Z0-9_]* 
        { printf("%s -> IDENTIFIER\n", yytext); }

[+\-*/=] 
        { printf("%s -> OPERATOR\n", yytext); }

[;,()]  
        { printf("%s -> PUNCTUATION\n", yytext); }

[ \t\n]  ;   // ignore whitespace

.       { printf("%s -> ERROR\n", yytext); }

%%

int main() {
    printf("Enter input (Ctrl+D to stop):\n");
    yylex();
    return 0;
}
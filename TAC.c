#include <stdio.h>
#include <string.h>
#include <ctype.h>

#define MAX 100

// Stack for operators
char stack[MAX];
int top = -1;

// Temp variable counter
int tempCount = 1;

// Stack functions
void push(char ch) {
    stack[++top] = ch;
}

char pop() {
    return stack[top--];
}

char peek() {
    return stack[top];
}

// Precedence
int precedence(char ch) {
    if (ch == '+' || ch == '-') return 1;
    if (ch == '*' || ch == '/') return 2;
    return 0;
}

// Generate temp variable
void newTemp(char *temp) {
    sprintf(temp, "t%d", tempCount++);
}

// Infix → Postfix
void infixToPostfix(char exp[], char postfix[]) {
    int k = 0;
    top = -1;

    for (int i = 0; i < strlen(exp); i++) {
        char ch = exp[i];

        if (isalnum(ch)) {
            postfix[k++] = ch;
        }
        else if (ch == '(') {
            push(ch);
        }
        else if (ch == ')') {
            while (top != -1 && peek() != '(') {
                postfix[k++] = pop();
            }
            pop();
        }
        else {
            while (top != -1 && precedence(peek()) >= precedence(ch)) {
                postfix[k++] = pop();
            }
            push(ch);
        }
    }

    while (top != -1) {
        postfix[k++] = pop();
    }

    postfix[k] = '\0';
}

// Generate TAC
void generateTAC(char postfix[], char lhs[]) {
    char st[MAX][MAX];
    int top2 = -1;

    for (int i = 0; i < strlen(postfix); i++) {
        char ch = postfix[i];

        if (isalnum(ch)) {
            char temp[2] = {ch, '\0'};
            strcpy(st[++top2], temp);
        }
        else {
            char op2[MAX], op1[MAX];
            strcpy(op2, st[top2--]);
            strcpy(op1, st[top2--]);

            char temp[MAX];
            newTemp(temp);

            printf("%s = %s %c %s\n", temp, op1, ch, op2);

            strcpy(st[++top2], temp);
        }
    }

    printf("%s = %s\n", lhs, st[top2]);
}

int main() {

    int n;
    char input[MAX], clean[MAX], lhs[MAX], rhs[MAX], postfix[MAX];

    printf("Enter number of expressions: ");
    scanf("%d", &n);
    getchar();

    for (int i = 0; i < n; i++) {

        printf("\nEnter expression (e.g., c=(a+b)*(c+d)):\n");
        fgets(input, sizeof(input), stdin);

        input[strcspn(input, "\n")] = '\0';

        // Remove spaces
        int k = 0;
        for (int j = 0; j < strlen(input); j++) {
            if (input[j] != ' ')
                clean[k++] = input[j];
        }
        clean[k] = '\0';

        // Split LHS and RHS
        char *token = strtok(clean, "=");
        strcpy(lhs, token);
        token = strtok(NULL, "=");
        strcpy(rhs, token);

        // Convert and generate TAC
        infixToPostfix(rhs, postfix);

        printf("Three Address Code:\n");
        generateTAC(postfix, lhs);
    }

    return 0;
}
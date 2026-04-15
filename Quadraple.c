#include <stdio.h>
#include <string.h>

#define MAX 50

// ---------- QUADRUPLE ----------
typedef struct {
    char op[10], arg1[10], arg2[10], result[10];
} Quad;

Quad quad[MAX];

// ---------- TRIPLE ----------
typedef struct {
    char op[10], arg1[10], arg2[10];
} Triple;

Triple triple[MAX];

// ---------- INDIRECT TRIPLE ----------
int indirect[MAX];

// ---------- BACKPATCHING ----------
char tac[MAX][50];
int instr = 0;

// Emit TAC instruction
int emit(char *str) {
    strcpy(tac[instr], str);
    return instr++;
}

// Backpatch function
void backpatch(int list[], int size, int target) {
    char temp[10];
    sprintf(temp, "%d", target);

    for (int i = 0; i < size; i++) {
        strcat(tac[list[i]], temp);
    }
}

int main() {

    printf("===== QUADRUPLE =====\n");

    strcpy(quad[0].op,"*"); strcpy(quad[0].arg1,"4"); strcpy(quad[0].arg2,"i"); strcpy(quad[0].result,"S1");
    strcpy(quad[1].op,"=[]"); strcpy(quad[1].arg1,"x"); strcpy(quad[1].arg2,"S1"); strcpy(quad[1].result,"S2");
    strcpy(quad[2].op,"*"); strcpy(quad[2].arg1,"4"); strcpy(quad[2].arg2,"j"); strcpy(quad[2].result,"S3");
    strcpy(quad[3].op,"=[]"); strcpy(quad[3].arg1,"y"); strcpy(quad[3].arg2,"S3"); strcpy(quad[3].result,"S4");
    strcpy(quad[4].op,"*"); strcpy(quad[4].arg1,"S2"); strcpy(quad[4].arg2,"S4"); strcpy(quad[4].result,"S5");
    strcpy(quad[5].op,"+"); strcpy(quad[5].arg1,"result"); strcpy(quad[5].arg2,"S5"); strcpy(quad[5].result,"S6");
    strcpy(quad[6].op,"="); strcpy(quad[6].arg1,"S6"); strcpy(quad[6].arg2,"-"); strcpy(quad[6].result,"result");
    strcpy(quad[7].op,"+"); strcpy(quad[7].arg1,"j"); strcpy(quad[7].arg2,"1"); strcpy(quad[7].result,"S7");
    strcpy(quad[8].op,"="); strcpy(quad[8].arg1,"S7"); strcpy(quad[8].arg2,"-"); strcpy(quad[8].result,"j");
    strcpy(quad[9].op,"if<="); strcpy(quad[9].arg1,"j"); strcpy(quad[9].arg2,"10"); strcpy(quad[9].result,"3");
    strcpy(quad[10].op,"+"); strcpy(quad[10].arg1,"i"); strcpy(quad[10].arg2,"1"); strcpy(quad[10].result,"S8");
    strcpy(quad[11].op,"="); strcpy(quad[11].arg1,"S8"); strcpy(quad[11].arg2,"-"); strcpy(quad[11].result,"i");
    strcpy(quad[12].op,"if<="); strcpy(quad[12].arg1,"i"); strcpy(quad[12].arg2,"20"); strcpy(quad[12].result,"1");

    for (int i = 0; i < 13; i++) {
        printf("%d: (%s, %s, %s, %s)\n", i,
               quad[i].op, quad[i].arg1, quad[i].arg2, quad[i].result);
    }

    // ---------- TRIPLE ----------
    printf("\n===== TRIPLE =====\n");

    strcpy(triple[0].op,"*"); strcpy(triple[0].arg1,"4"); strcpy(triple[0].arg2,"i");
    strcpy(triple[1].op,"=[]"); strcpy(triple[1].arg1,"x"); strcpy(triple[1].arg2,"(0)");
    strcpy(triple[2].op,"*"); strcpy(triple[2].arg1,"4"); strcpy(triple[2].arg2,"j");
    strcpy(triple[3].op,"=[]"); strcpy(triple[3].arg1,"y"); strcpy(triple[3].arg2,"(2)");
    strcpy(triple[4].op,"*"); strcpy(triple[4].arg1,"(1)"); strcpy(triple[4].arg2,"(3)");
    strcpy(triple[5].op,"+"); strcpy(triple[5].arg1,"result"); strcpy(triple[5].arg2,"(4)");
    strcpy(triple[6].op,"="); strcpy(triple[6].arg1,"(5)"); strcpy(triple[6].arg2,"-");

    for (int i = 0; i < 7; i++) {
        printf("%d: (%s, %s, %s)\n", i,
               triple[i].op, triple[i].arg1, triple[i].arg2);
    }

    // ---------- INDIRECT TRIPLE ----------
    printf("\n===== INDIRECT TRIPLE =====\n");

    for (int i = 0; i < 7; i++) {
        indirect[i] = i;
        printf("Pointer[%d] -> %d\n", i, indirect[i]);
    }

    // ---------- BACKPATCHING ----------
    printf("\n===== BACKPATCHING =====\n");

    int trueList[10], falseList[10];
    int tCount = 0, fCount = 0;

    // Step 1: generate conditional
    int i1 = emit("if i < j goto ");
    int i2 = emit("goto ");

    trueList[tCount++] = i1;
    falseList[fCount++] = i2;

    // Step 2: true block
    int label1 = instr;
    emit("x = y + z");

    // Step 3: false block
    int label2 = instr;
    emit("x = y - z");

    // Step 4: backpatch
    backpatch(trueList, tCount, label1);
    backpatch(falseList, fCount, label2);

    // Print TAC
    for (int i = 0; i < instr; i++) {
        printf("%d: %s\n", i, tac[i]);
    }

    return 0;
}
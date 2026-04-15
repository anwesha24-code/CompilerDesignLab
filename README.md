# Algorithms
## Lexical Analyser
1. make a String[] token with if else char int float double while for
2. keep taking line input as long as it is not # and keep adding it to input separated by space
3. split input String by "\\s+|(?=[=+\\-*/;,()])|(?<=[=+\\-*/;,()])"
4. Initialise a String HashSet as symbol table(add only identifiers there)
5. initialise idCount, litCount, opCount, puncCount, keyCount
6. traverse through tokens
    6.1. If is empty continue
    6.2. If Keyword increase counter
    6.3. If identifier increase counter and add token to symbolTable
    6.4. If digit "\\d+" increase counter
    6.5. If operator "[+\\-*/=]" increase counter
    6.6. If punctuation "[;,()]" increase counter
7. Print everything from the symbol table

## DFA 
1. Take number of states input
2. Take number of alphabets input make symbol array and take all aphabets input
3. Make a transition table [states][alphabets] and take inputs
4. Take input start state, no. of final states
5. Take input of all final states in array
6. Take input string
7. Store current=start, valid=true
8. Traverse over each character of input string
    8.1. find index in symbols array where char is present and store in index
    8.2. if index not found then valid=false and break error symbol
    8.3. next = transition[current][index]
    8.4. if next out of range of states valid=false and error transition break
    8.5. print current,character = next
    8.6 current=next
9. if valid print final state current, check if the final state found is present in our list of final states if so accepted=true and break

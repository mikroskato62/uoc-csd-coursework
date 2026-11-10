/*
- Petrakis Rafail Ioannis - csd5748
- UoC CSD HY255 Assignment #1: Translate Program
- main.c: Unix filter (ISO8859-7 -> ISO8859-1)
*/

#include <stdio.h>
#include <stdlib.h>

/* All the declaration; consts, types (FSM), functions, functions array ... */

#define TONOS "\'"
#define DIALYTIKA "\""

/* [ No globals & short line length (<85) --> ok ] */

typedef enum {
    STATE_DEFAULT,
    STATE_M_UPPER,
    STATE_M_LOWER,
    STATE_N_UPPER,
    STATE_N_LOWER
} State;

void initMappings(const char * mapArray[]);
void initUpperMappings1(const char * mapArray[]);
void initLowerMappings1(const char * mapArray[]);
void initUpperMappings2(const char * mapArray[]);
void initLowerMappings2(const char * mapArray[]);
void initUpperMappings3(const char * mapArray[]);
void initLowerMappings3(const char * mapArray[]);

void translateChar(int currentChar, const char * mapArray[], State * currentState);
void actionsHelper(int currentChar, const char * mapArray[], State * currentState,
                   int targetUpper, int targetLower, const char * matchChar,
                   const char * unmatchChar);

void actionDefault(int currentChar, const char * mapArray[], State * currentState);
void  actionUpperM(int currentChar, const char * mapArray[], State * currentState);
void  actionLowerM(int currentChar, const char * mapArray[], State * currentState);
void  actionUpperN(int currentChar, const char * mapArray[], State * currentState);
void  actionLowerN(int currentChar, const char * mapArray[], State * currentState);

typedef void (*StateHandler)(int, const char **, State *);
StateHandler stateArray[] =
{
    actionDefault,
    actionUpperM,
    actionLowerM,
    actionUpperN,
    actionLowerN
};

/*
Comment:    Initializes the translation mappings, reads characters one by one,
             dispatches them to the translation engine. After the loop, it handles
             any pending characters remained in the FSM buffer at the end of stream.
Parameters: None (void).
Returns:    0 upon successful completion, otherwise -1.
Streams:    Standard Input (reads via getchar), Standard Output (prints results via
             printf or putchar).
*/
int main (void)
{
        int currentChar;
	const char * mappings[256] = { NULL };
	State currentState = STATE_DEFAULT;

        initMappings(mappings);

	while ((currentChar = getchar()) != EOF) 
	      translateChar(currentChar, mappings, &currentState);

	if      (currentState == STATE_M_UPPER) printf("M");
        else if (currentState == STATE_M_LOWER) printf("m");
        else if (currentState == STATE_N_UPPER) printf("N");
        else if (currentState == STATE_N_LOWER) printf("n");

        return 0;
}

/*
Comment:    Calls every sub-function below for all the rules/steps #1, #2 and #3
             using ISO-8859-7 hex values to avoid UTF-8 warnings.
Parameters: mapArray (array of strings to store character mappings).
Returns:    None.
Streams:    None.
*/
void initMappings (const char * mapArray[])
{
	initUpperMappings1(mapArray); initLowerMappings1(mapArray);
	initUpperMappings2(mapArray); initLowerMappings2(mapArray);
	initUpperMappings3(mapArray); initLowerMappings3(mapArray);
}
void initUpperMappings1 (const char * mapArray[])
{
        mapArray[0xC1] = "A";  /* Α */  mapArray[0xC2] = "V";  /* Β */
	mapArray[0xC3] = "G";  /* Γ */  mapArray[0xC4] = "D";  /* Δ */
        mapArray[0xC5] = "E";  /* Ε */  mapArray[0xC6] = "Z";  /* Ζ */
        mapArray[0xC7] = "H";  /* Η */  mapArray[0xC8] = "8";  /* Θ */
        mapArray[0xC9] = "I";  /* Ι */  mapArray[0xCA] = "K";  /* Κ */
        mapArray[0xCB] = "L";  /* Λ */  mapArray[0xCC] = "M";  /* Μ */
        mapArray[0xCD] = "N";  /* Ν */  mapArray[0xCE] = "KS"; /* Ξ */
        mapArray[0xCF] = "O";  /* Ο */  mapArray[0xD0] = "P";  /* Π */
        mapArray[0xD1] = "R";  /* Ρ */  mapArray[0xD3] = "S";  /* Σ */
        mapArray[0xD4] = "T";  /* Τ */  mapArray[0xD5] = "Y";  /* Υ */
        mapArray[0xD6] = "F";  /* Φ */  mapArray[0xD7] = "X";  /* Χ */
        mapArray[0xD8] = "PS"; /* Ψ */  mapArray[0xD9] = "W";  /* Ω */
}
void initLowerMappings1 (const char * mapArray[])
{
        mapArray[0xE1] = "a";  /* α */  mapArray[0xE2] = "v";  /* β */
        mapArray[0xE3] = "g";  /* γ */  mapArray[0xE4] = "d";  /* δ */
        mapArray[0xE5] = "e";  /* ε */  mapArray[0xE6] = "z";  /* ζ */
        mapArray[0xE7] = "h";  /* η */  mapArray[0xE8] = "8";  /* θ */
        mapArray[0xE9] = "i";  /* ι */  mapArray[0xEA] = "k";  /* κ */
        mapArray[0xEB] = "l";  /* λ */  mapArray[0xEC] = "m";  /* μ */
        mapArray[0xED] = "n";  /* ν */  mapArray[0xEE] = "ks"; /* ξ */
        mapArray[0xEF] = "o";  /* ο */  mapArray[0xF0] = "p";  /* π */
        mapArray[0xF1] = "r";  /* ρ */  mapArray[0xF2] = "s";  /* ς */
        mapArray[0xF3] = "s";  /* σ */  mapArray[0xF4] = "t";  /* τ */
        mapArray[0xF5] = "y";  /* υ */  mapArray[0xF6] = "f";  /* φ */
        mapArray[0xF7] = "x";  /* χ */  mapArray[0xF8] = "ps"; /* ψ */
        mapArray[0xF9] = "w";  /* ω */
}
void initUpperMappings2 (const char * mapArray[])
{
        mapArray[0xB6] = (TONOS "A"); /* Ά */  mapArray[0xB8] = (TONOS "E"); /* Έ */
        mapArray[0xB9] = (TONOS "H"); /* Ή */  mapArray[0xBA] = (TONOS "I"); /* Ί */
        mapArray[0xBC] = (TONOS "O"); /* Ό */  mapArray[0xBE] = (TONOS "Y"); /* Ύ */
        mapArray[0xBF] = (TONOS "W"); /* Ώ */
}
void initLowerMappings2 (const char * mapArray[])
{
        mapArray[0xDC] = ("a" TONOS); /* ά */  mapArray[0xDD] = ("e" TONOS); /* έ */
        mapArray[0xDE] = ("h" TONOS); /* ή */  mapArray[0xDF] = ("i" TONOS); /* ί */
        mapArray[0xFC] = ("o" TONOS); /* ό */  mapArray[0xFD] = ("y" TONOS); /* ύ */
        mapArray[0xFE] = ("w" TONOS); /* ώ */
}
void initUpperMappings3 (const char * mapArray[])
{
        mapArray[0xDA] = ("I" DIALYTIKA); /* Ϊ */
        mapArray[0xDB] = ("Y" DIALYTIKA); /* Ϋ */

        /* The (I TONOS DIALYTIKA) does not exists in this encoding ... */
        /* The (Y TONOS DIALYTIKA) does not exists in this encoding ... */
}
void initLowerMappings3 (const char * mapArray[])
{
        mapArray[0xFA] = ("i" DIALYTIKA);       /* ϊ */
        mapArray[0xFB] = ("y" DIALYTIKA);       /* ϋ */

        mapArray[0xC0] = ("i" TONOS DIALYTIKA); /* ΐ */
        mapArray[0xE0] = ("y" TONOS DIALYTIKA); /* ΰ */
}

/*
Comment:    Dispatches the current character to the appropriate state handler
             function based on the current state of the finite state machine.
Parameters: currentChar (the input byte), mapArray (translation table),
             currentState (pointer to the current FSM state).
Returns:    None.
Streams:    None.
*/
void translateChar (int currentChar, const char * mapArray[], State * currentState)
{
    stateArray[*currentState](currentChar, mapArray, currentState);
}

/*
Comment:    Handles the logic for character combinations (rule/step #4). Prints the
             result or pending character and re-processes the current accordingly.
Parameters: currentChar, mapArray, currentState, targetUpper/Lower (the characters
             to look for), matchChar (output if found), unmatchChar (output if not).
Returns:    None.
Streams:    Standard Output (prints the resulting latin characters).
*/
void actionsHelper (int currentChar, const char * mapArray[], State * currentState,
                    int targetUpper, int targetLower, const char * matchChar,
		    const char * unmatchChar)
{
    if (currentChar == targetUpper || currentChar == targetLower)
    {
        printf("%s", matchChar);
        *currentState = STATE_DEFAULT;
    }
    else
    {
        printf("%s", unmatchChar);
        *currentState = STATE_DEFAULT;
        translateChar(currentChar, mapArray, currentState);
    }
}
void actionDefault (int currentChar, const char * mapArray[], State * currentState)
{
        if      (currentChar == 0xCC) { *currentState = STATE_M_UPPER; } /* Μ */
        else if (currentChar == 0xEC) { *currentState = STATE_M_LOWER; } /* μ */
        else if (currentChar == 0xCD) { *currentState = STATE_N_UPPER; } /* Ν */
        else if (currentChar == 0xED) { *currentState = STATE_N_LOWER; } /* ν */
        else
        {
	     /* The rule/step #5 ... */
             if (mapArray[currentChar] != NULL) printf("%s", mapArray[currentChar]);
             else putchar(currentChar);
        }
}
void actionUpperM(int currentChar, const char * mapArray[], State * currentState)
{ actionsHelper(currentChar, mapArray, currentState, 0xD0, 0xF0, "B", "M"); } /* Π ... π */
void actionLowerM(int currentChar, const char * mapArray[], State * currentState)
{ actionsHelper(currentChar, mapArray, currentState, 0xD0, 0xF0, "b", "m"); } /* Π ... π */
void actionUpperN(int currentChar, const char * mapArray[], State * currentState)
{ actionsHelper(currentChar, mapArray, currentState, 0xD4, 0xF4, "D", "N"); } /* Τ ... τ */
void actionLowerN(int currentChar, const char * mapArray[], State * currentState)
{ actionsHelper(currentChar, mapArray, currentState, 0xD4, 0xF4, "d", "n"); } /* Τ ... τ */

/* [ https://www.csd.uoc.gr/~hy255/as1/ ] */
/* [           + ReadMe.md +            ] */
/* [ ------------ The End ------------- ] */

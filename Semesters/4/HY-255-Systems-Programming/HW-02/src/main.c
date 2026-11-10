/*
@author Petrakis Rafail Ioannis - csd5748
@note UoC-CSD-HY255
@version Assignment2: A String Module.
@file main.c
@brief The main program to test the mystring module functions...
*/

#include <stdio.h>
#include <stdlib.h>
#include "mystring.h"

/*
 * @brief The function main.
 * @return 0 if successful, 1 otherwise.
 * @note Testing all other files...
*/
int main (void)
{
	const char test_str1[] = "Hello World!";
        const char test_str2[] = "";
	char copy_buffer[50];

	printf("--------------- [ Starting - csd5748 ] ---------------\n");

        printf("[#1] Testing ms_length:\n");
        printf("- Length of '%s': %lu\n", test_str1, (unsigned long)ms_length(test_str1));
        printf("- Length of 'empty' string: %lu\n", (unsigned long)ms_length(test_str2));

    	printf("[#2] Testing ms_copy:\n");
        ms_copy(copy_buffer, test_str1);
    	printf("- Copied string: '%s'\n", copy_buffer);
        ms_copy(copy_buffer, test_str2);
        printf("- Copied empty string: '%s'\n", copy_buffer);

	printf("[#3] Testing ms_ncopy:\n");
	ms_ncopy(copy_buffer, test_str1, 5);
	copy_buffer[5] = '\0';
	printf("- Copied 5 chars of '%s': '%s'\n", test_str1, copy_buffer);
	ms_ncopy(copy_buffer, "Hi", 10);
	printf("- Copied short string with padding: '%s'\n", copy_buffer);
	ms_ncopy(copy_buffer, test_str1, 0);
	printf("- First char after n=0 copy: '%c'\n", copy_buffer[0]);

	printf("[#4] Testing ms_concat:\n");
        ms_copy(copy_buffer, "Hello");
        printf("- Before concat: '%s'\n", copy_buffer);
        ms_concat(copy_buffer, " World!");
        printf("- After concat: '%s'\n", copy_buffer);
        ms_concat(copy_buffer, test_str2);
        printf("- After empty string concat: '%s'\n", copy_buffer);

	printf("[#5] Testing ms_nconcat:\n");
        ms_copy(copy_buffer, "Start");
        printf("- Before nconcat: '%s'\n", copy_buffer);
        ms_nconcat(copy_buffer, " - Middle - End", 9);
        printf("- After appending 9 chars: '%s'\n", copy_buffer);
        ms_nconcat(copy_buffer, " - Finish", 50);
        printf("- After appending short string (n=50): '%s'\n", copy_buffer);
        ms_nconcat(copy_buffer, "INVISIBLE", 0);
        printf("- After appending 0 chars: '%s'\n", copy_buffer);

	printf("[#6] Testing ms_compare:\n");
	printf("- Compare 'Same1' vs 'Same1': %d\n", ms_compare("Same1", "Same1"));
	printf("- Compare 'After' vs 'Before': %d\n", ms_compare("After", "Before"));
	printf("- Compare 'ZYX' vs 'ABC': %d\n", ms_compare("ZYX", "ABC"));

	printf("[#7] Testing ms_ncompare:\n");
	printf("- Compare 'Test' vs 'Test' (n=2): %d\n", ms_ncompare("Test", "Test", 2));
	printf("- Compare 'ABCDE' vs 'ABCDEFG' (n=6): %d\n", ms_ncompare("ABCDE", "ABCDEFG", 6));
	printf("- Compare '123' vs '321' (n=0): %d\n", ms_ncompare("123", "321", 0));

	printf("[#8] Testing ms_search:\n");
	printf("- Search 'World' in 'Hello World!': '%s'\n", ms_search("Hello World!", "World"));
	if (ms_search("Hello World!", "Earth") == NULL) { printf("- Search 'Earth' in 'Hello World!': NULL (Not found)\n"); }
	printf("- Search empty string in 'Hello World!': '%s'\n", ms_search("Hello World!", ""));

	printf("--------------- [ The  End - csd5748 ] ---------------\n");

	return 0;
}

/* --- The End --- */

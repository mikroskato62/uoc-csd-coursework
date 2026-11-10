/*
@author Petrakis Rafail Ioannis - csd5748
@note UoC-CSD-HY255
@version Assignment2: A String Module.
@file: mystring_ptrs.c
@brief Pointer-based implementation of mystring module...
*/

#include "mystring.h"
#include <assert.h>

/*
 * @brief The function ms_length (strlen equivalent).
 * @param pcStr A constant array of characters.
 * @return The length of the string pcStr (size_t).
 * @exception It is a checked runtime error for pcStr to be NULL.
 */
size_t ms_length (const char * pcStr)
{
	const char * pcStrEnd = pcStr;
        assert(pcStr != NULL);
        while (*pcStrEnd != '\0') pcStrEnd++;
        return pcStrEnd - pcStr;
}

/*
 * @brief The function ms_copy (strcpy equivalent).
 * @details Copies the input string, including the terminating null byte ('\0'), to the output buffer.
 * @param dest[] An array of characters representing the output.
 * @param src[] An array of characters representing the input.
 * @return A pointer to the destination string dest.
 * @exception It is a checked runtime error for dest or src to be NULL.
 */
char * ms_copy (char * dest, const char * src)
{
	char * original_dest = dest;

        assert(dest != NULL); assert(src != NULL);

    	while (*src != '\0')
	{
             *dest = *src;
             dest++;
             src++;
    	}
    	*dest = '\0';

    	return original_dest;
}

/*
 * @brief The function ms_ncopy (strncpy equivalent).
 * @details Copies up to n characters from src to dest. If the length of src
 *          is less than n, the remainder of dest will be padded with null bytes.
 * @param dest[] An array of characters representing the output.
 * @param src[] An array of characters representing the input.
 * @param n The maximum number of characters to copy (size_t).
 * @return A pointer to the destination string dest.
 * @exception It is a checked runtime error for dest or src to be NULL.
 */
char * ms_ncopy (char * dest, const char * src, size_t n)
{
	size_t i = 0U;
        char * original_dest = dest;

        assert(dest != NULL);
        assert(src != NULL);

        while (i < n && *src != '\0')
        {
             *dest = *src;
             dest++;
             src++;
             i++;
        }
        while (i < n)
        {
             *dest = '\0';
             dest++;
             i++;
        }

        return original_dest;
}

/*
 * @brief The function ms_concat (strcat equivalent).
 * @details Appends the src string to the dest string, overwriting the
 *          terminating null byte ('\0') at the end of dest, and then adds a new one.
 * @param dest[] An array of characters representing the output.
 * @param src[] An array of characters representing the input.
 * @return A pointer to the destination string dest.
 * @exception It is a checked runtime error for dest or src to be NULL.
 */
char * ms_concat (char * dest, const char * src)
{
        char * original_dest = dest;

        assert(dest != NULL);
        assert(src != NULL);

        while (*dest != '\0')
        {
             dest++;
        }
        while (*src != '\0')
        {
             *dest = *src;
             dest++;
             src++;
        }
        *dest = '\0';

        return original_dest;
}

/*
 * @brief The function ms_nconcat (strncat equivalent).
 * @details Appends up to n characters from the src string to the dest string, overwriting
 *          the null byte at the end of dest. A '\0' is always appended to the result.
 * @param dest[] An array of characters representing the output.
 * @param src[] An array of characters representing the input.
 * @param n The maximum number of characters to append (size_t).
 * @return A pointer to the destination string dest.
 * @exception It is a checked runtime error for dest or src to be NULL.
 */
char * ms_nconcat (char * dest, const char * src, size_t n)
{
	size_t i = 0U;
        char * original_dest = dest;

        assert(dest != NULL);
        assert(src != NULL);

        while (*dest != '\0')
        {
             dest++;
        }
        while (i < n && *src != '\0')
        {
             *dest = *src;
             dest++;
             src++;
             i++;
        }
        *dest = '\0';

        return original_dest;
}

/*
 * @brief The function ms_compare (strcmp equivalent).
 * @details Compares the two strings str1 and str2 character by character.
 * @param str1[] A constant array of characters to compare.
 * @param str2[] A constant array of characters to compare against.
 * @return 0 if strings are equal, a negative integer if str1 < str2, or a positive integer if str1 > str2.
 * @exception It is a checked runtime error for str1 or str2 to be NULL.
 */
int ms_compare (const char * str1, const char * str2)
{
        assert(str1 != NULL); assert(str2 != NULL);

        while (*str1 == *str2 && *str1 != '\0')
        {
             str1++;
             str2++;
        }

        return (*str1 - *str2);
}

/*
 * @brief The function ms_ncompare (strncmp equivalent).
 * @details Compares up to n characters of the two strings str1 and str2.
 * @param str1[] A constant array of characters to compare.
 * @param str2[] A constant array of characters to compare against.
 * @param n The maximum number of characters to compare (size_t).
 * @return 0 if strings are equal up to n characters, negative if str1 < str2, or positive if str1 > str2.
 * @exception It is a checked runtime error for str1 or str2 to be NULL.
 */
int ms_ncompare (const char * str1, const char * str2, size_t n)
{
        size_t i = 0U;

        assert(str1 != NULL);
        assert(str2 != NULL);

        while (i < n)
        {
             if (*str1 != *str2 || *str1 == '\0')
             {
                  return (*str1 - *str2);
             }
             str1++;
             str2++;
             i++;
        }

        return 0;
}

/*
 * @brief The function ms_search (strstr equivalent).
 * @details Locates the first occurrence of the string needle in the string haystack. If needle empty, returns haystack.
 * @param haystack[] The constant string to be scanned.
 * @param needle[] The constant string to be searched for.
 * @return A pointer to the beginning of the located substring, or NULL if the substring is not found.
 * @exception It is a checked runtime error for haystack or needle to be NULL.
 */
char * ms_search (const char * haystack, const char * needle)
{
        const char * h_ptr, * n_ptr;

        assert(haystack != NULL); assert(needle != NULL);

        if (*needle == '\0')
	{
	     return (char*) haystack;
	}

        while (*haystack != '\0')
        {
             h_ptr = haystack;
             n_ptr = needle;

             while (*h_ptr == *n_ptr && *h_ptr != '\0')
             {
                  h_ptr++;
                  n_ptr++;
             }
             if (*n_ptr == '\0')
             {
                  return (char*) haystack;
             }

             haystack++;
        }

        return NULL;
}

/* --- The End --- */

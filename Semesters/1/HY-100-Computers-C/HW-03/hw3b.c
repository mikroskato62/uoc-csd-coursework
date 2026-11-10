// UOC - CSD - HY100 - Fall2024 - csd5748 - :)

#include <stdio.h>
#include <string.h>

#define maxwords 30
#define maxlen (20 + 1)

int main(void)
{
    int N, palindrome = 1;
    char words[maxwords][maxlen], combined[maxwords * maxlen];

    scanf("%d", &N);

    for (int i = 0; i < N; i++)
    {
        scanf("%s", words[i]);
    }

    combined[0] = '\0';
    for (int i = 0; i < N; i++)
    {
        strcat(combined, words[i]);
    }

    int len = strlen(combined);
    for (int i = 0; i < (len / 2); i++)
    {
        if (combined[i] != combined[len - 1 - i])
        {
            palindrome = 0;
            break;
        }
    }

    if (palindrome == 1)
    {
        printf("Input is a palindrome\n");
    }
    else
    {
        printf("Input is not a palindrome\n");
    }

    return 0;
}

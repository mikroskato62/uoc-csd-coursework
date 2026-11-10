// UOC - CSD - HY100 - Fall2024 - csd5748 - :)

#include <stdio.h>
#include <string.h>

#define maxwords 30
#define maxlen (20 + 1)

int main(void)
{
    int N, count[maxwords] = {0}, maxindex = 0, maxcount = 0;
    char words[maxwords][maxlen];

    scanf("%d", &N);

    for (int i = 0; i < N; i++)
    {
        scanf("%s", words[i]);
    }

    for (int i = 0; i < N; i++)
    {
        for (int j = i + 1; j < N; j++)
        {
            if (strcmp(words[i], words[j]) == 0)
            {
                count[i] = count[i] + 1;
            }
        }
    }

    for (int i = 0; i < N; i++)
    {
        if (count[i] > maxcount)
        {
            maxcount = count[i];
        }
    }

    for (int i = 0; i < N; i++)
    {
        if (count[i] == maxcount)
        {
            printf("%s\n", words[i]);
        }
    }

    return 0;
}

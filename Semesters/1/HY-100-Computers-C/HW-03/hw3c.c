// UOC - CSD - HY100 - Fall2024 - csd5748 - :)
// [Passed] ---> Wrong Idea (sum = 45) ---> :(

#include <stdio.h>
#define size (9)

int main(void)
{
    int numbers[size][size] = {0};
    int invalidrows[size] = {0}, invalidcols[size] = {0}, invalidblocks[size] = {0};

    // Asking for the numbers.
    for (int i = 0; i < size; i++)
    {
        for (int j = 0; j < size; j++)
        {
            scanf("%d", &numbers[i][j]);
        }
    }

    // Checking rows firstly.
    for (int i = 0; i < size; i++)
    {
        int sum = 0;
        for (int j = 0; j < size; j++)
        {
            sum += numbers[i][j];
        }

        if (sum != (1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9))
        {
            invalidrows[i] = 1;
        }
    }

    // Checking columns secondly.
    for (int j = 0; j < size; j++)
    {
        int sum = 0;
        for (int i = 0; i < size; i++)
        {
            sum += numbers[i][j];
        }

        if (sum != (1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9))
        {
            invalidcols[j] = 1;
        }
    }

    // Checking blocks lastly.
    for (int blockrow = 0; blockrow < size; blockrow += 3)
    {
        for (int blockcol = 0; blockcol < size; blockcol += 3)
        {
            int sum = 0;
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    sum += numbers[blockrow + i][blockcol + j];
                }
            }

            if (sum != (1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9))
            {
                int block = ((blockrow / 3) * 3 + (blockcol / 3));
                invalidblocks[block] = 1;
            }
        }
    }

    // Printing the outputs.
    int valid = 0;
    for (int i = 0; i < size; i++)
    {
        if (invalidrows[i] == 1 || invalidcols[i] == 1 || invalidblocks[i] == 1)
        {
            valid = 1;
            break;
        }
    }

    if (valid == 0)
    {
        printf("OK\n");
    }
    else
    {
        printf("Invalid rows:");
        for (int i = 0; i < size; i++)
        {
            if (invalidrows[i] == 1)
            {
                printf(" %d", (i + 1));
            }
        }

        printf("\nInvalid columns:");
        for (int i = 0; i < size; i++)
        {
            if (invalidcols[i] == 1)
            {
                 printf(" %d", (i + 1));
            }
        }

        printf("\nInvalid blocks:");
        for (int i = 0; i < size; i++)
        {
            if (invalidblocks[i] == 1)
            {
                printf(" %d", (i + 1));
            }
        }
        printf("\n");
    }

    return 0;
}


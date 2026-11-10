// UOC - CSD - HY100 - Fall2024 - csd5748 - :)

#include<stdio.h>
#include<stdlib.h>
#include<time.h>


typedef struct {
    int x;
    int y;
} stoivat;


void eisagogi(stoivat, int *, int, int, int, int);
void exagogi(stoivat, int *);

void labirinthos(stoivat, int *, char, int, int);
void printarray(char, int, int);



int main(void)
{
        srand(time(NULL));

        int N, M;
        scanf("%d", &N);
        scanf("%d", &M);

        if (N < 3 || M < 3) return 1;
        if (N % 2 == 0) N = N - 1;
        if (M % 2 == 0) M = M - 1;

        stoivat prev[N * M];
        int top = -1;

        char *blocks = (char *)malloc((N * M) * sizeof(char));
        if (blocks == NULL) return 1;

        for (int i = 0; i <= N - 1; i++)
        {
                for (int j = 0; j <= M - 1; j++)
                {
                        blocks[i * M + j] = 'X';
                }
        }
        blocks[0 * M + 1] = ' ';
        blocks[1 * M + 1] = ' ';

        eisagogi(prev, &top, N, M, 1, 1);
        labirinthos(prev, &top, blocks, N, M);
        printarray(blocks, N, M);

        free(blocks);
        return 0;
}



void eisagogi(stoivat prev[], int *top, int N, int M, int x, int y)
{
        if (*top < N * M - 1)
        {
                (*top)++;
                prev[*top].x = x;
                prev[*top].y = y;
        }
        return;
}


void exagogi(stoivat prev[], int *top)
{
        if (*top >= 0) (*top)--;
        return;
}


void labirinthos(stoivat prev[], int *top, char blocks[], int N, int M)
{
        int x = prev[*top].x;
        int y = prev[*top].y;
        int check[4] = {0, 0, 0, 0};

        if (x >= 3)
        {
                if (blocks[(x - 1) * M + y] == 'X' && blocks[(x - 2) * M + y] == 'X') check[0] = 1;
        }

        if (x <= N - 3)
        {
                if (blocks[(x + 1) * M + y] == 'X' && blocks[(x + 2) * M + y] == 'X') check[1] = 1;
        }

        if (y <= M - 3)
        {
                if (blocks[x * M + (y + 1)] == 'X' && blocks[x * M + (y + 2)] == 'X') check[2] = 1;
        }

        if (y >= 3)
        {
                if (blocks[x * M + (y - 1)] == 'X' && blocks[x * M + (y - 2)] == 'X') check[3] = 1;
        }

        int count = 0;
        for (int i = 0; i < 4; i++) if (check[i] == 1) count++;

        if (count != 0)
        {
                int randomnumber;
                do{
                        randomnumber = rand() % (4);
                }
                while (check[randomnumber] != 1);
                int direction = randomnumber;

                if (direction == 0)
                {
                        blocks[(x - 1) * M + y] = ' ';
                        blocks[(x - 2) * M + y] = ' ';
                        x = x - 2;
                        eisagogi(prev, top, N, M, x, y);
                        labirinthos(prev, top, blocks, N, M);
                }
                else if (direction == 1)
                {
                        blocks[(x + 1) * M + y] = ' ';
                        blocks[(x + 2) * M + y] = ' ';
                        x = x + 2;
                        eisagogi(prev, top, N, M, x, y);
                        labirinthos(prev, top, blocks, N, M);
                }
                else if (direction == 2)
                {
                        blocks[x * M + (y + 1)] = ' ';
                        blocks[x * M + (y + 2)] = ' ';
                        y = y + 2;
                        eisagogi(prev, top, N, M, x, y);
                        labirinthos(prev, top, blocks, N, M);
                }
                else if (direction == 3)
                {
                        blocks[x * M + (y - 1)] = ' ';
                        blocks[x * M + (y - 2)] = ' ';
                        y = y - 2;
                        eisagogi(prev, top, N, M, x, y);
                        labirinthos(prev, top, blocks, N, M);
                }
        }
        else if (count == 0)
        {
                exagogi(prev, top);
                if (*top == -1) return;
                labirinthos(prev, top, blocks, N, M);
        }
        return;
}


void printarray(char blocks[], int N, int M)
{
        for (int i = 0; i < N; i++)
        {
                for (int j = 0; j < M; j++)
                {
                        printf("%c", blocks[i * M + j]);
                }
                printf("\n");
        }
        return;
}

// The End! Thanks :) 

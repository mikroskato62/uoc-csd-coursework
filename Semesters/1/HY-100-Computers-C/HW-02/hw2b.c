// UOC - CSD - HY100 - Fall2024 - csd5748 - :)

#include <stdio.h>

int main(void)
{
	int N, middle, counter;

	scanf("%d", &N);

	if (N <= 3) return 1;

	middle = N / 2 - 1;
	counter = middle - 1;

	for (int i = 0; i < N; i++)
	{
		if (i == middle)
		{
			printf("**\n");
		}
		else if (i < middle)
		{
			printf("*");
			for (int j = 0; j <= counter; j++)
			{
				printf(" ");
			}
			printf("*\n");
			counter = counter - 1;
		}
		else
		{
			counter = counter + 1;
			printf("*");
			for (int j = 0; j <= counter; j++)
			{
				printf(" ");
			}
			printf("*\n");
		}
	}
		return 0;
}

// UOC - CSD - HY100 - Fall2024 - csd5748 - :)

#include <stdio.h>

int main(void)
{
	int N, first = 0, second = 1, next, i;

	scanf("%d", &N);

	if (N <= 0) return 1;

	for (i = 1; i <= N; i++)
	{
		printf("%ld\n", first);
		next = first + second;
		first = second;
		second = next;
	}

	return 0;
}


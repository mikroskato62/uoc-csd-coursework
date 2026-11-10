// UOC - CSD - HY100 - Fall2024 - csd5748 - :)

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*
[Read If You Have Time] The idea for the following struct:
This struct represents a single linked list that saves the data for the file's movies and contains:
a dynamic title (name), the production year (year), the revenue it has so far (lefta) and a pointer
that points to the next (struct's) movie in our list (*next ptr). Also, i gave it a abbreviation of
'moviet', so moviet == struct movie == movie_t (i don't like writting underscores (_) sorry :D).
*/

typedef struct movie {
    char *name;
    int year;
    float lefta;
    struct movie *next;
} moviet;

/*
[Read If You Have Time] The idea for the following functions:
These functions do not need to return anything, but they may change the values of 'head' and 'tail'.
The following (9) functions perform the operations of the program, which are the following (in order):
Reading the file (creating list), Reseting the file, Inserting a new movie, Deleting an existing movie,
Printing all the movies, Printing all movies produced after a specific year, Adding money to a movie,
Sorting alphabetically the movies (with bubblesort), Over-Writing the file with new data from the list.
[For Creator] No * means variable copy - One * means variable pointer - Two ** means variable itself !
*/

void readfile(const char *filename, moviet **head, moviet **tail);
void resetfile(const char *filename, moviet **head, moviet **tail);
void addnewmovie(const char *filename, const char *movie, const int year, float income);
void deletemovie(const char *filename, const char *movie, moviet **head, moviet **tail);
void printall(moviet *head);
void printafter(moviet *head, int year);
void moremoney(const char *filename, moviet *head, const char *movie, float income);
void bubblesort(const char *filename, moviet *head);
void updatefile(const char *filename, moviet *head);

/*
[Please Read] The idea for the following main function (*):
This function may use user's command line parameters (argc) that saves (*argv) or converts (atoi, atof)...
At the start, we read the file's content, create a single linked list, and determine the head and the tail of it.
After that, we check the arguments from the command line and we call the corresponding function, otherwise we exit.
Lastly, before exiting (correctly), we open the file and write everything from our list, while freeing memory used.
[Edit] This program checks the list one by one, so only the first movie will be affected in case of the same name!
*/

int main(int argc, char *argv[])
{
        const char *filename = "test.dat";
        moviet *head = NULL, *tail = NULL;

        readfile(filename, &head, &tail);

        if (argc > 1)
        {
                if (strcmp(argv[1], "--reset") == 0)
                {
                        if (argc != 2) return 1;
                        resetfile(filename, &head, &tail);
                }
                else if (strcmp(argv[1], "--insert") == 0)
                {
                        if (argc != 5) return 1;
                        const char *movie = argv[2];
                        const int year = atoi(argv[3]);
                        float income = atof(argv[4]);
                        addnewmovie(filename, movie, year, income);
                        return 0;
                }
                else if (strcmp(argv[1], "--delete") == 0)
                {
                        if (argc != 3) return 1;
                        const char *movie = argv[2];
                        deletemovie(filename, movie, &head, &tail);
                        return 0;
                }
                else if (strcmp(argv[1], "--list-all") == 0)
                {
                        if (argc != 2) return 1;
                        printall(head);
                        return 0;
                }
                else if (strcmp(argv[1], "--list-after") == 0)
                {
                        if (argc != 3) return 1;
                        int year = atoi(argv[2]);
                        printafter(head, year);
                        return 0;
                }
                else if (strcmp(argv[1], "--add") == 0)
                {
                        if (argc != 4) return 1;
                        const char *movie = argv[2];
                        float income = atof(argv[3]);
                        moremoney(filename, head, movie, income);
                        return 0;
                }
                else if (strcmp(argv[1], "--sort") == 0)
                {
                        if (argc != 2) return 1;
                        bubblesort(filename, head);
                        return 0;
                }
        }
        updatefile(filename, head);
        return 0;
}

/*
[Please Read] The idea for the following 'readfile' function:
This function requires the name of the file and the (changable) head and tail of the list which are NULL.
Firstly, we try to open with read (r) the binary (b) file, if this fails we try to create the file or exit.
If we open the file, we loop through its content until the end, each time we read one by one the values of
each element and create a new struct for the list (checking if empty or) and updating the head and the tail.
If anything fails for any reason, we free any allocated memory, close the file and terminate the program.
*/

void readfile(const char *filename, moviet **head, moviet **tail)
{
        int frouros = 0;

        FILE *file = fopen(filename, "rb");
        if (file == NULL)
        {
                frouros = 1;
                resetfile(filename, head, tail);
        }

        if (frouros == 1)
        {
                file = fopen(filename, "rb");
                if (file == NULL) exit(1);
        }

        int True = 1;
        while(True)
        {
                int namelen;
                if (fread(&namelen, sizeof(int), 1, file) != 1) break;

                char *name = (char*)malloc(namelen * sizeof(char));
                if (name == NULL) break;

                if (fread(name, sizeof(char), namelen, file) != namelen)
                {
                        free(name);
                        break;
                }

                int year;
                if (fread(&year, sizeof(int), 1, file) != 1)
                {
                        free(name);
                        break;
                }

                float lefta;
                if (fread(&lefta, sizeof(float), 1, file) != 1)
                {
                        free(name);
                        break;
                }


                moviet *new = (moviet*)malloc(sizeof(moviet));
                if (new == NULL)
                {
                        free(name);
                        break;
                }

                new -> name = name;
                new -> year = year;
                new -> lefta = lefta;
                new -> next = NULL;

                if (*tail != NULL)
                {
                        (*tail) -> next = new;
                }
                else
                {
                        *head = new;
                }

                *tail = new;
        }
        fclose(file);
        return;
}

/*
[Please Read] The idea for the following 'updatefile' function:
This function requires the (unchangable) name of the file and the (unchangable) head of the list.
Firstly, we try to open with write (w) the binary (b) file, if this fails we terminate the program.
If we open the file, we loop through the single linked list from the begining, each time we write
one by one the values of each element of the struct and then move to the next one, until the end.
When we are done, we close the file and return!
*/

void updatefile(const char *filename, moviet *head)
{
        FILE *file = fopen(filename, "wb");
        if (file == NULL) exit(1);

        moviet *current = head;
        while (current != NULL)
        {
                int namelen = strlen(current -> name) + 1;
                fwrite(&namelen, sizeof(int), 1, file);
                fwrite(current -> name, sizeof(char), namelen, file);
                fwrite(&(current -> year), sizeof(int), 1, file);
                fwrite(&(current -> lefta), sizeof(float), 1, file);
                current = current -> next;
        }

        fclose(file);
        return;
}

/*
[Read If You Have Time] The idea for the following 'resetfile' function (--reset):
This function requires the (unchangable) name of the file and the (changable) head and tail of the list that we make NULL.
Firstly, we open with write (w) the binary (b) file and if it fails, we exit, otherwise we close the file since it reseted.
Then, we have to free the whole single linked list from our memory, so we loop from the start (head) until the end (NULL).
When we are done, we return!
*/

void resetfile(const char *filename, moviet **head, moviet **tail)
{
        FILE *file = fopen(filename, "wb");
        if (file == NULL) exit(1);
        fclose(file);

        moviet *current = *head;
        moviet *temp;

        while (current != NULL)
        {
                temp = current;
                current = current -> next;
                free(temp -> name);
                free(temp);
        }

        *head = NULL;
        *tail = NULL;

        return;
}

/*
[Read If You Have Time] The idea for the following 'addnewmovie' function (--insert <> <> <>):
This function requires the (unchangable) name of the file, name, year, as well as the lefta.
Firstly, try to open with append (a) the binary (b) file and if it fails, we exit, otherwise:
We calculate the first element that needs to be written (name length.) and then we can write,
one by one, that and the user's inputs. If there is any problem, we close the file and exit.
Morover, there is no need to add it in our list (here). When we are done, we can return back!
*/

void addnewmovie(const char *filename, const char *movie, const int year, float income)
{
        FILE *file = fopen(filename, "ab");
        if (file == NULL) exit(1);

        int namelen = strlen(movie) + 1;

        if (fwrite(&namelen, sizeof(int), 1, file) != 1 ||
            fwrite(movie, sizeof(char), namelen, file) != namelen ||
            fwrite(&year, sizeof(int), 1, file) != 1 ||
            fwrite(&income, sizeof(float), 1, file) != 1)
        {
                fclose(file);
                exit(1);
        }

        fclose(file);
        return;
}

/*
[Read If You Have Time] The idea for the following 'deletemovie' function (--delete <>):
This function requires the (unchangable) name of the file and movie, and the (changable) head and tail of the list.
Firstly, we check if our list is empty. If it is not, we loop through every one of it's structs, and we compare
each name (movie title) with the one we are looking for. When we find it, we change the previous struct's (*next)
(if any) pointer to the pointe (*next) of the next struct (basiclly removing the pointer that point to that movie).
When we are done, we return!
*/

void deletemovie(const char *filename, const char *movie, moviet **head, moviet **tail)
{
        moviet *current = *head;
        moviet *previous = NULL;

        while (current != NULL)
        {
                if (strcmp(current -> name, movie) == 0)
                {
                        if (previous == NULL)
                        {
                                *head = current -> next;
                        }
                        else
                        {
                                previous -> next = current -> next;
                        }

                        if (current == *tail)
                        {
                                *tail = previous;
                        }

                        free(current -> name);
                        free(current);
                        break;
                }
                previous = current;
                current = current -> next;
        }
	updatefile(filename, *head);
        return;
}

/*
[Read If You Have Time] The idea for the following 'printall' function (--list-all):
This function only requires the (unchangable) pointer to the list's head and it does not have return anything.
Firstly, we check if our list is empty. If it is not, we loop through every one of it's structs, and we print(f)
each of the elements (extept from the *next ptr of course), until we reach the end of our list (current == NULL).
-> I chose to print the items in a way that resembles the single linked list rather that one below another...
When we are done, we return!
*/

void printall(moviet *head)
{
        moviet *current = head;

        if (current == NULL)
        {
                printf("NULL\n");
                return;
        }

        while (current != NULL)
        {
                printf("%s ", current -> name);
                printf("%d ", current -> year);
                printf("%.5f ", current -> lefta);
                printf("-> ");
                current = current -> next;
        }
        printf("%s\n", "NULL");
        return;
}

/*
[Read If You Have Time] The idea for the following 'printafter' function (--list-after <>):
This function requires the (unchangable) pointer to the list's head and the year we need to compare every other to.
Firstly, we check if our list is empty. If it is not, we loop through every one of it's structs, and we compare the
year to the one that the user inputed, until the end. If its greater (>) we can print(f) the movie (and it's data).
-> I chose to print the items in a way that does not resembles the single linked list (it was not possible anyway).
When we are done, we return!
*/

void printafter(moviet *head, int year)
{
        moviet *current = head;

        if (current == NULL) return;

        while (current != NULL)
        {
                if (current -> year > year)
                {
                        printf("%s %d %.5f \n", current -> name, current -> year, current -> lefta);
                }
                current = current -> next;
        }
        return;
}

/*
[Read If You Have Time] The idea for the following 'moremoney' function (--add <> <>):
This function requires the (unchangable) file name, lists head, movie name and the income (extra lefta) - no returns.
Basically, we start a loop through every struct of our list, from the begining, that compares each movie name element
to the one that the user inputed. If we find the correct one, we add to the struct's lefta element the 'income' that
the user inputed as well. When we are done, we break early the loop and can return!
*/

void moremoney(const char *filename, moviet *head, const char *movie, float income)
{
        moviet *current = head;

        while (current != NULL)
        {
                if (strcmp(current -> name, movie) == 0)
                {
                        current -> lefta = current -> lefta + income;
                        break;
                }
                current = current -> next;
        }
	updatefile(filename, head);
        return;
}

/*
[Please Read] The idea for the following 'bubblesort' function (--sort):
This function requires the (unchangable) file name and list's head. It does not have to return anything.
Firstly, we check if our list is empty, if so we cannot change anything. If not, however, we start a loop
that basically keeps going if in its last rep, something changed (True var). Now, inside this loop, we
check if the current's struct name element is alphabetically in order with the next struct's name element.
(we know that a < w). So, if they are not, the swap and the check-flag 'True' becomes 1 (if its 0 we stop).
When we are done (cannot make any more swaps) we can return!
*/

void bubblesort(const char *filename, moviet *head)
{
        if (head == NULL) return;

        moviet *current;
        moviet *next;

        int True = 1;
        while (True)
        {
                True = 0;
                current = head;

                while (current != NULL && current -> next != NULL)
                {
                        next = current -> next;

                        if (strcmp(current -> name, next -> name) > 0)
                        {
                                char *tempname = current -> name;
                                int tempyear = current -> year;
                                float templefta = current -> lefta;

                                current -> name = next -> name;
                                current -> year = next -> year;
                                current -> lefta = next -> lefta;

                                next -> name = tempname;
                                next -> year = tempyear;
                                next -> lefta = templefta;

                                True = 1;
                        }
                        current = current -> next;
                }

                if (True == 0) break;
        }
        updatefile(filename, head);
        return;
}

// The End - Thanks :)

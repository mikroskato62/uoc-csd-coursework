/* [240] Project 1 - Phase 1 & 2 - HY240 - RIP - csd5748 */
/* [!] Please read the "ReadMe.md" file for information. */

// [!] Testing (Outputs): Enabled = 1 & Disabled = 0:
# define TESTING 0

// [P12] Includes (only the required):
# include <stdio.h>
# include <stdlib.h>
# include <string.h>

// [P12] Constants:
# define TITLE_MAX 128
# define NAME_MAX  64
# define LOAN_STATUS_MAX 16
# define MAX_BUFFER 128
# define MAX_HEAP_CAPACITY 64

// [P1] Extern global variable:
int SLOTS;

// [Bonus] Global variables:
int book_count = 0;
int member_count = 0;
int active_loans = 0;
double all_score_avg = 0.0;
int total_scores = 0;

// [P1] Data structures (from library.h):
typedef struct book
{
    int bid;
    int gid;
    char title[TITLE_MAX];
    int sum_scores;
    int n_reviews;
    int avg;
    int lost_flag;

    struct book * prev_in_genre;
    struct book * next_in_genre;
    struct book * next_global;

    // [P2] The book's position inside the "max recommendation heap" (-1 means not entered):
    int heap_pos;
}
book_t;

// [P2] The "AVL Tree" (self-balancing binary search tree):
typedef struct BookNode
{
    char title[TITLE_MAX];
    book_t * book;
    struct BookNode * lc;
    struct BookNode * rc;
    int height;
}
BookIndex_t;

// [P2] The "Max Recommendation Heap" (sorted by book -> avg):
typedef struct RecHeap
{
    int size;
    int capacity;
    book_t * heap[MAX_HEAP_CAPACITY];
}
RecHeap_t;

// [P2] The "Member Activity" single linked list & it's global head pointer:
typedef struct MemberActivity
{
    int sid;
    int loans_count;
    int reviews_count;
    double score_sum;
    struct MemberActivity * next;
    // struct MemberActivity * prev;
}
MemberActivity_t;

// [P1] Data structures (from library.h):
typedef struct loan
{
    int sid;
    int bid;
    char status [LOAN_STATUS_MAX];
    struct loan * next;
}
loan_t;

typedef struct member
{
    int  sid;
    char name[NAME_MAX];

    loan_t loans_head;

    struct member * prev_all;
    struct member * next_all;

    // [P2] A pointer to the "Member Activity" list node (unused):
    // MemberActivity_t * activity;
}
member_t;

typedef struct genre
{
    int  gid;
    char name[NAME_MAX];

    book_t book_sentinel;

    int display_count;
    book_t ** display;

    int slots;
    int lost_count;
    int invalid_count;

    struct genre * next_by_gid;

    // [P2] A pointer to the "AVL Tree" of this genre (unused):
    // BookIndex_t * bookIndex;
}
genre_t;

typedef struct library
{
    genre_t * genres;
    member_t * members;
    book_t * books;

    // [P2] The pointers to the new structs:
    BookIndex_t * root;
    RecHeap_t * recommendations;
    MemberActivity_t * activity;
}
library_t;

// [P1] The (one) global library:
library_t library;

// [P12] Functions (all declarations):
void ReadFile (char[]);
void S (int);
void G (int, char *);
void BK (int, int, char *);
void M (int, char *);
void L (int, int);
void R (int, int, int, char *);
void D (void);
void PG (int);
void PM (int);
void PD (void);
void PS (void);
void F (const char *);
void TOP (int);
void AM (void);
void U (int, char *);
void X (void);
void BF (void);
void InitLibrary (library_t *);
void AddGenre (int, char *);
void SortGenres (void);
genre_t * FindGenre (int);
book_t * AddBook (int, int, const char *);
void ReinsertBook (int, int);
book_t * FindBook (int, int);
void AddMember (int, const char *);
void SortMembers (void);
member_t * FindMember (int);
void LoanBook (member_t *, int, int);
void DeleteLoan (member_t *, loan_t *);
void PrintBooks (void);;
void PrintGenres (void);
void PrintMembers (void);
void PrintLoans (void);

// [P12] Main:
int main (int argc, char * argv[])
{
    printf("[!] Starting \"Library.c\" using \"%s\" ... \n", argv[1]);
    if (TESTING) printf("-------------------------------------------------- \n");

    if (argc > 1)
    {
        InitLibrary (& library);
        ReadFile (argv[1]);
    }
    else
    {
        printf("[Error] No parameters entered - a test file is required ... \n");
    }

    printf("[!] The End :) \n");

    return 0;
}

// [P12] Functions (all implementations):
void ReadFile(char * filename)
{
    FILE * file;
    file = fopen(filename, "r");
    if (file == NULL)
    {
        printf("[Error] The entered file (argument: \"%s\") was not found.\n"
               "[Info] Please enter the name of a file located on the same directory as the executable file (ex. \"test.txt\").\n"
               "[Info] Or enter the absolute path of a file (ex: \"C:\\Users\\User\\Desktop\\test.txt\").\n"
               "[*] Please try again ... \n", filename);
        exit(1);
    }
    if (TESTING) printf("[Ok] Reading the file: %s ... \n", filename);

    // Breaking all lines into parts and running the correct functions one by one:
    char buffer[MAX_BUFFER], cmd[5], str[MAX_BUFFER / 2];
    int n1, n2, n3;
    while (fgets(buffer, sizeof(buffer), file))
    {
        if (buffer[0] == '#' || buffer[0] == '\n' || sscanf(buffer, "%s", cmd) != 1)
            continue;

        if (strcmp(cmd, "S") == 0)
        {
            if (sscanf(buffer, "%*s %d", &n1) == 1)
                S(n1);
        }
        else if (strcmp(cmd, "G") == 0)
        {
            if (sscanf(buffer, "%*s %d \"%[^\"]\"", &n1, str) == 2)
                G(n1, str);
        }
        else if (strcmp(cmd, "BK") == 0)
        {
            if (sscanf(buffer, "%*s %d %d \"%[^\"]\"", &n1, &n2, str) == 3)
                BK(n1, n2, str);
        }
        else if (strcmp(cmd, "M") == 0)
        {
            if (sscanf(buffer, "%*s %d \"%[^\"]\"", &n1, str) == 2)
                M(n1, str);
        }
        else if (strcmp(cmd, "L") == 0)
        {
            if (sscanf(buffer, "%*s %d %d", &n1, &n2) == 2)
                L(n1, n2);
        }
        else if (strcmp(cmd, "R") == 0)
        {
            char score[3], status[5];
            if (sscanf(buffer, "%*s %d %d %s %s", &n1, &n2, score, status) == 4)
            {
                if (strcmp(score, "NA") == 0)
                    n3 = -1;
                else
                    n3 = atoi(score);
                R(n1, n2, n3, status);
            }
        }
        else if (strcmp(cmd, "D") == 0)
        {
            D();
        }
        else if (strcmp(cmd, "PG") == 0)
        {
            if (sscanf(buffer, "%*s %d", &n1) == 1)
                PG(n1);
        }
        else if (strcmp(cmd, "PM") == 0)
        {
            if (sscanf(buffer, "%*s %d", &n1) == 1)
                PM(n1);
        }
        else if (strcmp(cmd, "PD") == 0)
        {
            PD();
        }
        else if (strcmp(cmd, "PS") == 0)
        {
            PS();
        }
        else if (strcmp(cmd, "F") == 0)
        {
            if (sscanf(buffer, "%*s \"%[^\"]\"", str) == 1)
                F(str);
        }
        else if (strcmp(cmd, "TOP") == 0)
        {
            if (sscanf(buffer, "%*s %d", &n1) == 1)
                TOP(n1);
        }
        else if (strcmp(cmd, "AM") == 0)
        {
            AM();
        }
        else if (strcmp(cmd, "U") == 0)
        {
            if (sscanf(buffer, "%*s %d \"%[^\"]\"", &n1, str) == 2)
                U(n1, str);
        }
        else if (strcmp(cmd, "X") == 0)
        {
            X();
        }
        else if (strcmp(cmd, "BF") == 0)
        {
            BF();
        }
        else if (strcmp(cmd, "TEST") == 0)
        {
            if (TESTING)
            {
                printf("-------------------------------------------------- \n");
                PrintBooks(); PrintGenres(); PrintMembers(); PrintLoans();
                printf("-------------------------------------------------- \n");
            }
        }
        else
        {
            printf("[Error] Skipping unknown command read: %s ...\n", cmd);
        }
    }

    fclose(file);
}

// [P2] Helper functions for the AVL:
static int height(BookIndex_t * node) { return (node ? node -> height : 0); } // The height of a node (if node is null, height is 0).
static void updateHeight(BookIndex_t * node) { if (node) node -> height = 1 + ((height(node -> lc) > height(node -> rc)) ? height(node -> lc) : height(node -> rc)); } // Recalculates and updates the height of a node based on its children.
static int balanceFactor(BookIndex_t * node) { return (node ? height(node -> lc) - height(node -> rc) : 0); } // Balance factor = height(left subtree) - height(right subtree) & (positive → left-heavy, negative → right-heavy, zero → balanced).
// [P2] Single right rotation (used for Left-Left or Left-Right after left rotation on child).
static BookIndex_t * rotateRight(BookIndex_t * node1)
{
    BookIndex_t * node2 = node1 -> lc;
    BookIndex_t * temp  = node2 -> rc;
    node2 -> rc = node1;
    node1 -> lc = temp;
    updateHeight(node1);
    updateHeight(node2);
    return node2;
}
// [P2] Single left rotation (same as rotateRight).
static BookIndex_t * rotateLeft(BookIndex_t * node1)
{
    BookIndex_t * node2 = node1 -> rc;
    BookIndex_t * temp  = node2 -> lc;
    node2 -> lc = node1;
    node1 -> rc = temp;
    updateHeight(node1);
    updateHeight(node2);
    return node2;
}
// [P2] Finding the inorder successor (smallest node in a subtree).
static BookIndex_t * inorder(BookIndex_t * node)
{
    BookIndex_t * current = node;
    while (current && current -> lc)
        current = current -> lc;
    return current;
}
// [P2] Recursively free the entire AVL tree (with post-order traversal).
void freeAVLT(BookIndex_t * node)
{
    if (!node) return;
    freeAVLT(node -> lc);
    freeAVLT(node -> rc);
    free(node);
}
// [P2] Insert a new book into the AVL tree while maintaining balance (O(log n) as needed).
BookIndex_t * InsertAVLT(BookIndex_t * root, book_t * book)
{
    // [#1] Standard BST insertion:
    if (root == NULL)
    {
        BookIndex_t * newNode = malloc(sizeof(BookIndex_t));
        if (!newNode) return root;
        strncpy(newNode -> title, book -> title, TITLE_MAX - 1);
        newNode -> title[TITLE_MAX - 1] = '\0';
        newNode -> book = book;
        newNode -> lc = newNode -> rc = NULL;
        newNode -> height = 1;
        return newNode;
    }

    if (strcmp(book -> title, root -> title) < 0)
        root -> lc = InsertAVLT(root -> lc, book);
    else if (strcmp(book -> title, root -> title) > 0)
        root -> rc = InsertAVLT(root -> rc, book);

    // [#2] Update height of the current node after recursive insertion:
    updateHeight(root);

    // [#3] Get balance factor to check if node became unbalanced:
    int balance = balanceFactor(root);

    // [#4] Four cases of imbalance – perform appropriate rotations (Left-Left, Right-Right, Left-Right, Right-Left):
    if (balance > 1 && strcmp(book -> title, root -> lc -> title) < 0)
    {
        return rotateRight(root);
    }
    if (balance < -1 && strcmp(book -> title, root -> rc -> title) > 0)
    {
        return rotateLeft(root);
    }
    if (balance > 1 && strcmp(book -> title, root -> lc -> title) > 0)
    {
        root -> lc = rotateLeft(root -> lc);
        return rotateRight(root);
    }
    if (balance < -1 && strcmp(book -> title, root -> rc -> title) < 0)
    {
        root -> rc = rotateRight(root -> rc);
        return rotateLeft(root);
    }

    // [#5] Return:
    return root;
}
// [P2] Delete a book node identified by its title from the AVL tree while maintaining balance.
BookIndex_t * DeleteAVLT(BookIndex_t * root, const char * title)
{
    // [#1] Safety check:
    if (root == NULL) return root;

    // [#2] Standard BST traversal based on titles:
    if (strcmp(title, root -> title) < 0)
    {
        root -> lc = DeleteAVLT(root -> lc, title);
    }
    else if (strcmp(title, root -> title) > 0)
    {
        root -> rc = DeleteAVLT(root -> rc, title);
    }
    else
    {
        // If there is no left child (only right child):
        if (root -> lc == NULL)
        {
            BookIndex_t * temp = root -> rc;
            free(root);
            return temp;
        }
        // If there is no right child (only left child):
        else if (root -> rc == NULL)
        {
            BookIndex_t * temp = root -> lc;
            free(root);
            return temp;
        }

        // If there are two children, replace with inorder successor and delete it:
        BookIndex_t * successor = inorder(root -> rc);
        strcpy(root -> title, successor -> title);
        root -> book = successor -> book;
        root -> rc = DeleteAVLT(root -> rc, successor -> title);
    }

    // [#3] Update height of the current node after deletion/rotation:
    updateHeight(root);

    // [#4] Rebalance the tree if needed (Left-Left, Left-Right, Right-Right, Right-Left):
    int balance = balanceFactor(root);
    if (balance > 1)
    {
        if (balanceFactor(root -> lc) >= 0)
        {
            return rotateRight(root);
        }
        else
        {
            root -> lc = rotateLeft(root -> lc);
            return rotateRight(root);
        }
    }
    if (balance < -1)
    {
        if (balanceFactor(root -> rc) <= 0)
        {
            return rotateLeft(root);
        }
        else
        {
            root -> rc = rotateRight(root -> rc);
            return rotateLeft(root);
        }
    }

    // [#5] Return:
    return root;
}
// [P2] Search for a title in the AVL tree (O(log n) as needed).
BookIndex_t * SearchAVLT(BookIndex_t * root, const char * title)
{
    BookIndex_t * current = root;

    while (current != NULL)
    {
        int cmp = strcmp(title, current -> title);

        if (cmp == 0) return current;
        else if (cmp > 0) current = current->rc;
        else current = current->lc;
    }

    return NULL;
}

// [P2] Helper functions for the Heap:
// [P2] Maintain heap property by pushing an element down to its correct position:
static void heapify_down(RecHeap_t * h, int idx)
{
    const int True = 1;
    while (True)
    {
        int largest = idx;
        int left = 2 * idx + 1;
        int right = 2 * idx + 2;

        // Check the children (if they exist and are greater - higher avg or same avg + smaller bid):
        if (left < h->size && (h->heap[left]->avg > h->heap[largest]->avg || (h->heap[left]->avg == h->heap[largest]->avg && h->heap[left]->bid < h->heap[largest]->bid))) largest = left;
        if (right < h->size && (h->heap[right]->avg > h->heap[largest]->avg || (h->heap[right]->avg == h->heap[largest]->avg && h->heap[right]->bid < h->heap[largest]->bid))) largest = right;
        if (largest == idx) break;

        // Swap, update and continue:
        book_t * tmp = h -> heap[idx];
        h -> heap[idx] = h -> heap[largest];
        h -> heap[largest] = tmp;
        h -> heap[idx] -> heap_pos = idx;
        h -> heap[largest] -> heap_pos = largest;

        idx = largest;
    }
}
// [P2] Maintain heap property by bubbling an element up to its correct position:
static void heapify_up(RecHeap_t * h, int idx)
{
    while (idx > 0)
    {
        int parent = ((idx - 1) / 2);

        // If current node is greater than its parent swap and update them:
        if (h->heap[idx]->avg > h->heap[parent]->avg || (h->heap[idx]->avg == h->heap[parent]->avg && h->heap[idx]->bid < h->heap[parent]->bid))
        {
            book_t * temp = h -> heap[idx];
            h -> heap[idx] = h -> heap[parent];
            h -> heap[parent] = temp;

            h -> heap[idx] -> heap_pos = idx;
            h -> heap[parent] -> heap_pos = parent;

            idx = parent;
        }
        else
        {
            break;
        }
    }
}
// [P2] Insert a new book into the heap (O(log n) as needed):
void InsertHeap(RecHeap_t * h, book_t * book)
{
    // [#0] If the heap is empty:
    if (library.recommendations == NULL)
    {
        library.recommendations = malloc(sizeof(RecHeap_t));
        if (library.recommendations)
        {
            library.recommendations -> size = 0;
            library.recommendations -> capacity = MAX_HEAP_CAPACITY;
        }
    }
    // [#1] Safety checks:
    if (!h || !book || h -> size >= h -> capacity) return;

    // [#2] If book is not in the heap already:
    if (book -> heap_pos == -1)
    {
        int idx = h -> size;
        h -> heap[idx] = book;
        book -> heap_pos = idx;
        h -> size++;
        heapify_up(h, idx);
        return;
    }

    // [#3] Book already in the heap, sort:
    int idx = book -> heap_pos;
    heapify_up(h, idx);
    heapify_down(h, idx);
}
// [P2] Delete a book from the heap (O(log n) as needed):
void DeleteHeap(RecHeap_t * h, book_t * book)
{
    // [#1] Safety checks firstly:
    if (!h || !book) return;
    if (book -> heap_pos == -1 || book -> heap_pos >= h -> size) return;
    if (h -> heap[book -> heap_pos] != book) return;

    // [#2] If book is at the end:
    int idx = book -> heap_pos;
    int last_idx = h -> size - 1;
    if (idx == last_idx)
    {
        h -> heap[idx] = NULL;
        book -> heap_pos = -1;
        h -> size--;
        return;
    }

    // [#3] Otherwise, find book, remove it, and rearrange:
    book_t * last_book = h -> heap[last_idx];
    h -> heap[idx] = last_book;
    last_book -> heap_pos = idx;
    h -> heap[last_idx] = NULL;
    book -> heap_pos = -1;
    h -> size--;
    heapify_down(h, idx);
    heapify_up(h, idx);
}

// [P2] Helper functions for the List:
// [P2] Insert or update a member's activity node in the single linked list (0(1) as needed!):
void insertMemberActivity(MemberActivity_t ** head, int sid)
{
    // [#0]:
    MemberActivity_t * current = * head;

    // [#1] If the member already exists:
    while (current != NULL)
    {
        if (current -> sid == sid)
        {
            current -> loans_count++;
            return;
        }
        current = current -> next;
    }

    // [#2] Otherwise, create and initialize:
    MemberActivity_t * new = malloc(sizeof(MemberActivity_t));
    if (new == NULL) { return; }
    new -> sid = sid;
    new -> loans_count = 1;
    new -> reviews_count = 0;
    new -> score_sum = 0;
    new -> next = NULL;

    // [#3] Insert new node at the beginning of the list:
    new -> next = * head;
    * head = new;
}
// [P2] Update an existing member's activity (0(1) as needed!):
void updateMemberActivityList(MemberActivity_t ** head, int sid, int score)
{
    // [#1] Find the member:
    MemberActivity_t * current = * head;
    while (current -> sid != sid)
    {
        if (current -> next == NULL) return;
        current = current -> next;
    }

    // [#2] Update member:
    current -> reviews_count++;
    current -> score_sum += score;
}

// [P1] Parameter setup: Defines the total number of display slots (SLOTS) that will be distributed among the categories during D:
void S (const int slots)
{
    if (TESTING) printf("[Info] Function S: %d ... \n", slots);
    SLOTS = slots;
    printf("DONE. \n");
}

// [P1] Creation of core entities: Registers a Category (Genre) with code gid and name name:
void G (const int gid, char * name)
{
    if (TESTING) printf("[Info] Function G: %d & %s ... \n", gid, name);
    genre_t * genre = FindGenre(gid);
    if (genre == NULL)
    {
        AddGenre(gid, name);
        SortGenres();
        printf("DONE. \n");
        return;
    }
    printf("IGNORED. \n");
}

// [P1] Registers a Book with code bid, belonging to category gid, and title (sum_scores=0, n_reviews=0, avg=0, lost_flag=0):
void BK (int bid, int gid, char * title)
{
    if (TESTING) printf("[Info] Function BK: %d & %d & %s ... \n", bid, gid, title);
    book_t * book = FindBook(bid, gid);
    if (FindGenre(gid) == NULL)
    {
        printf("IGNORED. \n");
        return;
    }
    if (book == NULL)
    {
        book = AddBook(bid, gid, title);
        if (book != NULL) library.root = InsertAVLT(library.root, book);
        printf("DONE. \n");
        return;
    }
    printf("IGNORED. \n");
}

// [P1] Registers a Member with code sid and name:
void M (int sid, char * name)
{
    if (TESTING) printf("[Info] Function M: %d & %s ... \n", sid, name);
    member_t * member = FindMember(sid);
    if (member == NULL)
    {
        AddMember(sid, name);
        SortMembers();
        printf("DONE. \n");
        return;
    }
    printf("IGNORED. \n");
}

// [P1] The member sid borrows the book bid. A record is created in the member’s active loans list:
void L(int sid, int bid)
{
    if (TESTING) printf("[Info] L: %d %d\n", sid, bid);

    member_t * borrower = FindMember(sid);
    if (!borrower)
    {
        printf("IGNORED.\n");
        return;
    }

    // Finding first not loaned bid on global book list:
    book_t * book = library.books;
    while (book)
    {
        if (book -> bid == bid)
        {
            int notloaned = 0;
            member_t * member = library.members;
            while (member && !notloaned)
            {
                loan_t * loan = member -> loans_head.next;
                while (loan && !notloaned)
                {
                    if (loan -> bid == bid)
                    {
                        book_t * loanedbook = FindBook(bid, -1);
                        if (loanedbook && loanedbook == book)
                            notloaned = 1;
                    }
                    loan = loan -> next;
                }
                member = member -> next_all;
            }

            if (!notloaned)
            {
                LoanBook(borrower, sid, bid);
                active_loans += 1;
                insertMemberActivity(&library.activity, sid);
                printf("DONE.\n");
                return;
            }
        }
        book = book -> next_global;
    }

    printf("IGNORED.\n");
}

// [P1] Return of book bid by member sid. The corresponding active loan is removed. Rules [+]:
void R (int sid, int bid, int score, char * status)
{
    if (TESTING) printf("[Info] Function R: %d & %d & %d & %s ... \n", sid, bid, score, status);
    // Find the member and loan, then book, then genre and check all four possibilities:
    member_t * member = FindMember(sid);
    if (member)
    {
        loan_t * loan = member -> loans_head.next;
        while (loan && loan -> bid != bid)
            loan = loan -> next;
        if (loan)
        {
            book_t * book = FindBook(bid, -1);
            if (book)
            {
                genre_t * genre = FindGenre(book -> gid);
                if (genre)
                {
                    if (strcmp(status, "lost") == 0)
                    {
                        book -> lost_flag = 1;
                        genre -> lost_count ++;
                        DeleteLoan(member, loan);
                        DeleteAVLT(library.root, book -> title);
                        DeleteHeap(library.recommendations, book);
                    }
                    else if (strcmp(status, "ok") == 0)
                    {
                        if (score == -1)
                        {
                            DeleteLoan(member, loan);
                        }
                        else if (score < 0 || score > 10)
                        {
                            genre -> invalid_count ++;
                            DeleteLoan(member, loan);
                            active_loans -= 1;
                            printf("IGNORED. \n");
                            return;
                        }
                        else
                        {
                            book -> n_reviews ++;
                            book -> sum_scores += score;
                            book -> avg = (book -> n_reviews == 0) ? 0 : (book -> sum_scores / book -> n_reviews);
                            ReinsertBook(genre -> gid, bid);
                            DeleteLoan(member, loan);
                            InsertHeap(library.recommendations, book);
                            updateMemberActivityList(&library.activity, sid, score);
                            total_scores += 1; all_score_avg += score;
                        }
                    }
                    active_loans -= 1;
                    printf("DONE. \n");
                    return;
                }
            }
        }
    }
    printf("IGNORED. \n");
}

// [P1] Allocation of display slots & title selection: Calculation steps [+]: No O(G log G) ...
void D (void)
{
    if (TESTING) printf("[Info] Function D: void ...\n");

    // [#1] Starting - free previous memory (instructions) ...
    if (SLOTS <= 0 || library.genres == NULL)
    {
        printf("IGNORED. \n");
        return;
    }
    genre_t * genre = library.genres;
    while (genre)
    {
        if (genre -> display)
        {
            free(genre -> display);
            genre -> display = NULL;
        }
        genre -> display_count = 0;
        genre = genre -> next_by_gid;
    }

    // [#2] Count genres and allocate dynamic global arrays ...
    int totalgenres = 0;
    genre = library.genres;
    while (genre)
    {
        totalgenres ++;
        genre = genre -> next_by_gid;
    }
    if (totalgenres == 0)
    {
        printf("IGNORED. \n");
        return;
    }
    int * points = malloc(sizeof(int) * totalgenres);
    int * seats = malloc(sizeof(int) * totalgenres);
    int * rem = malloc(sizeof(int) * totalgenres);
    genre_t ** genres = malloc(sizeof(genre_t*) * totalgenres);

    // [#3] Calculate points per genre (old (and correct?) instructions) ...
    genre = library.genres;
    int VALID = 0;
    for (int i = 0; i < totalgenres; i++)
    {
        genres[i] = genre;
        int sum = 0;
        book_t * ptr = genre -> book_sentinel.next_in_genre;
        while (ptr != & genre -> book_sentinel)
        {
            if (ptr -> n_reviews > 0 && ptr -> lost_flag == 0)
                sum += ptr -> sum_scores;
            ptr = ptr -> next_in_genre;
        }
        points[i] = sum;
        VALID += sum;
        genre = genre -> next_by_gid;
    }
    if (VALID == 0)
    {
        printf("IGNORED. \n");
        free(points); free(seats); free(rem); free(genres);
        return;
    }

    // [#4] Initial seat allocation ...
    int remainingslots = SLOTS;
    for (int i = 0; i < totalgenres; i++)
    {
        int quota = VALID / SLOTS;
        if (quota == 0) quota = 1;
        seats[i] = points[i] / quota;
        if (seats[i] > remainingslots)
            seats[i] = remainingslots;
        remainingslots -= seats[i];
        rem[i] = points[i] - (seats[i] * quota);
        genres[i] -> slots = seats[i];
    }

    // [#5] Distribute leftover slots by largest remainder ...
    // [!] Sort remainders: O(SLOTS*#genres) != O(#genres*log(#genres)) ..!
    while (remainingslots > 0)
    {
        int best = -1;
        for (int i = 0; i < totalgenres; i++)
        {
            if (rem[i] > 0)
            {
                if (best == -1 || rem[i] > rem[best] || (rem[i] == rem[best] && genres[i] -> gid < genres[best] -> gid))
                    best = i;
            }
        }
        if (best == -1) break;
        genres[best] -> slots ++;
        rem[best] = 0;
        remainingslots --;
    }

    // [#6] Store top books in display arrays ...
    for (int i = 0; i < totalgenres; i++)
    {
        genre_t * g = genres[i];
        int n = g -> slots;
        int count = 0;
        book_t * ptr = g -> book_sentinel.next_in_genre;
        while (ptr != & g -> book_sentinel && count < n)
        {
            if (ptr -> n_reviews > 0 && ptr -> lost_flag == 0)
                count ++;
            ptr = ptr -> next_in_genre;
        }
        if (count > 0)
        {
            g -> display_count = count;
            g -> display = malloc(sizeof(book_t *) * count);
            ptr = g -> book_sentinel.next_in_genre;
            int j = 0;
            while (ptr != & g -> book_sentinel && j < count)
            {
                if (ptr -> n_reviews > 0 && ptr -> lost_flag == 0)
                {
                    g -> display[j] = ptr;
                    j ++;
                }
                ptr = ptr -> next_in_genre;
            }
        }
    }

    // [#7] Returning ...
    free(points); free(seats); free(rem); free(genres);
    printf("DONE.\n");
}

// [P1] Prints the current sorted list of books in the category:
void PG (int gid)
{
    if (TESTING) printf("[Info] Function PG: %d ... \n", gid);

    genre_t * genre = FindGenre(gid);
    if (genre == NULL)
    {
        printf("IGNORED. \n");
        return;
    }
    book_t * book = genre -> book_sentinel.next_in_genre;
    if (book == & genre -> book_sentinel)
    {
        printf("IGNORED. \n");
        return;
    }
    else
    {
        printf("===== Books - Genre %d ===== \n", gid);
        while (book != & genre -> book_sentinel)
        {
            if (TESTING) printf("* Book ID: %d & Avg: %d (Title: %s - SumScores: %d - NReviews: %d - LostFlag: %d ) \n", book -> bid, book -> avg, book -> title, book -> sum_scores, book -> n_reviews, book -> lost_flag);
            else printf("* ID: %d & Avg: %d \n", book -> bid, book -> avg);
            book = book -> next_in_genre;
        }
        printf("============================ \n");
    }
}

// [P1] Prints all active borrowed books of member sid (in order of entry):
void PM (int sid)
{
    if (TESTING) printf("[Info] Function PM: %d ... \n", sid);

    member_t * member = FindMember(sid);
    if (member == NULL)
    {
        printf("IGNORED. \n");
        return;
    }

    loan_t * loan = member -> loans_head.next;
    if (loan == NULL)
    {
        printf("[*] Loans: (empty list). \n");
        return;
    }
    else
    {
        int counter = 0;
        printf("===== Loans - Member %d ===== \n", sid);
        while (loan != NULL)
        {
            printf("* BID %d: %d (Status: %s) \n", ++counter, loan -> bid, loan -> status);
            loan = loan -> next;
        }
        printf("=============================== \n");
    }
}

// [P1] Prints the selected displays, grouped by category (order: ascending gid):
void PD (void)
{
    if (TESTING) printf("[Info] Function PD: void ... \n");

    genre_t * genre = library.genres;
    if (genre == NULL)
    {
        printf("[*] Display: (empty). \n");
        return;
    }

    int frouros = 0;
    printf("[*] Display: \n");

    while (genre != NULL)
    {
        if (genre -> display_count > 0 && genre -> display != NULL)
        {
            frouros = 1;
            printf("[GID: %d] ", genre->gid);

            for (int i = 0; i < genre -> display_count; i++)
            {
                book_t * book = genre -> display[i];
                if (book != NULL)
                    printf("BID: %d & AVG: %d. \n", book -> bid, book -> avg);
            }
        }
        genre = genre->next_by_gid;
    }

    if (!frouros)
        printf("[*] Display: (empty). \n");
}

// [P1] Prints, for each category gid, the points (g) that D would use if it ran now, as well as the SLOTS:
void PS (void)
{
    if (TESTING) printf("[Info] Function PS: void ... \n");

    genre_t * genre = library.genres;
    if (genre == NULL)
    {
        printf("IGNORED. \n");
        return;
    }

    printf("===== Genres Points ===== \n");
    genre = library.genres;
    while (genre)
    {
        int sum = 0;
        book_t * ptr = genre -> book_sentinel.next_in_genre;
        while (ptr != & genre -> book_sentinel)
        {
            if (ptr -> n_reviews > 0 && ptr -> lost_flag == 0)
                sum += ptr -> sum_scores;
            ptr = ptr -> next_in_genre;
        }
        printf("* GID %d: Points = %d. \n", genre -> gid, sum);
        genre = genre -> next_by_gid;
    }
    printf("========================= \n");
}

// [P2] Search for a book using its tittle in the avl tree and print it (O(logn) as needed!):
void F (const char * title)
{
    if (TESTING) printf("[Info] Function F ... \n");

    BookIndex_t * book = SearchAVLT(library.root, title);
    if (book != NULL && !TESTING) printf("Book: %d, \"%s\", %d. \n", book -> book -> bid, book -> book -> title, book -> book -> avg);
    else if (book != NULL && TESTING) printf("BOOK FOUND: ID=%d, NAME=\"%s\", AVG=%d. \n", book -> book -> bid, book -> book -> title, book -> book -> avg);
    else printf("NOT FOUND. \n");
}

// [P2] Print the top k books from the heap (O(k) > O(klogn) ?):
void TOP (int k)
{
    if (TESTING) printf("[Info] Function TOP ... \n");

    if (!library.recommendations || library.recommendations -> size == 0 || k <= 0) return;
    if (k > library.recommendations -> size) k = library.recommendations -> size;

    printf("TOP Books: ");
    for (int i = 0; i < k; i++)
    {
        book_t * book = library.recommendations -> heap[i];
        if (book)
        {
            if (TESTING) printf("[#%d] ID=%d, NAME=\"%s\", AVG=%d", i+1, book -> bid, book -> title, book -> avg);
            else printf("%d, \"%s\", %d", book -> bid, book -> title, book -> avg);
            if (i < k - 1) printf(" | ");
        }
    }

    printf(".\n");
}

// [P2] Print the top members from the activity list based on the max(loans_count+reviews_count):
void AM (void)
{
    if (TESTING) printf("[Info] Function AM ... \n");

    if (library.activity == NULL)
    {
        printf("NO ACTIVE MEMBERS. \n");
        return;
    }

    int max = -1;
    MemberActivity_t * current = library.activity;

    while (current != NULL)
    {
        int total = current -> loans_count + current -> reviews_count;
        if (total > max) max = total;
        current = current -> next;
    }

    int count = 0;
    current = library.activity;
    while (current != NULL)
    {
        if (current -> loans_count + current -> reviews_count == max) count++;
        current = current -> next;
    }

    MemberActivity_t ** most_active = malloc(sizeof(MemberActivity_t *) * count);
    if (!most_active) return;

    int k = 0;
    current = library.activity;
    while (current != NULL)
    {
        if (current -> loans_count + current -> reviews_count == max)
            most_active[k++] = current;
        current = current -> next;
    }

    for (int i = 0; i < count - 1; i++)
    {
        for (int j = 0; j < count - j - 1; j++)
        {
            if (most_active[j] -> sid > most_active[j + 1] -> sid)
            {
                MemberActivity_t * temp = most_active[j];
                most_active[j] = most_active[j + 1];
                most_active[j + 1] = temp;
            }
        }
    }

    printf("Active Members: ");
    for (int i = 0; i < count; i++)
    {
        member_t * member = FindMember(most_active[i] -> sid);
        if (TESTING) printf("[#%d] ID=%d, NAME=\"%s\", LOANS=%d, REVIEWS=%d", i+1, most_active[i] -> sid, member -> name, most_active[i] -> loans_count, most_active[i] -> reviews_count);
        else printf("%d, \"%s\", %d, %d", most_active[i] -> sid, member -> name, most_active[i] -> loans_count, most_active[i] -> reviews_count);
        if (i < count - 1) printf(" | ");
    }

    free(most_active);
    printf(".\n");
}

// [P2] Update a book tittle with a new tittle using the avl tree (with the old tittle):
void U (int bid, char * newtitle)
{
    if (TESTING) printf("[Info] Function U ... \n");

    book_t * book = FindBook(bid, -1);
    if (book == NULL)
    {
        printf("IGNORED. \n");
        return;
    }

    library.root = DeleteAVLT(library.root, book -> title);

    strncpy(book -> title, newtitle, TITLE_MAX - 1);
    book -> title[TITLE_MAX - 1] = '\0';

    library.root = InsertAVLT(library.root, book);

    printf("DONE. \n");
}

// [P2] Print all of the global library statistics:
void X (void)
{
    if (TESTING) printf("[Info] Function X ... \n");

    if (total_scores != 0 && !TESTING) printf("Stats: %d, %d, %d, %.1f. \n", book_count, member_count, active_loans, (all_score_avg/total_scores));
    else if (total_scores != 0 && TESTING) printf("LIBRARY STATS: BOOKS=%d, MEMBERS=%d, ACTIVELOANS=%d, TOTALSCOREAVG=%.1f ... \n", book_count, member_count, active_loans, (all_score_avg/total_scores));
    else printf("Stats: %d, %d, %d, 0. \n", book_count, member_count, active_loans);
}

// [P2] Free all memory allocations (from every struct) - (O(n) ?):
void BF(void)
{
    if (TESTING) printf("[Info] Function BF ... \n");

    // [#1] Free the AVL tree (using the helper function):
    if (library.root != NULL)
    {
        freeAVLT(library.root);
        library.root = NULL;
    }

    // [#2] Free the recommendation max heap:
    if (library.recommendations != NULL)
    {
        free(library.recommendations);
        library.recommendations = NULL;
    }

    // [#3] Free the member activity linked list:
    MemberActivity_t * tmp1 = library.activity;
    while (tmp1 != NULL)
    {
        MemberActivity_t * next = tmp1 -> next;
        free(tmp1);
        tmp1 = next;
    }
    library.activity = NULL;

    // [#4] Free all books (via global linked list):
    book_t * tmp2 = library.books;
    while (tmp2 != NULL)
    {
        book_t * next = tmp2 -> next_global;
        free(tmp2);
        tmp2 = next;
    }
    library.books = NULL;

    // [#5] Free all members and their loans:
    member_t * tmp3 = library.members;
    while (tmp3 != NULL)
    {
        loan_t * tmp4 = tmp3 -> loans_head.next;
        while (tmp4 != NULL)
        {
            loan_t * next_loan = tmp4 -> next;
            free(tmp4);
            tmp4 = next_loan;
        }
        member_t * next_member = tmp3 -> next_all;
        free(tmp3);
        tmp3 = next_member;
    }
    library.members = NULL;

    // [#6] Free all genres and their display arrays:
    genre_t * tmp5 = library.genres;
    while (tmp5 != NULL)
    {
        if (tmp5 -> display != NULL)
        {
            free(tmp5 -> display);
            tmp5 -> display = NULL;
        }
        genre_t * next_genre = tmp5 -> next_by_gid;
        free(tmp5);
        tmp5 = next_genre;
    }
    library.genres = NULL;

    // [#7] Return:
    free(tmp1); free(tmp2); free(tmp3); free(tmp5);
    return;
}

// [P1] Add a new genre (instructions) at the start of the list:
void AddGenre (int gid, char * name)
{
    genre_t * genre = malloc(sizeof(genre_t));
    if (!genre)
        return;

    genre -> book_sentinel.next_in_genre = & genre -> book_sentinel;
    genre -> book_sentinel.prev_in_genre = & genre -> book_sentinel;
    genre -> book_sentinel.gid = genre -> gid;
    genre -> gid = gid;
    strcpy(genre->name, name);
    genre -> display = NULL;
    genre -> display_count = 0;
    genre -> slots = 0;
    genre -> lost_count = 0;
    genre -> invalid_count = 0;

    genre -> next_by_gid = library.genres;
    library.genres = genre;
}

// [P1] Search and find a genre:
genre_t * FindGenre (int gid)
{
    genre_t * current = library.genres;
    while (current != NULL)
    {
        if (current -> gid == gid) return current;
        current = current -> next_by_gid;
    }
    return NULL;
}

// [P1] Print all of the genres:
void PrintGenres(void)
{
    genre_t * current = library.genres;

    if (current == NULL)
    {
        printf("[Ok] No genres  in library ...\n");
        return;
    }

    printf("===== Library Genres =====\n");
    while (current != NULL)
    {
        printf("* Genre ID: %d | Name: %s\n", current -> gid, current -> name);
        current = current -> next_by_gid;
    }
    printf("==========================\n");
}

// [P1] Sort all the genres (Bubble sort on linked list: O(n^2)):
void SortGenres (void)
{
    if (library.genres == NULL) return;

    int frouros;
    genre_t * ptr; genre_t * last = NULL;

    do
    {
        frouros = 0;
        ptr = library.genres;

        while (ptr -> next_by_gid != last)
        {
            if (ptr -> gid > ptr -> next_by_gid -> gid)
            {
                int temp1 = ptr -> gid;
                char temp2[NAME_MAX];
                strcpy(temp2, ptr -> name);

                ptr -> gid = ptr -> next_by_gid -> gid;
                strcpy(ptr -> name, ptr -> next_by_gid -> name);

                ptr -> next_by_gid -> gid = temp1;
                strcpy(ptr -> next_by_gid -> name, temp2);

                frouros = 1;
            }
            ptr = ptr -> next_by_gid;
        }
        last = ptr;
    }
    while (frouros);
}

// [P1] Add a new book (at the right place (sorted) immediately):
book_t * AddBook (int bid, int gid, const char * title)
{
    book_count += 1;

    genre_t * genre = FindGenre(gid);
    if (genre == NULL)
        return NULL;

    book_t * book = malloc(sizeof(book_t));
    if (!book) return NULL;

    book -> sum_scores = 0;
    book -> n_reviews = 0;
    book -> avg = 0;
    book -> lost_flag = 0;
    book -> bid = bid;
    book -> gid = gid;
    book -> heap_pos = -1;
    strcpy(book -> title, title);

    book_t * current = genre -> book_sentinel.next_in_genre;
    while (current != & genre -> book_sentinel && current -> avg > book -> avg)
        current = current -> next_in_genre;

    book -> next_in_genre = current;
    book -> prev_in_genre = current -> prev_in_genre;
    current -> prev_in_genre -> next_in_genre = book;
    current -> prev_in_genre = book;
    book -> next_global = library.books;
    library.books = book;

    return book;
}

// [P1] Find a specific book:
book_t * FindBook (const int bid, const int gid)
{
    // [#1] Search all books in library using global list (command -1*):
    if (gid == -1)
    {
        book_t * current = library.books;
        while (current != NULL)
        {
            if (current -> bid == bid)
                return current;
            current = current -> next_global;
        }
        return NULL;
    }

    // [#2] Search inside a specific genre:
    genre_t * genre = FindGenre(gid);
    if (!genre)
        return NULL;
    book_t * current = genre -> book_sentinel.next_in_genre;
    while (current != & genre -> book_sentinel)
    {
        if (current -> bid == bid)
            return current;
        current = current -> next_in_genre;
    }

    return NULL;
}

// [P1] Reinsert a book at the list instead of sorting:
void ReinsertBook (int gid, int bid)
{
    genre_t *genre = FindGenre(gid);
    book_t *book = FindBook(bid, gid);
    if (!book || !genre) return;

    // Splice-out:
    book -> prev_in_genre -> next_in_genre = book -> next_in_genre;
    book -> next_in_genre -> prev_in_genre = book -> prev_in_genre;

    // Search and splice-in:
    book_t * current;
    if (book -> avg > book -> prev_in_genre -> avg)
    {
        current = book -> prev_in_genre;
        while (current != & genre -> book_sentinel && current -> avg < book -> avg)
            current = current -> prev_in_genre;
        book -> next_in_genre = current -> next_in_genre;
        book -> prev_in_genre = current;
        current -> next_in_genre -> prev_in_genre = book;
        current -> next_in_genre = book;
    }
    else
    {
        current = book -> next_in_genre;
        while (current != & genre -> book_sentinel && current -> avg > book -> avg)
            current = current -> next_in_genre;
        book -> next_in_genre = current;
        book -> prev_in_genre = current -> prev_in_genre;
        current -> prev_in_genre -> next_in_genre = book;
        current -> prev_in_genre = book;
    }
}

// [P1] Print all books from a genre:
void PrintBooks (void)
{
    genre_t * genre = library.genres;
    if (genre == NULL)
    {
        printf("[Ok] No books   in library ... \n");
        return;
    }

    printf("=== Library Genres Books ===\n");

    int counter = 1;
    while (genre != NULL)
    {
        book_t * book = genre -> book_sentinel.next_in_genre;
        if (book != &genre -> book_sentinel)
            while (book != &genre -> book_sentinel)
            {
                printf("[%d] Book ID: %d | Title: %s | Avg: %d\n", counter, book -> bid, book -> title, book -> avg);
                book = book->next_in_genre;
            }
        genre = genre -> next_by_gid;
        counter++;
    }
    printf("======================\n");
}

// [P1] Add a new member at the start of the list:
void AddMember(int sid, const char * name)
{
    member_count += 1;

    member_t * newmember = malloc(sizeof(member_t));
    if (!newmember)
        return;

    newmember -> sid = sid;
    strcpy(newmember -> name, name);

    newmember -> loans_head.next = NULL;
    newmember -> loans_head.sid = sid;
    newmember -> loans_head.bid = -1;
    // newmember -> loans_head.status = NULL;
    newmember -> prev_all = NULL;
    newmember -> next_all = NULL;
    // newmember -> activity = NULL;

    if (library.members == NULL)
    {
        library.members = newmember;
    }
    else
    {
        newmember -> next_all = library.members;
        library.members -> prev_all = newmember;
        library.members = newmember;
    }
}

// [P1] Find a specific member:
member_t * FindMember (const int sid)
{
    member_t * current = library.members;
    while (current != NULL)
    {
        if (current -> sid == sid) return current;
        current = current -> next_all;
    }
    return NULL;
}

// [P1] Sort the member list (Bubble sort on linked list: O(n^2)):
void SortMembers (void)
{
    if (library.members == NULL) return;

    int frouros;
    member_t * ptr;
    member_t * last = NULL;

    do
    {
        frouros = 0;
        ptr = library.members;

        while (ptr -> next_all != last)
        {
            if (ptr -> sid > ptr -> next_all -> sid)
            {
                int temp1 = ptr -> sid;
                char temp2[NAME_MAX];
                strcpy(temp2, ptr -> name);
                ptr -> sid = ptr -> next_all -> sid;
                strcpy(ptr -> name, ptr -> next_all -> name);
                ptr -> next_all -> sid = temp1;
                strcpy(ptr -> next_all -> name, temp2);
                frouros = 1;
            }
            ptr = ptr -> next_all;
        }
        last = ptr;
    }
    while (frouros);
}

// [P1] Print all the members:
void PrintMembers (void)
{
    member_t * current = library.members;

    if (current == NULL)
    {
        printf("[Ok] No members in library ...\n");
        return;
    }

    printf("=== Library Members ===\n");
    while (current != NULL)
    {
        printf("Member: ID = %d & Name = %s ...\n", current -> sid, current -> name);
        current = current -> next_all;
    }
    printf("======================\n");
}

// [P1] Let a member loan a book from a genre:
void LoanBook (member_t * member, int sid, int bid)
{
    loan_t * current = member -> loans_head.next;
    while (current != NULL)
    {
        if (current -> bid == bid)
        {
            if (TESTING) printf("[Ok] Member %d already has book %d loaned ...\n", sid, bid);
            return;
        }
        current = current -> next;
    }

    loan_t * newloan = malloc(sizeof(loan_t));
    if (newloan == NULL) return;

    newloan -> sid = sid;
    newloan -> bid = bid;
    strcpy(newloan -> status, "active");
    newloan -> next = member -> loans_head.next;
    member -> loans_head.next = newloan;
}

// [P1] Print all loans from a member:
void PrintLoans (void)
{
    int counter1 = 0;
    member_t * member = library.members;
    if (member == NULL)
    {
        printf("[Ok] No loans   in library ... \n");
        return;
    }

    while (member != NULL)
    {
        int counter2 = 0;
        counter1 = counter1 + 1;
        printf("[#%d] Member %s (%d): \n", counter1, member -> name, member -> sid);
        loan_t * loan = member -> loans_head.next;
        if (loan == NULL)
            printf("[#%d] No active loans. \n", counter2);
        else
            while (loan != NULL)
            {
                counter2 = counter2 + 1;
                printf("[%d#%d] Loan: SID=%d & BID=%d & STS=%s. \n", counter1, counter2, loan -> sid, loan -> bid, loan -> status);
                loan = loan -> next;
            }
        member = member -> next_all;
    }
    printf("===================== \n");
}

// [P1] Delete a specific loan from a member (O(n) != O(1)):
void DeleteLoan(member_t * member, loan_t * loan)
{
    if (!member || !loan)
    {
        printf("[Error] Loan or Member not found ... \n");
        return;
    }

    loan_t * previous = & member -> loans_head;
    loan_t * current = member -> loans_head.next;
    while (current != loan)
    {
        previous = current;
        current = current -> next;
    }

    previous -> next = current -> next;
    free(current);
}

// [P12] Set up the global library:
void InitLibrary(library_t * lib)
{
    lib -> members = NULL;
    lib -> genres = NULL;
    lib -> books = NULL;

    lib -> root = NULL;
    lib -> recommendations = NULL;
    lib -> activity = NULL;
}

// [!] The End ...
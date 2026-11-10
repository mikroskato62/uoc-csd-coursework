# Petrakis Rafail Ioannis - csd5748
# [UoC-CSD-HY255] Assignment3: ADTs - Symbol Table.
# The makefile ...

CC = gcc
CFLAGS = -Wall -ansi -pedantic -g
VPATH = src

all: list.exe hash.exe

list: testsymtab.o symtablelist.o
	$(CC) $(CFLAGS) -o testsymtab testsymtab.o symtablelist.o

hash: testsymtab.o symtablehash.o
	$(CC) $(CFLAGS) -o testsymtab testsymtab.o symtablehash.o

testsymtab.o: testsymtab.c symtable.h
	$(CC) $(CFLAGS) -c $< -o $@

symtablelist.o: symtablelist.c symtable.h
	$(CC) $(CFLAGS) -c $< -o $@

symtablehash.o: symtablehash.c symtable.h
	$(CC) $(CFLAGS) -c $< -o $@

clean:
	rm -f *.o testsymtab *.exe

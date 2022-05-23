#define _CRT_SECURE_NO_WARNINGS
#include "chainingHash.h"

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <time.h>
#include <math.h>

#define ALLOCATED 0xcdcdcdcdcdcdcdcd


BFUNCTION** resize(BFUNCTION** table, int* table_size, int numOfItems);
BFUNCTION* find(BFUNCTION** table, char* name, int table_size);
BFUNCTION** insert(BFUNCTION** table, BFUNCTION* p, int* table_size, int* numOfItems);

BFUNCTION** init_table(BFUNCTION** table, int table_size) {
	//printf("Hash table initialised");
	table = (BFUNCTION**)malloc(table_size * sizeof(BFUNCTION*));

	for (int i = 0; i < table_size; i++) {
		table[i] = NULL;
	}

	return table;
}

unsigned long hash(char* name, int table_size) {
	unsigned int name_length = strlen(name), tmp = 0;
	unsigned long hash_value = 1;

	for (int i = 0; i < name_length; i++) {
		hash_value += (name[i] + i) * (name[i] + name_length - i) * i * 103;
	}
	return hash_value % table_size;
}


void print_table(BFUNCTION** table, int table_size) {
	int* items = (int*)malloc(sizeof(int) * table_size);
	int average = 0, max = 0;

	if (!items) exit(2);

	for (int i = 0; i < table_size; i++)
		items[i] = 0;


	for (int i = 0; i < table_size; i++)
	{
		if (table[i] == NULL)
			printf(" %d <EMPTY>", i + 1);
		else {
			BFUNCTION* tmp = table[i];
			printf(" %d          ", i + 1);
			while (tmp != NULL) {
				printf(" %s    ", tmp->function);
				if (tmp->next != NULL)
					printf("-> ");
				tmp = tmp->next;
				items[i]++;
			}
		}
		average += items[i];
		printf("\n");
	}
	printf("\n\n\n");

	for (int i = 0; i < table_size; i++) {
		//printf("Items[%d]  =  %d\n", i, items[i]);
		if (items[i] > max)
			max = items[i];
	}
	//printf("AVERAGE: %d\n", average/table_size);
	//printf("MAX in one line: %d\n", max);
}

void is_in_table(BFUNCTION** table, BFUNCTION* node, int table_size) {
	if (find(table, node->function, table_size) != NULL)
		printf("!!!%s found\n", node->function);
	else
		printf("!!!%s NOT found\n", node->function);
}

BFUNCTION* find(BFUNCTION** table, char* name, int table_size) {
	BFUNCTION* tmp = table[hash(name, table_size)];

	while (tmp != NULL) {
		if (!strcmp(name, tmp->function))
			return tmp;
		tmp = tmp->next;
	}
	return NULL;
}

BFUNCTION* delete(BFUNCTION** table, char* name, int table_size, int* numOfItems) {
	int pos = hash(name, table_size);
	BFUNCTION* parent = NULL;
	BFUNCTION* tmp = table[pos];

	while (tmp != NULL && strcmp(name, tmp->function)) {
		parent = tmp;
		tmp = tmp->next;
	}
	if (tmp == NULL) return table;
	if (parent == NULL) table[pos] = tmp->next;
	else parent->next = tmp->next;

	(*numOfItems) -= 1;

	return table;
}

BFUNCTION** insert(BFUNCTION** table, BFUNCTION* p, int* table_size, int* numOfItems) {
	//print_table(table, *table_size);
	int hash_val = hash(p->function, *table_size), inOneLine = 0;

	p->next = NULL;
	if (table[hash_val] == NULL) {
		table[hash_val] = p;
	}
	else {
		BFUNCTION* start = table[hash_val];
		while (table[hash_val]->next != NULL) {
			if (!strcmp(table[hash_val]->function, p->function))
				return table;
			table[hash_val] = table[hash_val]->next;
			inOneLine++;
		}
		table[hash_val]->next = p;
		table[hash_val] = start;
	}
	if (inOneLine >= 50) {
		table = resize(table, table_size, *numOfItems);
	}
	(*numOfItems) += 1;

	return table;
}

BFUNCTION** resize(BFUNCTION** table, int* table_size, int numOfItems) {
	int newSize = *table_size * 3, noi = numOfItems;
	BFUNCTION* tmpDel = NULL;
	BFUNCTION** tmp = table;
	BFUNCTION* tmp2 = NULL;
	BFUNCTION** newTable = NULL;

	newTable = init_table(newTable, newSize);

	for (int i = 0; i < *table_size; i++) {
		while (tmp[i] != NULL) {
			tmp2 = tmp[i]->next;
			newTable = insert(newTable, tmp[i], &newSize, &noi);
			tmp[i] = tmp2;
		}
	}
	// remove old table
	for (int i = 0; i < *table_size; i++) {
		while (table[i] != NULL) {
			tmpDel = table[i]->next;
			table[i] = NULL;
			free(table[i]);
			table[i] = tmpDel;
		}
	}
	free(table);
	*table_size = newSize;

	return newTable;
}

void chainingHash(BFUNCTION* p, int numOfInputs) {
	/*printf("Start CHAINING HASH function\n");
	int table_size = 5, numOfItems = 0;
	BFUNCTION** table = NULL;

	table = init_table(table, table_size);
	print_table(table, table_size);*/

}

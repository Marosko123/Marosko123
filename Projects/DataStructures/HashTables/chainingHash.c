#define _CRT_SECURE_NO_WARNINGS
#include "chainingHash.h"

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <time.h>
#include <math.h>

#define ALLOCATED 0xcdcdcdcdcdcdcdcd
#define NAME_LENGTH 12

typedef struct people {
	char name[NAME_LENGTH];

	// Chaining Hash Table
	struct people* next;

	// AVL Tree
	unsigned long age;
	int height;
	struct people* left;
	struct people* right;
}PEOPLE;



PEOPLE** resize(PEOPLE** table, int* table_size, int numOfItems);
PEOPLE* find(PEOPLE** table, char* name, int table_size);
PEOPLE** insert(PEOPLE** table, PEOPLE* p, int* table_size, int* numOfItems);

PEOPLE** init_table(PEOPLE** table, int table_size) {
	table = (PEOPLE**)malloc(table_size * sizeof(PEOPLE*));

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


void print_table(PEOPLE** table, int table_size) {
	int* items = (int*)malloc(sizeof(int) * table_size);
	int average = 0, max = 0;

	for (int i = 0; i < table_size; i++)
		items[i] = 0;


	for (int i = 0; i < table_size; i++)
	{
		if (table[i] == NULL);
		//printf(" %d <EMPTY>", i+1);
		else {
			PEOPLE* tmp = table[i];
			//printf(" %d          ", i+1);
			while (tmp != NULL) {
				//printf(" %s    ", tmp->name);
				//if (tmp->next != NULL) 
					//printf("-> ");
				tmp = tmp->next;
				items[i]++;
			}
		}
		average += items[i];
		//printf("\n\n\n");
	}

	for (int i = 0; i < table_size; i++) {
		//printf("Items[%d]  =  %d\n", i, items[i]);
		if (items[i] > max)
			max = items[i];
	}
	//printf("AVERAGE: %d\n", average/table_size);
	//printf("MAX in one line: %d\n", max);
}

void is_in_table(PEOPLE** table, PEOPLE* node, int table_size) {
	if (find(table, node->name, table_size) != NULL)
		printf("!!!%s found\n", node->name);
	else
		printf("!!!%s NOT found\n", node->name);
}

PEOPLE* find(PEOPLE** table, char* name, int table_size) {
	PEOPLE* tmp = table[hash(name, table_size)];

	while (tmp != NULL) {
		if (!strcmp(name, tmp->name))
			return tmp;
		tmp = tmp->next;
	}
	return NULL;
}

PEOPLE* delete(PEOPLE** table, char* name, int table_size, int* numOfItems) {
	int pos = hash(name, table_size);
	PEOPLE* parent = NULL;
	PEOPLE* tmp = table[pos];

	while (tmp != NULL && strcmp(name, tmp->name)) {
		parent = tmp;
		tmp = tmp->next;
	}
	if (tmp == NULL) return table;
	if (parent == NULL) table[pos] = tmp->next;
	else parent->next = tmp->next;

	(*numOfItems) -= 1;

	return table;
}

PEOPLE** insert(PEOPLE** table, PEOPLE* p, int* table_size, int* numOfItems) {
	//print_table(table, *table_size);
	int hash_val = hash(p->name, *table_size), inOneLine = 0;

	p->next = NULL;
	if (table[hash_val] == NULL) {
		table[hash_val] = p;
	}
	else {
		PEOPLE* start = table[hash_val];
		while (table[hash_val]->next != NULL) {
			if (!strcmp(table[hash_val]->name, p->name))
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

PEOPLE** resize(PEOPLE** table, int* table_size, int numOfItems) {
	int newSize = *table_size * 3, noi = numOfItems;
	PEOPLE* tmpDel = NULL;
	PEOPLE** tmp = table;
	PEOPLE* tmp2 = NULL;
	PEOPLE** newTable = NULL;

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

void chainingHash(PEOPLE* p, int numOfInputs) {
	printf("Start CHAINING HASH function\n");
	int table_size = 5, numOfItems = 0;
	PEOPLE** table = NULL;

	clock_t startInit = clock();
	table = init_table(table, table_size);
	print_table(table, table_size);
	clock_t endInit = clock();
	double cpu_time_used = ((double)(endInit - startInit)) / CLOCKS_PER_SEC;
	printf("\tINIT TIME: %f\n", cpu_time_used);

	clock_t startInsert = clock();
	for (int i = 0; i < numOfInputs; i++)
		table = insert(table, &p[i], &table_size, &numOfItems);
	clock_t endInsert = clock();
	cpu_time_used = ((double)(endInsert - startInsert)) / CLOCKS_PER_SEC;
	printf("\tInsert took around %f seconds to execute %d inserts\n", cpu_time_used, numOfInputs);

	clock_t start = clock();
	for (int i = 0; i < numOfInputs; i++) {
		find(table, p[i].name, table_size);
	}
	clock_t end = clock();
	cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
	printf("\tFind took around %f seconds to execute %d findings\n", cpu_time_used, numOfInputs);


	clock_t startDelete = clock();
	for (int i = 0; i < numOfInputs; i++)
		table = delete(table, p[i].name, table_size, &numOfItems);
	clock_t endDelete = clock();
	cpu_time_used = ((double)(endDelete - startDelete)) / CLOCKS_PER_SEC;

	printf("\tDelete took around %f seconds to execute %d deletions\n", cpu_time_used, numOfInputs);
	printf("End CHAINING HASH function\n\n\n\n\n");
}

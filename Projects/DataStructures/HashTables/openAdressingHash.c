#define _CRT_SECURE_NO_WARNINGS
#include "openAdressingHash.h"

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <time.h>

#define DELETED_NODE (PEOPLE*)(0xFFFFFFFFFFFFFFFFUL)
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


PEOPLE** resizeAdressing(PEOPLE** table, int* table_size, int* actual_num);

unsigned long hashAdressing(int table_size, char* name) {
	int name_length = (int)strlen(name);
	unsigned long hash_value = 1;

	for (int i = 0; i < name_length; i++) {
		hash_value += (unsigned long) (name[i] + i) * (name[i] + name_length - i) * i * 103;
	}
	hash_value %= table_size;

	return hash_value;
}

void printAdressing(PEOPLE** table, int table_size) {
	printf("\n");
	for (int i = 0; i < table_size; i++) {
		if (table[i] == NULL) printf(" %d\t<EMPTY>\n", i);
		else if (table[i] == DELETED_NODE) printf(" %d\t<DELETED>\n", i);
		else printf(" %d %s\n", i, table[i]->name);
	}
	printf("\n");
}

int recursion_insert(PEOPLE** table, int table_size, PEOPLE* p, int index) {
	if (index >= table_size)
		index = index - table_size;
	if (table[index] == NULL || table[index] == DELETED_NODE) {
		table[index] = p;
		return 0;
	}
	else if (!strcmp(table[index]->name, p->name)) return 2; // no duplicates
	else return recursion_insert(table, table_size, p, index + 7);
}

PEOPLE** insertAdressing(PEOPLE** table, int* table_size, PEOPLE* p, int* actual_num) {
	int index = hashAdressing(*table_size, p->name);

	recursion_insert(table, *table_size, p, index);

	if (*actual_num >= *table_size * 0.6f)
		table = resizeAdressing(table, table_size, actual_num);
	(*actual_num) += 1;
	return table;
}

PEOPLE* findAdressing(PEOPLE** table, int table_size, char* name) {
	int index = hashAdressing(table_size, name), jump = 7;

	for (int i = 0; i < table_size; i++) {
		if (index >= table_size)
			index -= table_size;
		if (table[index] == NULL)
			return NULL;
		if (table[index] != DELETED_NODE &&
			!strcmp(name, table[index]->name))
			return table[index];
		index += jump;
	}
	return NULL;
}

PEOPLE* deleteAdressing(PEOPLE** table, int table_size, char* name, int* actual_num) {
	int index = hashAdressing(table_size, name);
	int a = 0, jump = 7;
	bool passed = false;
	for (int i = 0; i < table_size - a; i++) {
		// check index
		if (index >= table_size) {
			index -= table_size;
		}
		// doesnt exist
		if (table[index] == NULL)
			return NULL;
		// Delete node
		if (table[index] != DELETED_NODE &&
			!strcmp(name, table[index]->name)) {
			PEOPLE* tmp = table[index];
			table[index] = DELETED_NODE;
			(*actual_num) -= 1;
			return tmp;
		}
		index += jump;
	}
	return NULL;
}

PEOPLE** initAdressing(PEOPLE** table, int table_size) {
	table = (PEOPLE**)malloc(table_size * sizeof(PEOPLE*));
	for (int i = 0; i < table_size; i++) {
		table[i] = NULL;
	}

	return table;
}

PEOPLE** resizeAdressing(PEOPLE** table, int* table_size, int* actual_num) {
	PEOPLE** tmp = (PEOPLE**)malloc(*table_size * sizeof(PEOPLE*));
	int newSize = 3 * (*table_size);

	for (size_t i = 0; i < *table_size; i++) {
		tmp[i] = table[i];
	}

	table = (PEOPLE**)realloc(table, newSize * sizeof(PEOPLE*));
	for (size_t i = 0; i < newSize; i++) {
		table[i] = NULL;
	}


	//printf("Rehashing START, items: %d\n", *actual_num);
	// new hashAdressing
	int tmpNumOfItems = 0, tmpSize = newSize;
	for (size_t i = 0; i < *table_size; i++) {
		if (tmp[i] != NULL) {
			insertAdressing(table, &tmpSize, tmp[i], &tmpNumOfItems);
		}
	}
	//printf("Rehashing END\n");


	for (size_t i = 0; i < *table_size; i++) {
		tmp[i] = NULL;
	} tmp = NULL;

	*table_size = newSize;

	return table;
}

void openAdressingHash(PEOPLE* p, int attempts) {
	printf("Start OPEN ADRESSING HASH function\n");
	int table_size = 10, actual_num = 0;
	PEOPLE** table = NULL;

	clock_t start = clock();
	table = initAdressing(table, table_size);

	clock_t end = clock();
	double cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
	printf("\tINIT TIME: %f\n", cpu_time_used);

	start = clock();
	for (int i = 0; i < attempts; i++)
		table = insertAdressing(table, &table_size, &p[i], &actual_num);
	end = clock();
	cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
	printf("\tInsert took around %f seconds to execute %d inserts\n", cpu_time_used, attempts);
	
	
	start = clock();
	for (int i = 0; i < attempts; i++) {
		findAdressing(table, table_size, p[i].name);
	}
	end = clock();
	cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
	printf("\tFind took around %f seconds to execute %d findings\n", cpu_time_used, attempts);
	
	
	start = clock();
	for (int i = 0; i < attempts; i++) {
		deleteAdressing(table, table_size, p[i].name, &actual_num);
	}
	end = clock();
	cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
	printf("\tDelete took around %f seconds to execute %d deletions\n", cpu_time_used, attempts);

	

	printf("End OPEN ADRESSING HASH function\n\n\n\n\n");

}

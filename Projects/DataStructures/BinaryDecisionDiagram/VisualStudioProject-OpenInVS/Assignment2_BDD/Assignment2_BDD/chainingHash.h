#ifndef CHAININGHASH_DOT_H
#define CHAININGHASH_DOT_H
#define MAX_FUNCT_SIZE 2500
#define MAX_WORD_SIZE 25

typedef struct bFunction {
	char letter;
	char function[MAX_FUNCT_SIZE];
	struct bdd_node* lChild, * rChild;  // sipky 
	struct bdd_node* parent;

	struct bFunction* next;
}BFUNCTION;

void chainingHash(BFUNCTION* p, int numOfInputs);
BFUNCTION** resize(BFUNCTION** table, int* table_size, int numOfItems);
BFUNCTION** insert(BFUNCTION** table, BFUNCTION* p, int* table_size, int* numOfItems);
BFUNCTION* delete(BFUNCTION** table, char* name, int table_size, int* numOfItems);
BFUNCTION* find(BFUNCTION** table, char* name, int table_size);
void is_in_table(BFUNCTION** table, BFUNCTION* node, int table_size);
void print_table(BFUNCTION** table, int table_size);
unsigned long hash(char* name, int table_size);
BFUNCTION** init_table(BFUNCTION** table, int table_size);

#endif /* CHAININGHASH_DOT_H */

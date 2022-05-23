// FIIT STU 2021/2022 Summer Semestre
// Bednar Maros 116822
#define _CRT_SECURE_NO_WARNINGS

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <time.h>
#include "chainingHash.h"

#define MAX_FUNCT_SIZE 3000
#define MAX_WORD_SIZE 25

typedef struct bdd_node {
	char letter;
	char function[MAX_FUNCT_SIZE];
	struct bdd_node* lChild, * rChild;
	struct bdd_node* parent;
}BDD_NODE;

typedef struct bdd {
	char startingFunction[MAX_FUNCT_SIZE];
	int numOfNodes;
	int numOfVariables;
	struct bdd_node* root;
}BDD;

BDD_NODE* BDD_create(char* bfunction, char* order, BDD_NODE* parent);
char BDD_use(BDD_NODE* bdd, char* inputs);
void createZero();
void createOne();
char* decimalToBinary(int num, int bits);
char* letters_order(char* letters);
char* generateBFunction();
char checkFunction(char* bFunction, char* order, char* values);

BDD_NODE* zero = NULL;
BDD_NODE* one = NULL;
BFUNCTION** hashTable = NULL;
int num_of_nodes = 1, numOfVariables, tableSize = 50;
char* order2;

int main()
{
	printf("DSA - BinaryDecisionDiagram\n\n");

	FILE* fw = NULL;
	BDD* bdd_root[100];
	BDD_NODE* bdd_node;
	int correctValues = 0, incorrectValues = 0, combinations = 1;
	char* bFunction = NULL, * order = NULL;
	char inputValues[] = "0000000000000000000000000";
	char bddResult, mathResult;
	double totalCreateTime = 0, totalUseTime = 0, useTime = 0, averageReduction = 0;

	srand(time(NULL));

	printf("Enter number of variables: ");
	if (scanf("%d", &numOfVariables) != 1) {
		printf("Incorrect numOfVariables..\n");
		exit(4);
	}

	if ((fw = fopen("results.txt", "w")) == NULL) {
		printf("Error while creating results.txt file...\n");
		exit(3);
	}

	printf("Program is starting\n\n");

	for (int j = 0; j < 100; j++) {
		hashTable = init_table(hashTable, tableSize);
		correctValues = incorrectValues = num_of_nodes = 0;
		combinations = 1;
		tableSize = 50;
		bFunction = generateBFunction();
		order = letters_order(bFunction);
		order2 = order;
		one = zero = NULL;

		// time complexity of BDD create
		clock_t startClock = clock();
		bdd_node = BDD_create(bFunction, order, NULL);
		clock_t endClock = clock();
		double cpu_time_used = ((double)(endClock - startClock)) / CLOCKS_PER_SEC;
		//printf("\tCreate took around %f seconds\n", cpu_time_used);
		float createTime = cpu_time_used;
		totalCreateTime += cpu_time_used;


		bdd_root[j] = (BDD*)malloc(sizeof(BDD));

		if (!bdd_root[j])
			exit(2);

		bdd_root[j]->numOfNodes = num_of_nodes;
		bdd_root[j]->numOfVariables = (int)strlen(order);
		bdd_root[j]->root = bdd_node;
		strcpy(bdd_root[j]->startingFunction, bFunction);

		for (int i = 0; i < strlen(order); i++)
			combinations *= 2;

		for (int i = 0; i < combinations; i++) {
			strcpy(inputValues, decimalToBinary(i, strlen(order)));

			// time complexity of BDD use
			startClock = clock();
			bddResult = BDD_use(bdd_node, inputValues);
			endClock = clock();
			cpu_time_used = ((double)(endClock - startClock)) / CLOCKS_PER_SEC;
			useTime += cpu_time_used;

			mathResult = checkFunction(bFunction, order2, inputValues);

			if (bddResult == mathResult) correctValues++;
			else {
				incorrectValues++;
				/*printf("Position: %d\n", j + 1);
				printf("Incorrect Function: %s\n", bFunction);
				printf("Combination: %s\n", inputValues);
				printf("BDD - MATH: %c - %c\n\n", bddResult, mathResult);*/
			}
		}
		// printf("\tUse took around %f seconds\n", useTime);
		totalUseTime += useTime;
		useTime = 0;
		float ammount = 100 - (((float)bdd_root[j]->numOfNodes / combinations) * 100);
		averageReduction += ammount > 0 ? ammount : (ammount = 0);

		fprintf(fw, "%3d/100:\n", j + 1);
		fprintf(fw, "Function: %s\n", bdd_root[j]->startingFunction);
		fprintf(fw, "Order: %s\n", order);
		fprintf(fw, "Number of nodes: %d\n", bdd_root[j]->numOfNodes);
		fprintf(fw, "Number of variables: %d\n", bdd_root[j]->numOfVariables);
		fprintf(fw, "Number of correct results: %d/%d\n", correctValues, combinations);
		fprintf(fw, "Number of incorrect results: %d/%d\n", incorrectValues, combinations);
		fprintf(fw, "Create time: %.3f\n", createTime);
		fprintf(fw, "Use time: %.3f\n", useTime);
		fprintf(fw, "Percentual decomposition: %3.2f%% \n\n", ammount);
	}

	printf("\nSUCCESS\n\n");

	printf("\nFollowing data show results for 100 BDDs: \n");
	fprintf(fw, "\nFollowing data show results for 100 BDDs: \n");
	printf("Total CREATE time spent: %f seconds\n", totalCreateTime);
	fprintf(fw, "Total CREATE time spent : % f seconds\n", totalCreateTime);
	printf("Total USE time spent: %f seconds\n", totalUseTime);
	fprintf(fw, "Total USE time spent: %f seconds\n", totalUseTime);
	printf("Average reduction is: %f%%\n", averageReduction/100);
	fprintf(fw, "Average reduction is: %f%%\n", averageReduction / 100);

	fclose(fw);

	printf("All results are stored in \"program-directory/results.txt\"\n\n\n\n");

	return 0;
}

// Generates a random boolean function consisted of N-variables and 1 - 100 multiplication words
char* generateBFunction() {

	char* function = NULL;
	char newWord[2];

	newWord[1] = '\0';

	function = (char*)malloc(sizeof(char) * 3000);
	if (!function)
		exit(2);
	function[0] = '\0';

	for (int i = 0; i < rand() % 100 + 1; i++) {
		char word[25];

		word[0] = '\0';

		for (int j = 0; j < rand() % numOfVariables + 1; j++) {
			if (rand() % 2 == 0) newWord[0] = rand() % numOfVariables + 65;
			else newWord[0] = rand() % numOfVariables + 97;
			strcat(word, newWord);
		}
		if (i > 0)
			strcat(function, " + ");
		strcat(function, word);
	}

	return function;
}

// initialize new node
BDD_NODE* new_node(char func[MAX_FUNCT_SIZE], char letter) {
	BDD_NODE* node = (BDD_NODE*)malloc(sizeof(BDD_NODE));

	if (!node) exit(2);

	strcpy(node->function, func);
	node->letter = letter;
	node->lChild = node->rChild = NULL;

	return node;
}

// returns true if the word contains a given character
bool containsChar(char* word, char c) {
	for (int i = 0; i < strlen(word); i++) {
		if (word[i] == c) {
			return true;
		}
	}
	return false;
}

// returns true if contains capital or small letter
bool containsWord(char** arrOfBFunctions, char* word, int size) {
	for (int i = 0; i < size; i++) {
		if (!strcmp(arrOfBFunctions[i], word)) {
			return true;
		}
	}
	return false;
}

// split the given boolean function to the separate words
char** splitFunctToWords(char* bFunction, char* delimiter, int* arr_size) {
	char* p = (char*)malloc(sizeof(char) * strlen(bFunction));
	char** array = (char**)malloc(sizeof(char*) * MAX_FUNCT_SIZE);
	int i = 0;

	if (!p || !array)
		exit(2);

	strcpy(p, bFunction);
	p = strtok(p, delimiter); // splits the string by given delimiter

	// loop initializing the array of new words
	while (p != NULL) {
		array[i++] = p;
		p = strtok(NULL, delimiter);
		//printf("%s  ", array[i-1]);
		(*arr_size)++;
	}

	return array;
}

// removes all the characters that are equal to argument letter 
char* removeChar(char* word, char letter) {
	int a = 0, i = 0;
	char* newWord = (char*)malloc(sizeof(char) * strlen(word));

	newWord[0] = '\0';

	if (!newWord)
		exit(2);

	for (i = 0; i < strlen(word); i++) {
		if (word[i] != letter) {
			newWord[a++] = word[i];
			newWord[a] = '\0';
		}
	}

	strcpy(word, newWord);

	return newWord;
}

// removes every duplicate inside the word
char* removeDuplicateLetters(char* word) {
	char* newStr = (char*)malloc(MAX_WORD_SIZE * sizeof(char));
	int a = 0;

	if (!newStr)
		exit(2);

	if (strlen(word) == 0)
		return "";

	newStr[0] = '\0';

	for (int i = 0; i < strlen(word); i++) {
		if (!containsChar(newStr, word[i]))
			newStr[a++] = word[i];
	}

	newStr[a] = '\0';

	return newStr;
}

// removes duplicate words inside the given string
char* removeDuplicates(char* bFunction) {
	if (!strcmp(bFunction, ""))
		return "";
	char* newFunction = (char*)malloc(sizeof(char) * MAX_FUNCT_SIZE);
	int arrSize = 0;
	char** arrOfBWords = splitFunctToWords(bFunction, " + ", &arrSize);
	bool doNotAdd = false;

	if (!newFunction)
		exit(2);

	newFunction[0] = '\0';

	// loop all words and add only original ones
	for (int i = 0; i < arrSize - 1; i++) {
		for (int j = i + 1; j < arrSize; j++) {
			if (!strcmp(arrOfBWords[j], arrOfBWords[i])) {
				doNotAdd = true;
				break;
			}
		}
		if (!doNotAdd) {
			if (strcmp(newFunction, ""))
				strcat(newFunction, " + ");
			strcat(newFunction, arrOfBWords[i]);
		}
		doNotAdd = false;
	}
	if (strcmp(newFunction, ""))
		strcat(newFunction, " + ");
	strcat(newFunction, arrOfBWords[arrSize - 1]);

	char opposite[] = "a";

	// check if the new words contain a 1 character long word. If yes, evaluate it
	for (int i = 0; i < arrSize; i++) {
		if (strlen(arrOfBWords[i]) == 1) {
			if (arrOfBWords[i][0] >= 'a' && arrOfBWords[i][0] <= 'z')
				opposite[0] = arrOfBWords[i][0] - 32;
			else if (arrOfBWords[i][0] >= 'A' && arrOfBWords[i][0] <= 'Z')
				opposite[0] = arrOfBWords[i][0] + 32;
			if (containsWord(arrOfBWords, opposite, arrSize)) {
				return "1";
			}
		}
	}

	return newFunction;
}

// uses shanon decomposition to ease the function
char* fix_function(char function[MAX_FUNCT_SIZE], char letter, bool right_path) {
	if (!strcmp(function, ""))
		return "0";

	int arrSize = 0;
	char** arrOfBWords = splitFunctToWords(function, " + ", &arrSize); // split function to words
	char* result = (char*)malloc(sizeof(char) * MAX_FUNCT_SIZE);

	if (!result)
		exit(2);

	result[0] = '\0';

	char opposite[] = "x";

	// if function contains only 1 multiplication, return its value
	if (arrSize == 1) {
		arrOfBWords[0] = removeDuplicateLetters(arrOfBWords[0]);
		if (strlen(arrOfBWords[0]) == 1) {
			if (arrOfBWords[0][0] >= 'a' && arrOfBWords[0][0] <= 'z')
				return right_path ? "0" : "1";
			else if (arrOfBWords[0][0] >= 'A' && arrOfBWords[0][0] <= 'Z')
				return right_path ? "1" : "0";
		}
	}

	// remove all duplicates, evaluate the words that can be evaluated and remove not needed ones
	for (int i = 0; i < arrSize; i++) {
		arrOfBWords[i] = removeDuplicateLetters(arrOfBWords[i]);
		if (containsChar(arrOfBWords[i], letter) &&
			containsChar(arrOfBWords[i], letter + 32)) {
			continue;
		}
		if (right_path) {
			// capital letter equal to 1
			if (containsChar(arrOfBWords[i], letter)) {
				if (strlen(arrOfBWords[i]) == 1)
					return "1";
				else {
					if (strlen(result) != 0)
						strcat(result, " + ");
					strcat(result, removeChar(arrOfBWords[i], letter));
					continue;
				}
			}
			// small letter equal to 1
			else if (containsChar(arrOfBWords[i], letter + 32))
				continue;
		}
		else {
			// capital letter equal to 0
			if (containsChar(arrOfBWords[i], letter))
				continue;

			// small letter equal to 0
			else if (containsChar(arrOfBWords[i], letter + 32)) {
				if (strlen(arrOfBWords[i]) == 1)
					return "1";
				else {
					if (strlen(result) != 0) strcat(result, " + ");
					strcat(result, removeChar(arrOfBWords[i], letter + 32));
					continue;
				}
			}
		}
		if (strlen(result) != 0) strcat(result, " + ");
		strcat(result, arrOfBWords[i]);
	}

	result = removeDuplicates(result);

	return !strcmp(result, "") ? "0" : result;
}

// if function contains given character return true
bool function_contains_char(char* bFunction, char c) {
	for (int i = 0; i < strlen(bFunction); i++) {
		if (bFunction[i] == ' ')
			return false;
		else if (bFunction[i] == c)
			return true;
	}

	return false;
}

// creates BDD diagrame
BDD_NODE* BDD_create(char* bFunction, char* order, BDD_NODE* parent) {
	if (!strcmp(bFunction, "0")) {
		if (zero == NULL)
			createZero();
		return zero;
	}
	else if (!strcmp(bFunction, "1")) {
		if (one == NULL)
			createOne();
		return one;
	}
	else if (!strcmp(bFunction, "")) return NULL;

	BDD_NODE* diagram = NULL, * tmp_diagram = NULL;
	char tmpStr[MAX_WORD_SIZE];
	char leftFunction[MAX_FUNCT_SIZE];
	char rightFunction[MAX_FUNCT_SIZE];
	bool fixLeftChild = true, fixRightChild = true;

	// if function exists, find it in the table and return its node
	if (tmp_diagram = find(hashTable, bFunction, tableSize)) {
		return tmp_diagram;
	}

	diagram = new_node(bFunction, order[0]);
	diagram->parent = parent;

	// insert current node to the hash table
	hashTable = insert(hashTable, diagram, &tableSize, &num_of_nodes);

	// ease functions
	strcpy(leftFunction, fix_function(diagram->function, order[0], false));
	strcpy(rightFunction, fix_function(diagram->function, order[0], true));

	char beforeLastLetter = order[0];

	for (int i = 0; i < strlen(order); i++) {
		tmpStr[i] = order[i + 1];
		tmpStr[i + 1] = '\0';
	}

	// if it is tail, stop creating new children
	if (!strcmp(leftFunction, "0")) { if (zero == NULL) createZero(); diagram->lChild = zero; fixLeftChild = false; }
	else if (!strcmp(leftFunction, "1")) { if (one == NULL) createOne(); diagram->lChild = one; fixLeftChild = false; }
	if (!strcmp(rightFunction, "0")) { if (zero == NULL) createZero(); diagram->rChild = zero; fixRightChild = false; }
	else if (!strcmp(rightFunction, "1")) { if (one == NULL) createOne(); diagram->rChild = one; fixRightChild = false; }


	// reduce the nodes which have pointer to the same value
	if (!strcmp(leftFunction, rightFunction)) {
		delete(hashTable, diagram->function, tableSize, &num_of_nodes);

		if (diagram->parent == NULL) {
			diagram = NULL;
			diagram = BDD_create(leftFunction, tmpStr, diagram);
		}
		else {
			return BDD_create(leftFunction, tmpStr, diagram);
		}
	}

	// if functions are different
	if (strcmp(leftFunction, rightFunction)) {
		if (fixLeftChild) {
			bool skip = false;
			for (int i = 0; i < strlen(tmpStr); i++) {
				if (containsChar(leftFunction, tmpStr[i]) ||
					containsChar(leftFunction, tmpStr[i] + 32)) {
					char newStr[MAX_FUNCT_SIZE];
					int k = 0;

					for (int j = i; j < strlen(tmpStr); j++) {
						newStr[k++] = tmpStr[j];
					}
					newStr[k] = '\0';
					diagram->lChild = BDD_create(leftFunction, newStr, diagram);
					diagram->lChild->parent = diagram;
					skip = true;
					break;
				}
			}
			if (!skip) {
				diagram->lChild = BDD_create(leftFunction, tmpStr, diagram);
				diagram->lChild->parent = diagram;
			}
		}
		if (fixRightChild) {
			bool skip = false;
			for (int i = 0; i < strlen(tmpStr); i++) {
				if (containsChar(rightFunction, tmpStr[i]) ||
					containsChar(rightFunction, tmpStr[i] + 32)) {
					char newStr[MAX_FUNCT_SIZE];
					int k = 0;

					for (int j = i; j < strlen(tmpStr); j++) {
						newStr[k++] = tmpStr[j];
					}
					newStr[k] = '\0';
					diagram->rChild = BDD_create(rightFunction, newStr, diagram);
					diagram->rChild->parent = diagram;
					skip = true;
					break;
				}
			}
			if (!skip) {
				diagram->rChild = BDD_create(rightFunction, tmpStr, diagram);
				diagram->rChild->parent = diagram;
			}
		}
	}

	return diagram;
}

// evaluates the current bdd by its input combination
char BDD_use(BDD_NODE* bdd, char* inputValues) {
	BDD_NODE* tmp = bdd;

	for (int i = 0; i < strlen(inputValues) + 1; i++) {
		if (!strcmp(tmp->function, "0")) return '0';
		else if (!strcmp(tmp->function, "1")) return '1';
		if (!containsChar(tmp->function, order2[i]) &&
			!containsChar(tmp->function, order2[i] + 32))
			continue;

		if (inputValues[i] == '0')
			tmp = tmp->lChild;
		else if (inputValues[i] == '1')
			tmp = tmp->rChild;
		else return 'E';
	}

	return 'X';
}

// create leaf node
void createOne() {
	one = (BDD_NODE*)malloc(sizeof(BDD_NODE));

	if (!one)
		exit(2);

	strcpy(one->function, "1");
	one->letter = '1';
	one->lChild = one->rChild = NULL;
	num_of_nodes++;
}

// create leaf node
void createZero() {
	zero = (BDD_NODE*)malloc(sizeof(BDD_NODE));

	if (!zero)
		exit(2);

	strcpy(zero->function, "0");
	zero->letter = '0';
	zero->lChild = zero->rChild = NULL;
	num_of_nodes++;
}

// convert a decimal number to its binary form
char* decimalToBinary(int num, int bits) {
	int i = 0, j = 0, a = 0;
	char binNum[32] = { 0 }, newBinNum[32];

	if (num == 0) {
		for (int i = 0; i < bits; i++) {
			binNum[i] = '0';
			binNum[i + 1] = '\0';
		}
		return binNum;
	}

	for (; num > 0; ) {
		binNum[i++] = (num % 2) + '0';
		binNum[i] = '\0';
		num /= 2;
	}

	for (; j < bits - i; j++) {
		newBinNum[j] = '0';
		newBinNum[j + 1] = '\0';
	}
	for (; j < bits; j++) {
		newBinNum[j] = binNum[--i];
		newBinNum[j + 1] = '\0';
	}
	return newBinNum;
}

// create the alphabetical order of  the given boolean function
char* letters_order(char* letters) {
	char* newOrder = (char*)malloc(sizeof(char) * strlen(letters));
	int a = 0;

	if (!newOrder)
		exit(2);

	for (int i = 0; i < strlen(letters); i++) {
		if (letters[i] >= 'a' && letters[i] <= 'z') {
			if (!containsChar(newOrder, letters[i] - 32)) {
				newOrder[a++] = letters[i] - 32;
				newOrder[a] = '\0';
			}
		}
		else if (letters[i] >= 'A' && letters[i] <= 'Z') {
			if (!containsChar(newOrder, letters[i])) {
				newOrder[a++] = letters[i];
				newOrder[a] = '\0';
			}
		}
	}

	for (int i = 0; i < strlen(newOrder) - 1; i++) {
		for (int j = 0; j < strlen(newOrder) - 1 - i; j++) {
			if (newOrder[j] > newOrder[j + 1]) {
				char tmp = newOrder[j];
				newOrder[j] = newOrder[j + 1];
				newOrder[j + 1] = tmp;
			}
		}
	}

	return newOrder;
}

// bz mathematical operations evaluate the truth value of the function
char checkFunction(char* bFunction, char* order, char* values) {
	int size = 0, a = 0, b = 0;
	char** words = splitFunctToWords(bFunction, " + ", &size);
	char newBFunction[3000];

	// loop all words
	for (int i = 0; i < size; i++) {
		// loop all characters in the word
		for (int j = 0; j < strlen(words[i]); j++) {
			// loop all values (000101..)
			for (int k = 0; k < strlen(order); k++) {
				if (words[i][j] == order[k] && values[k] == '0') words[i][j] = '0';
				else if (words[i][j] == order[k] && values[k] == '1') words[i][j] = '1';
				else if (words[i][j] == order[k] + 32 && values[k] == '0') words[i][j] = '1';
				else if (words[i][j] == order[k] + 32 && values[k] == '1') words[i][j] = '0';
				if (words[i][j] == '1' || words[i][j] == '0') {
					newBFunction[b++] = words[i][j];
					break;
				}
			}
		}

		if (containsChar(words[i], '1') && !containsChar(words[i], '0'))
			return '1';
	}

	return '0';
}
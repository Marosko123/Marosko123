#define _CRT_SECURE_NO_WARNINGS
#include "avlTree.h"
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <time.h>

#define NAME_LENGTH 12

typedef struct People {
    char name[NAME_LENGTH];

    // Chaining Hash Table
    struct People* next;

    // AVL Tree
    unsigned long age;
    int height;
    struct People* left;
    struct People* right;
}PEOPLE;

PEOPLE* newNode(unsigned int age);
PEOPLE* insert_avl(PEOPLE* node, unsigned int age);
bool find_avl(PEOPLE* node, unsigned int age);
PEOPLE* delete_avl(PEOPLE* node, unsigned int age);

PEOPLE* getSuccessor(PEOPLE* node) {
    PEOPLE* tmp = node;

    while (tmp->left != NULL)
        tmp = tmp->left;
    return tmp;
}

bool find_avl(PEOPLE* node, unsigned int age) {
    if (node == NULL)
        return false;
    else if (node->age == age)
        return true;
    else if (node->age > age)
        return find_avl(node->left, age);
    else if (node->age < age)
        return find_avl(node->right, age);
}

int getNodeHeight(PEOPLE* node) {
    return node == NULL ? 0 : node->height;
}

void print_elements(PEOPLE* node) {
    if (node != NULL) {
        print_elements(node->left);
        printf("%d ", node->age);
        print_elements(node->right);
    }
}

PEOPLE* newNode(unsigned int age) {
    PEOPLE* newNode = (PEOPLE*) malloc(sizeof(PEOPLE));
    newNode->age = age;
    newNode->left = NULL;
    newNode->right = NULL;
    newNode->height = 1;
    return (newNode);
}

PEOPLE* rotateRight(PEOPLE* node) {
    PEOPLE* chldNde = node->left;
    PEOPLE* chldNdeChld = chldNde->right;

    chldNde->right = node;
    node->left = chldNdeChld;

    node->height = ((getNodeHeight(node->left) > getNodeHeight(node->right)) 
        ? getNodeHeight(node->left) : getNodeHeight(node->right)) + 1;
    chldNde->height = ((getNodeHeight(chldNde->left) > getNodeHeight(chldNde->right)) 
        ? getNodeHeight(chldNde->left) : getNodeHeight(chldNde->right)) + 1;

    return chldNde;
}

PEOPLE* rotateLeft(PEOPLE* node) {
    PEOPLE* chldNde = node->right;
    PEOPLE* chldNdeChld = chldNde->left;

    chldNde->left = node;
    node->right = chldNdeChld;

    node->height = ((getNodeHeight(node->left) > getNodeHeight(node->right)) 
        ? getNodeHeight(node->left) : getNodeHeight(node->right)) + 1;
    chldNde->height = ((getNodeHeight(chldNde->left) > getNodeHeight(chldNde->right)) 
        ? getNodeHeight(chldNde->left) : getNodeHeight(chldNde->right)) + 1;

    return chldNde;
}

PEOPLE* insert_avl(PEOPLE* node, unsigned int age) {
    if (node == NULL)
        return (newNode(age));

    if (age < node->age)
        node->left = insert_avl(node->left, age);
    else if (age > node->age)
        node->right = insert_avl(node->right, age);
    else
        return node;

    node->height = 1 + ((getNodeHeight(node->left) > getNodeHeight(node->right)) 
        ? getNodeHeight(node->left) : getNodeHeight(node->right));

    int nodeBalance = 0;
    if (node != NULL) {
        nodeBalance = getNodeHeight(node->left) - getNodeHeight(node->right);
    }
    if (nodeBalance > 1 && age < node->left->age)
        return rotateRight(node);
    else if (nodeBalance < -1 && age > node->right->age)
        return rotateLeft(node);
    else if (nodeBalance < -1 && age < node->right->age) {
        node->right = rotateRight(node->right);
        return rotateLeft(node);
    }
    else if (nodeBalance > 1 && age > node->left->age) {
        node->left = rotateLeft(node->left);
        return rotateRight(node);
    }

    return node;
}

PEOPLE* delete_avl(PEOPLE* node, unsigned int age) {
    if (node == NULL)
        return node;
    if (age < node->age)
        node->left = delete_avl(node->left, age);
    else if (age > node->age)
        node->right = delete_avl(node->right, age);

    else {
        if ((node->left == NULL) || (node->right == NULL)) {
            PEOPLE* temp = node->left ? node->left : node->right;

            if (temp != NULL) {
                *node = *temp;
            }
            else {
                temp = node;
                node = NULL;
            }
            free(temp);
        }
        else {
            PEOPLE* successor = getSuccessor(node->right);
            node->age = successor->age;
            node->right = delete_avl(node->right, successor->age);
        }
    }
    if (node != NULL) {
        node->height = 1 + ((getNodeHeight(node->left) > getNodeHeight(node->right))
            ? getNodeHeight(node->left) : getNodeHeight(node->right));

        int nodeBalance = 0;
        if (node != NULL)
            nodeBalance = getNodeHeight(node->left) - getNodeHeight(node->right);

        int leftNodeBalance = 0;
        int rightNodeBalance = 0;

        if (node->left != NULL)
            leftNodeBalance = getNodeHeight(node->left->left) - getNodeHeight(node->left->right);
        if (node->right != NULL)
            rightNodeBalance = getNodeHeight(node->right->left) - getNodeHeight(node->right->right);

        if (nodeBalance < -1 && rightNodeBalance <= 0)
            return rotateLeft(node);
        else if (nodeBalance > 1 && leftNodeBalance >= 0)
            return rotateRight(node);
        else if (nodeBalance < -1 && rightNodeBalance > 0) {
            node->right = rotateRight(node->right);
            return rotateLeft(node);
        }
        else if (nodeBalance > 1 && leftNodeBalance < 0) {
            node->left = rotateLeft(node->left);
            return rotateRight(node);
        }
        return node;
    }
    else
        return node;
}

void avlTree(PEOPLE* p, int attempts) {
    PEOPLE* node = NULL;

	printf("Start AVL TREE function\n");
	
	clock_t start = clock();
	clock_t end = clock();
	double cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
	printf("\tINIT TIME: %f\n", cpu_time_used);

	start = clock();
	for (int i = 0; i < attempts; i++)
        node = insert_avl(node, p[i].age);
	end = clock();
	cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;

	printf("\tInsert took around %f seconds to execute %d inserts\n", cpu_time_used, attempts);

   // print_elements(node);
   // printf("\n");

	start = clock();
    for (int i = 0; i < attempts; i++)
       find_avl(node, p[i].age);
	
	end = clock();
	cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
	printf("\tFind took around %f seconds to execute %d findings\n", cpu_time_used, attempts);

	start = clock();
    for (int i = 0; i < attempts; i++)
        node = delete_avl(node, p[i].age);
	
	end = clock();
	cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
	printf("\tDelete took around %f seconds to execute %d deletions\n", cpu_time_used, attempts);

	printf("End AVL TREE function\n\n\n\n\n");
}

#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void findGoodCombinaisons(*str) {

}

// vietnameseAlgorithm
char *vietnameseAlgorithm(char *enteredNumbers, char **originalCalculus)
{
    // number stock to use in the algorithm
    int numberStock[10] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    char *arrayToString = malloc(28 * sizeof(char));
    arrayToString[0] = '\0';

    for (int i = 0; i < 27; i++)

        arrayToString = strcat(arrayToString, originalCalculus[i]);
    }
    printf("Array to string: %s\n", arrayToString);
    free(arrayToString);

    // printf("Array to string: %s\n", arrayToString);
    return enteredNumbers;
}

char *numberStringFunction(char *enteredNumbers)
{
    char *numberString[] = {"X", "+", "13", "*", "X", "/", "X", "+",
                            "X", "+", "12", "*", "X", "-", "X", "-", "11", "+", "X", "*", "X",
                            "/", "X", "-", "10", "=", "66"};

    vietnameseAlgorithm(enteredNumbers, numberString);
    return *numberString;
}

int main(int ac, char **av)
{
    if (ac != 2)
    {
        printf("Usage: %s <string>\n", av[0]);
        return 1;
    }
    char *str = av[1];
    int len = strlen(str);
    if (len < 1)
    {
        printf("String is empty\n");
        return 1;
    }
    else
    {
        numberStringFunction(str);
    }
}

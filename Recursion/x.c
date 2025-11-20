#include <stdio.h>
#include <stdlib.h>

struct Example
{
    char *str;
};

int main()
{
    struct Example *x = (struct Example *)malloc(sizeof(struct Example));
    x->str = "hello";
    struct Example **y = &x;

    printf("%s", (*y)->str);

    return 0;
}
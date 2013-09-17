#include <stdio.h>

int main ()
{
  int length = 5;
  char letters[length] = {'a','z','b','b'};

  for(int x = 0; x < length; x++)
  {
    printf("char[%d] = %c\n", x, letters[x]);
  }

return 0;
}

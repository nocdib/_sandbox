#include <stdio.h>
long double factorial(int num);

int main(void) {
 
  int number;
  long double y;

  printf("Enter a number to find its factorial: ");
  scanf("%i", &number);
  y = factorial(number);
  printf("%i! = %LG\n", number, y);
}

long double factorial(int num){

  long fact; 
  if(num > 1){
   fact = (long)(num * factorial(num-1));
   return fact;
  }
  else
    return 1;
}

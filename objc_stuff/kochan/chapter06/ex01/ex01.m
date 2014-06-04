// Take two numbers and see if the first is evenly divisible by the second

#import <Foundation/Foundation.h>
#include <stdlib.h>

int main(int argc, char *argv[])
{
	NSAutoreleasePool * pool = [[NSAutoreleasePool alloc] init];

	int num1, num2;

	printf("Enter a number: ");	
	if (scanf("%d", &num1) != 1)
		exit(1);

	printf("Enter a number: ");
	if (scanf("%d", &num2) != 1)
	   exit(1);

	if (num1 % num2 == 0)
		printf("The first number is evenly divisible by the second.\n");
	else
		printf("The first number is not evenly divisible by the second.\n");
	
	[pool drain];
	return 0;
}

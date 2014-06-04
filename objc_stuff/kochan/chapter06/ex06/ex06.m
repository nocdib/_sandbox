// take an integer and spell out each digit as a word

#import <Foundation/Foundation.h>
#include <string.h>
#include <stdlib.h>

const char* digitWord(unsigned int num);
void recursive(unsigned int num);


int main(int argc, char *argv[])
{
	NSAutoreleasePool * pool = [[NSAutoreleasePool alloc] init];

	int num;
	printf("Enter a number: ");
	
	if (scanf("%u", &num) == 1)
		recursive(num);

	printf("\n");
	[pool drain];
	return 0;
}

void recursive(unsigned int num)
{
	int mod = num % 10;
	int div = num / 10;

	if(div > 0)
	{		
	 	recursive(div);
		printf(" %s", digitWord(mod));
	}
	else
		printf("%s", digitWord(mod));
}

const char* digitWord(unsigned int num)
{
	char * retval;
	switch(num)
	{
		case 1:
			retval = "One";
			break;
      case 2:
         retval = "Two";
			break;
		case 3:
		   retval = "Three";
			break;
		case 4:
         retval = "Four";
			break;
		case 5:
         retval = "Five";
			break;
		case 6:
         retval = "Six";
			break;
		case 7:
	      retval = "Seven";
			break;
		case 8:
	      retval = "Eight";
			break;
		case 9:
	      retval = "Nine";
			break;
		case 0:
			retval = "Zero";
			break;
	}
	return retval;
}

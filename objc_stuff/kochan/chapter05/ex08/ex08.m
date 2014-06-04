// A modification of Exercise 8 where an integer's digits are listed indidually without string manipulation

#import <Foundation/Foundation.h>

int main(int argc, char *argv[])
{
	NSAutoreleasePool * pool = [[NSAutoreleasePool alloc] init];

	int num;
	printf("Enter a number: ");
	
	if (scanf("%d", &num) == 1)
		recursive(num);

	printf("\n");
	[pool drain];
	return 0;
}

void recursive(int num)
{
	int mod = num % 10;
	int div = num / 10;
	printf("mod = %d, div = %d\n", mod, div);

	if(div > 0)
	{		
	 	recursive(div);
		printf(" %d", mod);
	}
	else
		printf("%d", mod);
}

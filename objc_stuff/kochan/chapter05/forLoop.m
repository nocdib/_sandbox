#import <Foundation/Foundation.h>

int main(int argc, char *argv[])
{
	NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];

	int i, target;

	printf("Enter a number to count up to: ");
	scanf("%u", &target);

	for (i=0; i<=target; i++)
	{
		printf("...%i", i);		  
	}

	printf("\n");
	[pool drain];	
	return 0;
}

#import <Foundation/Foundation.h>
#import <math.h>

int main(int argc, char *argv[])
{
	NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];

	unsigned int n = 5;

	NSLog(@"---\t----------");
	NSLog(@"n\t\tn-squared");
	NSLog(@"---\t----------");

	for (n=1; n<=10; n++)
		NSLog(@"%u\t\t%.f",n,pow(n,2));

	[pool drain];	
	return 0;
}

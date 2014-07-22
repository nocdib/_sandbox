
#import "Foo.h"

int gGlobalVar = 5;

int main(int argc, char *argv[])
{
	NSAutoreleasePool * pool = [[NSAutoreleasePool alloc] init];
	Foo *myFoo = [[Foo alloc] init];
	printf("%d\n", gGlobalVar);
	
	[myFoo setgGlobalVar: 100];
	printf("%d\n", gGlobalVar);

	[myFoo release];	
	[pool drain];
	return 0;
}

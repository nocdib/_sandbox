#import "synthesize.m"

int main(int argc, char * argv[])
{
	NSAutoreleasePool * pool = [[NSAutoreleasePool alloc] init];
	
	Person * greg = [Person new];
	[greg setFirstName: @"Greg"];
	[greg setLastName: @"Joshua"];
	[greg setAge: 32];

	[greg print];
	NSLog(@"From the main function: %@, %@, %d", greg.firstName, [greg lastName], [greg age]);

	[pool drain];
	return 0;
}

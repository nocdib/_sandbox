#import "idType.h"

int main (int argc, char *argv[])
{
	NSAutoreleasePool * pool = [[NSAutoreleasePool alloc] init];
	Primate * origin = [Primate new];
	[origin speak];
	[origin setName:@"Cephus"];
	NSLog(@"%@ is of type %@", [origin getName], [origin getSpecies]);

	Man * greg = [Man new];
	[greg speak];
	[greg setName: @"Greg"];
	NSLog(@"His name is %@",[greg getSpecies]);

	//printf("The %@ spoke.\n", greg.species);
	Chimp * drZayas = [Chimp new];
	[drZayas speak];
	printf("The chimp spoke.\n");
	[pool drain];
	return 0;
}

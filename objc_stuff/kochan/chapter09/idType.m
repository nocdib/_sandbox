#import "idType.h"

int main (int argc, char *argv[])
{
	NSAutoreleasePool * pool = [[NSAutoreleasePool alloc] init];
	Primate * origin = [Primate new];
	[origin setName:@"Cephus"];
	NSLog(@"%@ is of type %@", [origin name], [origin species]);
	NSLog(@"%@ says: %@", [origin name], [origin speak]);

	Man * greg = [Man new];
	NSLog(@"%@ is of type %@",[greg name],[greg species]);
	[greg setName: @"Greg"];
	NSLog(@"Name change: %@ is of type %@",[greg name],[greg species]);
	NSLog(@"%@ says: %@",[greg name],[greg speak]);
	
	
	Chimp * drZayas = [Chimp new];
	drZayas.name = @"Doctor Zayas";
	NSLog(@"%@ is of type %@", drZayas.name, drZayas.species);
	NSLog(@"%@ says: %@", drZayas.name, drZayas.speak);
	printf("The chimp spoke.\n");
	
	[pool drain];
	return 0;
}

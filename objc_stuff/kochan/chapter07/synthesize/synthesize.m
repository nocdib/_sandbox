// Testing the @property and @synthesize directives

#import "synthesize.h"

@implementation Person

@synthesize firstName, lastName, age;

-(void) print
{
	NSLog(@"First Name: %@", firstName);
	NSLog(@"Last Name: %@", lastName);
	NSLog(@"Age: %d", age);
}

@end

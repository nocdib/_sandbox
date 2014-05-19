#import "Fraction.h"

@implementation Fraction

- (void) print
{
	NSLog(@"%i/%i",numerator, denominator);
}

- (void) setNumerator: (int) n
{
	numerator = n;
}

- (void) setDenominator: (int) d
{
	denominator = d;
}

@end

int main (int argc, const char *argv[])
{
	NSAutoreleasePool * pool = [[NSAutoreleasePool alloc] init];
	Fraction *example = [Fraction new];
	
	[example setNumerator: 5];
	[example setDenominator: 10];
	[example print];

	[pool drain];
	return 0;
}

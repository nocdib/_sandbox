#import "Fraction.h"

@implementation Fraction

- (void) print
{
	NSLog(@"%i/%i",numerator, denominator);
}

- (NSString*) returnFraction
{
  return [NSString stringWithFormat:@"%i/%i", numerator, denominator];
}

- (void) setNumerator: (int) n  setDenominator: (int) d
{
	numerator = n;
	denominator = d;
}

- (int) numerator
{
	return numerator;
}

- (int) denominator 
{
	return denominator;
}

@end

int main (int argc, const char *argv[])
{
	NSAutoreleasePool * pool = [[NSAutoreleasePool alloc] init];
	Fraction *example = [Fraction new];
	
	[example setNumerator: 5
			   setDenominator: 10];
	
	[example print];

	NSLog(@"Once again, the fraction is: %@", [example returnFraction]);
	NSLog(@"The numerator is %i and the denominator is %i", [example numerator], [example denominator]);

	[example release];

	[pool drain];
	return 0;
}

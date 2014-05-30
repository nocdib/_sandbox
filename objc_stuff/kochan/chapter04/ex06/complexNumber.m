#import "complexNumber.h"

@implementation complexNumber

- (void) setReal: (double) a
{
	real = a;
}		  

- (void) setImaginary: (double) b
{
	imaginary = b;
}

- (void) print
{
	NSLog(@"%0.2f + %0.2fi",[self real],[self imaginary]);
}

- (double) real
{
	return real;
}

- (double) imaginary
{
	return imaginary;
}
@end


int main(int argc, char *argv[])
{
	NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];

	complexNumber *complex = [complexNumber new];
	[complex setReal:1];
	[complex setImaginary:2];
	[complex print];

	[pool drain];
	return 0;
}


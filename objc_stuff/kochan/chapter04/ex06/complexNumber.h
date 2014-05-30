#import <Foundation/Foundation.h>

@interface complexNumber: NSObject
{
	double real;
	double imaginary;
}

- (void) setReal: (double) a;
- (void) setImaginary: (double) b;
- (void) print;
- (double) real;
- (double) imaginary;


@end

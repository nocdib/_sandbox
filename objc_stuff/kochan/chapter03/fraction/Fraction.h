#import <Foundation/Foundation.h>

@interface Fraction: NSObject
{
	int numerator;
	int denominator;
}
-(void) print;
-(NSString*) returnFraction;
-(void) setNumerator: (int) n  setDenominator: (int) d;
-(int) numerator;
-(int) denominator;

@end

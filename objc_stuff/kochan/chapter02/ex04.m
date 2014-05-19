#import <Foundation/Foundation.h>

int main(int argc, const char *argv[]){
	NSAutoreleasePool * pool = [[NSAutoreleasePool alloc] init];
	int num1, num2;
	num1 = 87;
	num2 = 15;
	NSLog(@"%i - %i = %i",num1, num2, num1-num2);
	[pool drain];
	return 0;
}

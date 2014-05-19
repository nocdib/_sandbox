#import <Foundation/Foundation.h>

int main(int argc, const char *argv[]) {

	NSAutoreleasePool * pool = [[NSAutoreleasePool alloc] init];
	NSLog(@"In Objective-C, lowercase letters are significant.");
	NSLog(@"main is where program execution begins.");
	NSLog(@"...");
	[pool drain];
	return 0;	
}

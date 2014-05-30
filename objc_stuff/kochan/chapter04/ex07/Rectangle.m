#import "Rectangle.h"

int main(int argc, char *argv[])
{
	NSAutoreleasePool *pool = [NSAutoreleasePool new];

	Rectangle *rect = [Rectangle new];
	[rect setWidth:4];
	[rect setHeight:3];
	
	NSLog(@"The area is %i units and the permieter is %i units", [rect area], [rect perimeter]);

	[pool drain];
	return 0;

}

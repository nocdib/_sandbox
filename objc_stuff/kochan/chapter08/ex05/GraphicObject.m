#import "GraphicObject.h"

int main(int argc, char * argv[])
{
	NSAutoreleasePool * pool = [NSAutoreleasePool new];

	GraphicObject * shape = [GraphicObject new];
	Rectangle * rect = [Rectangle new];
	Circle * circ = [Circle new];

	NSLog(@"Shape has %d sides", [shape sides]);
	[rect setWidth: 4];
	[rect setHeight: 5];
	NSLog(@"Rect has %d sides, a width of %d, a height of %d, the area is %d, and the perimeter is %d.", [rect sides], [rect width], [rect height], [rect area], [rect perimeter]);
	[circ setRadius: 5];
	NSLog(@"Circ has a radius of %d, and area of %.2f and a circumference of %.2f", [circ radius], [circ area], [circ perimeter]);


	[shape dealloc];
	[rect dealloc];
	[circ dealloc];

	[pool drain];
	return 0;
}

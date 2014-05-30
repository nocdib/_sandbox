#import <Foundation/Foundation.h>

@interface Rectangle: NSObject
{
	int width;
	int height;
}
- (void) setWidth: (int) w;
- (void) setHeight: (int) h;
- (int) width;
- (int) height;
- (int) area;
- (int) perimeter;
@end

@implementation Rectangle

- (void) setWidth: (int) w;
{
	width = w;
}

- (void) setHeight: (int) h;
{
	height = h;
}

- (int) width;
{
	return width;
}

- (int) height;
{
	return height;
}

- (int) area;
{
	return width * height;
}

- (int) perimeter;
{
	return width * 2 + height * 2;
}

@end

#import <Foundation/Foundation.h>
#include <math.h>

@interface GraphicObject: NSObject
{
	int fillColor;
	BOOL filled;
	int lineColor;
	int sides;
	float area;
	float perimeter; //circumference for spheres
}

@property int fillColor, lineColor;
@property BOOL filled;

@end

	@implementation GraphicObject

	@synthesize fillColor, filled, lineColor;

	- (int) sides
	{
		sides = 0;
		return sides;
	}

	@end

@interface Rectangle: GraphicObject
{
	int width, height;
}	

@property int width, height;

- (int) perimeter;
- (int) area;

@end

	@implementation Rectangle

	@synthesize width, height;
	- (int) sides
	{
		sides = 4;
		return sides;
   }

	- (int) perimeter
	{	
		return (width + height) * 2;
	}
	
	- (int) area
	{
		return (width * height);
	}

	@end


@interface Circle: GraphicObject
{
	int radius;
}

@property int radius;

- (float) perimeter; // a.k.a circumference
- (float) area;

@end

   @implementation Circle

   @synthesize radius;

	- (int) sides
	{
		sides = 1;
		return sides;
   }

   - (float) perimeter
   {
		return (float)(M_PI * 2 * radius);
	}

   - (float) area
	{
		return (float) (powf(radius,2) * M_PI);
	}

   @end


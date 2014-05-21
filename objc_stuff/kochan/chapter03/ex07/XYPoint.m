#import "XYPoint.h"

@implementation XYPoint

-(void) print
{
	NSLog(@"(%i,%i)",x,y);
}
-(void) setX: (int) a
{
	x = a;
}

-(void) setY: (int) b
{
	y = b;
}

-(int) getX
{
	return x;
}

-(int) getY
{
	return y;
}

-(void) setBothX:(int) a andY:(int) b
{
	x = a;
	y = b;
}


@end

int main(int argc, char *argv[])
{
	NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];

	XYPoint *origin = [[XYPoint alloc] init];
	[origin setBothX:1 andY:1];
	[origin print];
	[origin setX:0];
	[origin setY:0];
	[origin print];

	NSLog(@"The point's coordinates are (%i,%i)",[origin getX], [origin getY]);

	[pool drain];	
	return 0;
}


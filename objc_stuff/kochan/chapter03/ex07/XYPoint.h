#import <Foundation/Foundation.h>

@interface XYPoint: NSObject
{
	int x;
	int y;
}

-(void) print;
-(void) setX: (int) a;
-(void) setY: (int) b;
-(int) getX;
-(int) getY;
-(void) setBothX:(int) a andY:(int) b;

@end

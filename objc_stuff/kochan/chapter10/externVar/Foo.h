#import <Foundation/Foundation.h>

@interface Foo: NSObject
{
	NSString *name;
	
	@private
	int IDnumber;
}
-(void) setgGlobalVar: (int) val;
@end

@implementation Foo

-(id) init
{
	name = @"Default";
	IDnumber = 0;
	return self;
}

-(void) setgGlobalVar: (int) val;
{
	extern int gGlobalVar;
	gGlobalVar = val;
}

@end

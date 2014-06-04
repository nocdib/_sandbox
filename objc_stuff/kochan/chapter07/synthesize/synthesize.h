// Testing the @property and @synthesize directives

#import <Foundation/Foundation.h>

@interface Person : NSObject
{
	NSString *firstName, *lastName;
	int age;
}

@property NSString *firstName, *lastName;
@property int age;

-(void) print;

@end

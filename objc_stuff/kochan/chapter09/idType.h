#import <Foundation/Foundation.h>

@interface Primate : NSObject
{
	NSString * name;
	NSString * species;
}

@property (assign) NSString *name, *species;

- (id) init;
- (NSString *) speak;

@end

@implementation Primate

@synthesize name, species;

- (id) init
{
	name = @"none";
	species = @"generic";
	return self;
}
-(NSString *) speak
{
	return @".....";
}


@end


// ------- MAN	----------------

@interface Man : Primate
@end

@implementation Man

-(id) init
{
	species = @"Homo Sapien";
	name = @"Adam";
	return self;
}

- (NSString *) speak
{
	return [NSString stringWithFormat:@"Hello. My name is %@", [self name]];
}

@end

// ---------	Chimp -------------------

@interface Chimp : Primate
@end

@implementation Chimp

-(id) init
{
	name = @"Bubbles";
	species = @"Chimpanzee";
	return self;
}

-(NSString *) speak
{
	return @"Ooh ooh aaah aaaahhh!";
}
@end


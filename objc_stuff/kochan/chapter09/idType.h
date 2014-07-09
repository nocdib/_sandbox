#import <Foundation/Foundation.h>

@interface Primate : NSObject
{
	NSString * name;
	NSString * species;
}

//@property NSString * name;
//@property NSString * species;
- (id) init;
- (void) speak;
- (void) setName: (NSString *) newName;
- (NSString *) getName;
- (NSString *) getSpecies;

@end

@implementation Primate

- (id) init
{
	species = @"none";
	return nil;
}
-(void) speak
{
	printf(".....\n");
}

- (void) setName: (NSString *) newName;
{
	name = newName;
}
- (NSString *) getName;
{
	return name;
}
- (NSString *) getSpecies;
{
	return species;
}

@end


// ------- MAN	----------------

@interface Man : Primate
{
}
-(id) init;
-(void) speak;
@end

@implementation Man

-(id) init
{
	species = @"Homo Sapien";
	return 0;
}

- (void) speak
{
	printf("Hello.\n");
}

@end

// ---------	Chimp -------------------

@interface Chimp : Primate
{
}
@end

@implementation Chimp

-(id) init
{
	species = @"Chimpanzee";
	return 0;
}

-(void) speak
{
	printf("Ooh ooh aaah aaaahhh!");
}
@end



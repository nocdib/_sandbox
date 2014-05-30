#import <Foundation/Foundation.h>

int main(int argc, char *argv[])
{

	int farenheit = 90;
	float celsius = (farenheit - 32)/1.8;

	NSLog(@"%i degrees Farenheit is %0.2f degrees Celsius.", farenheit, celsius);
	return 0;
}

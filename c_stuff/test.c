#include <stdio.h>


void doSomething ( char **string )
{
		    sprintf(string,"some string");
}  

int main ( void )
{
		    char * origString[50];

			   doSomething ( origString );
				  printf ( "%s\n", origString );

				    return 0;
}

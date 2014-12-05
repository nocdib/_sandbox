#!/usr/bin/env python

# Using if-statements

iNumber = -10 

if iNumber > 0:
    print "The number is positive"
    if iNumber >= 1 and iNumber <=5:
        print "The number is between 1 and 5"
    elif iNumber >= 6 and iNumber <=10:
        print "The number is between 6 and 10"
    else:
        print "The number is not between 1 and 10"
elif iNumber < 0:
    print "The number is negative"
else:
    print "The number is zero"



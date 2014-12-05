#!/usr/bin/env python

# Using format specifiers to print text

sFirstName = "Jack"
sLastName = "Johnson"
iAge = 30

# This way can become cumbersome
print sFirstName + " " + sLastName + " is " + str(iAge) + " years old."

# This is one way of using format specifiers
print "%s %s is %d years old." % (sFirstName, sLastName, iAge)

# This is another way of using them
print "{0:s} {1:s} is {2:0.2f} years old.".format(sFirstName, sLastName, iAge)

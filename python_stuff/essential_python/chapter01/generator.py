#!/usr/bin/env python

# Using a generator

def generator(n):
    print "Countdown!"
    while n > 0:
        yield n
        n -= 1

x = generator(5)
print x.next()
#print x.next()

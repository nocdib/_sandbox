#!/usr/bin/python

# Spotify Puzzle
# Take a decimal integer, convert it to binary, reverse it then convert it
# back to a decimal integer

import sys
for line in sys.stdin:
  rev = int(line)
  rev = bin(rev)
  rev = rev.split('b')[1]
  print "Forward: " + rev
  rev = rev[::-1]
  print "Reverse: " + rev
  rev = int(rev, base=2)
  print str(rev)


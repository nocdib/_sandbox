#!/usr/bin/python

# Create pixel darkness ranges
def create_darkness_ranges(lightest, darkest, number_of_ranges)
  darkness = []
  temp = []

  # If the ranges will be equally-sized
  if 
  for i in range(0,256,8):
    temp.append(i)

  for x in temp:
    darkness.append(range(x,x+8))


darkness = create_darkness_ranges(0,255,8)
print len(darkness)


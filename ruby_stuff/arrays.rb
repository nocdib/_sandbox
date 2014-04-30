#!/usr/bin/ruby

temp = [1..10, 'All', 'of', 'these', 'are', 'array', 'elements']

puts temp

if temp[0].include?(2) then
  puts "It's in there"
else
  puts "It's not in there"
end

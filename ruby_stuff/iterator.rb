#!/usr/bin/env ruby

10.downto(-10) do	|x|
  if x.odd? then
    puts "#{x} is odd"
  else
    puts "#{x} is even"
  end
end


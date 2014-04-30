#!/usr/bin/env ruby

num = 5

num.downto(-10) do |x|
  if x.odd? then
    puts "#{x} is odd"
  else
    puts "#{x} is even"
  end
end


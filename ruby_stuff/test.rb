#!/usr/bin/ruby

def hello(name='none')
  puts "Hello, #{name}"
end

puts "There are #{ARGV.length} arguments. The first one is #{ARGV.first} and
the last one is #{ARGV.last}"

puts "The present working directory is: #{%x(pwd)}"

#print "Enter your name: "
#name = STDIN.gets.chomp()
#if name.strip().length < 1
#  hello()
#else
#  hello(name)
#end

a = [1,2,3,4]
b = [5,6,7,8]

puts "#{a}"
puts "#{b}"
puts
print "#{a + b}\n"

Process.exit(0)

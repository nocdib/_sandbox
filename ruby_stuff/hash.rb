#!//usr/bin/ruby
=begin
Testing out hashes (dictionaries) in Ruby
=end


cities = {'NJ' => 'Trenton',
'MI' => 'Detroit',
'CA' => 'San Francisco'}

#puts cities['CA']

def find_city(map, state)
  if map.include? state
    return map[state]
  else
    return "Not Found"
  end
end

cities[:find] = method(:find_city)

while true
  print "Enter state: "
  state = gets.chomp

  break if state.empty?
  
  puts cities[:find].call(cities, state)
end

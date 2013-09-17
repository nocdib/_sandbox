#!/usr/bin/env ruby

=begin
This is to get acquainted with Here Documents
=end

variable = "Greg"

text = <<HERE
This text
is multi-line and
needs no newline escape.
It interpolates variables like this: #{variable}
HERE

puts text
puts

text = <<'This is strict'
Everything is displayed literally.
No escapes or interpolations.
\n\t\'\" #{variable}
This is strict

puts text
puts

text = <<-"INDENTED DELIMETER"
Usually the delimter had to start at the beginning of the line
but when there is a '-' before the delimeter name it can start anywhere
in the line
It interpolates variables like this: #{variable}
                   INDENTED DELIMETER

puts text


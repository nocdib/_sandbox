#!/bin/sh
# Print the current timestamp every other second. Reverse the text on every
# second timestamp

while :
do
date +%r
sleep 1
date +%r | rev
sleep 1
done


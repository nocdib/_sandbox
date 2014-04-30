#!/bin/sh
# Prints the first command-line argument

if [ "$1" != "" ]
then
   echo "The first command-line argument is: $1"
else
   echo "There are no command-line arguments"
fi

exit

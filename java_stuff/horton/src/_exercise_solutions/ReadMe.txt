Dear Reader,
The solutions to the exercises for each chapter are in their own folder, so folder ch02
contains the solutions for exercises from Chapter 2. The solution to each exercise within
a chapter is in its own subfolder to the chapter folder, so the sol1 folder contains the source files
for Exercise 1.

Where exercises involving the Sketcher program have additional images, there is a single
Images folder containing all the images for the exercises in the chapter.

I have modified the DOCTYPE statement for XML sketch files so that the DTD is assumed to be
in the same chapter as the XML file, rather than a different folder as the code in the book assumes.

Some exercises involve writing an applet. I recommend that you test applets using appletviewer that comes with JDK 7. Otherwise the applets may not execute properly with your browser. The <applet> tag has been deprecated with HTML 4.01, so why am I still using it with examples? Well, there is now no single tag that works with all browsers. Different tags are required, depending on whether you are executing with Internet Explorer, or Firefox, or some other browser. Rather than spend pages in the book going through the complexities of this, I have stuck with the <applet> tag because this always works with appletviewer. After all, the book is supposed to be about Java, not HTML!

If you want to find out more about HTML tags for applets, go to this web page:

http://download.oracle.com/javase/1,5.0/docs/guide/plugin/developer_guide/using_tags.html#mixed

My solutions to the exercises are not unique. In general, there will be several ways of approaching a problem. You may well have different solutions from mine, but this does not imply they are wrong.
If your solutions works, it is probably right. I do not have a monopoly on producing 'best' solutions.
There are almost certainly folks out there who will come up with a better solution than mine.

Remember that the code for the examples in the book are designed to show various techniques, so the examples do not necessarily represent the best way of doing things. The Sketcher program would be quite different as a production application. When you get to the end of the book, you might like to redesign Sketcher from the ground up with things like menus and toolbars implemented consistently.
It should amount to a lot less code!

There are one or two exercises that are a little more challenging than the average. This reflects the way
things go in professional programming. A lot of the time, design and developement of an application
is straightforward, but now and again, something turns up that needs a bit of research or some creativity
in devising how something can be done.

I hope you are enjoying learning Java and I wish you every success in your programming.

Best wishes

Ivor Horton
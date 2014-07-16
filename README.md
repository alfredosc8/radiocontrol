Radio Control
=============

A cloud hosted radio control room application to manage and control an MPD/Icecast audio streaming solution

Building
--------

To build and run you need Maven (>= 3.2.x) and Java JDK 1.7.

    $ mvn clean install

Run a local appengine devserver

    $ cd radiocontrol-ear
    $ mvn appengine:devserver
    
    
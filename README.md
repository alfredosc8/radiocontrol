Discover new internet radio channels in a vintage way. This is the source code for http://play.mxt.se

Make sure you check out our Wiki for more info: <https://github.com/abcduolabs/radiocontrol/wiki>

Building
--------

To build and run you need Maven (>= 3.2.x) and Java JDK 1.7.

    $ mvn clean install

Run a local appengine devserver

    $ cd radiocontrol-ear
    $ mvn appengine:devserver
    
    
Testing
-------

To start Jasmine spec runner

    $ mvn jasmine:bdd
    
    
Then open a web browser to http://localhost:8234 and keep it open. It scans the spec/ directory where you have the
unit tests and run as soon as a source file in js/ directory is modified.
Read-Write Sets Implementation
==============================

Directories
-----------

```bin``` - build files (e.g., class files). <br/>
```code``` - instrumentation - ignore. <br/>
```dat``` - resources (e.g., .xml and .txt configuration files). <br/>
```docs``` - some documentation. <br/>
```example-apps``` - sample applications to demo this project. <br/>
```libs``` - java libraries (.jar files). <br/>
```results``` - temporary directory to store execution-generated files. <br/>
```scripts``` - script to use the system. <br/>
```src``` - source of the read-writes set implementation. <br/>
```src-tests``` - source of test cases.

Files modified in this fork
---------------------------

* Added several files under directories ```src-tests/rwsets``` and ```example-apps```. <br/>
* Added file ```rwset_bugs```.
* ```src-tests/rwsets/Helper.java``` - Modified <br/>
* ```src-tests/rwsets/AllTests.java``` - Modified <br/>
* ```README.txt``` - changed to ```README.md``` and added some stuff. <br/>

Apps used in the tests
----------------------

* ```Projeto IP``` - small app that models a delivery management system, featuring very simple classes, interfaces and subpackages. <br/>
* ```Restaurante``` - same as ```Projeto IP```, with all the code in a single package. <br/>
* ```Projeto PG``` - complex app that models graphic processing of surfaces. <br/>
* ```Projeto GDI``` - simple app that connects to an Oracle database and inserts one row into one table.

Configuration
-------------

Please follow configuration instructions in the following files for
your setup.

Please choose either ```Mac OS```, ```Unix``` or ```Windows```, if you want our support. Detailed
setup information can be found at ```docs/CONFIG_MAC```, ```docs/CONFIG_UNIX``` and ```docs/CONFIG_WINDOWS```.

Please use ```JDK 1.7```.  We apologize for this inconvenience, we should
address this limitation in the future. 

Documentation
-------------
 
```docs/RWSETS.txt``` - summarized explanation of the read-write sets algorithm. <br/>
```docs/TRY.txt``` - info on how to use the system from the command line.

Known issues
------------

- Unpredictable results for ```JDK``` different than ```1.7```.
- The directory where ```Java Runtime``` libraries are located is hardcoded in ```wala.properties```.
- Doesn't provide support for ```implements``` clause.
- Command-line script only handles ```Unix``` and ```Mac OS``` installations. Run scripts on Windows requires [Cygwin](https://www.cygwin.com) installed.
- ./scripts/runRegressionTests breaks (mab@cin.ufpe.br is working on this).
- 2 tests are ignored (mab@cin.ufpe.br is working on this).

Questions
---------

if688-l@cin.ufpe.br

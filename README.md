=== Read-Write Sets Implementation ===

> Directories

```bin``` - build files (e.g., class files)
```code``` - instrumentation - ignore
```dat``` - resources (e.g., xml and txt configuration files)
```docs``` - some documentation 
```example-apps``` - sample applications to demo this project  
```libs``` - java libraries (.jar files)
```results``` - temporary directory to store execution-generated files
```scripts``` - script to use the system
```src``` - source of the read-writes set implementation
```src-tests``` - source of test cases

=========================================

> Configuration

Please follow configuration instructions in the following files for
your setup.

Please choose either ```Mac OS```, ```Unix``` or ```Windows```, if you want our support. Detailed
setup information can be found at docs/CONFIG_MAC, docs/CONFIG_UNIX and docs/CONFIG_WINDOWS.

Please use JDK 1.7.  We apologize for this inconvenience, we should
address this limitation in the future.

=========================================

> Documentation
 
docs/RWSETS.txt - summarized explanation of the read-write sets algo
docs/TRY.txt - info on how to use the system from the command line

=========================================

> Known issues

- Unpredictable results for JDK different than 1.7
- Command-line script only handle ```Unix``` and ```Mac OS``` installations. Run scripts on Windows requires [Cygwin](https://www.cygwin.com) installed.
- ./scripts/runRegressionTests breaks 
  (mab@cin is working on this)
- 2 tests are ignored 
  (mab@cin is working on this)

=========================================

> Questions

if688-l@cin.ufpe.br

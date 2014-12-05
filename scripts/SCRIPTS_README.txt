This documents lists and shortly describes the scripts in this
directory.  The list in alphabetical order:

 build
 common
 genAgent
 genAppJar
 genDDG
 run
 runRegressionTests

- build: compiles all source code and updates directory /bin

- common: sets up variables used in several scripts

- genAgent: creates instrumentation agent.  (used with script run)

- genAppJar: create the jar file for some example application

- genDDG: invokes depend.Main to create dependency graph

- run: instruments and runs a Java program

- runRegressionTests: run the regression test suite for this project

Example of use 1 (Scenario "dependency graph"):

$> ./build
$> ./genAppJar
$> ./genDDG
$> acroread results/results.pdf

Example of use 2 (Scenario "program instrumentation"):

 $> ./build
 $> ./genAgent
 $> ./run

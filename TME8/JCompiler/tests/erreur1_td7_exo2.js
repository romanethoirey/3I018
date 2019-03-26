
var g = 3;

var f = lambda (y) { return x + y + z; };

/*****

$ java -jar jcompiler.jar tests/erreur2_td7_exo2.js 
Microjs compiler v0.0.1

-----------------------

[1] Parsing source file: tests/erreur2_td7_exo2.js...
... parsing done.
[2] Expanding...
... expansion done.
[3] Compiling ...
Compilation error at line 4 column 37:
  ==> Not in scope: z
Now quitting
  ==> compilation failed.

Bye bye !

*****/


function foo (x) {
    return lambda (y) { return x + y + z; };
}

foo(5);

/*****

$ java -jar jcompiler.jar tests/erreur_td7_exo2.js 
Microjs compiler v0.0.1

-----------------------

[1] Parsing source file: tests/erreur_td7_exo2.js...
... parsing done.
[2] Expanding...
... expansion done.
[3] Compiling ...
Compilation error at line 3 column 40:
  ==> Not in scope: z
Now quitting
  ==> compilation failed.

Bye bye !

*****/

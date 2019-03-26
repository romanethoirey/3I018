


12;
true;
false;
12 + 4;
true + 14 + (9 + false);
true + 14 * (9 + false);
true + 14 * 9 + false;

24;

var x = 0;

if (x == true) {
    42;
} else {
    37 * 9;
}

function fact(n) {
    if (n == 0) {
        return 1;
    } else {
        return n * fact(n - 1);
    }
}

var y = 42; // portée globale

let z = 52; // portée lexicale
x + y;
// implicitement on est ici dans la portée lexicale de y

z = x * y + 9;


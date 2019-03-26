
/***** Correct ==> affichera 252 *****/

function f (x) {
    return lambda (z) {
	return lambda (y) { return x * y * z; };
    } (2);
}

let z = 3;
let g = f(z);

g(42);

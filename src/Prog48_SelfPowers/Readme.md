# Prog 48

Modulo Solution

It seems that the BigInteger class is nowhere near using simple integral values of .net, so I wanted to look at other options.

The modulo operator or rather modular arithmetic has some pretty interesting properties for this problem both on addition and multiplication.

The properties we want to exploit are

(a*b) % c = ((a % c) * (b % c) )% c

and

(a+b) % c = ((a % c) + (b % c) )% c.

Since we only need the last ten digits of the results these properties mean that we can cut away all of the leading digits during our calculations. Which in terms means we can use longs to represent our numbers instead of BigInteger.

I have implemented the exponential as a for loop to be able to use the modulo operator inside it such that the source code looks like
# Prog 39

Integer right triangles
Problem 39

If p is the perimeter of a right angle triangle with integral length sides, {a,b,c}, 
there are exactly three solutions for p = 120.

{20,48,52}, {24,45,51}, {30,40,50}

For which value of p ≤ 1000, is the number of solutions maximised?

----

畢氐定理: a^2 + b^2 = c^2
條件 a+b+c=p


Written by Kristian on 3 May 2011

Topics: Project Euler

Even though the title nor the description of Problem 39 of Project Euler mentions Pythagorean Triplets, this is the topic we are revisiting. The problem description reads

    If p is the perimeter of a right angle triangle with integral length sides, {a,b,c}, there are exactly three solutions for p = 120.

    {20,48,52}, {24,45,51}, {30,40,50}

    For which value of p ≤ 1000, is the number of solutions maximised?

The answer is certainly not p = 1000 which I can state so boldly since we in Problem 9 was given the fact that there only exists 1 triplet with that property.

I have found three different approaches to solving this problem. We can brute force it, we can take an arithmetic approach where we rewrite the equations so we only have to work two parameters and check if an equation has an integer solution, and last but not least we can reuse all the work we derived in solving Problem 9 yielding a very efficient solution to the problem.

I wont cover the brute force method in this post, but I have included it in the source code for reference to timing. The brute force implementation took 348 ms to run.
Arithmetic Approach

We are given two equations to work with

a2 + b2 = c2 (1)

a + b+ c = p (2)

Thus we can rewrite (2) as c = p  – a – b and insert it into (1) yielding

a2 + b2 = (p-a-b)2 = p2 + a2 + b2 -2pa – 2pb + 2ab

Isolating b on one side of that equation yields

b = (p2 -2pa) / (2p-2a)

So for all values of p and a where b is an integer is a Pythagorean triplet with the perimeter p.

We can further make a bit of analysis on (1)

if a and b is even so is c and thus p is even

if a or b (but not both) is odd then c is odd and thus p is even

if both a and b is odd then c is even and thus p is even

Therefore we only need to check the numbers where p is even.

Furthermore we know a < c and b < c and without loss of generality we can assume that a ≤ b (otherwise we could switch them) which gives us that a ≤ b < c.  That implies  a < p/3 and thus we don’t need to check values higher than that.

This can be implemented in C# as
	
long result = 0, resultSolutions = 0;
 
for (long p = 2; p <= 1000; p += 2) {
    int numberOfSolutions = 0;
    for (long a = 2; a < (p/3); a++) {
          if(p*(p-2*a) % (2*(p-a)) == 0){
              numberOfSolutions++;
          }
      }
      if(numberOfSolutions > resultSolutions){
        resultSolutions = numberOfSolutions;
        result = p;
    }
}

The result we get out of this will give us the right number, but it wont give us all the ordered triplets. However, for the rather small number p < 1000 we get a fast solution yielding
1
2
	
The number of solutions is maximized for p=840
Solution took 2 ms
Number Theoretic approach

In the solution of Problem 9 we derived a rather intricate solution to finding a Pythagorean triplet for a specific p. I would suggest you read the deduction of that algorithm in order to understand the how to exploit coprimability and primitive Pythagorean triplets.

Once you understand that the solution, the modification to solve this problem is trivial and can be implemented in C# as

int pMax = 0, tMax = 0;
int m = 0, k = 0;
 
for (int s = 1; s <= 1000; s++) {
    int t = 0;
    int mlimit = (int)Math.Sqrt(s / 2);
    for (m = 2; m <= mlimit; m++) {
        if ((s / 2) % m == 0) { // m found
            if (m % 2 == 0) { // ensure that we find an odd number for k
                k = m + 1;
            } else {
                k = m + 2;
            }
            while (k < 2 * m && k <= s / (2 * m)) {
                 if (s / (2 * m) % k == 0 && gcd(k, m) == 1) {
                     t++;
                 }
                 k += 2;
             }
         }
     }
     if (t > tMax) {
        tMax = t;
        pMax = s;
    }
}

This code runs in 0ms for p ≤ 1000 and if we increase the the maximum of p, this is significantly faster than any of the other solutions I have tried.
Wrapping up

In the available source code you will find 3 solutions, the two presented here and a brute force solution. The last presented solution scales rather well and can solve the problem in less than 4 seconds.
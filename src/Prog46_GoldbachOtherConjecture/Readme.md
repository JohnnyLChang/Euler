# Prog 46

conjecture 推測.....

 The Goldbach conjecture states

    Every even integer greater than 2 can be expressed as the sum of two primes

Composite Number 合數（也稱為合成數）是因數除了1和其本身外具有另一因數的正整數（定義為包含1和本身的因數大於或等於3個的正整數）。依照定義，每一個大於1的整數若不是質數，就會是合數。而0與1則被認為不是質數，也不是合數。例如，整數14是一個合數，因為它可以被分解成2 × 7

It turns out that the conjecture was false.

What is the smallest odd composite that cannot be written as the sum of a prime and twice a square?


1. Generate a list of primes and a list of squares. Sum them up and check which is the smallest odd integer that has not been hit.
2. Generate a list of squares and check  odd numbers to see if the number minus twice a square is a prime.
3. As number two but make a list of primes instead and check if the difference is twice a square.
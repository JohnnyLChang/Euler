# Prog 40 Champernowne's constant

An irrational decimal fraction is created by concatenating the positive integers:

0.123456789101112131415161718192021...

It can be seen that the 12th digit of the fractional part is 1.

If dn represents the nth digit of the fractional part, find the value of the following expression.

d1 × d10 × d100 × d1000 × d10000 × d100000 × d1000000

由一連串的正整數組成的無理分數如下,第12位為1
0.123456789101112131415161718192021...
若Dn代表第N個數字,請找出下列數字並計算乘績
d1 × d10 × d100 × d1000 × d10000 × d100000 × d1000000

1*9
123456789


2*9*10
10111213141516171819 29
20211213141516171819 49
30311213141516171819 69
40411213141516171819 89
50511213141516171819 109
60611213141516171819
70711213141516171819
80811213141516171819
90911213141516171899 189

3*9*100
100-999

4*9*1000

f(x) = f(10^n+m-1) = Sum((1-n)*9*10^(0-(n-1))) + m

d(1) = 1
d(10) = 1
d(100) = 5, 100 = 9 + 91 = 9 + 2*45 + 1 = 10+45 = 55


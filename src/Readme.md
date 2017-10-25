# Project Euler

## Problem 51
二位數*3,在9個數字中,6有個為質數 13, 23, 43, 53, 73和83
將56**3的第三位和第四位取代為任意相同的整數,是第一個擁有7個質數的例子56003, 56113, 56333, 56443, 56663, 56773, and 56993
56003為當中最小的質數

請找出最小的質數,滿足件為為取代當中任意不一定相連的部分後,有八個質數的條件

問題分析
* 質數最後一位只有1,3,5,7 因此可以排除
* 根據真值表,可以得知只有三位重覆整數時,才有機會找出超過7組的質數 

1. 找出長度為 N 的質數集合
2. 找出 * 1~5 相同的質數
   2.1 NXXXN, XXXNN, XNXXN, XXNXN
   2.2 NXXXNN, ... etc
3. 判斷 * 為相同

演算法:

1. 産生長度為5和6的樣版
2. 從11~999列出可行解的結尾,為1,3,5,7
3. 測試每一個樣版
   3.1 長度5的樣版填入兩位數, 長度6的樣版填入三位數
   3.2 排除開頭為0
   3.3 填入共同數字
   3.4 填入重覆數字為0,1,或2
   3.5 測試是否為質數
   3.6 取得滿足條件質數的數量
   
答案為 121313

*2*3*3
121313
222323
323333
424343
525353
626363
828383
929393

# Prog 52
暴力法即可解

# Prog 53
1. 暴力列舉法可解題,但效率不夠好
2. 利用巴斯卡三角型可以加速計算,不用每一次都重新計算每一次階乘結果,只要 nCr > Limit,
   結果為 result += n - 2*r + 1

# Prog 54 Poker Hand
In the card game poker, a hand consists of five cards and are ranked, from lowest to highest, in the following way:

    High Card: Highest value card.
    One Pair: Two cards of the same value.
    Two Pairs: Two different pairs.
    Three of a Kind: Three cards of the same value.
    Straight: All cards are consecutive values.
    Flush: All cards of the same suit.
    Full House: Three of a kind and a pair.
    Four of a Kind: Four cards of the same value.
    Straight Flush: All cards are consecutive values of same suit.
    Royal Flush: Ten, Jack, Queen, King, Ace, in same suit.

The cards are valued in the order:
2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King, Ace. 



# Prog 55
利克瑞尔数（Lychrel Number）指的是将该数与将该数各数位逆序翻转后形成的新数相加、并将此过程反复迭代后，结果永遠無法是一个回文数的自然数。“利克瑞尔”的名字是Wade VanLandingham杜撰出的，这是从他的女友Cheryl的名字经过简单的字母换位而来。 在1至1000000的數字裡，發現有122962個不能產生迴文數字的可能性。

# Prog 57
Approximate Solution

We know from the problem description that the first time we encounter a numerator with more digits than the denominator is after 8 expansions. For some reason this got me a bit curious to see when the next solution would arise. This happens after expansion 13 and then after 21 and 26. So after a few iterations a pattern started emerging that we would have 8,5,8,5,8,5 expansions between the solutions to the problem. I tried to iterate several more times and got the following
1
2
3
4
5
6
7
8
9
10
11
	
1234567x1234x1234567x1234x
1234567x1234x1234567x
1234567x1234x1234567x1234x
1234567x1234x12x1234x
1234567x1234x1234567x1234x
1234567x1234567x1234x
1234567x1234x1234567x1234x
1234567x1234567x1234x
1234567x1234x1234567x
1234567x1234x1234567x1234x
1234567x1234x1234567x

which shows that there is a trend of 13 solutions per 13 expansions, but the pattern is not completely regular. However, for fun and giggles lets try to assume that the pattern was regular and see how close we get. We could form a solution looking like

f(x) =\left\{\begin{array}{ll} 2\lfloor x / 13\rfloor & \text{if } x \text{ mod } 13 < 8,\\ 2\lfloor x / 13\rfloor 1 & \text{otherwise}\end{array} \right.

where the x is the limit and the \lfloor\ \rfloor means floor.

Plotting the error term of this approximation against the actual value of problem shows that the approximation is within +/- 1 of the actual solution.

In 68% of the cases between 1 and 1000 it hits the target, in 8,4% it shoots 1 above and in 23,6% it is just one short. For the value 1000 it hits spot on. So even if it is not a completely accurate approximation it is rather close.

# Prog 64 Odd period square roots
所有平方根值都俱有循環的特性,可以表示為連續的分數

# Prog 66
http://mathworld.wolfram.com/PellEquation.html
我無言了.....  完全沒練習也沒認真.....

# Prog 67

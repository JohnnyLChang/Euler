# Problem 41 - Pandigital prime

We shall say that an n-digit number is pandigital if it makes use of all the digits 1 to n exactly once. For example, 2143 is a 4-digit pandigital and is also prime.

What is the largest n-digit pandigital prime that exists?


1. 從999999999開始找質數,很慢,因為質數很多
2. 利用123456789取出不同的排列組合,快很多,因為需要尋找的空間比較小
3. 單純用next permutation的方式,效能差了兩個數量級

```java


# Over-explaining a bit:
def magic(numList):         # [1,2,3]
    s = map(str, numList)   # ['1','2','3']
    s = ''.join(s)          # '123'
    s = int(s)              # 123
    return s


# How I'd probably write it:
def magic(numList):
    s = ''.join(map(str, numList))
    return int(s)


# As a one-liner  
num = int(''.join(map(str,numList)))


# Functionally:
s = reduce(lambda x,y: x+str(y), numList, '')
num = int(s)


# Using some oft-forgotten built-ins:
s = filter(str.isdigit, repr(numList))
num = int(s)


```​
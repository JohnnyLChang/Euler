#!/usr/bin/env python

from math import factorial

#
# n Choose R function
#
def ncr(n, r):
    #   n choose r = n! /((n-r)! * r!)
    num = factorial(n)
    den = factorial(r) * factorial(n-r)
    return num/den

#
# n Permutations r function
#
def npr(n, r):
    num = factorial(n)
    den = factorial(n-r)
    return num/den

sum = 0
for i in range(2,4):
    print i
    sum += ncr(9, i)


print sum

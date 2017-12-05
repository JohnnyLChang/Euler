import math
import random
from collections import OrderedDict
from random import randrange
MOD = 123454321

# return a dict or a list of primes up to N
# create full prime sieve for N=10^6 in 1 sec


def prime_sieve(n, output={}):
    nroot = int(math.sqrt(n))
    sieve = range(n + 1)
    sieve[1] = 0

    for i in xrange(2, nroot + 1):
        if sieve[i] != 0:
            m = n / i - i
            sieve[i * i: n + 1:i] = [0] * (m + 1)

    if type(output) == dict:
        pmap = {}
        for x in sieve:
            if x != 0:
                pmap[x] = True
        return pmap
    elif type(output) == list:
        return [x for x in sieve if x != 0]
    else:
        return None


# get a list of all factors for N
# ex: get_factors(10) -> [1,2,5,10]
def get_factors(n, primelist=None):
    if primelist is None:
        primelist = prime_sieve(n, output=[])

    fcount = {}
    for p in primelist:
        if p > n:
            break
        if n % p == 0:
            fcount[p] = 0

        while n % p == 0:
            n /= p
            fcount[p] += 1

    factors = [1]
    for i in fcount:
        level = []
        exp = [i**(x + 1) for x in range(fcount[i])]
        for j in exp:
            level.extend([j * x for x in factors])
        factors.extend(level)

    return factors


# get a list of prime factors
# ex: get_prime_factors(140) -> ((2,2), (5,1), (7,1))
#     140 = 2^2 * 5^1 * 7^1
def get_prime_factors(n, primelist=None):
    if primelist is None:
        primelist = prime_sieve(n, output=[])

    fs = []
    for p in primelist:
        count = 0
        while n % p == 0:
            n /= p
            count += 1
        if count > 0:
            fs.append((p, count))

    return fs


def primes_sieve2(limit, notprime=True):
    # Initialize the primality list
    a = [True] * limit
    a[0] = a[1] = False
    for (i, isprime) in enumerate(a):
        if isprime:
            if not notprime:
                yield i
            for n in xrange(i * i, limit, i):     # Mark factors non-prime
                a[n] = False
        else:
            if notprime:
                yield i


limit = 1000000


def get_nth_notprime(limit):
    limit += 2
    i = 1
    for p in primes_sieve2(pow(10, 7)):
        if p < 1:
            continue
        i += 1
        print p, get_prime_factors(p)
        if i == limit:
            return


array_factor = [0] * 1000000
list_factor = [[] for i in range(100)]


def prime_factors(limit):
    i = 1
    for p in primes_sieve2(pow(10, 7)):
        if p < 1:
            continue
        i += 1
        sum = 0
        pf = get_prime_factors(p)
        for f, e in pf:
            sum += e
        for j in range(0, sum + 1):
            list_factor[j].append(p)
            array_factor[j] += 1
            if array_factor[j] == j:
                print '{}:{}'.format(j, p)
                print list_factor[j]
        if i == limit:
            limit
            return

def rabinMiller(num):
    # Returns True if num is a prime number.

    s = num - 1
    t = 0
    while s % 2 == 0:
        # keep halving s while it is even (and use t
        # to count how many times we halve s)
        s = s // 2
        t += 1

    for trials in range(5): # try to falsify num's primality 5 times
        a = random.randrange(2, num - 1)
        v = pow(a, s, num)
        if v != 1: # this test does not apply if v is 1.
            i = 0
            while v != (num - 1):
                if i == t - 1:
                    return False
                else:
                    i = i + 1
                    v = (v ** 2) % num
    return True


def isPrime(num):
    # Return True if num is a prime number. This function does a quicker
    # prime number check before calling rabinMiller().

    if (num < 2):
        return False # 0, 1, and negative numbers are not prime

    # About 1/3 of the time we can quickly determine if num is not prime
    # by dividing by the first few dozen prime numbers. This is quicker
    # than rabinMiller(), but unlike rabinMiller() is not guaranteed to
    # prove that a number is prime.
    lowPrimes = [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997]

    if num in lowPrimes:
        return True

    # See if any of the low prime numbers can divide num
    for prime in lowPrimes:
        if (num % prime == 0):
            return False

    # If all else fails, call rabinMiller() to determine if num is a prime.
    return rabinMiller(num)

v = OrderedDict()

# pre-generate primelist
primelist = prime_sieve(1000000, output=[])
s = 6
max = 2
found = True
for i in range(3,1000):
    tmp = s*2
    step = int(pow(2, 2*len(str(tmp)) - 2))
    base = int(pow(2, i-max))
    test = tmp/base
    c = 0
    while True:
        test += 1
        c += 1
        pf = get_prime_factors(test, primelist)
        sum = i-max
        for p, f in pf:
            sum += f
        if sum >= i:
            print c
            s = test*base
            for p, f in pf:
                if p > 3: break
                if p == 3 and f == max:
                    max+=1
            print '{}:{} - {}'.format(i, s, pf)
            break

#for p in primes_sieve2(100, False):
#    print p

#get_nth_notprime(7)
#prime_factors(100000)
#for n in list_factor[2]:
#    print n, get_prime_factors(n)
'''
2: 6
3: 16
4: 36
5: 80
6: 192
7: 432
8: 896
9: 1920
10: 4096
11: 9216
12: 20480
'''

'''
prime numbers
2 3 5 7 11 13 17

2*2*2    8
2*2*3   12
2*2*2*2 16
2*3*3   18
2*2*5   20
2*2*3   12
3*3*3   27
2*2*7   28

'''
# print '{}:{}'.format(limit, get_nth_notprime(limit) * pow(2, limit - 2, MOD) % MOD)


'''
3:16
[(2, 4)]
4:36
2^5 -> [(2, 2), (3, 2)]   8->9      222 33
5:80
2^3 * 3*2 [(2, 4), (5, 1)] 9->10    33 25
6:192
2^5 * 5 [(2, 6), (3, 1)]  10->12    25  223
7:432
2^7 * 3  [(2, 4), (3, 3)] 12 14, 24+3  223 333
8:896
[(2, 7), (7, 1)] 27->28  333 227
9:1920
[(2, 7), (3, 1), (5, 1)] 14->15 27 35
10:4096
[(2, 12)] 15->16  35 2222
11:9216
[(2, 10), (3, 2)] 16->18  2222 233
12:20480
[(2, 12), (5, 1)] 18->20 233 225
13:41472
[(2, 9), (3, 4)] 20->81  225 3333
14:86016
[(2, 12), (3, 1), (7, 1)]  81->84 3333 2273

2 4
2
2
4 16
4
4
6 64
6
6
8 
8
8


1  2   *3    4     5      *6       7        8         9         10          *11           *12            13
2 23 2222 2233 22225 2222223 2222333 22222227 222222235 2222222222 222222222233 2222222222225 2222222223333
2  6   16   36    80     192     432      896      1920       4096         9216         20480         41472
4  6    8    9    10      12      27       14        15         16           18            20            81
        4    4     8      32      48       32       128        256         1024          2048           512                                     
            14
22222222222237
         86016
            21
            
   23          6    12
   222         8
4   33          9  
5   25          10  
6   223         12  
7   333          27  333
8   27          14 
9   35         15
10 2222       16
11 233        18
12 2225       20
13 3333       81?  3333
14 22237      21
15           22
16 335        45?   335
17           24
18           25
19           26
20           27
21           28
22           30   240
23 3333      243?  243 33333
24 2337      126?  252   337    
25           32   256
             33
             34
             35
             36
             38
             39
             40
             
'''
import math
from sortedcontainers import SortedDict
import time

start = time.time()

Primes = []
POW2 = [0] * 100

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


def cachepow2(n):
    if POW2[n] == 0:
        POW2[n] = pow(2, n)
    return POW2[n]

def sortedMultiPrimes(count, limit, max):
    pf3 = SortedDict()
    s = [0] * count
    s[-1] = -1
    idx = len(s) - 1
    max = max - 1

    while s[0] != max:
        for i in range(count-1, -1, -1):
            if s[i] < max:
                s[i] += 1
                idx = i
                for j in range(i+1, count):
                    s[j] = s[i]
                break
        sum = 1.0
        p = [0] * count
        for i in range(0, count):
            p[i] = (Primes[s[i]])
            sum *= p[i]

        #if overflow at beginning, skipp all value behind
        f = sum / cachepow2(count - 2)
        high = int(f)
        if high > limit:
            for i in range(idx, count):
                s[i] = max
            continue

        for div in range(count-2, 0, -1):
            f = sum/cachepow2(div)
            high = int(f)
            if high > limit:
                continue
            pf3[int(f*10000)] = (int(sum), div)
    return pf3

def getMultiPrimes(limit, mod):
    maxexp = int(math.log(limit, 3)) * 3 - 8
    '''
    Max prime = 222247
    Max exp = 30
    '''
    smp = sortedMultiPrimes(maxexp, limit/2.5, len(Primes))
    y = smp.values()[limit - 1]
    return (y[0]*int(pow(2, limit - 2 - y[1], mod))) % mod

limit = 1000000
MOD = 123454321
Primes = prime_sieve(200000, [])
#print getMultiPrimes(limit, MOD) == 49932491  #100
#print getMultiPrimes(limit, MOD) == 54143359  #100000
print getMultiPrimes(1000000, MOD)
print("time = "+str(time.time()-start))
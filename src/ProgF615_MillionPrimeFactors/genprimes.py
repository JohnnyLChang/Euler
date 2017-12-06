import math
import random
import cProfile
from collections import OrderedDict
from random import randrange
MOD = 123454321

# return a dict or a list of primes up to N
# create full prime sieve for N=10^6 in 1 sec

def prime_sieve(n, output={}):
    primes_map = [False]*n
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
        for x in sieve:
            if x != 0: primes_map[x] = True
        return [x for x in sieve if x != 0], primes_map
    else:
        return None


# get a list of all factors for N
# ex: get_factors(10) -> [1,2,5,10]
def get_factors(n, primelist=None, primemap=None):
    if primelist is None:
        primelist, primemap = prime_sieve(n, output=[])

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
def get_prime_factors(n, primelist=None, primemap=None):
    if primelist is None:
        return
        #primelist, primemap = prime_sieve(n, output=[])
    max = len(primemap)
    fs = []
    for p in primelist:
        if n==1: break
        if n < max and primemap[n]:
            fs.append((n, 1))
            break
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

def miller_rabin_pass(a, s, d, n):
    a_to_power = pow(a, d, n)
    if a_to_power == 1:
        return True
    for i in xrange(s-1):
        if a_to_power == n - 1:
            return True
        a_to_power = (a_to_power * a_to_power) % n
    return a_to_power == n - 1

def miller_rabin(n):
    '''if n < 1,373,653, it is enough to test a = 2 and 3;
    if n < 9,080,191, it is enough to test a = 31 and 73;
    if n < 4,759,123,141, it is enough to test a = 2, 7, and 61;
    if n < 2,152,302,898,747, it is enough to test a = 2, 3, 5, 7, and 11;'''
    if n < 2: return False
    if n in {2, 7, 61}: return True
    d = n - 1
    s = 0
    while d % 2 == 0:
        d >>= 1
        s += 1
    for a in {2,7,61}:
        if not miller_rabin_pass(a, s, d, n):
            return False
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
    return miller_rabin(num)

def bruteforce(limit):
    # pre-generate primelist
    primelist, primemap = prime_sieve(10000000, output=[])
    s = 432
    max = 4
    maxprime = len(primemap)
    stop3 = 20.25
    base3 = 81
    n = 0
    for i in range(8,limit+1):
        tmp = s*2
        base = int(pow(2, i-max))
        test = tmp/base
        c = 0
        if test > MOD:
            print test
        if n == int(stop3):
            stop3 *= 1.5
            s = base*base3
            base3*=3
            max+=1
            continue
        while True:
            test += 2
            pf = get_prime_factors(test, primelist, primemap)
            sum = i - max   # sum of factors
            for p, f in pf:
                sum += f
            if sum >= i:
                s = test*base
                n = 1
                for p, f in reversed(pf):
                    n *= pow(p, f)
                n *= pow(2, -1*max+2)
                if not isinstance(n, int) and n.is_integer():
                    n = int(n)
                ret = '{:4d}:{} {}'.format(i, pf, n)
                print ret
                break
    return ''

# 99:    14736438227653166792399174762496 [(2, 6), (3, 1), (31L, 1)] 93

# 999:876627979254264951950930243262213981265549311576589682589918286485493453701588365214747923668591173930638391780673168246356233236857024426918988252830027151146896826077807397494761666126665239044890126527396114734594297569939061670934231064088429709698356011830730231091718915602080332648118888718925824 [(2, 10), (7, 1), (11, 1), (17L, 1)] 654.5
# 2.828 seconds

#print bruteforce(1802)#
#cProfile.run('bruteforce(100)')



limit  = 1000000
output = 'Primes = ['
for p in primes_sieve2(limit, False):
    if p/2 > limit:
        output += ']'
        break
    output += '{},'.format(p)
print output


#get_nth_notprime(7)
#prime_factors(100000)
#for n in list_factor[2]:
#    print n, get_prime_factors(n)
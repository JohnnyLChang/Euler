import math
from decimal import getcontext, Decimal
import time

m = []*100
def fastquote(d):
    if d == 0: return 0
    q = 1
    if d > 80:
        q = 9
    elif d > 63:
        q = 8
    elif d > 48:
        q = 7
    elif d > 35:
        q = 6
    elif d > 24:
        q = 5
    elif d > 15:
        q = 4
    elif d > 8:
        q = 3
    elif d > 3:
        q = 2
    return q, d-q*q, q*2

def findquote(divisor, divident):
    if divisor == 0: return fastquote(divident)
    divisor *= 10
    q = divident / divisor
    if q == 0: return q, divident, divisor
    while True:
        remain = divident - (q + divisor) * q
        if remain > 0: return q, remain, divisor+q*2
        q -= 1
    return 0,0

def sqrt(n, exp):
    high, low = '', ''
    num = str(n)
    if len(num)%2 != 0:
        num = '0'+num

    divisor, divident = 0, 0
    for i in range(0, len(num), 2):
        divident = divident*100 + int(num[i:i+2])
        i, divident, divisor = findquote(divisor, divident)
        high += str(i)

    if divident == 0: return high+''
    for i in range(0, exp):
        divident *= 100
        i, divident, divisor = findquote(divisor, divident)
        low += str(i)

    return high+low

if __name__ == "__main__":
    ans = 0
    t1 = time.time()
    for i in range(1, 100):
        d = sqrt(i, 99)
        if len(d) > 1:
            ans += sum(int(x) for x in d)
    t2 = time.time()
    print 'ans:{} time expand {}'.format(ans, (t2-t1)*1000.0)


    t1 = time.time()
    getcontext().prec = 102
    L, d, s = 100, 100, 0
    p = pow(10, d-1)

    for z in range(2, L):
        q = Decimal(z).sqrt()
        s += sum(int(c) for c in str(q * p)[:d]) if  q % 1 != 0 else 0
    t2 = time.time()
    print 'time expand {}'.format((t2-t1)*1000.0)
    print "Project Euler 80 Solution =", s

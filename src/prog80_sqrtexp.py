import math
from decimal import getcontext, Decimal
import time

def sqrt(n, exp):
    high = ''
    low = ''
    num = str(n)
    if len(num)%2 != 0:
        num = '0'+num

    divisor = 0
    divident = 0
    for i in range(0, len(num), 2):
        divident = divident*100 + int(num[i:i+2])
        for i in range(1, 11):
            if (divisor*10+i)*i > divident:
                high += str(i-1)
                divident -= (divisor*10+i-1)*(i-1)
                divisor = divisor*10 + (i-1)*2
                break
    if divident == 0: return high+''
    for i in range(0, exp):
        divident *= 100
        pv = 0
        for i in range(1, 11):
            v = (divisor*10+i)*i
            if v > divident:
                low += str(i-1)
                divident -= pv
                divisor = divisor*10+(i-1)*2
                break
            pv = v

    return high+low

if __name__ == "__main__":
    ans = 0
    t1 = time.time()
    for i in range(1, 100):
        d = sqrt(i, 99)
        if len(d) > 1:
            ans += sum(int(x) for x in d)
    print ans
    t2 = time.time()
    print 'time expand {}'.format((t2-t1)*1000.0)

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

import math
from primes import *
from sortedcontainers import SortedDict, SortedSet
import cProfile

#Primes = [3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997]

def sortedMultiPrimes(count, limit, max):
    pf3 = SortedDict()
    s = [0] * count
    s[-1] = -1
    idx = len(s) - 1
    max = max - 1

    while s[0] != max:
        for i in range(len(s)-1, -1, -1):
            if s[i] < max:
                s[i] += 1
                idx = i
                for j in range(i+1, len(s)):
                    s[j] = s[i]
                break
        sum = 1.0
        p = []
        try:
            for i in s:
                p.append(Primes[i])
                sum *= Primes[i]
        except Exception as e:
            print e
            print i


        #if overflow at beginning, skipp all value behind
        f = sum / pow(2, count - 2)
        high = int(f)
        if high > limit:
            for i in range(idx, len(s)):
                s[i] = max
            continue

        for div in range(count-2, 0, -1):
            f = round(sum/pow(2, div), 10)
            try:
                _, low = '{0:.10f}'.format(round(int(f)-f, 10)).split('.')
                high = int(f)
                low = int('1'+low)
            except Exception as e:
                print f
                print 'e'+str(e)
                continue
            if high > limit:
                continue
            if pf3.get((high, low)) != None:
                raise Exception('fraction value conflict')
            pf3[(high, low)] = (p, div)
    return pf3

def getMultiPrimes(limit):
    maxexp = int(math.log(limit, 3)) * 3
    print maxexp
    '''
    Max prime = 222247
    Max exp = 30
    '''
    divPrimeFactors = SortedDict()
    divPrimeFactors.update(sortedMultiPrimes(maxexp, limit, len(Primes)))
    return divPrimeFactors

#cProfile.run('getMultiPrimes(30000)')

limit = 1000000
t = 1000000
divPrimeFactors = getMultiPrimes(limit)
print len(divPrimeFactors)
MOD = 123454321
i = -1
x,y = divPrimeFactors.items()[t-1]
sum = 1
s = SortedSet(y[0])
for e in y[0]:
    sum *= e
sum*=pow(2, t-2 - y[1], MOD)
print i + 2, int(sum%MOD), list(s), x


#1700, 1041.25
#print ''.join(['{0}{1}\n'.format(k, v) for k,v in divPrimeFactors.iteritems()])

'''
2000:34386514323463923000773354375271575421468415177556421254305399967931699528701490900006951615179362784057456640937674510064741212877848507176206644117191262902469431749021027860206289354897893438992963633524014130312093831362093315002221449299119673014286046469169638443278916566884181839036081184480114638062578588240845322427912655699970260640829965561878486313653634436241341435604766246882499731053503376399121227229043023067592010626876028383836496385453676530206209856185106775728444546186380859535236437462325941650740461532619900325420125097272846240042940875846441409859980653401774362919134298112
[(2, 14), (599L, 1)] 1198
'''
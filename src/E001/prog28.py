from z3 import *

a = Real('a')
b = Real('b')
c = Real('c')
d = Real('d')

s = Solver()
s.add( d == 1)
s.add( a + b + c + d == 25)
s.add( 8*a + 4*b + 2*c + d == 101)
s.add( 27*a + 9*b + 3*c + d == 261)

s.check()
m = s.model()
print m[a], m[b], m[c], m[d]


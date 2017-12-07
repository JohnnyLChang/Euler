import time

n = 30
x = [1] * n
t1 = time.time()
for i in range(2, n):
    print x
    for j in range(i, n):
        x[j] += x[j - i]
t2 = time.time()

total = int(x[n - 1] - 1)
print x
print '%i (%0.3f ms)' % (total, (t2 - t1) * 1000.0)

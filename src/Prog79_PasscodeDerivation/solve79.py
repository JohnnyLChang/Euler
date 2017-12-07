passcode = [
319,
680,
180,
690,
129,
620,
762,
689,
762,
318,
368,
710,
720,
710,
629,
168,
160,
689,
716,
731,
736,
729,
316,
729,
729,
710,
769,
290,
719,
680,
318,
389,
162,
289,
162,
718,
729,
319,
790,
680,
890,
362,
319,
760,
316,
729,
380,
319,
728,
716,
]

c = [0]*10
x = [set() for i in range(10)]
p = []
for p in passcode:
    t = str(p)
    for e in map(int, str(p)) :
        c[e] += 1
    for i in range(1, len(t)):
        x[int(t[i])].add(int(t[i-1]))

a = [i for i in range(10)]
for i in range(0, 10):
    if c[i] == 0: a.remove(i)

#build the relatinoship
ans = ''
while a:
    for i in range(0, len(a)):
        if not x[a[i]]:
            tmp = a[i]
            break
    ans += str(tmp)
    a.remove(tmp)
    for i in range(0, len(x)):
        if tmp in x[i]:
            x[i].remove(tmp)

print ans
from time import time
t=time()
m=1000267129
dic={(0,0):1}
def comb(k,n):
    if (k,n) in dic:return dic[(k,n)]
    if k==0 or k==n:
        dic[(k,n)]=1
        return 1
    dic[(k,n)]=comb(k-1,n-1)+comb(k,n-1)
    return dic[(k,n)]
def nb1(n): #list of ways to create a n-digit numbers with i different ones, and without 0
    nb=[0]
    for i in range(1,10):
        nb.append(i**n-sum(nb[j]*comb(j,i) for j in range(i)))
    return nb
def nbtot1(n): #no restriction on the number of digits, except <10**n
    u=["shift"]+[nb1(j) for j in range(1,n+1)]
    return [sum(u[j][i] for j in range(1,n+1)) for i in range(10)]
def nb2(n): #with 0s
    nb=[0] #nb = with or without 0
    for i in range(1,10):
        nb.append((i+1)**(n-1)*i-sum(nb[j]*comb(j,i) for j in range(i)))
    nbwo0=nb1(n) #nbwo0 = without 0
    return [nb[i]-nbwo0[i] for i in range(10)]
def nbtot2(n): #no restriction on the number of digits, except <10**n
    u=["shift"]+[nb2(j) for j in range(1,n+1)]
    return [sum(u[j][i] for j in range(1,n+1)) for i in range(10)]
def main(n): #for 10**n : calculates the number for 0 digits in common, does the substraction
    c=0
    wo0=nbtot1(n)
    w0=nbtot2(n)
    for i in range(1,10):
        for j in range(1,10-i):
            c=(c+(wo0[i]*wo0[j]+wo0[i]*w0[j]+w0[i]*wo0[j])*comb(i,9)*comb(j,9-i))%(2*m)
    return ((10**n-1)*(10**n-2)//2-c//2)%m
print(main(18))
print(time()-t)

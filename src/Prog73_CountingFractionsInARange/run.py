
with open('test.log', 'r') as f:
    skip = False
    while(True):
        if skip:
            skip = False
        else:
            l1 = f.readline()[:-1]
        l2 = f.readline()[:-1]
        if l1[-2:] == l2[-2:] :
            print l1
            print l2
            l1 = l2
            skip = True
            break

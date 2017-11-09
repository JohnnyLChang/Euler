import math
import collections
import numpy as np
from itertools import permutations, product
import pickle

Z = np.array([[0, 2, 2],
              [2, 3, 0],
              [4, 1, 1],
              [2, 0, 3],
              [1, 4, 4],
              [3, 5, 2],
              [5, 3, 3],
              [3, 2, 5],
              ])


ZP = np.array([[1, 1, 1],
               [1, 1, 4],
               [4, 1, 1],
               [1, 4, 1],
               [4, 4, 1],
               [4, 1, 4],
               [1, 4, 4],
               [4, 4, 4],
               ])

def load(filename):
    try:
        fh = open(filename, 'rb')
        return pickle.load(fh)
    except (EnvironmentError, pickle.PicklingError) as err:
        print 'file not found'
    except EOFError as err:
        print 'empty file'
    return None

def save(filename, data):
    try:
        with open(filename, 'wb') as fh:
            pickle.dump(data, fh)
    except (EnvironmentError, pickle.PicklingError) as err:
        pass

def get_cubics(vectors):    
    vertex = [None]*8
    vertex[0] = np.array([0,0,0])
    vertex[7] = np.array([0,0,0])
    vectors = list(vectors[1])
    for i in range(0, 3):
        vertex[7] = np.add(vertex[7], vectors[i])
    for i in range(1,4):
        vertex[i] = np.add(vertex[0],vectors[i-1])
        vertex[i+3] = np.add(vertex[i],vectors[i%3])
    return vertex

'''
Iterate all different combination to find vertical pair
1. set can add tuple as element
''' 
def find_right_angles(v1, v2=None):
    vector = set()
    vector.add(tuple(v1))
    if np.array_equal(v2,None): 
        v2 = v1
        for v2p in permutations(v2, 3):
            v = v2p * np.array(v1)   # use to find right vector for [26, 50, 24] 
            p = np.array([-1, 1, 1])
            if v[1] >= v[2] and v[1] >= v[0]:
                p = np.array([1, -1, 1])
            elif v[2] >= v[1] and v[2] >= v[0]:
                p = np.array([-1, -1, 1])
            if np.dot(v1, v2p * p) == 0:
                v2 = v2p * p
                vector.add(tuple(v2))
                v3 = np.cross(v1, v2) / np.linalg.norm(v2)
                if v3[2] < 0: v3 = v3*-1 # if x,y,-z change it to x,y,z
                vector.add(tuple(v3.astype(int)))
        return True, tuple(vector)
    else:
        for v2p in permutations(v2, 3):
            v = v2p * np.array(v1)  # use to find right vector for [26, 50, 24] 
            p = np.array([-1, 1, 1])
            if v[1] >= v[2] and v[1] >= v[0]:
                p = np.array([1, -1, 1])
            elif v[2] >= v[1] and v[2] >= v[0]:
                p = np.array([-1, -1, 1])
            if np.dot(v1, v2p * p) == 0:
                v2 = v2p * p
                vector.add(tuple(v2))
                v3 = np.cross(v1, v2) / np.linalg.norm(v2)
                v3 = v3.astype(int)
                if v3[2] < 0: # if x,y,-z change it to x,y,z
                    v3 = v3*-1
                vector.add(tuple(v3))
                break
        return True, tuple(vector)
    return False, ()

def gen_lattice_cube(vectors):
    # given x,y,z vector, we fixed z to be positive
    count = len(vectors)
    if count == 1:
        return find_right_angles(vectors[0])
    elif count == 2:
        return find_right_angles(vectors[0])
    elif count == 3:
        return find_right_angles(vectors[0])
    else:
        raise ValueError('un expected amount of vectors')

def get_surface(vertex):
    p1 = vertex[0]
    edge = 0
    dist = 0
    distmap = {}
    p1plane = []
    p2plane = []

    for i in range(1, len(vertex)):
        dist = np.linalg.norm(p1 - vertex[i])
        if dist not in distmap:
            distmap[dist] = []
        distmap[dist].append(vertex[i])
    distmap = collections.OrderedDict(sorted(distmap.items()))

    edge = distmap.keys()[0]
    pclose = distmap.items()[0]
    for i in range(0, len(pclose[1])):
        v1 = p1 - pclose[1][i]
        v2 = p1 - pclose[1][(i + 1) % 3]
        normv = np.cross(v1, v2)
        p1plane.append(normv)

    p2 = distmap.items()[2][1][0]
    p2close = distmap.items()[1]
    for i in range(0, len(p2close[1])):
        v1 = p2 - p2close[1][i]
        v2 = p2 - p2close[1][(i + 1) % 3]
        normv = np.cross(v1, v2)
        p2plane.append(normv)
    return ((p1, tuple(p1plane)), (p2, tuple(p2plane))), edge

def maximum(v):
    m = 0
    for p in v:
        if p > m: m = p
    return m

def dump_max_edges(cubic):
    boundary = [0, 10000, 0, 10000, 0, 10000]
    for v in cubic:
        if v[0] > boundary[0]:
            boundary[0] = v[0]
        if v[1] > boundary[2]:
            boundary[2] = v[1]
        if v[2] > boundary[4]:
            boundary[4] = v[2]
        if v[0] < boundary[1]:
            boundary[1] = v[0]
        if v[1] < boundary[3]:
            boundary[3] = v[1]
        if v[2] < boundary[5]:
            boundary[5] = v[2]
    max = []
    for i in range(0,3):
        max.append(boundary[i*2] - boundary[i*2+1])
    m = maximum(max)
    print np.array(max)-m
    return max

def find_latticepoints(cubic):
    fname = './pkl/lattice'+str(cubic)
    lattice = load(fname)
    if lattice is not None:
        return lattice
    cubicPlane, dist = get_surface(cubic)
    notinCube = False
    boundary = [0, 10000, 0, 10000, 0, 10000]
    #find the max space for the cubic
    for v in cubic:
        if v[0] > boundary[0]:
            boundary[0] = v[0]
        if v[1] > boundary[2]:
            boundary[2] = v[1]
        if v[2] > boundary[4]:
            boundary[4] = v[2]
        if v[0] < boundary[1]:
            boundary[1] = v[0]
        if v[1] < boundary[3]:
            boundary[3] = v[1]
        if v[2] < boundary[5]:
            boundary[5] = v[2]

    lattice = []
    for i in range(boundary[1], boundary[0] + 1):
        for j in range(boundary[3], boundary[2] + 1):
            for k in range(boundary[5], boundary[4] + 1):
                p = np.array([i, j, k])
                notinCube = False
                for vset in cubicPlane:
                    vf = p - vset[0]
                    for plain in vset[1]:
                        pdist = np.abs(np.dot(vf, plain)) / \
                            np.linalg.norm(plain)
                        if pdist > dist:
                            notinCube = True
                            break
                    if notinCube:
                        break
                if not notinCube:
                    lattice.append(p)
    save(fname, lattice)
    return lattice

def find_centerv(a):
    max = 0
    ret = []
    for v in a:
        for vv in a:
            t = np.linalg.norm(v - vv)
            if t > max:
                max = t
                ret = vv
    return ret

def get_vector_all(vector):
    vset = set()
    d = np.array([1,-1])
    for v in vector:
        for x in permutations(v, 3):
            for y in product(d, repeat=2):
                vset.add(tuple(np.array(x)*np.append(np.array(y), [1])))
    return vset

def main():
    v = [np.array([2,2,1])]
    vset = set()
    print get_vector_all(v)

if __name__ == '__main__':
    main()

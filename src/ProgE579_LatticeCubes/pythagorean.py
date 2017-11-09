from sets import Set
from array import array
from itertools import combinations, permutations
import numpy as np
import fractions  # for gcd
import collections  # ordered dictionary
import pickle  # data serialization
import time
import math
from fractions import gcd
from surface import *
from lattice import *

def pytha_triple(LIMIT):
    triples = {}
    for s in range(3, int(math.sqrt(LIMIT)), 2):
        for t in range(s - 2, 0, -2):
            if fractions.gcd(s, t) == 1:
                a = s * t
                b = (s * s - t * t) / 2
                c = (s * s + t * t) / 2
                if True:
                    triples[c] = (a, b, c)
    return collections.OrderedDict(sorted(triples.items()))


'''
Iterate all different combination to find vertical pair
'''
def find_right_angle(v1, v2):
    for v2p in permutations(v2, 3):
        v = v2p * np.array(v1)
        p = np.array([-1, 1, 1])
        if v[1] >= v[2] and v[1] >= v[0]:
            p = np.array([1, -1, 1])
        elif v[2] >= v[1] and v[2] >= v[0]:
            p = np.array([1, 1, -1])
        if np.dot(v1, v2p * p) == 0:
            return True, v2p * p
    return False, ()


def combine_quadruple(quad):
    quadType = []
    paired = set()
    # check self vertical vector
    for vector in quad:
        ret, v = find_right_angle(vector, vector)
        if not ret:
            pass
        else:
            vcross = np.sort(np.absolute(np.cross(vector, v)))[
                ::-1] / np.linalg.norm(vector)
            vcross = tuple(vcross.astype(int))
            if not np.array_equal(vcross, vector):
                paired.add(vcross)
                # v,v,T
                quadType.append((2, (vector, vcross)))
            else:
                # v,v,v
                quadType.append((1, vector))
            paired.add(vector)
    # find pairs in not self vertical vector
    for combo in combinations(set(quad) - paired, 2):
        vectory = combo[1]
        vector = combo[0]
        vcross = []
        if vector in paired or vectory in paired:
            continue
        ret, tmp = find_right_angle(vector, vectory)
        if not ret:
            continue
        vcross = np.cross(vector, tmp)
        vcross = np.sort(np.absolute(vcross))[
            ::-1] * np.linalg.norm(vector) / np.linalg.norm(vcross)
        if np.linalg.norm(vcross) == np.linalg.norm(vector) and vcross[0].is_integer():
            paired.add(vector)
            paired.add(vectory)
            paired.add(tuple(vcross.astype(int)))
            tmp = [vector, vectory, tuple(vcross.astype(int))]
            for i in range(0, len(tmp)):
                for j in range(i, len(tmp)):
                    if tmp[i][0] < tmp[j][0]: tmp[i],tmp[j] = tmp[j],tmp[i]
                    elif tmp[i][0] == tmp[j][0] and tmp[i][1] < tmp[j][1]: tmp[i],tmp[j] = tmp[j],tmp[i]
                    elif tmp[i][0] == tmp[j][0] and tmp[i][1] == tmp[j][1] and tmp[i][2] < tmp[j][2]: tmp[i],tmp[j] = tmp[j],tmp[i]
            quadType.append((3, tuple(tmp)))
        else:
            print 'not integer'
    # give up those vector cannot make cubic
    if len(set(quad) - paired) > 0:
        print 'lost', set(quad) - paired
    return quadType

'''
Find Pythagorean Quadruples 
1. Use square root to reduce search space
2. using cache cannot impove performance significantly
'''
quad_file = "pytha_quadruple.pkl"
def pytha_quadruple(LIMIT):
    quadruple = collections.OrderedDict()
    saved = load(quad_file)
    start = 3
    if saved is not None:
        quadruple = saved
        start = quadruple.keys()[-1]
        print 'data loaded before {}'.format(start)
    size = LIMIT * LIMIT * 3
    max = int(LIMIT - 1)
    squareSet = [False] * size
    for i in range(1, LIMIT):
        squareSet[i * i] = True

    for i in range(start, LIMIT):
        d = i * i
        quad = []
        for j in range(i - 1, i / 2, -1):
            x = d - j * j
            cachelen = 0
            for k in range(j, 1, -1):
                z = x - k * k
                if squareSet[z] and z <= k * k and z > 0:
                    quad.append((j, k, int(math.sqrt(z))))
        quadruple[i] = combine_quadruple(quad)
    save(quad_file, quadruple)
    return quadruple

def lpoints(C,E,S,I,D, n=0):
    if n == 0:
        return int(math.pow(E+1, 3)) - S*I
    else:
        return int(math.pow(E+1, 3)) - S*(n*I + n*(n-1)*D/2)

def load(filename):
    try:
        print filename
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

def dump(quadruple, limit):
    sum = 0
    primitive = {}
    l = Lattice()

    for k in quadruple.keys():
        if k > limit: break
        sum += len(quadruple[k])
        for q in quadruple[k]:
            if q[0] != 0:
                if q[0] == 1:
                    v = (q[1],)
                else:
                    v = tuple([tuple(x) for x in q[1]])
                g = reduce(gcd,tuple(v[0]))
                if q[0] == 2 or q[0] == 3:
                    if g > reduce(gcd,tuple(v[1])):
                        g = reduce(gcd,tuple(v[1]))
                    if q[0] == 3:
                        if g > reduce(gcd,tuple(v[2])):
                            g = reduce(gcd,tuple(v[2]))
                if g != 1: 
                    vp = tuple([tuple(x) for x in np.divide(np.array(v).flatten(), g).reshape([len(v),3])])
                    C = primitive[str(vp)][0]*g
                    E = primitive[str(vp)][1]*g
                    S = primitive[str(vp)][2]
                    I = primitive[str(vp)][3]
                    D = primitive[str(vp)][4]
                    if q[0] == 3:
                        real = len(find_latticepoints(get_cubics(find_right_angles(v[0], v[1]))))
                        if lpoints(C,E,S,I,D, g) == real:
                            print 'C{} V{} E{} S{} I{} D{} L{} correct'.format(C,v,E,S,I,D, lpoints(C,E,S,I,D))
                        else:
                            print 'C{} V{} E{} S{} I{} D{} L{} real{}'.format(C,v,E,S,I,D, lpoints(C,E,S,I,D), real)
                    print 'C{} V{} E{} S{} I{} D{} L{}'.format(C,v,E,S,I,D, lpoints(C,E,S,I,D, g)) 
                    continue
                C = np.sum(v[0])
                if q[0] == 2 and np.sum(v[1]) > C: C = np.sum(v[1])
                if q[0] == 3 and np.sum(v[2]) > C: C = np.sum(v[2])
                E = k
                S = 6*(E-1)
                if v[0][0] == v[0][1]: S = 6
                I = int(round(E/2.0))
                if v[0][0] == v[0][1]: I = 4
                if q[0] == 3:
                    if v[2][0] == v[2][1]: S = S-I-4
                    if gcd(v[0][0], v[0][1]) == 5: S = 2*(3*(E-1)-4)
                    if reduce(gcd, (tuple(v[1]))) == 5: S = 2*(3*(E-1)-4)
                    if reduce(gcd, (tuple(v[2]))) == 5: S = 2*(3*(E-1)-4)
                D = E
                if v[0][0] == v[0][1]: D = 6
                primitive[str(v)] = (C,E,S,I,D)
                '''
                if q[0] == 3:
                    real = len(find_latticepoints(get_cubics(find_right_angles(v[0], v[1]))))
                    if lpoints(C,E,S,I,D) == real:
                        print 'C{} V{} E{} S{} I{} D{} L{} correct'.format(C,v,E,S,I,D, lpoints(C,E,S,I,D))
                    else:
                        print 'C{} V{} E{} S{} I{} D{} L{} real{}'.format(C,v,E,S,I,D, lpoints(C,E,S,I,D), real)
                else:
                    print 'C{} V{} E{} S{} I{} D{} L{}'.format(C,v,E,S,I,D, lpoints(C,E,S,I,D))
                '''
                print 'C{} V{} E{} S{} I{} D{} L{} U{}'.format(C,v,E,S,I,D, lpoints(C,E,S,I,D), l.get_quadruple_cubic(v))

def test_quad():
    limit = 80
    print 'test triple'
    start = time.time()
    triple = pytha_triple(5000)
    end = time.time()
    print 'elapsed {}'.format(end - start)

    print 'test quad'
    start = time.time()
    quadruple = pytha_quadruple(limit)
    end = time.time()
    print 'elapsed {}'.format(end - start)

    dump(quadruple, limit) 

def main():
    test_quad()

if __name__ == '__main__':
    main()
import time
import euler as er
import numpy as np
import math as math
from random import *
import itertools as it
from mpl_toolkits.mplot3d import Axes3D
from mpl_toolkits.mplot3d.art3d import Poly3DCollection, Line3DCollection
import matplotlib.pyplot as plt
import matplotlib._color_data as mcd
from itertools import *
from sets import Set
from array import array
from itertools import combinations, permutations
import fractions  # for gcd
import collections  # ordered dictionary
import pickle  # data serialization


from MyCube import *
from PythagoPair import *

fig = plt.figure()
ax = fig.add_subplot(111, projection='3d')

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


class Lattice:
    def __init__(self):
        self.name = 'Lattice cube'
        self.cube = MyCube()
        self.ax = fig.add_subplot(111, projection='3d')

    def rotation(self, v, axis, theta):
        return np.dot(er.rotation_matrix(axis, theta), v)

    def rotateedges(self, edges, axis, theta):
        return np.array([self.rotateedge(x, axis, theta) for x in edges])

    def rotateedge(self, edge, axis, theta):
        return np.array([self.rotation(edge[0], axis, theta), self.rotation(edge[1], axis, theta)])

    def rotatedots(self, vert, axis, theta):
        return np.array([self.rotation(v, axis, theta) for v in vert])

    def draw_cube(self, size, base=np.array([0, 0, 0])):
        cn = uniform(0, len(mcd.XKCD_COLORS))
        c = mcd.XKCD_COLORS.values()[int(cn)]
        cc = uniform(0, len(mcd.XKCD_COLORS))
        ccc = mcd.XKCD_COLORS.values()[int(cc)]

        cc = uniform(0, len(mcd.XKCD_COLORS))
        c3 = mcd.XKCD_COLORS.values()[int(cc)]

        eulercubic = self.cube.get_dot(size, False)
        cubics = self.find_cubes(size)

        i = 0
        centerV = []
        cubic_edge = 9
        wanted = cubics[cubic_edge]['[1 4 8]'] + \
            cubics[cubic_edge]['[4 4 7]']
        centerdict = {}
        for mycubic in wanted:
            i += 1
            cubic = np.array(mycubic)
            centerV = self.cube.find_centerv(cubic)
            if np.array_str(centerV) not in centerdict:
                centerdict[np.array_str(centerV)] = 0
            centerdict[np.array_str(centerV)] += 1
            centerV = np.array(centerV)

            colorcode = uniform(0, len(mcd.XKCD_COLORS))
            xkcd = mcd.XKCD_COLORS.values()[int(cc)]

            # for label, line in zip(linev, line):
            #    centerV = (line[0] + line[1]) / 2
            # self.ax.text(centerV[0], centerV[1], centerV[2], '{0}'.format(
            #    np.array_str(label)), size=10)
            if np.array_equal(centerV, np.array([7.5, 7.5, 7.5])):
                line, linev = self.cube.get_cubelines(cubic_edge, cubic)
                self.ax.add_collection3d(Line3DCollection(
                    line, linewidths=1, colors=xkcd, alpha=.5))
                # self.ax.scatter3D(centerV[0], centerV[1],
                #                  centerV[2], s=120, c=xkcd)
                self.ax.scatter3D(cubic[:, 0], cubic[:, 1],
                                  cubic[:, 2], s=120, c=xkcd)
        for c, k in centerdict.items():
            print c, k
        colorcode = uniform(0, len(mcd.XKCD_COLORS))
        xkcd = mcd.XKCD_COLORS.values()[int(cc)]

    def draw_innerCube(self, size):
        # iterate normal cubes
        for i in range(size, size + 1):
            for x in range(0, self.size - i + 1, 1):
                for y in range(0, self.size - i + 1, 1):
                    for z in range(0, self.size - i + 1, 1):
                        self.draw_cube(i, np.array([x, y, z]))

    # 0 0 0norm
    # 0 0 1
    # 0 1 0
    # 1 0 0
    # 0 1 1
    # 1 0 1
    # 1 1 0
    # 1 1 1
    def getCubeVertexes(self, v, d):
        cubic = [None] * 8
        cubic[0] = (v[0], v[1], v[2])
        cubic[1] = (v[0], v[1], v[2] + d)
        cubic[2] = (v[0], v[1] + d, v[2])
        cubic[3] = (v[0] + d, v[1], v[2])
        cubic[4] = (v[0], v[1] + d, v[2] + d)
        cubic[5] = (v[0] + d, v[1], v[2] + d)
        cubic[6] = (v[0] + d, v[1] + d, v[2])
        cubic[7] = (v[0] + d, v[1] + d, v[2] + d)
        return cubic

    def hasCube(self, cube, lattice):
        for v in cube:
            if v not in lattice:
                return False
        return True

    def draw(self):
        self.ax.set_xlabel('X')
        self.ax.set_ylabel('Y')
        self.ax.set_zlabel('Z')
        plt.show()

    def find_cubes(self, size):
        filename = 'cubic{0}.pkl'.format(size)
        try:
            fh = open(filename, 'rb')
            return pickle.load(fh)
        except (EnvironmentError, pickle.PicklingError) as err:
            print 'file not found'

        allvertex = self.cube.get_dot(size, norm=False)
        vectors = []
        for i in range(0, 30):
            vectors.append({})
        for x in allvertex:
            for y in allvertex:
                if x[0] != y[0] and x[1] != y[1] and x[2] != y[2]:
                    dist = np.linalg.norm(x - y)
                    if dist.is_integer():
                        if tuple(x) in vectors[int(dist)]:
                            vectors[int(dist)][tuple(x)].append(tuple(y))
                        else:
                            vectors[int(dist)][tuple(x)] = [tuple(y)]
        cubes = self.get_cube(vectors, size)
        with open(filename, 'wb') as fh:
            pickle.dump(cubes, fh)
        return cubes

    def sort(self, vs):
        for i in range(0, len(vs)):
            for j in range(i, len(vs)):
                if vs[i][0] > vs[j][0] or (vs[i][0] == vs[j][0] and vs[i][1] > vs[j][1]) or (vs[i][0] == vs[j][0] and vs[i][1] == vs[j][1] and vs[i][2] > vs[j][2]):
                    vs[i][0], vs[j][0] = vs[j][0], vs[i][0]
                    vs[i][1], vs[j][1] = vs[j][1], vs[i][1]
                    vs[i][2], vs[j][2] = vs[j][2], vs[i][2]
        return vs

    def get_cube(self, e, size):
        cubes = {}
        i = 0
        for ev in e:
            cube = {}
            vset = set()
            for vertex in ev.keys():
                for combo in combinations(ev[vertex], 3):
                    p = np.array(vertex)
                    vector = np.sort(np.absolute(np.array(combo[0]) - p))
                    x = np.array(combo[0]) - p
                    y = np.array(combo[1]) - p
                    z = np.array(combo[2]) - p
                    if np.dot(x, y) == 0 and np.dot(y, z) == 0 and np.dot(z, x) == 0:
                        pp = p + x + y + z
                        max = size
                        c = np.array(
                            [p, p + x, p + y, p + z, p + x + y, p + x + y + z, p + x + z, p + y + z])
                        c = self.sort(c)
                        inRange = True
                        for v in c:
                            for value in v:
                                if value > max or value < 0:
                                    inRange = False
                                    continue
                        if inRange == True and np.array_str(c) not in vset:
                            vset.add(np.array_str(c))
                            if np.array_str(vector) not in cube:
                                cube[np.array_str(vector)] = []
                            cube[np.array_str(vector)].append(list(c))
            cubes[i] = cube
            i += 1
        return cubes

    def latticeS(self, size):
        n = size
        sum = 0
        for i in range(2, n + 2):
            sum += math.pow(i, 3) * math.pow(n - i + 2, 3)
        return int(sum)

    def latticeC(self, size):
        n = size
        sum = 0
        # Standard Cubes
        for i in range(2, n + 2):
            sum += math.pow(n - i + 2, 3)

        # count for Quadruple
        quad = [(k, v) for k, v in PythagoreanQuadrupleSqrt(
            50).items() if k <= size * 3 / 5]
        for i in range(0, len(quad)):
            count = len(quad[i][1])
            quadrupleK = quad[i][0]
            quadrupleV = quad[i][1]
            diff = size - quadrupleK * 5 / 3
            if count == 1:
                quadrupleV = quadrupleV[0]
                # for (2,2,1) (4,4,2) condition
                if quadrupleV[1] == quadrupleV[0]:
                    sum += 4 * count * math.pow(1 + diff, 3)
                # for (2,3,6) condition
                elif quadrupleV[0] != quadrupleV[1] and quadrupleV[1] != quadrupleV[2]:
                    sum += 8 * count * math.pow(1 + diff, 3)
            elif count == 2:  # 11: [(9, 6, 2), (7, 6, 6)] not sure?
                sum += 4 * count * math.pow(1 + diff, 3)
            elif count == 3:  # 9: [(8, 4, 1), (7, 4, 4), (6, 6, 3)]
                sum += 4 * math.pow(1 + diff, 3)
                sum += 4 * math.pow(3 + diff, 3)
            else:
                print quadrupleK, quadrupleV
        # Pythagorean Quadruples 3:(1,2,2), for cubic 3
        # Pythagorean Quadruples 6:(2:4:4), for cubic 6
        # Pythagorean Quadruples 9:(3:6:6) 9:(1:4:8) 9:(7:4:4), for cubic 6
        # for i in range(1, size/5+1, 1):
        #    sum += math.pow(size-(5*i-1), 3) * 4

        t1 = (3, 4, 5)
        # 2 4 6
        # Pythagorean Triple 3 * 4 * 5
        # 3 dimension
        # 6 in height - 5-10
        # 1 2 2 2 1 => 8 square for each line 2 4 6 8
        # 0-7 2-8 3-9 4-10 => 4 times for one layer
        # if size >= t1[0]+t1[1]:
        #    sum += math.pow((size-(t1[0]+t1[1])+1), 2)*2*(size-t1[2]+1)*3

        triples = [v for k, v in PythagoreanTriple(
            size * 2).items() if k <= size]
        for p in triples:
            sum += math.pow((size - (p[0] + p[1]) + 1),
                            2) * 2 * (size - p[2] + 1) * 3

        return int(sum)

    # list Pythagorean Quadruples vectors for x^2 + y^2 + z^2 = N^2
    def get_vectors(self, size):
        quad = []
        for k, v in PythagoreanQuadrupleSqrt(size * 2).items():
            max = 0
            for vv in v:
                if vv[0] == 1:
                    max = sum(vv[1])
                elif vv[0] == 2:
                    max = sum(vv[1][0])
                    if sum(vv[1][1]) > max:
                        max = sum(vv[1][1])
                elif vv[0] == 3:
                    max = sum(vv[1][0])
                    if sum(vv[1][1]) > max:
                        max = sum(vv[1][1])
                    if sum(vv[1][2]) > max:
                        max = sum(vv[1][2])
            print '{}({})-{}, {}'.format(k, max, len(v), v)
        # for q in quad:vcross = []
        #    print q
        vectors = []
        # 1,2,2  => 2,3
        # 2 3 3,2
        # 3 2 2,3
        #---------
        # 2 2 3 3
        # 3 3 2 2

        # 2,3,6
        # 5,8 - 3,6
        # 6,8 - 3,5
        # 8,5 - 6,3
        # 8,6 - 5,3
        #-------------
        # 5,3
        # 6,3
        # 3,5
        # 3,6
        for i in range(0, 30):
            vectors.append({})
        # for x in range(0, size+1):
        #    for y in range(0, size+1):

        return self.get_cube(vectors, size)


def PythagoreanTriple(LIMIT):
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


def validateVector(v1, v2):
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


def validateQuadruple(quad):
    quadType = []
    paired = set()
    # check self vertical vector
    for vector in quad:
        ret, v = validateVector(vector, vector)
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
        ret, tmp = validateVector(vector, vectory)
        if not ret:
            continue
        vcross = np.cross(vector, tmp)
        vcross = np.sort(np.absolute(vcross))[
            ::-1] * np.linalg.norm(vector) / np.linalg.norm(vcross)
        if np.linalg.norm(vcross) == np.linalg.norm(vector) and vcross[0].is_integer():
            paired.add(vector)
            paired.add(vectory)
            paired.add(tuple(vcross.astype(int)))
            quadType.append((3, (vector, vectory, tuple(vcross.astype(int)))))
        else:
            print 'not integer'
    # give up those vector cannot make cubic
    if len(set(quad) - paired) > 0:
        print 'lost', set(quad) - paired
    return quadType

# using cache cannot impove performance significantly
#


def PythagoreanQuadrupleSqrt(LIMIT):
    quadruple = {}
    size = LIMIT * LIMIT * 3
    max = int(LIMIT - 1)
    squareSet = [False] * size
    for i in range(1, LIMIT):
        squareSet[i * i] = True

    for i in range(3, LIMIT):
        d = i * i
        quad = []
        for j in range(i - 1, i / 2, -1):
            x = d - j * j
            cachelen = 0
            minJ = 1
            for k in range(j, minJ, -1):
                z = x - k * k
                if squareSet[z] and z <= k * k:
                    quad.append((j, k, int(math.sqrt(z))))
        quadruple[i] = validateQuadruple(quad)
    return quadruple


def test_Formula():
    l = Lattice()
    for i in range(2, 16):
        print '{}: {} {}'.format(i, l.latticeC(i), l.latticeS(i))
    print '{}: {} {}'.format(50, l.latticeC(50), l.latticeS(50))


def test_quad():
    limit = 1000
    print 'test triple'
    start = time.time()
    quadruple = PythagoreanTriple(5000)
    end = time.time()
    print 'elapsed {}'.format(end - start)

    print 'test quad'
    start = time.time()
    quadruple = PythagoreanQuadrupleSqrt(limit)
    end = time.time()
    print 'elapsed {}'.format(end - start)

    sum = 0
    for k in quadruple.keys():
        sum += len(quadruple[k])
        print '{}:{}'.format(k, len(quadruple[k]))
    print len(quadruple[17])
    print len(quadruple[34])
    print len(quadruple[51])
    print sum


def main():
    print 'Lattice'
    LIMIT = 5000
    # test_quad()
    # l = Lattice()
    # l.get_vectors(30)

    # test_Formula()
    # print PythagoreanTriple(100)
    # print PythagoreanQuadrupleSqrt(50)
    # test_quad()
    # triple = PythagoreanTriple(LIMIT))

    l = Lattice()
    l.draw_cube(15)
    l.draw()
    # cubes = l.find_cubes(18)
    # print cubes[9]['[4 4 7]']
    for i in range(3, 16):
        cubes = l.find_cubes(i)
        print '{0}:{1}'.format(i, sum([len(x) for x in cubes[9].values()]))
    # for edge in cubes.keys():
    #    print '{0}:{1}'.format(edge, len(cubes[edge]))


if __name__ == '__main__':
    main()

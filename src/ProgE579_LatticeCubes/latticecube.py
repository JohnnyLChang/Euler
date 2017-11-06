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
from itertools import combinations
import fractions
import collections

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

class MyCube:
    def __init__(self):
        self.name = 'Cube'

    def get_cube(self, size, base=np.array([0, 0, 0])):
        v = np.array(list(it.product((0, size), repeat=3)))
        c = self.find_centerv(v)
        return np.array([x - c for x in v])

    def get_sampledot(self):
        c = self.find_centerv(Z)
        return np.array([x - c for x in Z])

    def get_sampelines(self):
        lines = []
        verts = self.get_sampledot()
        for i in range(0, len(verts)):
            v = verts[i]
            for j in range(0, len(verts)):
                vv = verts[j]
                if np.linalg.norm(np.abs(v - vv)) == 3:
                    lines.append([v, vv])
        return np.array(lines)

    def get_dot(self, size, norm=True):
        v = np.array(self.get_dot_impl(size))
        if norm:
            c = self.find_centerv(v)
            return np.array([x - c for x in v])
        return v

    def get_dot_impl(self, size):
        dots = []
        size += 1
        for x in range(0, size, 1):
            for y in range(0, size, 1):
                for z in range(0, size, 1):
                    dots.append([x, y, z])
        return np.array(dots)

    def find_centerv(self, a):
        max = 0
        ret = []
        for v in a:
            for vv in a:
                t = np.linalg.norm(v - vv)
                if t > max:
                    max = t
                    ret = (v + vv) / (2 * 1.0)
        return ret

    def get_cubelines(self, size, verts=[],  base=np.array([0, 0, 0])):
        lines = []
        if len(verts) == 0:
            verts = self.get_cube(size, base)
        for i in range(0, len(verts)):
            v = verts[i]
            for j in range(0, len(verts)):
                vv = verts[j]
                if np.linalg.norm(np.abs(v - vv)) == size:
                    lines.append([v, vv])
        return np.array(lines)


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
        print eulercubic
        #self.ax.scatter3D(
        #    eulercubic[:, 0], eulercubic[:, 1], eulercubic[:, 2], s=59, c='g')
        cubics = self.find_cubes(size)
        for v in cubics.keys():
            print 'size {0}: {1}'.format(v, len(cubics[v]))

        i = 0
        centerV = []
        cubic_edge = 9
        for mycubic in cubics[cubic_edge]:
            i += 1
            colorcode = uniform(0, len(mcd.XKCD_COLORS))
            xkcd = mcd.XKCD_COLORS.values()[int(cc)]
            line = self.cube.get_cubelines(cubic_edge, mycubic)
            cubic = np.array(mycubic)
            print cubic
            self.ax.scatter3D(cubic[:, 0], cubic[:, 1], cubic[:, 2], s=59, c='g')
            self.ax.add_collection3d(Line3DCollection(line, linewidths=1, colors=xkcd, alpha=.5))
            if i==5:
                break
            #centerV.append(np.array(self.cube.find_centerv(mycubic)))
        colorcode = uniform(0, len(mcd.XKCD_COLORS))
        xkcd = mcd.XKCD_COLORS.values()[int(cc)]
        centerV = np.array(centerV)
            #self.ax.add_collection3d(Line3DCollection(line, linewidths=1, colors=xkcd, alpha=.5))
        #self.ax.scatter3D(centerV[:, 0], centerV[:, 1], centerV[:, 2], s=120, c=xkcd)

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
        return self.get_cube(vectors, size)

    def sort(self, vs):
        for i in range(0, len(vs)):
            for j in range (i, len(vs)):
                if vs[i][0] > vs[j][0] or ( vs[i][0] == vs[j][0] and vs[i][1] > vs[j][1]) or ( vs[i][0] == vs[j][0] and vs[i][1] == vs[j][1]  and vs[i][2] > vs[j][2]):
                    vs[i][0], vs[j][0] = vs[j][0], vs[i][0]
                    vs[i][1], vs[j][1] = vs[j][1], vs[i][1]
                    vs[i][2], vs[j][2] = vs[j][2], vs[i][2]
        return vs

    def get_cube(self, e, size):
        cubes = {}
        i=0
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
                        pp = p + x + y +z
                        max = size
                        c = np.array([p, p+x, p+y, p+z, p+x+y, p+x+y+z, p+x+z, p+y+z])
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
            i+=1
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

        # Pythagorean Quadruples 3:(1,2,2), for cubic 3
        # Pythagorean Quadruples 6:(2:4:4), for cubic 6
        # Pythagorean Quadruples 9:(3:6:6) 9:(1:4:8) 9:(7:4:4), for cubic 6
        for i in range(1, size/5+1, 1):
            print math.pow(size-(5*i-1), 3) * 4
            sum += math.pow(size-(5*i-1), 3) * 4

        pt = [v for k,v in PythagoreanTriple(size*2).items() if k <= size ]

        t1 = (3,4,5)
        # 2 4 6
        # Pythagorean Triple 3 * 4 * 5
        # 3 dimension
        # 6 in height - 5-10
        # 1 2 2 2 1 => 8 square for each line 2 4 6 8
        # 0-7 2-8 3-9 4-10 => 4 times for one layer
        #if size >= t1[0]+t1[1]:
        #    sum += math.pow((size-(t1[0]+t1[1])+1), 2)*2*(size-t1[2]+1)*3

        for p in pt: 
            sum += math.pow((size-(p[0]+p[1])+1), 2)*2*(size-p[2]+1)*3

        return int(sum)

    def get_vectors(self, size):
        allvertex = self.cube.get_dot(size, norm=False)
        vectors = []
        for i in range(0, 30):
            vectors.append({})
        for x in allvertex:
            for y in allvertex:
                if x[0] != y[0] and x[1] != y[1] and x[2] != y[2]:
                    dist = np.linalg.norm(x - y)
                    if dist.is_integer():
                        print '{0}:{1}'.format(dist, x-y)
                        if tuple(x) in vectors[int(dist)]:
                            vectors[int(dist)][tuple(x)].append(tuple(y))
                        else:
                            vectors[int(dist)][tuple(x)] = [tuple(y)]
        return self.get_cube(vectors, size)

def solN():
    for i in range(1,10):
        for j in range(1,10):
            for k in range(3,10):
                if i*i+2*j*j == k*k:
                    print i,j,k

    # find x^2 + y^2 + z^2 = N^2
    for i in range(1, 11):
        for j in range(1, 11):
            for k in range(1, 11):
                d = math.sqrt(i*i+j*j+k*k)
                if d.is_integer():
                    print '{0},{1},{2}:{3}'.format(i,j,k,d)

def PythagoreanTriple(LIMIT):
    triples = {}
    for s in range(3, int(math.sqrt(LIMIT)), 2):
        for t in range(s-2, 0, -2):
            if fractions.gcd(s, t) == 1:
                a = s*t
                b = (s*s-t*t)/2
                c = (s*s+t*t)/2
                if True:
                    triples[c]= (a, b, c)
    return collections.OrderedDict(sorted(triples.items()))

def PythagoreanQuadruple(LIMIT):
    quadruple = {}
    size = LIMIT*LIMIT*3
    max = int(LIMIT-1)
    squareSet = [False] * size
    for i in range(1, LIMIT):
        squareSet[i*i] = True
    for i in range(1, LIMIT):
        for j in range(i, LIMIT):
            for k in range(j, LIMIT):
                q = i*i+j*j+k*k
                if squareSet[q]:
                    if q not in quadruple:
                        quadruple[q] = []
                    quadruple[q].append((i, j ,k))
    return quadruple

# using cache cannot impove performance significantly
# 
def PythagoreanQuadrupleSqrt(LIMIT):
    quadruple = {}
    size = LIMIT*LIMIT*3
    max = int(LIMIT-1)
    squareSet = [False] * size
    for i in range(1, LIMIT):
        squareSet[i*i] = True

    for i in range(3, LIMIT):
        d = i*i
        for j in range(i-1, i/2, -1):
            x = d - j*j
            cachelen = 0
            minJ = 1
            for k in range(j, minJ, -1):
                z = x - k*k
                if squareSet[z] and z <= k*k:
                    if i not in quadruple:
                        quadruple[i] = []
                    quadruple[i].append((j,k,int(math.sqrt(z))))
    return quadruple

def test_Formula():
    l = Lattice()
    for i in range(2,15):
        print '{}: {} {}'.format(i, l.latticeC(i), l.latticeS(i))
    print '{}: {} {}'.format(50, l.latticeC(50), l.latticeS(50))

def test_quad():
    limit = 1000
    print 'test triple'
    start = time.time()
    quadruple = PythagoreanTriple(5000)
    end = time.time()
    print(end - start)

    print 'test quad'
    start = time.time()
    quadruple = PythagoreanQuadrupleSqrt(limit)
    end = time.time()
    print(end - start)

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
    test_Formula()
    #print PythagoreanTriple(100)
    print PythagoreanQuadrupleSqrt(50)
    #test_quad()
    #triple = PythagoreanTriple(LIMIT))
    l = Lattice()
    cubics = l.find_cubes(15)
    for v in cubics.keys():
        for vv in cubics[v].keys():
            print 'cubic {0}, vector {1}, size{2}'.format(v, vv, len(cubics[v][vv]))
    #l.draw()
    #cubes = l.get_vectors(12)
    #for edge in cubes.keys():
    #    print '{0}:{1}'.format(edge, len(cubes[edge]))

if __name__ == '__main__':
    main()
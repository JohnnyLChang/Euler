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
from logger import logging
from mpi4py import MPI

from cube import *
from pythagorean import *
from surface import *

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
        self.cubefilename = './pkl/cubic{}{}.pkl'
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

    def is_incubic(self, cubic, vertex):
        for v in cubic:
            if np.array_equal(v, vertex): return True
        return False

    def get_boundary_vertex(self, csize, cubic):
        vertex = []
        for v in cubic:
            for cord in v:
                if cord == 0 or cord == csize: 
                    vertex.append(list(v))
                    break
        return vertex

    def draw_cube(self, size, vector=[], base=np.array([0, 0, 0])):
        cn = uniform(0, len(mcd.XKCD_COLORS))
        c = mcd.XKCD_COLORS.values()[int(cn)]
        cc = uniform(0, len(mcd.XKCD_COLORS))
        ccc = mcd.XKCD_COLORS.values()[int(cc)]

        cc = uniform(0, len(mcd.XKCD_COLORS))
        c3 = mcd.XKCD_COLORS.values()[int(cc)]

        cubic_vertex = self.cube.get_dot(size, norm=False, surface=True)
        vectors = get_vector_all(vector)
        cubics, cubic_edge = self.find_cubes(size, vectors)
        
        i = 0
        centerV = []
        for mycubic in cubics[cubic_edge]:
            i += 1
            #if not self.is_incubic(mycubic, np.array([9,9,0])): continue
            cubic = np.array(mycubic)
            colorcode = uniform(0, len(mcd.XKCD_COLORS))
            xkcd = mcd.XKCD_COLORS.values()[int(cc)]

            vertex = np.array(self.get_boundary_vertex(size, cubic))

            line, linev = self.cube.get_cubelines(cubic_edge, cubic)
            if False:
                for l, label in zip(line, linev):
                    centerV = (l[0] + l[1]) / 2
                    self.ax.text(centerV[0], centerV[1], centerV[2], '{0}'.format(np.array_str(label)), size=10)
            #self.ax.add_collection3d(Line3DCollection(line, linewidths=1, colors=xkcd, alpha=.5))
            #self.ax.scatter3D(cubic[:, 0], cubic[:, 1], cubic[:, 2], s=60, c=xkcd)
            self.ax.scatter3D(vertex[:, 0], vertex[:, 1], vertex[:, 2], s=10, c='r')

        colorcode = uniform(0, len(mcd.XKCD_COLORS))
        xkcd = mcd.XKCD_COLORS.values()[int(cc)]
        image = './pic/cube{}-{}.png'.format(size, str(vector[0]))
        self.save_img(image)

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


    def sort(self, vs):
        for i in range(0, len(vs)):
            for j in range(i, len(vs)):
                if vs[i][0] > vs[j][0] or (vs[i][0] == vs[j][0] and vs[i][1] > vs[j][1]) or (vs[i][0] == vs[j][0] and vs[i][1] == vs[j][1] and vs[i][2] > vs[j][2]):
                    vs[i][0], vs[j][0] = vs[j][0], vs[i][0]
                    vs[i][1], vs[j][1] = vs[j][1], vs[i][1]
                    vs[i][2], vs[j][2] = vs[j][2], vs[i][2]
        return vs

    def save_img(self, filename):
        self.ax.set_xlabel('X')
        self.ax.set_ylabel('Y')
        self.ax.set_zlabel('Z')
        fig.set_size_inches(5, 5)
        plt.savefig(filename, dpi=300)

    def draw(self):
        self.ax.set_xlabel('X')
        self.ax.set_ylabel('Y')
        self.ax.set_zlabel('Z')
        
        plt.show()

    def find_cubes(self, size, pvectors, test=False):
        vectors = {}
        edge = int(np.linalg.norm(next(iter(pvectors))))
        pvectors = list(pvectors)
        filename = self.cubefilename.format(size, str(next(iter(pvectors))))
        if not test:
            try:
                fh = open(filename, 'rb')
                logging.info('load file {}'.format(filename))
                return pickle.load(fh), edge
            except (EnvironmentError, pickle.PicklingError) as err:
                logging.info('file not found '+filename)

        min = [10000,10000,10000]
        for pv in pvectors:
            for i in range(0, len(pv)):
                if min[i] > np.abs(pv[i]): 
                    min[i] = np.abs(pv[i])

        allvertex = self.cube.get_cubic_vertex(size, min=[0,0,0], norm=False, surface=False)

        for i in range(0, len(allvertex)):
            x = allvertex[i]
            for j in range(0, len(pvectors)):
                y = np.array(x)+np.array(pvectors[j])
                if y[0] < 0 or y[0] > size or \
                    y[1] < 0 or y[1] > size or \
                    y[2] < 0 or y[2] > size: continue
                if edge not in vectors: vectors[edge] = {}
                if tuple(x) not in vectors[edge]: vectors[edge][tuple(x)] = []
                vectors[edge][tuple(x)].append(tuple(y))

        cubes = self.search_cube_by_vector(vectors, edge, size)
        with open(filename, 'wb') as fh:
            pickle.dump(cubes, fh)
        return cubes, edge

    # form the cubes inside the cubics based on vectors
    def search_cube_by_vector(self, vectorS, edge, size):
        cubes = {}
        i = 0
        for edge, vertexVectors in vectorS.items():
            cube = []
            result = set()
            for vertex, target in vertexVectors.items():
                vset = set(target)
                while vset:
                    checked = set()
                    p = np.array(vertex)
                    select = vset.pop()
                    x = np.array(select) - p
                    for other in vset:
                        y = np.array(other) - p
                    
                        if np.dot(x, y) != 0: continue
                    
                        z = np.cross(x,y)/np.linalg.norm(x)
                        z = z.astype(int)                        
                        if z[2] < 0: z *= -1
                    
                        bad = False
                        c = np.array(
                            [p, p + x, p + y, p + z, p + x + y, p + x + y + z, p + x + z, p + y + z])
    
                        for pp in c:
                            for e in pp: ## check if vertext is out of range
                                if e < 0 or e > size:
                                    bad = True
                                    break                                        
                        if bad: break

                        c = self.sort(c)
                        if np.array_str(c) not in result:
                            result.add(np.array_str(c))
                            cube.append(list(c))
                            for i in range(1, 4):
                                checked.add(tuple(c[i]))
                    vset = vset - checked
            cubes[edge] = cube
        return cubes

    def get_quadruple_cubic(self, vector):
        cubic_size = np.sum(vector[0])
        if len(vector) >= 2 and np.sum(vector[1]) > cubic_size: cubic_size = np.sum(vector[1])
        if len(vector) == 3 and np.sum(vector[2]) > cubic_size: cubic_size = np.sum(vector[2])
        vectors = get_vector_all(vector)
        cubics, edge = self.find_cubes(cubic_size, vectors)
        return len(cubics[edge])

    def selftest(self):
        print 'test start'
        tests = [(4, 5, [np.array([2,2,1])]),
        (8, 11, [np.array([6,3,2])]),
        (8, 19, [np.array([12,4,3])]),
        (108, 15, [np.array([8,4,1]), np.array([7,4,4])]),
        (108, 19, [np.array([9,6,2]), np.array([7,6,6])]),
        (32, 6, [np.array([2,2,1])]),  #4*2^3
        (108, 7, [np.array([2,2,1])]),  #4*3^3
        (256, 8, [np.array([2,2,1])]),  #4*4^3
        (363, 25, [np.array([14,5,2]), np.array([11,10,2]), np.array([10,10,5])])  #4*4^3
        ]
        for t in tests:
            vector = t[2]
            cubic_size = t[1]
            vectors = get_vector_all(vector)
            cubics, edge = self.find_cubes(cubic_size, vectors)
            print '{} result {} ,ret={}'.format(t[2], t[0] == len(cubics[edge]), len(cubics[edge]))
        print 'test end'

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

def main():
    print 'Lattice'
    LIMIT = 5000
    l = Lattice()
    #print l.get_quadruple_cubic([np.array([12,9,8]), np.array([12,12,1])])
    l.draw_cube(51, [(32, 7, 4), (28, 17, 4), (28, 16, 7)])
    #l.draw()
    #l.selftest()
    # l.get_vectors(50)

    # test_Formula()
    # print PythagoreanTriple(100)
    # print PythagoreanQuadrupleSqrt(50)
    # test_quad()
    # triple = PythagoreanTriple(LIMIT))


    #l.draw_cube(5, [np.array([2,2,1])])
    #l.draw_cube(35, [np.array([19,8,4]), np.array([16,11,8])])
    #l.draw()
    # cubes = l.find_cubes(18)
    # print cubes[9]['[4 4 7]']
    #for i in range(5, 8):
    #    cubes = l.find_cubes(i)
    #    for edge in cubes.kemin2ys():
    #        print '{0}:{1}'.format(edge, sum([len(x) for x in cubes[edge].values()]))
        #for edge in cubes.keys():
        #    print '{0}:{1}'.format(edge, len(cubes[edge]))


if __name__ == '__main__':
    main()

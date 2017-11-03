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

    def get_dot(self, size):
        v = np.array(self.get_dot_impl(size))
        c = self.find_centerv(v)
        return np.array([x - c for x in v])

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

    def get_cubelines(self, size, base=np.array([0, 0, 0])):
        lines = []
        verts = self.get_cube(size, base)
        for i in range(0, len(verts)):
            v = verts[i]
            for j in range(0, len(verts)):
                vv = verts[j]
                if np.linalg.norm(np.abs(v - vv)) == size:
                    lines.append([v, vv])
        return np.array(lines)


class Lattice:
    def __init__(self, size):
        self.name = 'Lattice cube'
        self.cube = MyCube()
        self.ax = fig.add_subplot(111, projection='3d')
        self.size = size

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

        axis = [-1, -1, 0]
        theta = math.radians(70)
        theta2 = math.radians(-70)

        n = size
        allvertex = self.rotatedots(self.cube.get_dot(size), axis, theta)
        allvertex = np.array([x for x in allvertex if x[0] < 1.6 and x[0] >
                              -1.6 and x[1] < 1.6 and x[1] > -1.6 and x[2] < 1.6 and x[2] > -1.6])
        vertexOuter = np.array([x for x in allvertex if x[0] < 1.4 and x[0] >
                                -1.4 and x[1] < 1.4 and x[1] > -1.4 and x[2] < 1.4 and x[2] > -1.4])
        allvertex = self.rotatedots(allvertex, axis, theta2)
        vertexOuter = self.rotatedots(vertexOuter, axis, theta2)

        eulercubic = self.cube.get_sampledot()
        cu = self.rotateedges(self.cube.get_sampelines(), axis, theta)
        clines = self.cube.get_sampelines()
        cv = self.cube.get_cubelines(n, base)

        for x, y, z in [x + np.array([2.5, 2.5, 2.5]) for x in eulercubic]:
            label = '(%d, %d, %d)' % (x, y, z)
            self.ax.text(x - 2.5, y - 2.5, z - 2.5, label)

        self.ax.scatter3D(
            eulercubic[:, 0], eulercubic[:, 1], eulercubic[:, 2], s=59, c=c)
        self.ax.scatter3D(
            allvertex[:, 0], allvertex[:, 1], allvertex[:, 2], s=59, c=ccc)
        self.ax.scatter3D(
            vertexOuter[:, 0], vertexOuter[:, 1], vertexOuter[:, 2], s=59, c=c3)
        self.ax.add_collection3d(Line3DCollection(
            cv, linewidths=1, colors='b', alpha=.5))
        self.ax.add_collection3d(Line3DCollection(
            clines, linewidths=2, colors='g', alpha=.5))

    def draw_innerCube(self, size):
        # iterate normal cubes
        for i in range(size, size + 1):
            for x in range(0, self.size - i + 1, 1):
                for y in range(0, self.size - i + 1, 1):
                    for z in range(0, self.size - i + 1, 1):
                        self.draw_cube(i, np.array([x, y, z]))

    # 0 0 0
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

    def getCubeCount(self, size):
        count = 0
        n = size
        vertex = self.cube.get_dot(size)
        vertexSet = set([tuple(v) for v in vertex])
        for edge in range(1, n + 1):
            for verts in vertexSet:
                if self.hasCube(self.getCubeVertexes(verts, edge), vertexSet):
                    count += 1
        print count

    def hasCube(self, cube, lattice):
        for v in cube:
            if v not in lattice:
                return False
        return True

    # x,y,z => check the integer solution for instance d
    def bruteforce(self):
        self.getCubeCount(10)

    def draw(self):
        self.ax.set_xlabel('X')
        self.ax.set_ylabel('Y')
        self.ax.set_zlabel('Z')
        plt.show()


def main():
    print 'Lattice'
    l = Lattice(5)
    # l.bruteforce()
    # l.test()
    l.draw_cube(5)
    l.draw()


if __name__ == '__main__':
    main()

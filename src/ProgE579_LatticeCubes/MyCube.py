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
import fractions ## for gcd
import collections ## ordered dictionary
import pickle 

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
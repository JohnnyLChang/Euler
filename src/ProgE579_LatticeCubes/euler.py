import numpy as np
import math

def rotation_matrix(axis, theta):
    """
    Return the rotation matrix associated with counterclockwise rotation about
    the given axis by theta radians.
    """
    axis = np.asarray(axis)
    theta = np.asarray(theta)
    axis = axis/math.sqrt(np.dot(axis, axis))
    a = math.cos(theta/2)
    b, c, d = -axis*math.sin(theta/2)
    aa, bb, cc, dd = a*a, b*b, c*c, d*d
    bc, ad, ac, ab, bd, cd = b*c, a*d, a*c, a*b, b*d, c*d
    return np.array([[aa+bb-cc-dd, 2*(bc+ad), 2*(bd-ac)],
                     [2*(bc-ad), aa+cc-bb-dd, 2*(cd+ab)],
                     [2*(bd+ac), 2*(cd-ab), aa+dd-bb-cc]])


def main():
    v = [3, 5, 0]
    axis = [1, 10, 2]
    theta = 30

    print(np.dot(rotation_matrix(axis,theta), v))

    v1 = [0,2,2]
    v2 = [2,0,3]
    mindelta = 100
    for i in range(0, 30):
        for j in range(0, 30):
            for k in range(0, 30):
                for t in range(0, 90):
                    a = [i,j,k]
                    theta = t
                    v1 = np.dot(rotation_matrix(axis,theta), v1)
                    v2 = np.dot(rotation_matrix(axis,theta), v2)
                    delta = np.abs(v1[2]-v2[2])+np.abs(v1[1]-v2[1])+np.abs(v1[0]-v2[0]) - 3
                    if delta < mindelta:
                        mindelta = delta
                        print v1
                        print v2
                        print '{0},{1},{2}:{3}'.format(i,j,k,t)


if __name__ == '__main__':
    main()

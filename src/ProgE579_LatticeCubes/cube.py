from visual import sphere,color

count = 3
R=0.3

for x in range(-count,count+1):
    for y in range(-count,count+1):
        for z in range(-count,count+1):
            if ((x+y+z+3*count)%2) == 0:
                sphere(pos=[x,y,z],radius=R,color=color.green)
            else:
                sphere(pos=[x,y,z],radius=R,color=color.yellow)

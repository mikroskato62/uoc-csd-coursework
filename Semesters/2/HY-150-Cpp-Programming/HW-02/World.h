// The "World.h" header file that works with "World.cpp" and is needed for "Assignment2.cpp" ..!

#ifndef WORLD_H
#define WORLD_H

#include "std_lib_facilities.h"
#include "Triangle.h"
#include "Camera.h"
using namespace std;

// The class that creates the world based on different values.
class World
{
public:
    // Constructor: Creates a world with sky color and filename.
    World(vec3 skycolor, string filename);

    // Method Add: Adds a triangle to the world.
    void Add(Triangle &tri);

    // Method Render: Renders the world into an image.
    void Render(Camera &cam);

private:
    // The triangles (private) variables needed.
    vector<Triangle> Triangles_;
    vec3 SkyColor_;
    string filename_;
};

#endif // WORLD_H

// The end of "World.h" ...

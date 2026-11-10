// The "Triangle.h" header file that works with "Triangle.cpp" and needed for the "Assignment2.cpp" ..!

#ifndef TRIANGLE_H  // Checks if the "TRIANGLE_H" macro is not defined.
#define TRIANGLE_H  // Defines the "TRIANGLE_H" macro to prevent multiple inclusions of the same file.

#include "vec3.h"
#include "ray.h"

// The class that creates the 2D triangles based different values.
class Triangle
{
public:
    // Constructor: Creates a triangle with 3 vectors and one (rgb) color.
    Triangle(const vec3 &v0, const vec3 &v1, const vec3 &v2, const vec3 &color);

    // Method Hit: Checks if a ray intersects with the triangle and returns true, otherwise false.
    bool Hit(const ray &r) const;

    // Getters: Retrieve the values of the private member variables.
    vec3 GetV0() const;
    vec3 GetV1() const;
    vec3 GetV2() const;
    vec3 GetColor() const;

    // Setters: Modify the values of the private member variables.
    void SetV0(const vec3 &v0);
    void SetV1(const vec3 &v1);
    void SetV2(const vec3 &v2);
    void SetColor(const vec3 &color);

private:
    // The triangles (private) variables needed.
    vec3 (v0_);
    vec3 (v1_);
    vec3 (v2_);
    vec3 (color_);
};

#endif // TRIANGLE_H  // End of include guard.

// The end of the "Triangle.h" ...

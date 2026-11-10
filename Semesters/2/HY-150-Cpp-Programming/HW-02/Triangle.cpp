// The "Triangle.cpp" source file that works with "Triangle.h" and needed for the "Assignment2.cpp" ..!

#include "Triangle.h"
#define TriangleClass Triangle::

// Constructor: Creates one triangle and initializes its variables.
TriangleClass Triangle(const vec3 &v0, const vec3 &v1, const vec3 &v2, const vec3 &color) : v0_(v0), v1_(v1), v2_(v2), color_(color) {}

// Method Hit: Checks if a ray intersects with the triangle.
bool TriangleClass Hit(const ray &r) const
{
    // A way to check if a ray intersects with a triangle.
    vec3 edge1 = (v1_) - (v0_);
    vec3 edge2 = (v2_) - (v0_);
    vec3 h = cross(r.direction(), edge2);
    double a = dot(edge1, h);
    if (fabs(a) < 0) { return false; }
    double f = (1.0 / a);
    vec3 s = (r.origin() - v0_);
    double u = (f * dot(s, h));
    if (u < 0.0 || u > 1.0) { return false; }
    vec3 q = cross(s, edge1);
    double v = (f * dot(r.direction(), q));
    if (v < 0.0 || (u + v) > 1.0) { return false; }
    double t = (f * dot(edge2, q));
    if (t < 0) { return false; }
    else return true;
}

// Getters: Retrieve the values of the private member variables.
vec3 TriangleClass GetV0() const { return (v0_); }
vec3 TriangleClass GetV1() const { return (v1_); }
vec3 TriangleClass GetV2() const { return (v2_); }
vec3 TriangleClass GetColor() const { return (color_); }

// Setters: Modify the values of the private member variables.
void TriangleClass SetV0(const vec3 &v0) { v0_ = v0; }
void TriangleClass SetV1(const vec3 &v1) { v1_ = v1; }
void TriangleClass SetV2(const vec3 &v2) { v2_ = v2; }
void TriangleClass SetColor(const vec3 &color) { color_ = color; }

// The end of the "Triangle.cpp" ...

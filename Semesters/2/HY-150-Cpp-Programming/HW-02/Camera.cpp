// The "Camera.cpp" source file that works with "Camera.h" and needed for the "Assignment2.cpp" ..!

#include "Camera.h"
#define CameraClass Camera::

// Constructor: Creates a camera with a center, width, and aspect ratio.
CameraClass Camera(vec3 center, int width, float aspectW, float aspectH) : center_(center), width_(width), aspectW_(aspectW), aspectH_(aspectH)
{
    // Make the right (standard) calculations.
    aspectratio_ = (aspectW_) / (aspectH_);
    height_ = static_cast<int>((width_ / aspectratio_));
    if (height_ < 1) { height_ = 1; }
    focallength_ = 1.0;
    viewportH_ = 2.0;
    viewportW_ = (viewportH_ * (static_cast<float>(width_) / height_));
    viewportU_ = vec3(viewportW_, 0, 0);
    viewportV_ = vec3(0, -viewportH_, 0);
    pixeldeltaU_ = (viewportU_ / width_);
    pixeldeltaV_ = (viewportV_ / height_);
    viewportupperleft_ = (center_ - vec3(0, 0, focallength_) - viewportU_ / 2 - viewportV_ / 2);
    pixel00Loc_ = (viewportupperleft_ + 0.5 * (pixeldeltaU_ + pixeldeltaV_));
}

// Method GetPixelCenter: Returns the position of the center of a pixel.
vec3 CameraClass GetPixelCenter(int i, int j) const { return (pixel00Loc_ + (i * pixeldeltaU_) + (j * pixeldeltaV_)); }

// Getters: Return the values of the variables.
vec3 CameraClass GetCenter() const { return (center_); }
int CameraClass GetWidth() const { return (width_); }
int CameraClass GetHeight() const { return (height_); }

// The end of the "Camera.cpp" ...

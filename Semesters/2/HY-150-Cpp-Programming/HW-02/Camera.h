// The "Camera.h" header file that works with "Camera.cpp" and needed for the "Assignment2.cpp" ..!

#ifndef CAMERA_H
#define CAMERA_H

#include "vec3.h"

// The class that creates the camera based on different values.
class Camera {
public:
    // Constructor: Creates a camera with a center, width, and aspect ratio.
    Camera(vec3 center, int width, float aspectW, float aspectH);

    // Method GetPixelCenter: Returns the position of the center of a pixel.
    vec3 GetPixelCenter(int i, int j) const;

    // Getters: Return the values of the variables.
    vec3 GetCenter() const;
    int GetWidth() const;
    int GetHeight() const;

private:
    // The camera's (private) variables needed.
    vec3 center_;              // Center of the camera.
    int height_;               // Computed image height.
    int width_;                // Width of the image.
    float aspectH_;            // Aspect ratio height.
    float aspectW_;            // Aspect ratio width.
    float aspectratio_;        // Computed aspect ratio.
    float focallength_;        // Distance from camera to viewport.
    float viewportH_;          // Height of the viewport.
    float viewportW_;          // Width of the viewport.
    vec3 viewportU_;           // Viewport width vector.
    vec3 viewportV_;           // Viewport height vector.
    vec3 pixeldeltaU_;         // Horizontal step per pixel.
    vec3 pixeldeltaV_;         // Vertical step per pixel.
    vec3 viewportupperleft_;   // Upper-left corner of the viewport.
    vec3 viewportupperright_;  // Upper-right corner of the viewport.
    vec3 viewportbottomleft_;  // Bottom-left corner of the viewport.
    vec3 viewportbottomright_; // Bottom-right corner of the viewport.
    vec3 pixel00Loc_;          // Default center of pixel (0,0).
    vec3 upper_left_pixel_;    // Upper-left corner of the pixel.
    vec3 upper_right_pixel;    // Upper-right corner of the pixel.
    vec3 bottom_left_pixel_;   // Bottom-left corner of the pixel.
    vec3 bottom_right_pixel_;  // Bottom-right corner of the pixel.
    vec3 pixel_center;         // Center of the pixel.
};

#endif // CAMERA_H

// The end of the "Camera.h" ...

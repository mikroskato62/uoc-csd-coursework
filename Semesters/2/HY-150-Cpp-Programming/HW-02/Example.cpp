#include "vec3.h"
#include "color.h"
#include "ray.h"

#include <fstream> // For file I/O
#include <iostream>

/**
 * Checks if a ray intersects with a triangle.
 *
 * @param v0 The first point of the triangle.
 * @param v1 The second point of the triangle.
 * @param v2 The third point of the triangle.
 * @param r The ray to check for intersection.
 * @return True if the ray intersects with the triangle, false otherwise.
 */
bool hit_triangle(vec3 v0, vec3 v1, vec3 v2, const ray &r)
{
    vec3 edge1 = v1 - v0;
    vec3 edge2 = v2 - v0;
    vec3 h = cross(r.direction(), edge2);
    double a = dot(edge1, h);

    if (fabs(a) < 0) {
        return false;  
    }

    double f = 1.0 / a;
    vec3 s = r.origin() - v0;
    double u = f * dot(s, h);

    if (u < 0.0 || u > 1.0) {
        return false;
    }

    vec3 q = cross(s, edge1);
    double v = f * dot(r.direction(), q);

    if (v < 0.0 || (u + v) > 1.0) {
        return false;
    }
    double t = f * dot(edge1, q);

    if (t < 0) {
        return false;
    }

    return true;
}

/**
 * Calculates the color of a ray.
 *
 * @param r The ray for which to calculate the color.
 * @return The calculated color.
 */
color ray_color(const ray &r)
{
    // This line calls the hit_triangle function with a triangle centered at (0, 0, -1), a radius of 0.5, and a ray r. 
    // The hit_triangle function checks if the ray intersects the triangle and returns true if it does and false otherwise.
    // If the hit_triangle function returns true, it returns a color object representing the color red. 
    // The color class is a custom type that represents a color in RGB format. 
    // The constructor color() initializes a color with the given red, green, and blue components. 
    // In this case, the color is fully red, as indicated by the arguments (1, 0, 0).
    if (hit_triangle(vec3(-0.5, 0.5, -1), vec3(0.5, 0.5, -1),vec3(0, -0.5, -1), r)){
        return color(1, 0, 0); // red
    }

    vec3 unit_direction = unit_vector(r.direction());
    auto a = 0.5 * (unit_direction.y() + 1.0);
    return (1.0 - a) * color(1.0, 1.0, 1.0) + a * color(0.5, 0.7, 1.0);
}

int main()
{
    // Image
    auto aspect_ratio = 16.0 / 9.0;
    int image_width = 600;

    // Calculate the image height, and ensure that it's at least 1.
    int image_height = static_cast<int>(image_width / aspect_ratio);
    image_height = (image_height < 1) ? 1 : image_height;

    // Camera
    auto focal_length = 1.0;
    auto viewport_height = 2.0;
    auto viewport_width = viewport_height * (static_cast<double>(image_width) / image_height);
    auto camera_center = point3(0, 0, 0);

    std::cout << "Image width: " << image_width << "\nImage height: " << image_height << "\n";
    std::cout << "Viewport width: " << viewport_width << "\nViewport height: " << viewport_height << "\n";

    // Calculate the vectors across the horizontal and down the vertical viewport edges.
    auto viewport_u = vec3(viewport_width, 0, 0);
    auto viewport_v = vec3(0, -viewport_height, 0);

    // Calculate the horizontal and vertical delta vectors from pixel to pixel.
    auto pixel_delta_u = viewport_u / image_width;
    auto pixel_delta_v = viewport_v / image_height;

    // Calculate the position of the upper left corner of a viewport
    // and the location of the center of the pixel at the (0,0) position in a 3D space.
    // It starts from the camera_center, moves backwards along the z-axis by
    // focal_length units (since vec3(0, 0, focal_length) represents a vector pointing along the z-axis),
    // and then moves half the viewport's width and height in the negative u and v directions.
    // The result is stored in the viewport_upper_left variable.
    auto viewport_upper_left = camera_center - vec3(0, 0, focal_length) - viewport_u / 2 - viewport_v / 2;
    auto viewport_upper_right = camera_center - vec3(0, 0, focal_length) + viewport_u / 2 - viewport_v / 2;
    auto viewport_bottom_left = camera_center - vec3(0, 0, focal_length) - viewport_u / 2 + viewport_v / 2;
    auto viewport_bottom_right = camera_center - vec3(0, 0, focal_length) + viewport_u / 2 + viewport_v / 2;



    // This line calculates the location of the center of the pixel at the (0,0) position.
    //  It starts from the viewport_upper_left position, and then moves half the width and
    //  height of a pixel in the positive u and v directions. The result is stored in the pixel00_loc variable.
    auto pixel00_loc = viewport_upper_left + 0.5* (pixel_delta_u + pixel_delta_v);
    auto upper_right_pixel = viewport_upper_right + 0.5* (pixel_delta_u + pixel_delta_v);
    auto bottom_left_pixel = viewport_bottom_left + 0.5 * (pixel_delta_u + pixel_delta_v);
    auto bottom_right_pixel = viewport_bottom_right + 0.5 * (pixel_delta_u + pixel_delta_v);


    std::cout << "Upper Left Pixel Location: " << pixel00_loc.x() << ", " << pixel00_loc.y() << ", " << pixel00_loc.z() << "\n";
    std::cout << "Upper Right Pixel Location: " << upper_right_pixel.x() << ", " << upper_right_pixel.y() << ", " << upper_right_pixel.z() << "\n";
    std::cout << "Bottom Left Pixel Location: " << bottom_left_pixel.x() << ", " << bottom_left_pixel.y() << ", " << bottom_left_pixel.z() << "\n";
    std::cout << "Bottom Right Pixel Location: " << bottom_right_pixel.x() << ", " << bottom_right_pixel.y() << ", " << bottom_right_pixel.z() << "\n";

    
    std::ofstream fout = std::ofstream("output.ppm", std::ofstream::trunc);

    // Render the ra-traced image
    fout << "P3\n"
              << image_width << " " << image_height << "\n255\n";

    for (int j = 0; j < image_height; ++j) // y coordinates
    {
        std::cout << "\rScanlines remaining: " << (image_height - j) << ' ' << std::flush; // number of scanlines remaining
        for (int i = 0; i < image_width; ++i) // x coordinates
        {
            // calculates the location of the center of the current pixel in the viewport. 
            // It starts from the location of the center of the pixel at the (0,0) position (pixel00_loc), 
            // and then moves i times the width of a pixel in the u direction and j times the height of a pixel in the v direction.
            auto pixel_center = pixel00_loc + (i * pixel_delta_u) + (j * pixel_delta_v);

            // This line calculates the direction of the ray that will be cast through the current pixel. 
            // It subtracts the position of the camera from the position of the pixel center, 
            // resulting in a vector that points from the camera to the pixel center.
            auto ray_direction = pixel_center - camera_center;
            
            // creates a ray object with the camera center as the origin and the calculated direction.
            ray r(camera_center, ray_direction);
            // calculates the color of the pixel based on the direction of the ray
            color pixel_color = ray_color(r);
            
            // writes the color to the output stream in a format suitable for an image file.
            write_color(fout, pixel_color);
        }
    }
    return 0;
}
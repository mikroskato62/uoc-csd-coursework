// The "World.cpp" source file that works with "World.h" and needed for the "Assignment2.cpp" ..!

#include "World.h"    // Includes the declaration of the "World" class
#define WorldClass World::
using namespace std;

// Constructor: Creates a world with a color and a name and initializes its varaibles.
WorldClass World(vec3 skycolor, string filename) : SkyColor_(skycolor), filename_(filename) {}

// Method Add: Adds a triangle to the end of the Triangles_ vector
void WorldClass Add(Triangle &tri) { (Triangles_).push_back(tri); }

// Method Render: Outputs the world to an (.ppm) image using the camera.
void WorldClass Render(Camera &cam)
{
    int image_width = cam.GetWidth();
    int image_height = cam.GetHeight();

    ofstream fout((filename_), ofstream::trunc);
    fout << "P3\n" << (image_width) << " " << (image_height) << "\n255\n";

    for (int j = 0; j < image_height; j++)
    {
        for (int i = 0; i < image_width; i++)
        {
            vec3 pixel_center = cam.GetPixelCenter(i, j);
            vec3 ray_direction = (pixel_center - cam.GetCenter());
            ray r(cam.GetCenter(), ray_direction);
            vec3 color = (SkyColor_);

            for (size_t k = 0; k < Triangles_.size(); k++)
            {
                if (Triangles_[k].Hit(r))
                {
                    color = Triangles_[k].GetColor();
                    break;
                }
            }
            int r0 = static_cast<int>(255.999 * color.x());
            int g0 = static_cast<int>(255.999 * color.y());
            int b0 = static_cast<int>(255.999 * color.z());
            fout << (r0) << " " << (g0) << " " << (b0) << "\n";
        }
    }

    fout.close();
}

// The end of the "World.cpp" ...

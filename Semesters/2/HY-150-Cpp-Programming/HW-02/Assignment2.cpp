// UOC - CSD - HY150 - C++ - Spring2025 - csd5748 - Ass2(multiple files) - :)

/**
 * @file Assignment2.cpp
 * @brief Assignment 2 - HY-150
 * @author: csd5748
 */

#include "std_lib_facilities.h"
#include "Triangle.h"
#include "Camera.h"
#include "World.h"
#define Error runtime_error
using namespace std;


int main()
{
    try
    {
        // Reading from the file ...
        ifstream fin("world.txt");
        if (fin.is_open() == false) { throw Error("[Error] Please make sure 'world.txt' and 'assignment2.exe' are on the same folder !"); }

        string filename;
        fin >> filename;
        string realfilename = filename + ".ppm";

        float camx, camy, camz;
        fin >> camx >> camy >> camz;
        vec3 cameracenter(camx, camy, camz);

        int imagewidth;
        fin >> imagewidth;
        if (imagewidth <= 0) { throw Error("[Error] Number of width must be a positive integer !"); }

        float aspectw, aspecth;
        fin >> aspectw >> aspecth;
        if (aspecth < 0 || aspectw < 0) { throw Error("[Error] Number of aspects must be a positive integer !"); }

        float skyr, skyg, skyb;
        fin >> skyr >> skyg >> skyb;
        vec3 skycolor(skyr, skyg, skyb);
        if (skyr < 0 || skyg < 0 || skyb < 0) { throw Error("[Error] Number of colours must be a positive float !"); }

        int trinum;
        fin >> trinum;
        if (trinum < 0) { throw Error("[Error] Number of triangles must be a positive integer !"); }


        // Creating the image ...
        World world(skycolor, realfilename);

        for (int i = 0; i < trinum; i++)
        {
            float r0, g0, b0;
            float v0x, v0y, v0z;
            float v1x, v1y, v1z;
            float v2x, v2y, v2z;
            fin >> r0 >> g0 >> b0 >> v0x >> v0y >> v0z >> v1x >> v1y >> v1z >> v2x >> v2y >> v2z;
            if (r0 < 0 || g0 < 0 || b0 < 0) { throw Error("[Error] Numbers for colors must be positive floats !"); }
            if (! fin.good()) { throw Error("[Error] Failed to read triangle's data correctly !"); }

            vec3 v0(v0x, v0y, v0z);
            vec3 v1(v1x, v1y, v1z);
            vec3 v2(v2x, v2y, v2z);
            vec3 color(r0, g0, b0);

            Triangle triangle(v0, v1, v2, color);
            world.Add(triangle);
        }

        fin.close();

        Camera camera(cameracenter, imagewidth, aspectw, aspecth);
        world.Render(camera);

        cout << "[!] " << realfilename << " Generated Successfully.\n";

    }
    catch (const Error & e)
    {
        cerr << e.what() << endl;
        return 1;
    }

    return 0;
}

// [!] Other Files Required: "Triangle.h", "Triangle.cpp", "Camera.h", "Camera.cpp", "World.h", "World.cpp".
// [!] Other Files Required: "color.h", "ray.h", "vec3.h", "world.txt", "std_lib_facilities.h", "CMakeLists.txt".
// [!] I Have Created The GitHub Repository ...
// The End Of My Code :)

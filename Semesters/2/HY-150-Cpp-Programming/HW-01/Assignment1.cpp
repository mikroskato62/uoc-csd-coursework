// UOC - CSD - Spring 2025 - HY150 - Ass01 - C++ - csd5748 - :)

/**
 * @file Assignment1.cpp
 *
 * @brief Assignment 1 - HY-150
 *
 * @author: csd5748
 */

// External libraries and header files required:
#include "std_lib_facilities.h"
#include <iostream>
#include <fstream>
// #include "color.h"
// #include "vec3.h"

// Standard namespace and an alias:
using namespace std;
using Error = runtime_error;

// Variable for the max number of user's options:
#define MAXOPTIONS 4

// Used functions declaration:
int dimensionscheck(char);
void colorscheck(int &, int &, int &);
void printlines(const string &, int, int, int, int, int, int, int, int, int, int);

// The main function:
int main(void)
{
    // Variable for errors checking:
    bool frouros = false;

    // The required "Exceptions":
    try
    {
        // Declarations:
        int r1, g1, b1, r2, g2, b2;    // The two user's colors.
        int k;                         // The user's size/thickness of lines/blocks.
        int imageheight, imagewidth;   // The user's image dimensions.
        string imagename;              // The user's file name.
        cout << "\n";

        // The simple menu I/O with functions for input check:
        cout << "Welcome - Image Generator - csd5748\n\n";
        cout << "Image Name: \n";
        cin >> imagename;
        cout << "Image Width: \n";
        imagewidth = dimensionscheck('a');
        cout << "Image Height: \n";
        imageheight = dimensionscheck('a');
        cout << "First Color: \n";
        colorscheck(r1, g1, b1);
        cout << "Second Color: \n";
        colorscheck(r2, g2, b2);
        cout << "Select One Image Type: \n1. Horizontal Stripes. \n2. Vertical Stripes." << "\n3. Checkerboard. \n4. Alternating Rectangles. \n";
        int selected = dimensionscheck('b');
        cout << "Enter the size of the rectangles: \n";
        k = dimensionscheck('a');

        // Finally, creating the .ppm image based on the inputs:
        printlines(imagename, selected, imageheight, imagewidth, r1, g1, b1, r2, g2, b2, k);
    }
    catch (const Error & b)
    {
        // If anything goes wrong, print the message:
        cout << "\n[ERROR] Too many wrong attempts ... \n";
        frouros = true;
    }

    // The end of the code:
    if (!frouros) {cout<<"\nThe Image Was Generated Successfully :)\n\n";}
    else {cout << "The Image Was Not Generated Successfully :(\n\n";}
    return 0;
}

// Function for checking the user's inputs and return it:
int dimensionscheck(char mode)
{
    // Declarations:
    int value, errorcounter = 0;

    // Loop for re-checking:
    while (true)
    {
        // If input is not "character/string" or "0" or "negative":
        cin >> value;
        if (((value) && (value > 0) && (mode == 'a')))
        {
            // Reset the error state, discard the invalid input and return the input if ok:
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            return value;
        }
        else if (((value) && (value > 0) && (value <= MAXOPTIONS) && (mode == 'b')))
        {
            // Reset the error state, discard the invalid input and return the input if ok:
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            return value;
        }
        else
        {
            // Increase the attempts, print a warning, reset the error state, discard the invalid input and return the input if not ok:
            errorcounter += 1;
            if (errorcounter != 3)
            {
                if (mode == 'a') cout << "Invalid selection! Please enter a positive integer. \n";
                if (mode == 'b') cout << "Invalid selection! Please enter one of the options. \n";
            }
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
        }
        // At three wrong attempts the program stops with an error:
        if (errorcounter == 3)
        {
            throw Error("[!]");
        }
    }
} // End of the function.

// Function for checking the user's inputs and save it by reference:
void colorscheck(int &r, int &g, int &b)
{
    // Declarations:
    int errorcounter = 0;

    // Loop for re-checking:
    while (true)
    {
        // If input is not "string/character" or "between 0 and 255":
        if ((cin >> r >> g >> b) && (r >= 0 && r <= 255) && (g >= 0 && g <= 255) && (b >= 0 && b <= 255))
        {
            // Reset the error state, discard the invalid input and return the input if ok:
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            return;
        }
        else
        {
            // Increase the attempts, print a warning, reset the error state, discard the invalid input and return the input if not ok:
            errorcounter += 1;
            if (errorcounter != 3) cout << "Invalid selection! Please enter: [ r(0-255) g(0-255) b(0-255) ] \n";
            cin.clear();                                                  // Reset error state
            cin.ignore(numeric_limits<streamsize>::max(), '\n');   // Discard invalid input
        }
        // At three wrong attempts the program stops with an error:
        if (errorcounter == 3)
        {
            throw Error("[!]");
        }
    }
} // End of the function.

// Function for creating the file/image based on all user's inputs:
void printlines(const string &name,int mode, int height, int width, int r1, int g1, int b1, int r2, int g2, int b2, int k)
{
    fstream fout; // Fout variable is the file stream. It can be used the same way as cout, but it writes to the file instead of the console.
    fout.open((name + ".ppm"), ios::out | ios::trunc); // Opens a file for output and overwrites the file if it already exists.
    fout << "P3\n" << width << ' ' << height << "\n255\n"; // P3 is the PPM file format and 255 is the maximum color value.

    // Declarations:
    int color0[3];                 // RGB for the current color (without using the «helpful» class):
    int color1[3] = {r1, g1, b1};  // RGB for the first color (user's).
    int color2[3] = {r2, g2, b2};  // RGB for the second color (user's).

    // Start the two loops:
    for (int j = 0; j < height; ++j)
    {
        for (int i = 0; i < width; ++i)
        {
            // (1) Horizontal Stripes - colour #1 (alternating every k rows):
            if ((((mode == 1) && ((j / k) % 2 == 0))))
            {
                color0[0] = color1[0]; color0[1] = color1[1]; color0[2] = color1[2];
            }
            // (2) Vertical Stripes - colour #1 (alternating every k columns):
            else if ((mode == 2) && ((i / k) % 2 == 0))
            {
                color0[0] = color1[0]; color0[1] = color1[1]; color0[2] = color1[2];
            }
            // (3) Checkerboard - colour #1 (alternating between rows and columns every k loops).
            else if ((mode == 3) && (((i / k) + (j / k)) % 2 == 0))
            {
                color0[0] = color1[0]; color0[1] = color1[1]; color0[2] = color1[2];
            }
            // (4) Alternating Rectangles - colour #1 (based on min between distance i to j and distance edge to edge):
            else if ((mode == 4) && ((min(min(i, j), min(width - i - 1, height - j - 1)) / k % 2) == 0))
            {
                color0[0] = color1[0]; color0[1] = color1[1]; color0[2] = color1[2];
            }
            // (-) Other case - colour #2 (for all other positions).
            else
            {
                color0[0] = color2[0]; color0[1] = color2[1]; color0[2] = color2[2];
            }
            // Write "RGB" colors to the file (without using the «helpful» function):
            fout << color0[0] << " " << color0[1] << " " << color0[2] << "\n";
        }
    // Print the progress of the image creation:
    cout << "Generating Line " << (j+1) << " Out Of " << width << " ...\n";
    }
    // Close the file and return to main:
    fout.close();
} // End of the function.


// End of Code :D
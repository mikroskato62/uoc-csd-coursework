// ----------------------------------------------------------------------------------------------------

// [-] This is the example code from Chapter 12.3 "A first example" of
// "Programming -- Principles and Practice Using C++" by Bjarne Stroustrup.
// Modified by "csd5748" for the HY-150 Assignment 3 ...

// [!] Prevent Windows from including wingdi.h to avoid 'Polygon' ambiguous error:
#if defined(WIN32) || defined(_WIN32) || defined(__WIN32) && !defined(__CYGWIN__)
#define NOGDI
#endif

// [!] Access to window library, graphics library facilities, etc.:
#include "Simple_window.h"
#include "Graph.h"
#include "std_lib_facilities.h"
#include <iostream>

// [!] Use standard namespace, Graph_lib namespace for graphics, and Color:
using namespace std;
using namespace Graph_lib;
#define Color Color::

// [A] Struct to represent a node in the graph:
struct Node
{
    int x;          // X-coordinate of the node.
    int y;          // Y-coordinate of the node.
    string label;   // Label (name) of the node.
};

// [A] Struct to represent an edge between two nodes:
struct Edge
{
    int from;   // Index of the starting node.
    int to;     // Index of the ending node.
};

// [A] Struct to represent the arrowhead of an edge:
struct ArrowHead
{
    int ax1, ay1;   // First point of the arrowhead.
    int ax2, ay2;   // Second point of the arrowhead.
};

// [A] Vectors to store all nodes and edges of the graph:
vector<Node> Nodes;
vector<Edge> Edges;

// [A] The given function to compute the arrowhead points:
ArrowHead ComputeArrowHead(Node & from, Node & to)
{
    double angle = atan2(to.y - from.y, to.x - from.x);
    int arrow_len = 10;
    int ax1 = to.x - arrow_len * cos(angle - 0.5);
    int ay1 = to.y - arrow_len * sin(angle - 0.5);
    int ax2 = to.x - arrow_len * cos(angle + 0.5);
    int ay2 = to.y - arrow_len * sin(angle + 0.5);
    return ArrowHead{ax1, ay1, ax2, ay2};
}


// [-] The main function:
int main(void)
{
    // [B] Try opening the Graph.txt file for reading:
    ifstream file("Graph.txt");
    if (!file.is_open())
    {
        cerr << "[Error]: Could not open the Graph.txt file ... \n";
        return 1;
    }

    // [B] Reading nodes until "#edges" is encountered:
    string line;
    while (getline(file, line) && (line != "#edges"))
    {
        // [B] Create a string-stream to store the line and read it:
        stringstream ss(line);
        string label;
        int x, y;
        ss >> label >> x >> y;

        // [B] Create a new Node and add it to Nodes vector:
        Node node;
        node.label = label;
        node.x = x;
        node.y = y;
        Nodes.push_back(node);
    }

    // [B] Reading edges after the "#edges":
    int from, to;
    while (file >> from >> to)
    {
        // [B] Create a new Edge and add it to Edges vector
        Edge edge;
        edge.from = from;
        edge.to = to;
        Edges.push_back(edge);
    }

    // [B] Close the file when done:
    file.close();

    // [!] Create a simple window:
    Point tl(100, 100);
    Simple_window win(tl, 600, 400, " Canvas - csd5748 ");

    // [C] Create circles with constant radius and labels for each node:
    const int radius = 26;
    vector<Circle*> circles;
    vector<Text*> labels;

    for (auto i = 0; i < Nodes.size(); i++)
    {
        // [C] Create a new Circle with the point as center:
        Circle* circle = new Circle(Point(Nodes[i].x, Nodes[i].y), radius);
        circle->set_color(Color blue);
        win.attach(*circle);
        circles.push_back(circle);

        // [C] Create a new Text label as the "example image":
        Text* label = new Text(Point((Nodes[i].x - 5), (Nodes[i].y + 7)), Nodes[i].label);
        label->set_color(Color red);
        win.attach(*label);
        labels.push_back(label);
    }

    // [C] Create lines and arrowheads for each edge:
    vector<Line*> lines;
    vector<Line*> arrows1;
    vector<Line*> arrows2;
    for (auto i = 0; i < Edges.size(); i++)
    {
        int from_idx = Edges[i].from;
        int to_idx = Edges[i].to;

        // Create a line:
        Line* line = new Line(Point(Nodes[from_idx].x, Nodes[from_idx].y), Point(Nodes[to_idx].x, Nodes[to_idx].y));
        line->set_color(Color black);
        win.attach(*line);
        lines.push_back(line);

        // [C] Compute arrowhead for the edge:
        ArrowHead ah = ComputeArrowHead(Nodes[from_idx], Nodes[to_idx]);

        // [C] Create two lines for the arrowhead:
        Line* arrow1 = new Line(Point(Nodes[to_idx].x, Nodes[to_idx].y), Point(ah.ax1, ah.ay1));
        arrow1->set_color(Color blue);
        win.attach(*arrow1);
        arrows1.push_back(arrow1);
        Line* arrow2 = new Line(Point(Nodes[to_idx].x, Nodes[to_idx].y), Point(ah.ax2, ah.ay2));
        arrow2->set_color(Color blue);
        win.attach(*arrow2);
        arrows2.push_back(arrow2);
    }

    // [!] Wait for the user to press the "Next" button:
    cout << "[!] Window created successfully! Click the button to exit ... \n";
    win.wait_for_button();

    // [-] Clean up the dynamically allocated memory used:
    for (Circle* c : circles) delete c;
    for (Text* t : labels) delete t;
    for (Line* l : lines) delete l;
    for (Line* a1 : arrows1) delete a1;
    for (Line* a2 : arrows2) delete a2;

    // [-] The End:
    return 0;
}

// Other Files Required:
// std_lib_facilities.h, Simple_window.h, Graph.h, vec3.h, Graph.txt, etc. ...
// ----------------------------------------------------------------------------------------------------

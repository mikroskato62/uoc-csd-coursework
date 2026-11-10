# [CMakeLists.txt] The needed file that includes only the Library.c ...
cmake_minimum_required(VERSION 3.30)
project(Project__1 C)
set(CMAKE_C_STANDARD 11)
include_directories(.)
add_executable(Project Library.c)

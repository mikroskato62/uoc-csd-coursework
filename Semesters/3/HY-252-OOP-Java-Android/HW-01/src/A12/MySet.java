// File 'MySet.java' ...
/** HY252 - A1 - Exercise 2 - RIP csd5748 **/
// TODO: add(int x), contains(int x) ...

package A12;

public class MySet
{
    // Declarations and Initializations:
    private final int[] data;
    private int size;
    public MySet(int capacity)
    {
        data = new int[capacity];
        size = 0;
    }

    // Add a number if it doesn't already exist:
    public void add(int x)
    {
        if (!contains(x))
        {
            data[size] = x;
            size++;
        }
    }

    // Check if the number already exists:
    public boolean contains(int x)
    {
        for (int i = 0; i < size; i++)
            if (data[i] == x) return true;
        return false;
    }

}

// The End :( 
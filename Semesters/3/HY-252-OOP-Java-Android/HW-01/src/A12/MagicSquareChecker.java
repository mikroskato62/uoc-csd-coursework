// File 'MagicSquareChecker.java' ...
/** HY252 - A1 - Exercise 2 - RIP csd5748 **/

package A12;
import java.util.Scanner;

public class MagicSquareChecker
{
    // Main:
    public static void main(String[] args)
    {
        System.out.println("[csd5748] MagicSquareChecker.java & MySet.java ...");

        // TODO 1a: Read 2 < n < 10 from console ...
        Scanner in = new Scanner(System.in);
        int n;
        do
        {
            System.out.print("[Input] Please enter an integer n between 2 and 10: ");
            // Input checking:
            while (!in.hasNextInt())
            {
                System.out.print("[Error] Invalid input entered ... Please try again: ");
                in.next();
            }
            n = in.nextInt();
        }
        while (n < 2 || n > 10);

        // TODO 1b: Read n*n integers from console ...
        // [!] We assume that the users inputs are always correct (so we can input all n^2 numbers at once) ...
        int[][] s = new int[n][n];
        System.out.print("[Input] * Please enter " + (n * n) + " integer numbers (seperated): ");
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                s[i][j] = in.nextInt();
            }
        }

        // TODO 2: Print the formatted table (beautifully) ...
        String line = "-".repeat(n * 6 + 1);
        for (int i = 0; i < n; i++)
        {
            System.out.println(line);
            for (int j = 0; j < n; j++)
            {
                String text = String.valueOf(s[i][j]);
                int width = 5;
                int spaces = width - text.length();
                int left = spaces / 2;
                int right = spaces - left;
                System.out.print("|" + " ".repeat(left) + text + " ".repeat(right));
            }
            System.out.println("|");
        }
        System.out.println(line);


        // TODO 3: Call CheckIsMagic and print the results ...
        if (hasDuplicates(s))
        {
            System.out.println("[Result] The numbers you gave contain duplicates.");
            System.out.println("[Result] The square isn't magic !");
        }
        else
        {
            boolean magic = checkIsMagic(s);
            if (magic) System.out.println("[Result] The square is magic and the magic element is: " + getMagicNumber(s) + ".");
            else System.out.println("[Result] The square isn't magic !");
        }

        System.out.println("[Ok] The End :)");
    }

    // [!] Other (new) functions and methods ...

    // TODO 4: Checking rows, columns and diagonals for the same sum:
    public static boolean checkIsMagic(int[][] s)
    {
        int n = s.length;
        int sum = sumOfRow(s, 0);
        for (int i = 0; i < n; i++)
            if (sumOfRow(s, i) != sum)
                return false;
        for (int j = 0; j < n; j++)
            if (sumOfColumn(s, j) != sum)
                return false;
        if (sumOfDiagonal1(s) != sum || sumOfDiagonal2(s) != sum)
            return false;
        return true;
    }

    // TODO 5: Calculating the sum of rows:
    private static int sumOfRow(int[][] s, int k)
    {
        int sum = 0;
        for (int j = 0; j < s.length; j++)
        {
            sum += s[k][j];
        }
        return sum;
    }

    // TODO 6: Calculating the sum of columns:
    private static int sumOfColumn(int[][] s, int k)
    {
        int sum = 0;
        for (int i = 0; i < s.length; i++)
            sum += s[i][k];
        return sum;
    }

    // TODO 7: Calculating the sum of the first diagonal:
    private static int sumOfDiagonal1(int[][] s)
    {
        int sum = 0;
        for (int i = 0; i < s.length; i++) sum += s[i][i];
        return sum;
    }

    // TODO 8: Calculating the sum of the s diagonal:
    private static int sumOfDiagonal2(int[][] s)
    {
        int sum = 0;
        int n = s.length;
        for (int i = 0; i < n; i++)
            sum += s[i][n - 1 - i];
        return sum;
    }

    // TODO 9: Returning magic number if magic else -1:
    public static int getMagicNumber(int[][] s)
    {
        if (checkIsMagic(s)) return sumOfRow(s, 0);
        else return -1;
    }

    // TODO 10: Check for duplicates using MySet (or another way):
    private static boolean hasDuplicates(int[][] s)
    {
        int n = s.length;
        MySet set = new MySet(n * n);
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (set.contains(s[i][j]))
                {
                    return true;
                }
                set.add(s[i][j]);
            }
        }
        return false;
    }

}

// The End :(
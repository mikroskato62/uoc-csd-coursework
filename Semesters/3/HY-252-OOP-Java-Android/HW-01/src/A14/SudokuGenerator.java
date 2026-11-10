// File 'SudokuGenerator.java' ...
/** HY252 - A1 - Exercise 4 - RIP csd5748 **/

package A14;
import java.util.Random;

// The Class:
public class SudokuGenerator
{
    // Constants:
    private static int MIN_VALUE = 1;
    private static int MAX_VALUE = 9;

    // Main:
    public static void main(String[] args)
    {
        // TODO 1: Gen N and X (default values or user's values) ...
        int N = 10;
        if (args.length > 0)
        {
            try { N = Integer.parseInt(args[0]); }
            catch (NumberFormatException e) { System.err.println("[Error] Invalid N entered - using default ..."); }
        }
        int X = 55;
        if (args.length > 1)
        {
            try { X = Integer.parseInt(args[1]); }
            catch (NumberFormatException e) { System.err.println("[Error] Invalid X entered - using default ..."); }
        }

        // TODO 2: Create Ν sets of board and solution ...
        // TODO 3: Calculate runtime in seconds ...
        // Instructions, declarations and initializations:
        long StartTime = System.currentTimeMillis();
        int validcount = 0, invalidcount = 0, unsolvablecount = 0;
        int[][][] validboards = new int[N][9][9];
        int[][][] solutions = new int[N][9][9];

        // [!!!] Testing - Timeout Program - Lines: 18 & 24 & 40 & 46-50 & 83 & 113 ...
        long timeout = 10_000;
        boolean stop = false;
        while (validcount < N)
        {
            // Instructions:
            long CurrentTime = System.currentTimeMillis();
            if (CurrentTime - StartTime > timeout)
            {
                stop = true;
                break;
            }

            // Keep generating random boards until we have a valid one:
            int[][] board = generateBoardWithHoles(X);
            if (!SudokuUtils.isValidBoard(board))
            {
                invalidcount++;
                continue;
            }

            // Keep trying to solve the board until we can or its unsolvable:
            int[][] solvedboard = SudokuUtils.isSolvableBoard(board);
            if (solvedboard != null)
            {
                for (int i = 0; i < 9; i++)
                    for (int j = 0; j < 9; j++)
                    {
                        validboards[validcount][i][j] = board[i][j];
                        solutions[validcount][i][j] = solvedboard[i][j];
                    }
                validcount++;
            }
            else unsolvablecount = unsolvablecount + 1;
        }

        // Runtimes:
        long EndTime = System.currentTimeMillis();
        double elapsedsecs = (EndTime - StartTime) / 1000.0;

        // TODO 4: Print all statistics ...
        System.out.println("[csd5748] SudokuGenerator.java & SudokuUtils.java ...");
        System.out.println("----------");

        // [!] Testing - for (int i = 0; i < N; i++) ...
        for (int i = 0; i < validcount; i++)
        {
            System.out.println("Board #" + (i + 1));
            for (int r = 0; r < 9; r++)
            {
                for (int c = 0; c < 9; c++)
                {
                    System.out.print(validboards[i][r][c]);
                    if (c < 8) System.out.print(" ");
                }
                System.out.println();
            }
            System.out.println("Solution #" + (i + 1));
            for (int r = 0; r < 9; r++)
            {
                for (int c = 0; c < 9; c++)
                {
                    System.out.print(solutions[i][r][c]);
                    if (c < 8) System.out.print(" ");
                }
                System.out.println();
            }
        }
        System.out.println("----------");
        System.out.println("Empty cells per board (X) : " + X);
        System.out.println("Valid boards created (N)  : " + validcount);
        System.out.println("Invalid boards created    : " + invalidcount);
        System.out.println("Unsolvable boards created : " + unsolvablecount);
        System.out.println("Elapsed time in seconds   : " + String.format("%.3f", elapsedsecs));
        if (stop) System.out.println("[!] Timeout: Program terminated for exceeded ten seconds of runtime without meeting the requirements ...");
        System.out.println("----------\n[!] The End :)");
    }


    // TODO (c): Create board with Χ empty cells ...
    public static int[][] generateBoardWithHoles(int holes)
    {
        // Instructions:
        Random randomGenerator = new Random();
        int[][] board = new int[9][9];
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
                board[row][col] =  randomGenerator.nextInt(MAX_VALUE - MIN_VALUE + 1) + MIN_VALUE;

        // Holes:
        Integer[] positions = new Integer[81];
        for (int i = 0; i < 81; i++) positions[i] = i;
        for (int i = 80; i > 0; i--)
        {
            int j = randomGenerator.nextInt(i + MIN_VALUE);
            int temp = positions[i];
            positions[i] = positions[j];
            positions[j] = temp;
        }
        for (int i = 0; i < holes; i++)
        {
            int pos = positions[i];
            int row = pos / 9;
            int col = pos % 9;
            board[row][col] = 0;
        }
        return board;
    }
}

// [!] Comment 'Instructions' means the code was copied from the provided instructions file ...
// The End :(
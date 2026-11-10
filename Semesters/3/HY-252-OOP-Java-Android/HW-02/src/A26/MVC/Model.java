/** HY-252 - Java - Assignment 2.6 - PRI - csd5748 **/

package A26.MVC;

public class Model
{
    public Model() {}

    public static int[][] GetEmptyBoard() { return (new int[9][9]); }

    public static int[][] Solve(int[][] board)
    {
        // Returning solved board or original if invalid or unsolvable:
        if (IsValidBoard(board))
        {
            int[][] copy = new int[9][9];
            for (int i = 0; i < 9; i++)
                System.arraycopy(board[i], 0, copy[i], 0, 9);

            if (SolveBoard(copy)) { return copy; } else { return board; }
        }
        return board;
    }

    public static boolean IsValidBoard(int[][] board)
    {
        // Initial (basic validity) check for user inputs:
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
            {
                int num = board[row][col];
                if (num > 9) return false;
                if (num != 0)
                {
                    // Temporarily remove number to check, then restore and return:
                    board[row][col] = 0;
                    if (!IsValid(board, row, col, num))
                    {
                        board[row][col] = num;
                        return false;
                    }
                    board[row][col] = num;
                }
            }
        return true;
    }

    private static boolean SolveBoard(int[][] board)
    {
        // Solving using backtracking:
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
                if (board[row][col] == 0)
                {
                    for (int num = 1; num <= 9; num++)
                        if (IsValid(board, row, col, num))
                        {
                            board[row][col] = num;
                            if (SolveBoard(board)) { return true; }
                            board[row][col] = 0;
                        }
                    return false;
                }
        return true;
    }

    private static boolean IsValid(int[][] board, int row, int col, int num)
    {
        // Check row and column:
        for (int i = 0; i < 9; i++)
        { if (board[row][i] == num || board[i][col] == num) return false; }

        // Check 3x3 subgrid:
        int startRow = row - row % 3; int startCol = col - col % 3;
        for (int r = startRow; r < startRow + 3; r++)
            for (int c = startCol; c < startCol + 3; c++)
                if (board[r][c] == num)
                    return false;

        // If we can safely place the <num> then its valid:
        return true;
    }

}

// The End ...
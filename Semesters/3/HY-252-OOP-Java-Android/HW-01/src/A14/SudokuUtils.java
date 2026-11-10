// File 'SudokuUtils.java' ... 
/** HY252 - A1 - Exercise 4 - RIP csd5748 **/
package A14;

// Class:
public class SudokuUtils
{
    // TODO (a): Check for Sudoku validity ...
    public static boolean isValidBoard(int[][] brd)
    {
        // Check rows firstly:
        for (int row = 0; row < 9; row++)
        {
            boolean[] seen = new boolean[9];
            for (int col = 0; col < 9; col++)
            {
                int value = brd[row][col];
                if (value != 0)
                {
                    if (value < 1 || value > 9) return false;
                    if (seen[value - 1]) return false;
                    seen[value - 1] = true;
                }
            }
        }
        // Check columns secondly:
        for (int col = 0; col < 9; col++)
        {
            boolean[] seen = new boolean[9];
            for (int row = 0; row < 9; row++)
            {
                int value = brd[row][col];
                if (value != 0)
                {
                    if (value < 1 || value > 9) return false;
                    if (seen[value - 1]) return false;
                    seen[value - 1] = true;
                }
            }
        }
        // Check 3x3 squares lastly:
        for (int startRow = 0; startRow < 9; startRow += 3)
        {
            for (int startCol = 0; startCol < 9; startCol += 3)
            {
                boolean[] seen = new boolean[9];
                for (int row = startRow; row < startRow + 3; row++)
                {
                    for (int col = startCol; col < startCol + 3; col++)
                    {
                        int value = brd[row][col];
                        if (value != 0)
                        {
                            if (value < 1 || value > 9) return false;
                            if (seen[value - 1]) return false;
                            seen[value - 1] = true;
                        }
                    }
                }
            }
        }

        return true;
    }

    // TODO (b): Checking with backtracking ...
    public static int[][] isSolvableBoard(int[][] brd)
    {
        // If board is valid try to solve a copy of it:
        if (!isValidBoard(brd)) return null;

        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                copy[i][j] = brd[i][j];
        if (SolveTheSudoku(copy))
        {
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 9; j++)
                    if (copy[i][j] == 0)
                        return null;
            return copy;
        }

        return null;
    }

    // Instructions:
    // [!] Code taken from 'https://github.com/eugenp/tutorials/blob/master/algorithms-modules/algorithms-miscellaneous-2/src/main/java/com/baeldung/algorithms/sudoku/BacktrackingAlgorithm.java' !!!
    // Solve sudoku by checking if we can place a specific number into a specific place and if so do it recursively ...
    public static boolean SolveTheSudoku(int[][] board)
    {
        for (int row = 0; row < 9; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                if (board[row][col] == 0)
                {
                    for (int num = 1; num <= 9; num++)
                    {
                        boolean placeable = true;

                        for (int c = 0; c < 9; c++)
                        {
                            if (board[row][c] == num)
                            {
                                placeable = false;
                                break;
                            }
                        }
                        if (placeable)
                        {
                            for (int r = 0; r < 9; r++)
                            {
                                if (board[r][col] == num)
                                {
                                    placeable = false;
                                    break;
                                }
                            }
                        }
                        if (placeable)
                        {
                            int startrow = ((row / 3) * 3);
                            int startcol = ((col / 3) * 3);

                            for (int r = startrow; r < startrow + 3; r++)
                            {
                                for (int c = startcol; c < startcol + 3; c++)
                                {
                                    if (board[r][c] == num)
                                    {
                                        placeable = false;
                                        break;
                                    }
                                }
                                if (!placeable) break;
                            }
                        }
                        if (placeable)
                        {
                            board[row][col] = num;
                            if (SolveTheSudoku(board))
                                return true;
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

}

// The End :(
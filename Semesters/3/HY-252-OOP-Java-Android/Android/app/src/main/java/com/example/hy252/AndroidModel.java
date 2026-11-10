/** HY-252 - Java (Android Studio) - Assignment 2.6 - PRI - csd5748 **/
// [!] Please note: this file is exactly the same as "Model.java" from "A26\MVC" ...

package com.example.hy252;

public class AndroidModel
{
    public AndroidModel() {}

    public int[][] getEmptyBoard() { return new int[9][9]; }

    public int[][] solve(int[][] board)
    {
        if (isValidBoard(board))
        {
            int[][] copy = new int[9][9];
            for (int i = 0; i < 9; i++)
                System.arraycopy(board[i], 0, copy[i], 0, 9);

            if (solveBoard(copy)) { return copy; } else { return board; }
        }
        return board;
    }

    public boolean isValidBoard(int[][] board)
    {
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
            {
                int num = board[row][col];
                if (num > 9 || num < 0) return false;
                if (num != 0)
                {
                    board[row][col] = 0;
                    if (!isValid(board, row, col, num))
                    {
                        board[row][col] = num;
                        return false;
                    }
                    board[row][col] = num;
                }
            }
        return true;
    }

    private boolean solveBoard(int[][] board)
    {
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
                if (board[row][col] == 0)
                {
                    for (int num = 1; num <= 9; num++)
                        if (isValid(board, row, col, num))
                        {
                            board[row][col] = num;
                            if (solveBoard(board)) { return true; }
                            board[row][col] = 0;
                        }
                    return false;
                }
        return true;
    }

    private boolean isValid(int[][] board, int row, int col, int num)
    {
        for (int i = 0; i < 9; i++)
            if (board[row][i] == num || board[i][col] == num) return false;

        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int r = startRow; r < startRow + 3; r++)
            for (int c = startCol; c < startCol + 3; c++)
                if (board[r][c] == num)
                    return false;

        return true;
    }
}

// The End ...
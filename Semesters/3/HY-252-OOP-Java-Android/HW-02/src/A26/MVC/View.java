/** HY-252 - Java - Assignment 2.6 - PRI - csd5748 **/

package A26.MVC;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.awt.*;

public class View extends JFrame
{
    private static final JTextField[][] cells = new JTextField[9][9];
    private static JButton resetButton = null, solveButton = null;

    public View()
    {
        // Window settings:
        setTitle("[csd5748] Java (MVC) Sudoku Solver ...");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // All input text boxes:
        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        for (int row = 0; row < 9; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setColumns(1);
                cells[row][col].setFont(new Font("SansSerif", Font.PLAIN, 20));
                gridPanel.add(cells[row][col]);
            }
        }
        add(gridPanel, BorderLayout.CENTER);

        // The two buttons:
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        resetButton = new JButton("Reset");
        solveButton = new JButton("Solve");
        buttonPanel.add(resetButton);
        buttonPanel.add(solveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // All the listeners (to buttons and textboxes):
    public static void AddResetListener(ActionListener listener)
    { resetButton.addActionListener(listener); }

    public static void AddSolveListener(ActionListener listener)
    { solveButton.addActionListener(listener); }

    public static void AddCellKeyListener(KeyListener listener)
    {
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
                cells[row][col].addKeyListener(listener);
    }

    public static void DisplayBoard(int[][] theboard)
    {
        // Overwrite all of the text boxes with the board:
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
            {
                if (theboard[row][col] != 0) cells[row][col].setText(Integer.toString(theboard[row][col]));
                else cells[row][col].setText("");
            }
    }

    public static int[][] ReadInputs()
    {
        // Save the users inputted board (empty=0):
        int[][] userboard = new int[9][9];
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
            {
                String text = cells[row][col].getText();
                int value;
                if (text.isEmpty()) value = 0;
                else value = Integer.parseInt(text);
                userboard[row][col] = value;
            }
        return userboard;
    }

}

// The End ...
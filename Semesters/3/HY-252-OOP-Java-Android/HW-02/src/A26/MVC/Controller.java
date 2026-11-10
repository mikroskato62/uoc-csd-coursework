/** HY-252 - Java - Assignment 2.6 - PRI - csd5748 **/

package A26.MVC;

import java.awt.event.*;

public class Controller
{
    // Constructor:
    public Controller()
    {
        View.AddResetListener(new ResetListener());
        View.AddSolveListener(new SolveListener());
        View.AddCellKeyListener(new InputListener());
    }

    // Text box listener:
    private static class InputListener extends KeyAdapter
    {
        @Override public void keyTyped(KeyEvent e)
        { if (e.getKeyChar() < '1' || e.getKeyChar() > '9') { e.consume(); } }
    }

    // Reset button listener:
    private static class ResetListener implements ActionListener
    {
        @Override public void actionPerformed(ActionEvent e)
        { View.DisplayBoard(Model.GetEmptyBoard()); }
    }

    // Solve button listener:
    private static class SolveListener implements ActionListener
    {
        @Override public void actionPerformed(ActionEvent e)
        { View.DisplayBoard(Model.Solve(View.ReadInputs())); }
    }

}

// The End ...
/** HY-252 - Java (Android Studio) - Assignment 2.6 - PRI - csd5748 **/
// [!] Please note: this file is based on "\A26\MVC\Controller.java" ...

package com.example.hy252;

import android.text.Spanned;
import android.view.KeyEvent;
import android.widget.EditText;
import android.text.InputFilter;
import android.view.inputmethod.EditorInfo;

public class AndroidController
{
    private final SolverActivity view;
    private final AndroidModel model;

    public AndroidController(SolverActivity view, AndroidModel model)
    {
        this.view = view;
        this.model = model;
    }

    public void attachCellListeners(EditText[][] cells)
    {
        InputFilter digitFilter = new InputFilter()
        {
            @Override public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
            {
                for (int i = start; i < end; i++)
                    if (!Character.isDigit(source.charAt(i)) || source.charAt(i) == '0')
                        return "";
                return null;
            }
        };

        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
                cells[row][col].setFilters(new InputFilter[]{digitFilter});
    }


    public void onReset()
    { view.displayBoard(model.getEmptyBoard()); }

    public void onSolve()
    {
        int[][] user = view.readBoard();
        if (!model.isValidBoard(user))
        {
            view.displayBoard(user);
            return;
        }
        int[][] solved = model.solve(user);
        view.displayBoard(solved);
    }
}

// The End ...
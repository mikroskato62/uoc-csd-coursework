/** HY-252 - Java (Android Studio) - Assignment 2.6 - PRI - csd5748 **/
// [!] Please note: this file is based on "\A26\MVC\View.java" ...

package com.example.hy252;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class SolverActivity extends AppCompatActivity
{
    private final EditText[][] cells = new EditText[9][9];
    private AndroidModel model;
    private AndroidController controller;

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_solver);

        model = new AndroidModel();
        controller = new AndroidController(this, model);

        buildGrid();
        hookButtons();
    }

    // The new way of creating the text boxes ...
    private void buildGrid()
    {
        // Get the container that will hold the 9x9 grid:
        LinearLayout container = findViewById(R.id.gridContainer);
        container.removeAllViews();

        // Loop over each 3x3 block row:
        for (int blockRow = 0; blockRow < 3; blockRow++)
        {
            LinearLayout rowOfBlocks = new LinearLayout(this);
            rowOfBlocks.setOrientation(LinearLayout.HORIZONTAL);
            rowOfBlocks.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f));

            // Loop over each 3x3 block column:
            for (int blockCol = 0; blockCol < 3; blockCol++)
            {
                LinearLayout block = new LinearLayout(this);
                block.setOrientation(LinearLayout.VERTICAL);

                // Add padding and background color for the 3x3 block
                int pad = (int) (2 * getResources().getDisplayMetrics().density);
                block.setPadding(pad, pad, pad, pad);
                block.setBackgroundColor(getResources().getColor(R.color.block_border_black));

                LinearLayout.LayoutParams blockParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                blockParams.setMargins(2, 2, 2, 2);
                block.setLayoutParams(blockParams);

                // Loop over rows inside the 3x3 block:
                for (int localRow = 0; localRow < 3; localRow++)
                {
                    LinearLayout row = new LinearLayout(this);
                    row.setOrientation(LinearLayout.HORIZONTAL);
                    row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f));

                    // Loop over columns inside the 3x3 block:
                    for (int localCol = 0; localCol < 3; localCol++)
                    {
                        int r = blockRow * 3 + localRow;
                        int c = blockCol * 3 + localCol;

                        // The (input) text boxes:
                        EditText cell = new EditText(this);
                        cell.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
                        cell.setMaxLines(1);
                        cell.setFilters(new android.text.InputFilter[]{ new android.text.InputFilter.LengthFilter(1) });
                        cell.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        cell.setGravity(android.view.Gravity.CENTER);
                        cell.setTextSize(20);

                        // Thin inner border:
                        int thin = (int) (0.5f * getResources().getDisplayMetrics().density);
                        cell.setPadding(thin, thin, thin, thin);

                        // Layout for each cell:
                        cell.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f));

                        // Save cell in array and add to row:
                        cells[r][c] = cell;
                        row.addView(cell);
                    }
                    // Add row to block:
                    block.addView(row);
                }
                // Add block to row of blocks:
                rowOfBlocks.addView(block);
            }
            // Add row of blocks to container:
            container.addView(rowOfBlocks);
        }

        // Attach listeners to all cells
        controller.attachCellListeners(cells);
    }


    private void hookButtons()
    {
        Button resetButton = findViewById(R.id.btnReset);
        resetButton.setOnClickListener
        (
            new View.OnClickListener()
            {
                @Override public void onClick(View v)
                { controller.onReset(); }
            }
        );

        Button solveButton = findViewById(R.id.btnSolve);
        solveButton.setOnClickListener
        (
            new View.OnClickListener()
            {
                @Override public void onClick(View v)
                { controller.onSolve(); }
            }
        );
    }



    public void displayBoard(int[][] board)
    {
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
            {
                int val = board[row][col];
                cells[row][col].setText(val == 0 ? "" : String.valueOf(val));
            }
    }

    public int[][] readBoard()
    {
        int[][] brd = new int[9][9];
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
            {
                String txt = cells[row][col].getText().toString().trim();
                brd[row][col] = txt.isEmpty() ? 0 : Integer.parseInt(txt);
            }
        return brd;
    }

}

// The End ...
/** HY-252 - Java (Android Studio) - Assignment 2.6 - PRI - csd5748 **/
// [!] Please note: this file is based on "\A26\MVC\App.java" ...

package com.example.hy252;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button solveButton = findViewById(R.id.btnSolve);
        Button exitButton = findViewById(R.id.btnExit);

        solveButton.setOnClickListener
        (
            new android.view.View.OnClickListener()
            {
                @Override public void onClick(android.view.View v)
                {
                    Intent intent = new Intent(MainActivity.this, SolverActivity.class);
                    startActivity(intent);
                }
            }
        );

        exitButton.setOnClickListener
        (
            new android.view.View.OnClickListener()
            {
                @Override public void onClick(android.view.View v)
                {
                    finishAffinity();
                }
            }
        );
    }
}

// The End ...
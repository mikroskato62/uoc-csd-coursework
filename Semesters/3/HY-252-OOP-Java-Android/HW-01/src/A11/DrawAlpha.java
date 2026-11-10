// File 'DrawAlpha.java' ...
/** HY252 - A1 - Exercise 1 - RIP csd5748 **/

package A11;

import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.UIManager;
import java.io.PrintWriter;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.util.Scanner;


public class DrawAlpha
{
    public static void main(String[] args)
    {
        System.out.println("[csd5748] DrawAlpha.java ...");

        // TODO 1: Handle args: M in {c,w,f,g}, L in [4..20] ...
        if (args.length != 2)
        {
            System.out.println("[Error] Required 2 arguments: M (c, w, f, g) and L (4-20) ...");
            return;
        }
        String M = args[0];
        if (!"c".equals(M) && !"w".equals(M) && !"f".equals(M) && !"g".equals(M))
        {
            System.out.println("[Error] M must be one of: c, w, f, g ...");
            return;
        }
        int L;
        try
        {
            L = Integer.parseInt(args[1]);
            if (L < 4 || L > 20)
            {
                System.out.println("[Error] L must be between 4 and 20 ...");
                return;
            }
        }
        catch (NumberFormatException e)
        {
            System.out.println("[Error] L must be an integer ...");
            return;
        }
        System.out.println("[Ok] Mode = " + M + ", Length = " + L + " ...");

        // TODO 2: switch(M) --> call the appropriate method ...
        switch (M)
        {
            case "c": drawConsole(L); break;
            case "w": drawWindow(L); break;
            case "f": writeHtmlFile(L); break;
            case "g": drawGraphics(L); break;
        }

        // TODO 4: Recursively call main with L-1 ...
        if (L > 4)
        {
            String[] newArgs = { M, Integer.toString(L - 1) };
            main(newArgs);
        }

        // TODO 3: Ask for new L until out of range ...
        boolean correct = true;
        while (correct)
        {
            int newL = 0;
            if (!M.equals("w"))
            {
                // Console input:
                Scanner in = new Scanner(System.in);
                System.out.print("[Input] Please enter a new argument L between 4 and 20 or out of this range to exit: ");
                try
                {
                    newL = in.nextInt();
                }
                catch (Exception e)
                {
                    System.out.println("[Error] Invalid user input ...");
                    break;
                }
            }
            else
            {
                // Dialog input:
                try
                {
                    String input = JOptionPane.showInputDialog("Please enter new L ...", L);
                    if (input == null) break;
                    newL = Integer.parseInt(input);
                }
                catch (Exception e)
                {
                    System.out.println("[Error] Something went wrong ... ");
                    break;
                }
            }
            // Next Part:
            if (newL < 4 || newL > 20)
            {
                System.out.println("[Info] Out of range argument L entered - terminating ...");
                correct = false;
            }
            else
            {
                switch (M)
                {
                    case "c": drawConsole(newL); break;
                    case "w": drawWindow(newL); break;
                    case "f": writeHtmlFile(newL); break;
                    case "g": drawGraphics(newL); break;
                }
            }
        }

        System.out.println("[Ok] The End ?");
    }


    // TODO (ii): Drawing in console ...
    static void drawConsole(int L)
    {
        System.out.println("[Info] Drawing Letter A In Console ...");

        // From top to bottom:
        int middle = L / 2;
        for (int row = 0; row < L; row++)
        {
            // Left side:
            for (int i = 0; i < L - row; i++)
            {
                System.out.print(" ");
            }
            System.out.print("*");

            // Middle part:
            for (int i = 0; i < 2 * row - 1; i++)
            {
                if (row == middle)
                {
                    System.out.print("*");
                }
                else
                {
                    System.out.print(" ");
                }
            }
            // Right side:
            if (row > 0)
            {
                System.out.print("*");
            }
            System.out.println();
        }

        return;
    }

    // TODO (iii): Drawing in dialog window ...
    static void drawWindow(int L)
    {
        System.out.println("[Info] Drawing Letter A In Dialog Window ...");

        // Instructions:
        UIManager.put("OptionPane.messageFont", new Font("Lucida Console", Font.BOLD, 14));

        // Using same logic and 'stringbuilder' and not 'stringbuffer' since we are not multi threaded:
        int middle = L / 2;
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < L; row++)
        {
            for (int i = 0; i < L - row; i++) sb.append(" ");
            sb.append("*");
            for (int i = 0; i < 2 * row - 1; i++)
            {
                if (row == middle) sb.append("*");
                else sb.append(" ");
            }
            if (row > 0) sb.append("*");
            sb.append("\n");
        }

        // Instructions:
        JOptionPane.showMessageDialog( null, sb.toString(), "[csd5748] Letter 'A' In Dialog Window :)", JOptionPane.INFORMATION_MESSAGE );

        return;
    }


    // TODO (iv): Write to HTML file: ...
    static void writeHtmlFile(int L)
    {
        System.out.println("[Info] Writing Letter A To HTML File ...");

        // Instructions:
        int fontsize = L;
        PrintWriter writer;
        try
        {
            writer = new PrintWriter("A.html", "UTF-8");
            writer.println("<!DOCTYPE html>");
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\"/>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<font size=\"" + fontsize + "\">A with font size = " + fontsize + "</font>");
            writer.println("</body>");
            writer.println("</html>");
            writer.close();
            System.out.println("[Ok] File A.html created successfully ...");
        }
        catch (Exception e)
        {
            System.out.println("[Error] Problem creating the HTML file ...");
        }

        return;
    }

    // TODO (v): Drawing in graphics window ...
    static void drawGraphics(int L)
    {
        System.out.println("[Info] Drawing Letter A In Graphics Window ...");

        // Instructions:
        Frame f = new Frame("[csd5748]  'A'  (ctr+c to exit)  :)")
        {
            public void paint(Graphics g)
            {
                Graphics2D g2 = (Graphics2D) g;

                // Calculations:
                double height = (L * 10);
                double width = (height / 2);
                // Centered starting points:
                double x0 = ((400 - width) / 2), y0 = (400 - (400 - height) / 2);
                // Left, right and middle lines:
                g2.draw(new Line2D.Double(x0, y0, x0 + width / 2, y0 - height));
                g2.draw(new Line2D.Double(x0 + width, y0, x0 + width / 2, y0 - height));
                g2.draw(new Line2D.Double(x0 + width / 4, y0 - height / 2, x0 + 3 * width / 4, y0 - height / 2));
            }
        };
        // Instructions:
        f.setSize(400, 400);
        f.setVisible(true);

        return;
    }

}

// [!] Info: 'Instructions' means the written code was copied from the provided instruction file !
// The End :(
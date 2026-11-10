// File 'MagicSquareIO.java' ...
/** HY252 - A1 - Exercise 3 - RIP csd5748 **/

package A13;
import javax.swing.JFileChooser;
import java.io.*;

public class MagicSquareIO
{
    public static void main(String[] args)
    {
        System.out.println("[csd5748] MagicSquareIO.java (#Downloader.java) ...");

        try
        {
            // TODO (a): Ask the user to select a file (.txt) ...
            // Instructions:
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("* Please select a text file.");
            int selected = fileChooser.showOpenDialog(null);
            if (selected != JFileChooser.APPROVE_OPTION)
            {
                System.out.println("[Error] No file selected ...");
                return;
            }
            File file = fileChooser.getSelectedFile();
            String filepath = file.getAbsolutePath();
            System.out.println("[Info] The path of the selected file is: " + filepath + ".");

            // TODO (b): Read numbers separated by commas into int[][] and check format.
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            int rows = 0;
            int cols = -1;
            boolean format = false;

            // Firstly, count rows and columns and check format consistency:
            while ((line = reader.readLine()) != null)
            {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (cols == -1)
                {
                    cols = parts.length;
                }
                else if (parts.length != cols)
                {
                    format = true;
                    break;
                }
                rows++;
            }
            reader.close();

            if (format || rows != cols)
            {
                System.out.println("[Error] The file format is not correct ...");
                return;
            }

            // Secondly, read all the numbers and fill the int[][] array:
            int[][] s = new int[rows][cols];
            reader = new BufferedReader(new FileReader(filepath));
            int i = 0;
            while ((line = reader.readLine()) != null)
            {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                for (int j = 0; j < cols; j++)
                {
                    try
                    {
                        s[i][j] = Integer.parseInt(parts[j].trim());
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("[Error] Invalid number in file ...");
                        reader.close();
                        return;
                    }
                }
                i++;
            }
            reader.close();

            // Print formatted square (same as exercise 2) ...
            printSquare(s);

            // TODO (c): Check if magic and optionally write report ...
            if (checkIsMagic(s))
            {
                int magicnumber = getMagicNumber(s);
                System.out.println("[Result] The square is magic and the magic element is: " + magicnumber + ".");

                // Ask user for folder to save result:
                // Instructions:
                JFileChooser folderChooser = new JFileChooser();
                folderChooser.setDialogTitle("* Please select a folder to save the report file.");
                folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int folderSelection = folderChooser.showSaveDialog(null);
                if (folderSelection == JFileChooser.APPROVE_OPTION)
                {
                    File folder = folderChooser.getSelectedFile();
                    File outputFile = new File(folder, "MagicSquareSavedFile.txt");
                    BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
                    BufferedReader reReader = new BufferedReader(new FileReader(file));

                    String lines;
                    while ((lines = reReader.readLine()) != null)
                    {
                        writer.write(lines);
                        writer.newLine();
                    }
                    reReader.close();

                    writer.newLine();
                    writer.write("Μαγικό τετράγωνο με μαγικό αριθμό " + magicnumber + ".");
                    writer.newLine();
                    writer.close();
                    System.out.println("[Info] Result written to: " + outputFile.getAbsolutePath() + ".");
                }
                else
                {
                    System.out.println("[Error] No directory selected ...");
                    return;
                }
            }
            else
            {
                System.out.println("[Result] The square isn't magic !");
            }
        }
        catch (Exception e)
        {
            System.out.println("[Error] " + e.getMessage() + " ...");
        }

        System.out.println("[SOS] For URLs please run the 'Downloader.java' ...");
        System.out.println("[Ok] The End ...");
    }

    // [!] Helper methods and functions (copied from exercise 2) ...

    private static void printSquare(int[][] s)
    {
        System.out.println("[Info] The file's content:");
        int n = s.length;
        String line = "-".repeat(n * 6 + 1);
        for (int i = 0; i < n; i++)
        {
            System.out.println(line);
            for (int j = 0; j < n; j++)
            {
                String text = String.valueOf(s[i][j]);
                int width = 5;
                int spaces = width - text.length();
                int left = spaces / 2;
                int right = spaces - left;
                System.out.print("|" + " ".repeat(left) + text + " ".repeat(right));
            }
            System.out.println("|");
        }
        System.out.println(line);
    }

    public static boolean checkIsMagic(int[][] s)
    {
        int n = s.length, sum = sumOfRow(s, 0);
        for (int i = 0; i < n; i++)
            if (sumOfRow(s, i) != sum)
                return false;
        for (int j = 0; j < n; j++)
            if (sumOfColumn(s, j) != sum)
                return false;
        if (sumOfDiagonal1(s) != sum || sumOfDiagonal2(s) != sum)
            return false;
        return true;
    }

    private static int sumOfRow(int[][] s, int k)
    {
        int sum = 0;
        for (int j = 0; j < s.length; j++) sum += s[k][j];
        return sum;
    }

    private static int sumOfColumn(int[][] s, int k)
    {
        int sum = 0;
        for (int i = 0; i < s.length; i++) sum += s[i][k];
        return sum;
    }

    private static int sumOfDiagonal1(int[][] s)
    {
        int sum = 0;
        for (int i = 0; i < s.length; i++) sum += s[i][i];
        return sum;
    }

    private static int sumOfDiagonal2(int[][] s)
    {
        int sum = 0, n = s.length;
        for (int i = 0; i < n; i++) sum += s[i][n - 1 - i];
        return sum;
    }

    public static int getMagicNumber(int[][] s)
    {
        if (checkIsMagic(s)) return sumOfRow(s, 0);
        else return -1;
    }
}

// The End :(
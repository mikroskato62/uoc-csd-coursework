// File 'Encrypt.java' ...
/* HY252 - A1 - Exercise 5 - RIP csd5748 */

package A15;
import java.io.*;
import javax.swing.JFileChooser;

public class Encrypt
{
    public static void main(String[] args)
    {
        System.out.println("[csd5748] Encrypt.java ...");

        // TODO (a1): If <inputFile> <K> are given --> Transform(file, "Encrypted...", K) ...
        // TODO (a2): Otherwise (no args) --> activate graphical file chooser flow ...
        int K = 0;

        try
        {
            if (args.length == 2)
            {
                String inputfilename = args[0];
                K = Integer.parseInt(args[1]);
                String outputfilename = "Encrypted_" + new File(inputfilename).getName();

                // Instructions:
                long startTime = System.nanoTime();
                Transform(inputfilename, outputfilename, K);
                long endTime = System.nanoTime();
                long timeInNanosecs = endTime - startTime;
                System.out.println("[Info] Selected K = " + K + ".");
                System.out.println("[Info] Encryption completed in " + timeInNanosecs + " nanoseconds.");
                System.out.println("[Info] The new encrypted file was created.");

            }
            else if (args.length == 0)
            {
                // GUI - instructions:
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Please select a file to encrypt/decrypt.");
                int selected = fileChooser.showOpenDialog(null);

                if (selected == JFileChooser.APPROVE_OPTION)
                {
                    File file = fileChooser.getSelectedFile();
                    String filepath = file.getAbsolutePath();
                    System.out.println("[Info] The path of the selected file is: " + filepath + " ");

                    // [!] Ask user for K with GUI !?
                    String k = javax.swing.JOptionPane.showInputDialog("Please enter an integer K:");
                    K = Integer.parseInt(k);

                    File outputfile = new File(file.getParent(), "Encrypted_" + file.getName());

                    // Instructions:
                    long startTime = System.nanoTime();
                    Transform(file, outputfile, K);
                    long endTime = System.nanoTime();
                    long timeInNanosecs = endTime - startTime;
                    System.out.println("[Info] Selected K = " + K + ".");
                    System.out.println("[Info] Encryption completed in " + timeInNanosecs + " nanoseconds.");
                    System.out.println("[Info] The new encrypted file was created.");
                }
                else
                {
                    System.out.println("[Info] No file selected so the program is terminated ...");
                    return;
                }
            }
            else
            {
                System.out.println("[Error] You have entered an incorrect number of arguments ...");
                return;
            }

            // TODO (b): Call test() at the end and print result ...
            boolean ok = test(K);
            if (ok) System.out.println("[Info] The test was passed successfully.");
            else System.out.println("[Info] The test failed ...");

        }
        catch (Exception e)
        {
            System.out.println("[Error] No integer K was entered (null) ...");
            return;
        }

        System.out.println("[!] The End :)");
    }

    // TODO (a3) Signature of the required method ...
    static void Transform(String inputFileName, String outputFileName, int inputK)
    {
        // Creating file objects and calling the overloaded method:
        File inputfile = new File(inputFileName);
        File outputfile = new File(outputFileName);
        Transform(inputfile, outputfile, inputK);
    }

    // TODO (c) Overload with file objects ...
    static void Transform(java.io.File inputFile, java.io.File outputFile, int inputK)
    {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        // Read and write files:
        try
        {
            fis = new FileInputStream(inputFile);
            fos = new FileOutputStream(outputFile);
            int bytes;
            while ((bytes = fis.read()) != -1)
                fos.write((bytes + inputK) & 0xFF);
        }
        catch (IOException e)
        {
            System.out.println("[Error] I/O: " + e.getMessage() + " ...");
        }
        finally
        {
            // Close the file:
            try
            {
                if (fis != null) fis.close();
                if (fos != null) fos.close();
            }
            catch (IOException e)
            {
                System.out.println("[Error] While closing files: " + e.getMessage() + " ...");
            }
        }
    }

    // TODO (b): Correctness checks ...
    static boolean test(int K)
    {
        try
        {
            // Create test files:
            String original = "Test_Original.txt";
            String encrypted = "Encrypted_" + original;
            String decrypted = "Decrypted_" + original;

            FileWriter writer = new FileWriter(original);
            writer.write("[csd5748] Hello, world!");
            writer.close();

            Transform(original, encrypted, K);
            Transform(encrypted, decrypted, -K);

            // Compare files byte by byte:
            FileInputStream f1 = new FileInputStream(original);
            FileInputStream f2 = new FileInputStream(decrypted);

            int b1, b2;
            boolean same = true;
            while ((b1 = f1.read()) != -1 && (b2 = f2.read()) != -1)
            {
                if (b1 != b2)
                {
                    same = false;
                    break;
                }
            }
            f1.close();
            f2.close();

            return same;
        }
        catch (IOException e)
        {
            System.out.println("[Error] Test(): " + e.getMessage() + " ...");
            return false;
        }
    }

}
// The End :( 
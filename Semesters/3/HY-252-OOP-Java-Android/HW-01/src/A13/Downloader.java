// File 'Downloader.java' ...
/** HY252 - A1 - Exercise 3 - RIP csd5748 **/

package A13;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.JOptionPane;

public class Downloader
{
    static int download(String address)
    {
        // TODO (d2): Download content from URL and print it ...
        try
        {
            // Instructions:
            URL url = new URL(address);
            BufferedReader in = new BufferedReader( new InputStreamReader(url.openStream(), "UTF-8") );

            System.out.println("----------");
            int c = in.read();
            while (c != -1)
            {
                System.out.print((char) c);
                c = in.read();
            }
            in.close();
            if (address.equals("http://users.ics.forth.gr/~tzitzik/courses/hy252/2025-a13/test1")) System.out.println("----------");
            else System.out.println("\n----------");
        }
        catch (Exception e)
        {
            System.out.println("[Error] " + e + " ...");
            return -1;
        }
        return 0;
    }

    // Main:
    public static void main(String[] args)
    {
        System.out.println("[csd5748] Downloader.java (#MagicSquareIO.java) ...");

        // TODO (d1): Read the users URL and call download() ...
        String downloading = JOptionPane.showInputDialog("* Please enter a URL (or add 1, 2 or 3 at the end):                ",
                                                         "http://users.ics.forth.gr/~tzitzik/courses/hy252/2025-a13/test");
        System.out.println("[Info] URL: " + downloading + ".");
        download(downloading);

        System.out.println("[SOS] For (text) files please run the 'MagicSquareIO.java' ...");
        System.out.println("[Ok] The End ...");
    }

}
// The End :(
// [!] UOC - CSD - HY252 - Java - Project Phase A & B - Amphipolis Board Game - RIP - csd5748 - :)
// [!] Note: the game can be started by running either this main method or the .jar executable ...
// [!] Please read the ReadMe.md file !

package PhaseB;

import PhaseB.MVC.*;

public class Main
{
    public static void main(String... args)
    {
        Model model = new Model();
        View view  = new View();
        Controller controller = new Controller(model, view);
    }
}


// [!] The End :(
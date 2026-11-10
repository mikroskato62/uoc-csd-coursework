/* UOC - CSD - HY252 - Project Phase 1 - Java - MVC: Controller - RIP - csd5748 */

package PhaseA.MVC;

import javax.swing.*;

import javax.sound.sampled.Clip;

public class Controller
{
    static Model model;

    static View view;

    private static Clip currentClip;

    private static Timer turnTimer;

    private int timeLeft;

    private static final int TURN_TIME = -1;

    public Controller () { }

    public void playPlayerMusic () { }

    public void startTurnTimer () { }

    public void AddMainMenuActionListeners () { }

    public void AddGameStartActionListeners () { }

    public void AddActionListenersForBoardButtons () { }

    public void AddActionListenersForCardsButtons () { }

    public void AddGameOverScreenActionListeners (JButton button) { }
}

/* The End */
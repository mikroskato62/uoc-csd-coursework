// [!] UOC - CSD - HY252 - Java (MVC) - Project Phase A & B - Amphipolis Board Game - RIP - csd5748 - :)
// [!] Please read the ReadMe.md file !

package PhaseB.MVC;

import javax.swing.*;
import java.util.List;
import java.awt.Color;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.sound.sampled.Clip;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.AudioInputStream;
import java.util.concurrent.ThreadLocalRandom;

public class Controller
{
    private final Model model;
    private final View view;
    private final Controller controller;

    private static Clip currentClip = null;

    private static javax.swing.Timer turnTimer;
    private int timeLeft = 30;
    private static final int MAX_TURN_TIME = 30;

    public Controller (Model model, View view)
    {
        this.view = view;
        this.model = model;
        this.controller = this;
        view.MainMenu();
        AddMainMenuActionListeners();
    }

    private static Color randomColor()
    {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        float hue; do { hue = r.nextFloat(); } while ( hue < 0.08f || hue > 0.92f || (hue > 0.22f && hue < 0.45f) );
        float saturation = 0.6f + r.nextFloat() * 0.4f; float brightness = 0.4f + r.nextFloat() * 0.6f;
        return Color.getHSBColor(hue, saturation, brightness);
    }

    public void startTurnTimer (String cmd, int time)
    {
        if (turnTimer != null && turnTimer.isRunning()) { turnTimer.stop(); }
        if (cmd.equals("Start")) { this.timeLeft = MAX_TURN_TIME; }
        else if (cmd.equals("Resume")) { this.timeLeft = time; }
        else { this.timeLeft = 10 * MAX_TURN_TIME; }
        this.view.getLabels(1).ChangeText((this.model.getPlayer().getName().endsWith(" 0") ? "Your Turn" : (this.model.getPlayer().getName())) + "  (Time: " + (timeLeft < 10 ? " " : "") + timeLeft + ")");
        turnTimer = new javax.swing.Timer(1000, e ->
        {
            timeLeft--;
            this.view.getLabels(1).ChangeText((this.model.getPlayer().getName().endsWith(" 0") ? "Your Turn" : (this.model.getPlayer().getName())) + "  (Time: " + (timeLeft < 10 ? " " : "") + timeLeft + ")");
            if (timeLeft <= 0) { turnTimer.stop(); for (ActionListener al : this.view.getButton("End Turn").getActionListeners()) al.actionPerformed(new ActionEvent(this.view.getButton("End Turn"), ActionEvent.ACTION_PERFORMED, "EndTurn")); }
        });
        turnTimer.start();
    }

    public void playPlayerMusic()
    {
        if (currentClip != null && currentClip.isRunning()) { currentClip.stop(); currentClip.close(); }
        try
        {
            String path = this.model.getGameMode().equals("Singleplayer") ? ("assets/msc/Player" + (new Random().nextInt(4) + 1) + ".wav") : ("assets/msc/Player" + (this.model.getPlayer().getId()) + ".wav");
            java.net.URL soundUrl = getClass().getResource("/" + path);

            if (soundUrl != null)
            {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundUrl);
                currentClip = AudioSystem.getClip();
                currentClip.open(audioIn);
                FloatControl volume = (FloatControl) currentClip.getControl(FloatControl.Type.MASTER_GAIN); volume.setValue(-37.25f);
                currentClip.loop(Clip.LOOP_CONTINUOUSLY); currentClip.setFramePosition(0);
                currentClip.start();
            }
        }
        catch (Exception ignored) { }
    }

    public void AddMainMenuActionListeners()
    {
        if (currentClip != null && currentClip.isRunning()) { currentClip.stop(); currentClip.close(); }
        for (int i = 0; i < 3; i++)
        {
            this.RemoveAllActionListeners(this.view.getButton(i));
            this.view.getButton(i).setEnabled(true);
            this.view.getButton(i).setContentAreaFilled(false);
            this.view.getButton(i).setBorder(new LineBorder(randomColor(), 3, true));
        }
        AddHelpButtonActionListener();

        this.view.getButton("Multiplayer").addActionListener(e ->
        {
             this.model.ResetModel(); this.view.ResetView();
             this.model.setGameMode("Multiplayer");
             this.model.SelectPlayerForRound("RandomPlayer");
             this.ImplementGameStartActions();
        });

        this.view.getButton("Singleplayer").addActionListener(e ->
        {
            this.model.ResetModel(); this.view.ResetView();
            this.model.setGameMode("Singleplayer");
            this.model.SelectPlayerForRound("OnlyPlayer");
            this.model.getBag().editBagForSingleplayer();
            this.model.getBoardTile(0).setCount(8); this.model.getPlayer().getPlayertiles().get(0).setCount(8);
            this.ImplementGameStartActions();
        });

        Object[] data = this.model.LoadGameStateAndStart();
        if (data == null)
        {
            this.view.getButton("Load & Continue").setEnabled(false);
            this.view.getButton("Load & Continue").setBorder(new LineBorder(Color.RED, 3, true));
        }
        else
        {
            this.view.getButton("Load & Continue").setBorder(new LineBorder(Color.GREEN, 3, true));
            this.view.getButtons(2).ChangeText(this.view.getButton("Load & Continue").getText() + " *");
            if ((data[3].toString().equals("Multiplayer"))) this.view.getButtons(0).ChangeText(data[3].toString() + " *");
            else this.view.getButtons(1).ChangeText(data[3].toString() + " *");
        }
        this.view.getButton("Load & Continue").addActionListener(e ->
        {
            if (data != null)
            {
                this.model.setGameMode(data[3].toString());
                view.GameWindow((this.model.getGameMode() + " (Loaded)"));
                AddGameStartActionListeners();
                AddBoardButtonsActionListeners();
                AddCardsButtonsActionListeners();
                view.getButton("Draw Tiles").setEnabled((boolean) data[1]); view.getButton("End Turn").setEnabled((boolean) data[2]);
                for (Model.InventoryTiles tile : this.model.getPlayer().getPlayertiles()) view.getInventoryView().get(tile.getIndex() - 22).UpdateInventoryCount(tile.getCount());
                for (View.ButtonTilesView tile : this.view.getBoardView())
                {
                    tile.changeLabelCount(model.getBoardTile(tile.getIndex() - 6).getCount());
                    if (!tile.getName().equals("landslide")) tile.getButton().setEnabled((model.getBoardTile(tile.getIndex() - 6).getCount() > 0) && (!(boolean) data[1]));
                }
                if (!(boolean) data[1] && this.model.getPlayer().getCurrentArea() != null)
                    for (Model.CardTiles card : this.model.getPlayer().getPlayercards())
                        this.view.getCardsView().get(card.getIndex() - 1).getButton().setEnabled(card.isCardPlayable(this.model.getPlayer().getLastArea()));
                updateCardLabels();
                showBagContent();
                controller.startTurnTimer("Resume", (int) data[0] + 1);
                controller.playPlayerMusic();
            }
        });
    }

    private void ImplementGameStartActions()
    {
        List<String> temptiles = this.model.DrawTilesFromBag("RandomDraw");
        List<Integer> tempindexes = null; if (temptiles != null) tempindexes = this.view.GetLabelIndexFromName(temptiles);
        if (tempindexes != null) for (int i : tempindexes) this.model.getBoardTile(i - 6).increaseCount();
        this.view.GameWindow(this.model.getGameMode());
        for (View.ButtonTilesView tile : this.view.getBoardView())
        {
            tile.changeLabelCount(this.model.getBoardTile(tile.getIndex() - 6).getCount());
            tile.getButton().setEnabled(this.model.getBoardTile(tile.getIndex() - 6).getCount() > 0);
        }
        for (Model.InventoryTiles tile : this.model.getPlayer().getPlayertiles()) this.view.getInventoryView().get(tile.getIndex() - 22).UpdateInventoryCount(tile.getCount());
        AddGameStartActionListeners();
        AddBoardButtonsActionListeners();
        AddCardsButtonsActionListeners();
        for (View.ButtonTilesView button : this.view.getBoardView()) if (!button.getName().equals("landslide")) button.getButton().setEnabled(false);
        updateCardLabels();
        showBagContent();
        controller.startTurnTimer("Start", -1);
        controller.playPlayerMusic();
    }

    public void AddGameStartActionListeners()
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter("cookies.txt"))) { writer.print(""); } catch (IOException ignored) {}
        Model temp1 = this.model; View temp2 = this.view;
        for (int i = 0; i < 3; i++)
            if (this.view.getButton(i) != null)
                for (ActionListener al : this.view.getButton(i).getActionListeners())
                    this.view.getButton(i).removeActionListener(al);
        AddHelpButtonActionListener();
        this.view.getBoardView().get(0).getButton().setEnabled(false); this.view.getBoardView().get(0).getButton().setDisabledIcon(this.view.getBoardView().get(0).getButton().getIcon());

        this.view.getButton("Draw Tiles").setEnabled(true);
        this.view.getButton("Draw Tiles").addActionListener(new ActionListener()
        {
            private final Model model = temp1; private final View view = temp2;
            @Override public void actionPerformed(ActionEvent e)
            {
                this.view.getButton("Draw Tiles").setEnabled(false); this.view.getButton("End Turn").setEnabled(true);
                List<String> temp3 = this.model.getGameMode().equals("Multiplayer") ? this.model.DrawTilesFromBag("PlayerDraw") : this.model.DrawTilesFromBag("ThieveDraw");
                List<Integer> temp4 = null; if (temp3 != null) temp4 = this.view.GetLabelIndexFromName(temp3);
                if (temp4 != null) for (int i : temp4) this.model.getBoardTile(i - 6).increaseCount();
                for (View.ButtonTilesView tile : this.view.getBoardView())
                {
                    tile.changeLabelCount(this.model.getBoardTile(tile.getIndex() - 6).getCount());
                    if (!tile.getName().equals("landslide")) tile.getButton().setEnabled(this.model.getBoardTile(tile.getIndex() - 6).getCount() > 0);
                }
                for (Model.InventoryTiles tile : this.model.getPlayer().getPlayertiles()) this.view.getInventoryView().get(tile.getIndex() - 22).UpdateInventoryCount(tile.getCount());
                if (temp3 != null && this.model.getGameMode().equals("Singleplayer"))
                {
                    if (temp3.get(temp3.size() - 1).equals("landslide"))
                    {
                        this.model.SelectPlayerForRound("ThievePlayer");
                        this.model.PlayThiefTurn();
                        for (View.ButtonTilesView tile : this.view.getBoardView())
                        {
                            tile.changeLabelCount(this.model.getBoardTile(tile.getIndex() - 6).getCount());
                            if (!tile.getName().equals("landslide")) tile.getButton().setEnabled(false);
                        }
                        this.view.getLabels(1).ChangeText((this.model.getPlayer().getName().endsWith(" 0") ? "Your Turn" : (this.model.getPlayer().getName())) + "  (Time: " + 30 + ")");
                        this.view.getButton("Draw Tiles").setEnabled(true); this.view.getButton("End Turn").setEnabled(false);
                        controller.startTurnTimer("Start", -1);
                    }
                }
                showBagContent();
                CheckForGameOver();
            }
        });

        this.view.getButton("End Turn").setEnabled(false);
        this.view.getButton("End Turn").addActionListener(new ActionListener()
        {
            private final Model model = temp1; private final View view = temp2;
            @Override public void actionPerformed(ActionEvent e)
            {
                this.view.getButton("End Turn").setEnabled(false); this.view.getButton("Draw Tiles").setEnabled(true);
                this.model.getPlayer().DisableAllCards();
                if (this.model.getGameMode().equals("Multiplayer")) this.model.SelectPlayerForRound("NextPlayer"); else this.model.SelectPlayerForRound("ThievePlayer");
                this.view.getLabels(1).ChangeText((this.model.getPlayer().getName().endsWith(" 0") ? "Your Turn" : (this.model.getPlayer().getName())) + "  (Time: ?)");
                for (Model.InventoryTiles tile : this.model.getPlayer().getPlayertiles()) this.view.getInventoryView().get(tile.getIndex() - 22).UpdateInventoryCount(tile.getCount());
                for (View.ButtonTilesView button : this.view.getBoardView()) { if (!button.getName().equals("landslide")) button.getButton().setEnabled(false); }
                for (View.ButtonTilesView cardView : this.view.getCardsView()) { cardView.getButton().setEnabled(false); }
                AddCardsButtonsActionListeners();
                updateCardLabels();
                showBagContent();
                controller.startTurnTimer("Start", -1);
                controller.playPlayerMusic();
            }
        });

        this.view.getButton("Save & Exit").setEnabled(true);
        this.view.getButton("Save & Exit").addActionListener(e ->
        {
            this.model.SaveGameStateAndExit(this.timeLeft, this.view.getButton("Draw Tiles").isEnabled(), this.view.getButton("End Turn").isEnabled(), this.view.GetAllCountsFromTheBoard());
            model.ResetModel(); view.ResetView();
            view.MainMenu();
            AddMainMenuActionListeners();
        });
    }

    public void AddBoardButtonsActionListeners()
    {
        for (View.ButtonTilesView button : this.view.getBoardView())
            if (!button.getName().equals("landslide"))
            {
                this.RemoveAllActionListeners(button.getButton());
                button.getButton().addActionListener(e ->
                {
                    this.model.getPlayer().GetOneTileFromTheBoard(button.getIndex());
                    for (View.ButtonTilesView tile : this.view.getBoardView()) tile.changeLabelCount(this.model.getBoardTile(tile.getIndex() - 6).getCount());
                    for (Model.InventoryTiles tile : this.model.getPlayer().getPlayertiles()) this.view.getInventoryView().get(tile.getIndex() - 22).UpdateInventoryCount(tile.getCount());
                    for (Model.CardTiles card : this.model.getPlayer().getPlayercards()) this.view.getCardsView().get(card.getIndex() - 1).getButton().setEnabled(card.isCardPlayable(this.model.getPlayer().getLastArea()));
                    button.getButton().setEnabled(button.getCount() != 0);
                    updateCardLabels();
                    showBagContent();
                });
                button.getButton().setEnabled(button.getCount() != 0);
            }
    }

    public void AddCardsButtonsActionListeners()
    {
        View temp = this.view;
        for (Model.CardTiles card : this.model.getPlayer().getPlayercards())
        {
            this.RemoveAllActionListeners(this.view.getCardsView().get(card.getIndex() - 1).getButton());
            this.view.getCardsView().get(card.getIndex() - 1).getButton().setEnabled(false);
            if (!card.isAvailable()) { continue; }
            this.view.getCardsView().get(card.getIndex() - 1).getButton().addActionListener(e ->
            {
                card.makeAvailable(false); card.setActive(true);
                this.view.getCardsView().get(card.getIndex() - 1).getButton().setEnabled(false);
                updateCardLabels();
            });
            this.view.getCardsView().get(card.getIndex() - 1).getButton().addMouseListener(new java.awt.event.MouseAdapter()
            {
                private final View view = temp;
                final String temptext = "<br>" + "[?] Note: if you need any help, please left-click the question-mark on the bottom-right ..." + "<br>" +
                                        "[!!] Warning: if this window closes while not in the main-menu, all your progress will be lost ..." + "</html>";
                @Override public void mouseEntered(java.awt.event.MouseEvent e)
                { this.view.getLabels(2).ChangeText("<html>" + "[+] " + this.view.getCardsView().get(card.getIndex() - 1).getEffect() + temptext); }
                @Override public void mouseExited(java.awt.event.MouseEvent e)
                { this.view.getLabels(2).ChangeText("<html>" + "[+] " + "Tip: by hovering over a card you can see its effect here ..." + temptext); }
            });
        }
    }

    public void AddGameOverScreenActionListeners()
    {
        this.view.getButton("Main Menu ...").addActionListener(e ->
        { model.ResetModel(); view.ResetView(); view.MainMenu(); AddMainMenuActionListeners(); });
    }

    public void AddHelpButtonActionListener()
    {
        this.view.getButton("?").addActionListener(e ->
        {
            if (currentClip != null && currentClip.isRunning()) { currentClip.stop(); currentClip.close(); }
            if (this.view.getButton("Save & Exit") != null) this.view.getButton("Save & Exit").doClick();
            this.view.HelpScreen(); AddGoBackButtonActionListener();
        });
    }

    public void AddGoBackButtonActionListener()
    {
        this.view.getButton("Go Back ...").addActionListener(e ->
        { this.view.MainMenu(); AddMainMenuActionListeners(); });
    }

    private void CheckForGameOver()
    {
        if (this.view.getBoardView().get(0).getCount() >= 16)
        {
            if (turnTimer != null && turnTimer.isRunning()) turnTimer.stop();
            if (currentClip != null && currentClip.isRunning()) { currentClip.stop(); currentClip.close(); }
            this.view.GameOver(this.model.CalculatePlayerPointsAtGameOver());
            AddGameOverScreenActionListeners();
        }
    }

    private void updateCardLabels()
    {
        for (Model.CardTiles card : this.model.getPlayer().getPlayercards())
        {
            boolean limitReached = false;
            switch (card.getIndex())
            {
                case 1: if (this.model.getPlayer().getRoundTilesCollected()[1] >= 2) limitReached = true; break;
                case 2: if (this.model.getPlayer().getRoundTilesCollected()[2] >= 1) limitReached = true; break;
                case 3: if (this.model.getPlayer().getRoundTilesCollected()[3] >= 2) limitReached = true; break;
                case 4: if (this.model.getPlayer().getRoundTilesCollected()[4] >= 2) limitReached = true; break;
                case 5: if (this.model.getPlayer().getRoundTilesCollected()[5] == 1 &&
                        this.model.getPlayer().getRoundTilesCollected()[6] == 1 &&
                        this.model.getPlayer().getRoundTilesCollected()[7] == 1) limitReached = true; break;
            }
            if (!card.isAvailable())
            {
                if (limitReached || !card.getActive()) this.view.getCardsView().get(card.getIndex() - 1).ChangeText("  ( Used )");
                else this.view.getCardsView().get(card.getIndex() - 1).ChangeText(" ( Active )");
            }
            else this.view.getCardsView().get(card.getIndex() - 1).ChangeText("( Available )");
        }
    }

    private void showBagContent()
    {
        StringBuilder sb = new StringBuilder(
                "<html>--------<br/>CALA<br/>--------<br/>" +
                (this.model.getPlayer().getCurrentArea() == null ? "None" : (String.format("&nbsp;&nbsp;&nbsp;%s", this.model.getPlayer().getCurrentArea()))) +
                "<br/>" + (this.model.getPlayer().getLastArea() == null ? "None" : (String.format("&nbsp;&nbsp;&nbsp;%s", this.model.getPlayer().getLastArea()))) +
                "<br/>--------<br/>TBAG<br/>--------<br/>"
        );
        for (Model.ButtonTiles type : this.model.getBoardTiles())
        {
            int tempcount = 0;
            for (String tileInBag : this.model.getBag().getTiles()) if (tileInBag.equals(type.getName())) tempcount++;
            sb.append(String.format("%02d/%02d<br/>", tempcount, type.getTotal()));
        }
        sb.append("--------</html>");
        if (this.view.getLabels(3).getLabel() != null) this.view.getLabels(3).ChangeText(sb.toString());
    }

    private void RemoveAllActionListeners(JButton button) { if (button == null) return; for (ActionListener al : button.getActionListeners()) button.removeActionListener(al); }
}

// [!] The End ...
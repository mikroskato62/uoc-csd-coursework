// [!] UOC - CSD - HY252 - Java (JUnit Tests) - Project Phase A & B - Amphipolis Board Game - RIP - csd5748 - :)
// [!] Note: "JUnit4" is added to Classpath as a Module & Library "junit:junit:4.13.1" downloaded from Maven Repository ...
// [!] Please read the ReadMe.md file !

package PhaseB.JUnitTests;

import PhaseB.MVC.*;
import javax.swing.*;
import java.util.List;
import org.junit.Test;
import org.junit.Before;
import java.lang.reflect.Field;
import static org.junit.Assert.*;

public class GameTests
{

    private Model model;
    private View view;
    // private Controller controller;

    @Before
    public void SetUp()
    {
        model = new Model();
        view = new View();
        // controller = new Controller(model, view);
        model.ResetModel();
        view.ResetView();
    }

    @Test
    public void TestMultiplayerModeGameContents() throws Exception
    {
        model.SelectPlayerForRound("RandomPlayer");
        model.setGameMode("Multiplayer");
        Object[] data = this.RunSameGameActions();

        assertEquals("[!] Total Players should be 4 on multiplayer ...", 4, (int)data[0]);
        assertEquals("[!] Player Inventory should be empty on start ...", 0, (int)data[1]);
        assertEquals("[!] Multiplayer game should have 20 total available cards (4 players * 5 each) ...", 20, (int)data[2]);

        assertEquals("[!] Multiplayer tiles bag should have 27 Mosaic tiles ...", 27, (int)data[3]);
        assertEquals("[!] Multiplayer tiles bag should have 24 Statue tiles ...", 24, (int)data[4]);
        assertEquals("[!] Multiplayer tiles bag should have 24 Landslide tiles ...", 24, (int)data[5]);
        assertEquals("[!] Multiplayer tiles bag should have 30 Skeleton tiles ...", 30, (int)data[6]);
        assertEquals("[!] Multiplayer tiles bag should have 30 Amphora tiles ...", 30, (int)data[7]);

        assertEquals("[!] Board should have four tiles initially ...", 4, (int)data[8]);
        assertEquals("[!] Tiles Bag should have 131 tiles at multiplayer game start ...", 131, (int)data[9]);
        assertNotNull("[!] Background JLabel should be initialized ...", data[10]);
        assertNotNull("[!] Background JLabel ImageIcon should be attached ...", data[11]);
        assertTrue("[!] Background image dimensions should be > 0 ...",((Icon)data[11]).getIconWidth() > 0 && ((Icon)data[11]).getIconHeight() > 0);
    }

    @Test
    public void TestSingleplayerModeGameContents() throws Exception
    {
        model.SelectPlayerForRound("OnlyPlayer");
        model.setGameMode("Singleplayer");
        model.getBag().editBagForSingleplayer();
        model.getBoardTile(0).setCount(8);
        Object[] data = this.RunSameGameActions();

        assertEquals("[!] Total Players should be 2 on multiplayer ...", 2, (int)data[0]);
        assertEquals("[!] Player Inventory should be empty on start ...", 0, (int)data[1]);
        assertEquals("[!] Multiplayer game should have 10 total available cards (2 players * 5 each) ...", 10, (int)data[2]);

        assertEquals("[!] Multiplayer tiles bag should have 27 Mosaic tiles ...", 27, (int)data[3]);
        assertEquals("[!] Multiplayer tiles bag should have 24 Statue tiles ...", 24, (int)data[4]);
        assertEquals("[!] Multiplayer tiles bag should have 16 Landslide tiles ...", 24 - 8, (int)data[5]);
        assertEquals("[!] Multiplayer tiles bag should have 30 Skeleton tiles ...", 30, (int)data[6]);
        assertEquals("[!] Multiplayer tiles bag should have 30 Amphora tiles ...", 30, (int)data[7]);

        assertEquals("[!] Board should have 12 tiles initially ...", 4 + 8, (int)data[8]);
        assertEquals("[!] Tiles Bag should have 123 tiles at multiplayer game start ...", 131 - 8, (int)data[9]);
        assertNotNull("[!] Background JLabel should be initialized ...", data[10]);
        assertNotNull("[!] Background JLabel ImageIcon should be attached ...", data[11]);
        assertTrue("[!] Background image dimensions should be > 0 ...", ((Icon)data[11]).getIconWidth() > 0 && ((Icon)data[11]).getIconHeight() > 0);
    }

    private Object[] RunSameGameActions() throws Exception
    {
        Field playersField = Model.class.getDeclaredField("players");
        playersField.setAccessible(true);
        Object[] players = (Object[]) playersField.get(model);
        int totalPlayers = 0, totalCards = 0, totalTiles = 0;
        for (Object p : players)
        {
            totalPlayers++;
            Model.Player player = (Model.Player) p;
            for (Model.CardTiles card : player.getPlayercards()) if (card.isAvailable() && !card.getActive()) totalCards++;
            for (Model.InventoryTiles tile : player.getPlayertiles()) totalTiles += tile.getCount();
        }

        List<String> tiles = model.getBag().getTiles();
        int mosaic = 0, statue = 0, landslide = 0, skeleton = 0, amphora = 0;
        for (String tile : tiles)
        {
            if (tile.startsWith("mosaic")) mosaic++;
            else if (tile.equals("caryatid") || tile.equals("sphinx")) statue++;
            else if (tile.equals("landslide")) landslide++;
            else if (tile.startsWith("skeleton")) skeleton++;
            else if (tile.startsWith("amphora")) amphora++;
        }

        View.GameLabels backgroundObj = view.getLabels(4);
        JLayeredPane dummyPanel = new JLayeredPane();
        backgroundObj.addTo(dummyPanel, JLayeredPane.DEFAULT_LAYER);
        JLabel backgroundLabel = backgroundObj.getLabel();
        Icon icon = backgroundLabel.getIcon();
        List<String> temptiles = model.DrawTilesFromBag("RandomDraw");
        List<Integer> tempindexes = null; if (temptiles != null) tempindexes = view.GetLabelIndexFromName(temptiles);
        if (tempindexes != null) for (int i : tempindexes) model.getBoardTile(i - 6).setCount(model.getBoardTile(i - 6).getCount() + 1);
        for (View.ButtonTilesView tile : this.view.getBoardView()) tile.changeLabelCount(model.getBoardTile(tile.getIndex() - 6).getCount());
        int totalCounts = 0;
        for (View.ButtonTilesView boardbutton : view.getBoardView()) totalCounts += boardbutton.getCount();
        int totalBagTiles = model.getBag().getTiles().size();

        return new Object[] { totalPlayers, totalTiles, totalCards, mosaic, statue, landslide, skeleton, amphora, totalCounts, totalBagTiles, backgroundLabel, icon };
    }

    @Test
    public void TestGameOverPointsCalculations() throws Exception
    {
        Model.Player player;
        List<Model.InventoryTiles> inventory;
        String[] result;

        model.SelectPlayerForRound("OnlyPlayer");
        player = model.getPlayer();
        inventory = player.getPlayertiles();
        inventory.get(1).setCount(8); inventory.get(2).setCount(5); inventory.get(3).setCount(3);
        result = model.CalculatePlayerPointsAtGameOver();
        assertEquals("[!] Mosaic tiles calculations ...", 14 + 12, Integer.parseInt(result[1]));

        model.SelectPlayerForRound("OnlyPlayer");
        player = model.getPlayer();
        inventory = player.getPlayertiles();
        inventory.get(7).setCount(4); inventory.get(15).setCount(3); inventory.get(6).setCount(2); inventory.get(14).setCount(1);
        result = model.CalculatePlayerPointsAtGameOver();
        assertEquals("[!] Skeleton tiles calculations ...", 7 + 12, Integer.parseInt(result[1]));

        model.SelectPlayerForRound("OnlyPlayer");
        player = model.getPlayer();
        inventory = player.getPlayertiles();
        inventory.get(8).setCount(4); inventory.get(9).setCount(3); inventory.get(10).setCount(2);
        inventory.get(11).setCount(1); inventory.get(12).setCount(0); inventory.get(13).setCount(1);
        result = model.CalculatePlayerPointsAtGameOver();
        assertEquals("[!] Amphora tiles calculations ...", 5 + 12, Integer.parseInt(result[1]));

        model.SelectPlayerForRound("RandomPlayer");
        model.setGameMode("Multiplayer");
        java.lang.reflect.Field playersField = Model.class.getDeclaredField("players");
        playersField.setAccessible(true);
        Model.Player[] players = (Model.Player[]) playersField.get(model);
        players[0].getPlayertiles().get(4).setCount(5);
        players[1].getPlayertiles().get(4).setCount(3);
        players[2].getPlayertiles().get(4).setCount(3);
        players[3].getPlayertiles().get(4).setCount(1);
        result = model.CalculatePlayerPointsAtGameOver();
        assertEquals("[!] Statue tiles calculations ...", "12", result[1]);

        assertTrue("[!] Winner (of this example): Player 1 ...", result[0].contains("Player 1"));
    }

}

// [!] The End ...
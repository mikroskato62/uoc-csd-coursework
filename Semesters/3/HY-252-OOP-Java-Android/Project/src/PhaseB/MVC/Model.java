// [!] UOC - CSD - HY252 - Java (MVC) - Project Phase A & B - Amphipolis Board Game - RIP - csd5748 - :)
// [!] Please read the ReadMe.md file !

package PhaseB.MVC;

import java.io.File;
import java.util.List;
import java.util.Arrays;
import java.util.Random;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;

public class Model
{
    public Model () {}

    private static final List<String> categories = Arrays.asList("M", "TL", "TR", "BL", "BR");

    private static final List<CardTiles> cards = Arrays.asList(
            new CardTiles("archaeologist", 1),
            new CardTiles("assistant", 2),
            new CardTiles("coder", 3),
            new CardTiles("digger", 4),
            new CardTiles("professor", 5)
    );

    private static final List<ButtonTiles> board = Arrays.asList(
            new ButtonTiles("landslide", 24, categories.get(0), 6),
            new ButtonTiles("mosaic_red", 9, categories.get(1), 7),
            new ButtonTiles("mosaic_green", 9, categories.get(1), 8),
            new ButtonTiles("mosaic_yellow", 9, categories.get(1), 9),
            new ButtonTiles("caryatid", 12, categories.get(2), 10),
            new ButtonTiles("sphinx", 12, categories.get(2), 11),
            new ButtonTiles("skeleton_small_top", 5, categories.get(4), 12),
            new ButtonTiles("skeleton_big_top", 10, categories.get(4), 13),
            new ButtonTiles("amphora_brown", 5, categories.get(3), 14),
            new ButtonTiles("amphora_purple", 5, categories.get(3), 15),
            new ButtonTiles("amphora_yellow", 5, categories.get(3), 16),
            new ButtonTiles("amphora_blue", 5, categories.get(3), 17),
            new ButtonTiles("amphora_green", 5, categories.get(3), 18),
            new ButtonTiles("amphora_red", 5, categories.get(3), 19),
            new ButtonTiles("skeleton_small_bottom", 5, categories.get(4), 20),
            new ButtonTiles("skeleton_big_bottom", 10, categories.get(4), 21)
    );
    public ButtonTiles getBoardTile(int index) { return board.get(index); } public List<ButtonTiles> getBoardTiles() { return board; }

    private static final List<InventoryTiles> inventory = Arrays.asList(
            new InventoryTiles("landslide", 22),
            new InventoryTiles("mosaic_red", 23),
            new InventoryTiles("mosaic_green", 24),
            new InventoryTiles("mosaic_yellow", 25),
            new InventoryTiles("caryatid", 26),
            new InventoryTiles("sphinx", 27),
            new InventoryTiles("skeleton_small_top", 28),
            new InventoryTiles("skeleton_big_top", 29),
            new InventoryTiles("amphora_brown", 30),
            new InventoryTiles("amphora_purple", 31),
            new InventoryTiles("amphora_yellow", 32),
            new InventoryTiles("amphora_blue", 33),
            new InventoryTiles("amphora_green", 34),
            new InventoryTiles("amphora_red", 35),
            new InventoryTiles("skeleton_small_bottom", 36),
            new InventoryTiles("skeleton_big_bottom", 37)
    );

    private String gameMode;
    private Player player; private Player[] players;
    private final TilesBag bag = new TilesBag();
    public TilesBag getBag() { return this.bag; }
    public Player getPlayer() { return this.player; } public void setPlayer(Player player) { this.player = player; }
    public String getGameMode() { return this.gameMode; } public void setGameMode(String gameMode) { this.gameMode = gameMode; }

    public static class InventoryTiles
    {
        private int index;
        private String name;
        private int count = 0;

        public InventoryTiles(String name, int index) { setName(name); setIndex(index); }

        public void setIndex(int index) { this.index = index; } public int getIndex() { return this.index; }
        public void setName(String name) { this.name = name; } public String getName() { return name; }
        public void setCount(int count) { this.count = count; } public int getCount() { return count; }

        protected void increaseCount() { this.setCount(this.getCount() + 1); }
    }

    public static class ButtonTiles extends InventoryTiles
    {
        private String category;
        private int total;

        public ButtonTiles(String name, int total, String category, int index) { super(name, index); setTotal(total); setCategory(category); }

        public void setTotal(int total) { this.total = total; } public int getTotal() { return this.total; }
        public void setCategory(String category) { this.category = category; } public String getCategory() { return this.category; }
    }

    public static class CardTiles extends ButtonTiles
    {
        private boolean available = true;
        private boolean active = false;

        public CardTiles(String name, int index) { super(name, 1, "None", index); }

        public void makeAvailable(boolean available) { this.available = available; } public boolean isAvailable() { return this.available; }
        public boolean getActive() { return this.active; } public void setActive(boolean active) { this.active = active; }

        public boolean isCardPlayable(String area) { if (!this.isAvailable()) return false; return (this.getIndex() != 3 || area != null); }
    }

    public static class TilesBag
    {
        private final Random random = new Random();
        private final List<String> tiles = new ArrayList<>();
        public List<String> getTiles() { return this.tiles; }

        public TilesBag() { for (ButtonTiles tile : board) for (int i = 0; i < tile.getTotal(); i++) this.tiles.add(tile.getName()); }

        public List<String> drawTheRoundTiles(int n)
        {
            List<String> drawn = new ArrayList<>();
            for (int i = 0; i < n && !this.tiles.isEmpty(); i++) { drawn.add(this.tiles.remove(this.random.nextInt(this.tiles.size()))); }
            return drawn;
        }
        public List<String> drawOneLandslideTile(int n)
        {
            List<String> drawn = new ArrayList<>();
            boolean frouros = false;
            for (int i = 0; i < n && !this.tiles.isEmpty(); i++)
            {
                int temp = this.random.nextInt(this.tiles.size());
                if (this.tiles.get(temp).equals("landslide") && !frouros) { drawn.add(this.tiles.remove(temp)); frouros = true; }
                if (!frouros) drawn.add(this.tiles.remove(temp));
            }
            return drawn;
        }
        public void removeTileByName(String name) { this.tiles.remove(name); }
        public void editBagForSingleplayer() { for (int i = 0; i < 8; i = i + 1) this.tiles.remove("landslide"); }

        public void RefillTiles() { this.tiles.clear(); for (ButtonTiles tile : board) for (int i = 0; i < tile.getTotal(); i++) this.tiles.add(tile.getName()); }
    }

    public class Player
    {
        // private String color;
        private final String name;
        private final int id;
        private String currentArea = null, lastArea = null;
        private int[] roundTilesCollected = new int[8];
        private final List<InventoryTiles> playertiles;
        private final List<CardTiles> playercards;

        public Player(int id)
        {
            this.id = id; this.name = (id == -1 ? ("Thieve...") : ("Player " + id));
            this.playertiles = new ArrayList<>();
            for (InventoryTiles global : inventory)
            {
                InventoryTiles copy = new InventoryTiles(global.getName(), global.getIndex());
                copy.setCount(0);
                this.playertiles.add(copy);
            }
            this.playercards = new ArrayList<>();
            for (CardTiles global : cards)
            {
                CardTiles copy = new CardTiles(global.getName(), global.getIndex());
                copy.makeAvailable(true);
                this.playercards.add(copy);
            }
        }

        public String getName() { return this.name; } public int getId() { return this.id; }
        public String getCurrentArea() { return this.currentArea; } public void setCurrentArea(String currentArea) { this.currentArea = currentArea; }
        public String getLastArea() { return this.lastArea; } public void setLastArea(String lastArea) { this.lastArea = lastArea; }
        public int[] getRoundTilesCollected() { return this.roundTilesCollected; } public void setRoundTilesCollected(int[] array) { this.roundTilesCollected = array; }
        public List<InventoryTiles> getPlayertiles() { return this.playertiles; } public List<CardTiles> getPlayercards() { return this.playercards; }

        public void GetOneTileFromTheBoard(int index)
        {
            ButtonTiles tochange = null;
            for (ButtonTiles i : board) if (i.getIndex() == index) { tochange = i; break; }
            if (tochange == null) return;

            List<CardTiles> active = new ArrayList<>();
            for (CardTiles c : this.getPlayercards()) if (c.getActive()) active.add(c);

            int[] collected = getPlayer().getRoundTilesCollected();
            if (collected[0] == 0 || (collected[0] == 1 && player.getCurrentArea().equals(tochange.getCategory())))
            {
                tochange.setCount(tochange.getCount() - 1);
                getPlayer().getPlayertiles().get(index - 6).increaseCount();
                collected[0] += 1;
                getPlayer().setRoundTilesCollected(collected);
                getPlayer().setCurrentArea(tochange.getCategory());
                return;
            }

            List<String> ctg = new ArrayList<>(categories);
            ctg.remove("M"); ctg.remove(getPlayer().getCurrentArea());

            boolean frouros = true;
            for (CardTiles c : active)
            {
                if (!frouros) break;
                switch (c.getIndex() - 1)
                {
                    case 0:
                        if (collected[1] <= 1 && !player.getCurrentArea().equals(tochange.getCategory()))
                        { HelperForGetOneTileFromTheBoard(tochange, 1); frouros = false; }
                        break;
                    case 1:
                        if (collected[2] == 0)
                        { HelperForGetOneTileFromTheBoard(tochange, 2); frouros = false; }
                        break;
                    case 2:
                        if (getPlayer().getLastArea() != null && collected[3] <= 1 && player.getLastArea().equals(tochange.getCategory()))
                        { HelperForGetOneTileFromTheBoard(tochange, 3); frouros = false; }
                        break;
                    case 3:
                        if (collected[4] <= 1 && player.getCurrentArea().equals(tochange.getCategory()))
                        { HelperForGetOneTileFromTheBoard(tochange, 4); frouros = false; }
                        break;
                    case 4:
                        if (collected[5] == 0 && !player.getCurrentArea().equals(tochange.getCategory()) && tochange.getCategory().equals(ctg.get(0)))
                        { HelperForGetOneTileFromTheBoard(tochange, 5); frouros = false; }
                        else if (collected[6] <= 0 && !player.getCurrentArea().equals(tochange.getCategory()) && tochange.getCategory().equals(ctg.get(1)))
                        { HelperForGetOneTileFromTheBoard(tochange, 6); frouros = false; }
                        else if (collected[7] < 1 && !player.getCurrentArea().equals(tochange.getCategory()) && tochange.getCategory().equals(ctg.get(2)))
                        { HelperForGetOneTileFromTheBoard(tochange, 7); frouros = false; }
                        break;
                }
            }
        }
        void HelperForGetOneTileFromTheBoard(ButtonTiles toChange, int arrayIndex)
        {
            toChange.setCount(toChange.getCount() - 1);
            getPlayer().getPlayertiles().get(toChange.getIndex() - 6).increaseCount();
            int[] array = getPlayer().roundTilesCollected;
            array[arrayIndex] += 1;
            getPlayer().setRoundTilesCollected(array);
        }

        public void DisableAllCards() { for (CardTiles card : getPlayer().getPlayercards()) { card.setActive(false); } }
    }

    public void SelectPlayerForRound(String cmd)
    {
        switch (cmd)
        {
            case "RandomPlayer":
                this.players = new Player[] { new Player(1), new Player(2), new Player(3), new Player(4) };
                Random rnd = new Random(); setPlayer(this.players[rnd.nextInt(4)]);
                break;
            case "NextPlayer":
                Arrays.fill(getPlayer().getRoundTilesCollected(), 0);
                getPlayer().setLastArea(getPlayer().getCurrentArea()); getPlayer().setCurrentArea(null);
                for (CardTiles c : cards) c.setActive(false);
                setPlayer(this.players[getPlayer().getId() % 4]);
                break;
            case "OnlyPlayer":
                this.players = new Player[] { new Player(0), new Player(-1) };
                setPlayer(this.players[0]);
                break;
            case "ThievePlayer":
                Arrays.fill(getPlayer().getRoundTilesCollected(), 0);
                getPlayer().setLastArea(getPlayer().getCurrentArea()); getPlayer().setCurrentArea(null);
                for (CardTiles c : cards) c.setActive(false);
                setPlayer(this.players[0]);
                break;
        }
    }

    public List<String> DrawTilesFromBag(String cmd)
    {
        switch (cmd)
        {
            case "RandomDraw":
                List<String> selectedTiles = new ArrayList<>();
                for (int i = 1; i < categories.size(); i++)
                {
                    List<String> candidates = new ArrayList<>();
                    for (ButtonTiles btn : board) if (btn.getCategory().equals(categories.get(i))) candidates.add(btn.getName());
                    if (candidates.isEmpty()) return null;
                    Random rnd = new Random();
                    String name = candidates.get(rnd.nextInt(candidates.size()));
                    this.getBag().removeTileByName(name);
                    selectedTiles.add(name);
                }
                return selectedTiles;
            case "PlayerDraw":
                List<String> result1 = this.getBag().drawTheRoundTiles(4);
                for (InventoryTiles i : inventory)
                    for (String drawnTileName : result1)
                        if (i.getName().equals(drawnTileName) && i.getName().equals("landslide"))
                            getPlayer().getPlayertiles().get(i.getIndex() - 22).increaseCount();
                return result1;
            case "ThieveDraw":
                List<String> result2 = this.getBag().drawOneLandslideTile(4);
                for (InventoryTiles i : inventory)
                    for (String drawnTileName : result2)
                        if (i.getName().equals(drawnTileName) && i.getName().equals("landslide"))
                            getPlayer().getPlayertiles().get(i.getIndex() - 22).increaseCount();
                return result2;
        }
        return null;
    }

    public void PlayThiefTurn()
    {
        this.player = this.players[1];
        for (ButtonTiles tile : board)
        {
            if (tile.getName().equals("landslide")) continue;
            int count = tile.getCount();
            if (count > 0)
            {
                this.player.getPlayertiles().get(tile.getIndex() - 6).setCount(this.player.getPlayertiles().get(tile.getIndex() - 6).getCount() + count);
                tile.setCount(0);
            }
        }
        this.player = this.players[0];
    }

    public String[] CalculatePlayerPointsAtGameOver()
    {
        Player[] players = this.players;
        int[] totalPoints = new int[players.length];
        int[] caryatidCount = new int[players.length]; int[] sphinxCount = new int[players.length];

        for (int p = 0; p < players.length; p++)
        {
            Player player = players[p];
            List<InventoryTiles> tiles = player.getPlayertiles();
            int points = 0;

            int mosaicRed = tiles.get(1).getCount(); int mosaicGreen = tiles.get(2).getCount(); int mosaicYellow = tiles.get(3).getCount();
            points += (mosaicRed / 4) * 4; points += (mosaicGreen / 4) * 4; points += (mosaicYellow / 4) * 4;
            int leftovers = (mosaicRed % 4) + (mosaicGreen % 4) + (mosaicYellow % 4);
            points += (leftovers / 4) * 2;

            int smallTop = tiles.get(6).getCount(); int bigTop = tiles.get(7).getCount();
            int smallBottom = tiles.get(14).getCount(); int bigBottom = tiles.get(15).getCount();
            int completeSmall = Math.min(smallTop, smallBottom); int completeBig = Math.min(bigTop, bigBottom);
            while (completeBig >= 2 && completeSmall >= 1) { points += 6; completeBig -= 2; completeSmall -= 1; }
            points += completeBig; points += completeSmall;

            int[] amphoraCounts = new int[] { tiles.get(8).getCount(), tiles.get(9).getCount(), tiles.get(10).getCount(), tiles.get(11).getCount(), tiles.get(12).getCount(), tiles.get(13).getCount() };
            while (true)
            {
                int differentColours = 0;
                for (int count : amphoraCounts) if (count > 0) differentColours++;
                if (differentColours >= 6) points += 6;
                else if (differentColours == 5) points += 4;
                else if (differentColours == 4) points += 2;
                else if (differentColours == 3) points += 1;
                else break;
                for (int i = 0; i < amphoraCounts.length; i++) if (amphoraCounts[i] > 0) amphoraCounts[i]--;
            }

            caryatidCount[p] = tiles.get(4).getCount(); sphinxCount[p] = tiles.get(5).getCount();

            totalPoints[p] = points;
        }

        HelperForCalculatePlayerPointsAtGameOver(players, totalPoints, caryatidCount);
        HelperForCalculatePlayerPointsAtGameOver(players, totalPoints, sphinxCount);

        int maxScore = -1; List<String> winners = new ArrayList<>();
        for (int score : totalPoints) { if (score > maxScore) maxScore = score; }
        for (int p = 0; p < players.length; p++) { if (totalPoints[p] == maxScore) { if (players[p].getName().equals("Player 0")) winners.add("You!"); else winners.add(players[p].getName()); } }

        return new String[] { (String.join(" & ", winners).equals("Player 1 & Player 2 & Player 3 & Player 4") ? "Everyone!" : (String.join(" & ", winners))), String.valueOf(maxScore) };
    }
    private void HelperForCalculatePlayerPointsAtGameOver(Player[] players, int[] totalPoints, int[] sphinxCount)
    {
        int maxS = -1, minS = 1000;
        for (int s : sphinxCount) { if (s > maxS) maxS = s; if (s < minS) minS = s; }
        for (int p = 0; p < players.length; p++)
        {
            if (sphinxCount[p] == maxS) totalPoints[p] += 6;
            else if (sphinxCount[p] == minS) totalPoints[p] += 0;
            else totalPoints[p] += 3;
        }
    }

    public void SaveGameStateAndExit (int time, boolean btn1, boolean btn2, int[] board)
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter("cookies.txt")))
        {
            writer.println("[!] Warning: Do Not Edit This File; It Contains Game Data - Your Progress Will Be Lost ...\n");

            writer.println(this.getPlayer().getName() + " " + time + " " + btn1 + " " + btn2 + "\n");
            for (Player p : players)
            {
                writer.print(p.getCurrentArea() + " " + p.getLastArea() + " " + Arrays.toString(p.getRoundTilesCollected()) + " ");
                for (int i = 0; i < p.getPlayertiles().size(); i++) { writer.print(p.getPlayertiles().get(i).getCount() + " "); }
                for (int i = 0; i < p.getPlayercards().size(); i++) { writer.print(p.getPlayercards().get(i).isAvailable() + " " + p.getPlayercards().get(i).getActive() + " "); }
                writer.print("\n");
            }
            writer.println("\n" + Arrays.toString(board));

            for (InventoryTiles type : inventory)
            {
                String typeName = type.getName(); int count = 0;
                for (String tileInBag : this.getBag().getTiles()) if (tileInBag.equals(typeName)) count++;
                writer.print(count + " " + typeName + " ");
            }

            writer.println(); writer.println("\n[!] The End ... Thanks For Playing My Game :)");
        }
        catch (IOException ignored) { }
    }

    public Object[] LoadGameStateAndStart ()
    {
        File file = new File("cookies.txt");
        if (!file.exists()) return null;

        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            br.readLine(); br.readLine();
            String gameInfoLine = br.readLine();
            String[] infoParts = gameInfoLine.split(" ");
            int activePlayer = Integer.parseInt(infoParts[1]);
            int timeRemaining = Integer.parseInt(infoParts[2]);
            boolean btn1State = Boolean.parseBoolean(infoParts[3]);
            boolean btn2State = Boolean.parseBoolean(infoParts[4]);
            br.readLine();

            this.players = new Player[(activePlayer == 0) ? 2 : 4];
            for (int i = 0; i < this.players.length; i++)
            {
                String line = br.readLine();
                int firstSpace = line.indexOf(' ');
                String currentAreaStr = line.substring(0, firstSpace);
                int secondSpace = line.indexOf(' ', firstSpace + 1);
                String lastAreaStr = line.substring(firstSpace + 1, secondSpace);
                int openBracket = line.indexOf('['); int closeBracket = line.indexOf(']');
                String arrayContent = line.substring(openBracket + 1, closeBracket);
                int[] collectedArr = Arrays.stream(arrayContent.split(", ")).mapToInt(Integer::parseInt).toArray();
                String remaining = line.substring(closeBracket + 1).trim();
                String[] tokens = remaining.split(" ");

                Player p;
                if (this.players.length == 4) p = new Player(i + 1);
                else { if (i == 0) p = new Player(0); else p = new Player(-1); }
                if (!currentAreaStr.equals("null")) p.setCurrentArea(currentAreaStr);
                if (!lastAreaStr.equals("null")) p.setLastArea(lastAreaStr);
                p.setRoundTilesCollected(collectedArr);
                int tokenIndex = 0;
                for (InventoryTiles t : p.getPlayertiles()) { t.setCount(Integer.parseInt(tokens[tokenIndex++])); }
                for (CardTiles c : p.getPlayercards()) { c.makeAvailable(Boolean.parseBoolean(tokens[tokenIndex++])); c.setActive(Boolean.parseBoolean(tokens[tokenIndex++])); }
                this.players[i] = p;
            }

            br.readLine();
            String boardLine = br.readLine();
            String boardContent = boardLine.substring(boardLine.indexOf('[') + 1, boardLine.indexOf(']'));
            int[] boardCounts = Arrays.stream(boardContent.split(", ")).mapToInt(Integer::parseInt).toArray();
            for (int i = 0; i < board.size(); i++) if (i < boardCounts.length) board.get(i).setCount(boardCounts[i]);

            String bagLine = br.readLine();
            if (bagLine != null && !bagLine.trim().isEmpty())
            {
                this.getBag().getTiles().clear();
                String[] parts = bagLine.trim().split(" ");
                for (int i = 0; i < parts.length - 1; i += 2)
                {
                    try
                    {
                        int count = Integer.parseInt(parts[i]);
                        String name = parts[i+1];
                        for (int k = 0; k < count; k++) this.getBag().getTiles().add(name);
                    }
                    catch (NumberFormatException ignored) { }
                }
            }

            this.setPlayer(this.players[Math.max((activePlayer - 1), 0)]);

            return new Object [] { timeRemaining, btn1State, btn2State, (this.players.length == 4) ? ("Multiplayer") : ("Singleplayer") };

        }
        catch (Exception ignored) { return null; }
    }

    public void ResetModel() { this.bag.RefillTiles(); for (ButtonTiles b : board) { b.setCount(0); } }
}

// [!] The End ...
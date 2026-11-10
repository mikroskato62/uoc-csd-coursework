/* UOC - CSD - HY252 - Project Phase 1 - Java - MVC: Model - RIP - csd5748 */

package PhaseA.MVC;

import java.util.List;

import java.util.Random;

import java.util.ArrayList;

public class Model
{
    public Model () {}

    static List<ButtonTiles> board = new ArrayList<>();

    public List<ButtonTiles> getBoard () { return board; }

    static List<InventoryTiles> inventory = new ArrayList<>();

    public List<InventoryTiles> getInventory () { return inventory; }

    static List<CardTiles> cards = new ArrayList<>();

    public List<CardTiles> getCards () { return cards; }

    private Player player = null;

    public Player getPlayer () { return this.player; }

    public void setPlayer (Player player) { this.player = player; }

    private TilesBag bag = new TilesBag();

    public TilesBag getBag () { return this.bag; }

    public static enum TileTypes
    {
        TEST(-1, "");

        private final int total;

        private final String category;

        TileTypes (int total, String category) { this.total = total; this.category = category; }

        public int getTotal () { return total; }

        public String getCategory () { return category; }

        public static String getRandomNameByCategory(String category) { return ""; }
    }

    public class InventoryTiles
    {
        private int index;

        private String name;

        private int count = -1;

        public InventoryTiles (String name, int index) { }

        public void setIndex (int index) { this.index = index; }

        public int getIndex () { return this.index; }

        public void setName (String name) { this.name = name; }

        public String getName () { return name; }

        public void setCount (int count) { this.count = count; }

        public int getCount () { return count; }

        public void increaseCount() { }
    }

    public class ButtonTiles extends InventoryTiles
    {
        private boolean state;

        private String category;

        public ButtonTiles (String name, int index, String category) { super(name, index); }

        public void setCategory (String category) { this.category = category; }

        public String getCategory () { return category; }

        public boolean getState () { return this.state; }

        public void setState (boolean state) { this.state = state; }
    }

    public class CardTiles extends ButtonTiles
    {
        private String effect;

        private boolean available;

        public CardTiles (String name, int index, String effect) { super(name, index, "None"); }

        public void setEffect (String effect) { this.effect = effect; }

        public String getEffect () { return this.effect; }

        public void makeAvailable (boolean available) { this.available = available; }

        public boolean isAvailable () { return this.available; }
    }

    public class TilesBag
    {
        private final List<TileTypes> tiles = new ArrayList<>();

        public List<TileTypes> getTiles () { return tiles; }

        private Random random = new Random();

        public TilesBag () { }

        public List<TileTypes> drawTiles(int n) { return null; }

        public TileTypes findBagTileByName(String name) { return null; }
    }

    public class Player
    {
        private final String name = "";

        private final int id = -1;

        private String currentArea;

        private String lastArea;

        private int[] boardTilesCollected;

        private final List<InventoryTiles> playertiles = new ArrayList<>();

        private final List<CardTiles> playercards = new ArrayList<>();

        public Player (String name, int id) { }

        public String getName () { return name; }

        public int getId () { return id; }

        public String getCurrentArea () { return currentArea; }

        public void setCurrentArea (String currentArea) { this.currentArea = currentArea; }

        public void setLastArea (String lastArea) { this.lastArea = lastArea; }

        public String getLastArea () { return lastArea; }

        public int[] getBoardTilesCollected () { return boardTilesCollected; }

        public void setBoardTilesCollected (int[] array) { this.boardTilesCollected = array; }

        public List<InventoryTiles> getPlayertiles () { return playertiles; }

        public List<CardTiles> getPlayercards () { return playercards; }

        public void GetOneTileFromTheBoard (int index) { }

        private void HelperForGetOneTileFromTheBoard (ButtonTiles toChange, int arrayIndex) { }
    }

    public class PlayTheGame
    {
        private Player[] players;

        private int currentPlayerIndex;

        public PlayTheGame () { }

        public void SelectPlayer (String cmd) { }

        public List<String> DrawTiles (String cmd) { return null; }

        public int[] CalculatePlayerPointsAtGameOver () { return null; }

        private void addStatuePoints (int[] totalPoints, int[] counts) { }
    }

    public class Methods
    {
        public Methods () {}

        private InventoryTiles SearchEverywhereForIndex (int index) { return null; }

        public String getName (int index) { return null; }

        public int getCount (int index) { return -1; }

        public void ChangeInventoryCounts(List<Integer> indexes) { }
    }

    public static void SaveStateAndExit ( ) { }

    public static void ReadFileAndContinueGame ( ) { }

}

/* The End */
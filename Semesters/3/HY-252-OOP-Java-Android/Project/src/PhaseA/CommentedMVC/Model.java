/* UOC - CSD - HY252 - Project Phase 1 - Java - MVC: Model - RIP - csd5748 */
// [!] Command: ( javadoc -d docs src/PhaseA/CommentedMVC/*.java ) ...

/** [Extra]: The package of the file ... */
package PhaseA.CommentedMVC;

/** [Extra]: A required import ... */
import java.util.List;
/** [Extra]: A required import ... */
import java.util.Random;
/** [Extra]: A required import ... */
import java.util.ArrayList;

/**
 * [Class] Model
 * [Info]:
 * The class containing the game logic, the state of the tiles, cards, bag, board and the players.
 * [Invariants]:
 * The lists of board, inventory, and cards are static and initialized once.
 * The bag is always a single, unique tile bag.
 */
public class Model
{
    /** [Constructor] The (no arguments) constructor of the class ... */
    public Model() {}

    /**
     * [Info]: List of all tiles currently on the excavation board (visible to players).
     * [Invariants]: Immutable after initialization.
     */
    static List<ButtonTiles> board = new ArrayList<>();

    /**
     * [Getter]: A list of all the tiles on the board all players have drawn in the game.
     * @return The board tiles list.
     */
    public static List<ButtonTiles> getBoard() {return board;}

    /**
     * [Info]: List of global tile templates used as reference for inventory and counting.
     * [Invariants]: Immutable after initialization.
     */
    static List<InventoryTiles> inventory = new ArrayList<>();

    /**
     * [Getter]: A list of all the tiles each player has collected in the game.
     * @return The inventory tiles list.
     */
    public static List<InventoryTiles> getInventory() {return inventory;}

    /**
     * [Info]: List of all action cards available in the game.
     * [Invariants]: Immutable after initialization.
     */
    static List<CardTiles> cards = new ArrayList<>();

    /**
     * [Getter]: A list of all the cards each player will have in the game.
     * @return The cards list.
     */
    public static List<CardTiles> getCards() { return cards; }

    /**
     * [Field]: The current player playing in the current round of the game ...
     */
    private Player player = null;

    /**
     * [Getter]: Get the player that is currently playing each round.
     * @return The current player.
     */
    public Player getPlayer() { return this.player; }

    /**
     * [Setter]: Set the player that is going to play before the round starts.
     * @param player The player to set as current.
     */
    public void setPlayer(Player player) { this.player = player; }

    /**
     * [Field]: The (invisible global) bag containing all the tiles that can be randomly drawn ...
     */
    private TilesBag bag = new TilesBag();

    /**
     * [Getter]: Get the (invisible) bag that has all the remaining tiles not on the board.
     * @return The tile bag instance.
     */
    public TilesBag getBag() { return this.bag; }

    /**
     * [Class] TileTypes
     * [Info]:
     * An enumeration containing all the different tile types, their total quantity, and the category (area) they belong to.
     * [Invariants]:
     * Each tile type has a constant total and category.
     * [Preconditions]:
     * None.
     * [Post-conditions]:
     * None.
     */
    public static enum TileTypes
    {
        /**
         * [Example]: One of the enumeration entries ...
         */
        LANDSLIDE(24, "MIDDLE");

        /**
         * [Field]: The max number of these tiles allowed in the bag ...
         */
        private final int total;
        /**
         * [Field]: The category (area) of the board the tile is located ...
         */
        private final String category;
        /**
         * [Constructor]: The constructor of the enumeration ...
         */
        TileTypes(int total, String category) { this.total = total; this.category = category; }
        /**
         * [Getter]: Get the total (max) count of the tile.
         * @return The integer total.
         */
        public int getTotal() { return total; }
        /**
         * [Getter]: Get the category (area) of the tile.
         * @return The string category.
         */
        public String getCategory() { return category; }

        /**
         * [Info] Selects a random tile type based on the category.
         * [Preconditions]: `category` must be one of "TL", "TR", "BL", "BR".
         * [Post-conditions]: The name of a random tile of the specified category is returned.
         * [Invariants]: None.
         * @param category The category (area) of the tile.
         * @return The name of the random tile.
         */
        public static String getRandomNameByCategory(String category) { return "[+++]"; }
    }


    /**
     * [Class] ImageTiles
     * [Info] Base class for all tiles (images), containing name, index, and count.
     * [Invariants]:
     * - The `index` is unique.
     */
    public class InventoryTiles
    {
        /**
         * [Field]: The index of the tile (image label) used for easy communication using the controller.
         */
        private int index;
        /**
         * [Field]: The name of the tile (image label).
         */
        private String name;
        /**
         * [Field]: The count of the tile (image label).
         */
        private int count = 0;

        /**
         * [Info] Constructor for the ImageTiles class.
         * [Preconditions]: None.
         * [Post-conditions]: An ImageTiles object is created.
         * [Invariants]: `count` is initialized to 0.
         * @param name The name of the tile.
         * @param index The unique identifier.
         */
        public InventoryTiles(String name, int index) { }

        /**
         * [Setter]: Set the index of the tile.
         * @param index The integer value.
         */
        public void setIndex(int index) { this.index = index; }
        /**
         * [Getter]: Get the index of the tile.
         * @return The index as an integer.
         */
        public int getIndex() { return this.index; }
        /**
         * [Setter]: Set the name of the tile.
         * @param name The string name.
         */
        public void setName(String name) { this.name = name; }
        /**
         * [Getter]: Get the name of the tile.
         * @return The name as a string.
         */
        public String getName() { return name; }
        /**
         * [Setter]: Set the count of the tile.
         * @param count The integer count.
         */
        public void setCount(int count) { this.count = count; }
        /**
         * [Getter]: Get the count of the tile.
         * @return The count as an integer.
         */
        public int getCount() { return count; }

        /**
         * [Info] Increases the tile count by 1.
         * [Preconditions]: None.
         * [Post-conditions]: The `count` variable is incremented by 1.
         * [Invariants]: None.
         */
        public void increaseCount() { }
    }


    /**
     * [Class] ButtonTiles
     * [Info] Class for Board tiles, inherits from ImageTiles and adds state and category (area).
     * [Invariants]:
     * - `category` is one of "TL", "TR", "BL", "BR", "M".
     */
    public class ButtonTiles extends InventoryTiles
    {
        /**
         * [Field]: The current state of the button (on = true, off = false).
         */
        private boolean state = false;
        /**
         * [Field]: The category (area) where the button is located.
         */
        private String category;

        /**
         * [Info] Constructor for the ButtonTiles class.
         * [Preconditions]: None.
         * [Post-conditions]: A ButtonTiles object is created.
         * [Invariants]: `state` is initialized to False.
         * @param name The name of the tile.
         * @param index The unique identifier.
         * @param category The category (area) of the tile.
         */
        public ButtonTiles(String name, int index, String category) { super(name, index); }

        /**
         * [Setter]: Set the category (area) of the button.
         * @param category The string category.
         */
        public void setCategory(String category) { this.category = category; }
        /**
         * [Getter]: Get the category (area) of the button.
         * @return The string category (area).
         */
        public String getCategory() { return category; }
        /**
         * [Getter]: Get the current state of the button.
         * @return The boolean value state.
         */
        public boolean getState() { return this.state; }
        /**
         * [Setter]: Set the current state of the button.
         * @param state The boolean value state.
         */
        public void setState(boolean state) { this.state = state; }
    }


    /**
     * [Class] CardTiles
     * [Info] Class for action cards, inherits from ButtonTiles (although not buttons in the board sense) and adds effect and availability.
     * [Invariants]:
     * - `category` is always "None".
     */
    public class CardTiles extends ButtonTiles
    {
        /**
         * [Field]: The effect (power) of the card as a whole string.
         */
        private String effect;
        /**
         * [Field]: The availability of the card.
         */
        private boolean available = true;

        /**
         * [Info] Constructor for the CardTiles class.
         * [Preconditions]: None.
         * [Post-conditions]: A CardTiles object is created.
         * [Invariants]: `available` is initialized to True.
         * @param name The name of the card.
         * @param index The unique identifier.
         * @param effect The card's effect description.
         */
        public CardTiles(String name, int index, String effect) { super(name, index, "None"); }

        /**
         * [Setter]: Set the effect (power) of a card.
         * @param effect The string effect.
         */
        public void setEffect(String effect) { this.effect = effect; }
        /**
         * [Getter]: Get the effect (power) of a card.
         * @return The string effect.
         */
        public String getEffect() { return this.effect; }

        /**
         * [Setter]: Sets the availability of the card.
         * @param available The availability value.
         */
        public void makeAvailable(boolean available) { this.available = available; }
        /**
         * [Getter]: Get the availability of the card.
         * @return The boolean available.
         */
        public boolean isAvailable() { return this.available; }
    }


    /**
     * [Class] TilesBag
     * [Info] Represents the bag from which tiles are drawn.
     * [Invariants]:
     * - The `tiles` list contains all tiles at the start of the game, as defined in the `TileTypes` Enum.
     */
    public class TilesBag
    {
        /**
         * [Field]: A list of all the tiles inside the bag.
         */
        private final List<TileTypes> tiles = new ArrayList<>();
        /**
         * [Getter]: Get all the tiles currently inside the bag.
         * @return The list of TileTypes objects.
         */
        public List<TileTypes> getTiles() { return tiles; }
        /**
         * [Field]: A random number used for drawing the first tiles from the bag at game start.
         */
        private Random random = new Random();

        /**
         * [Info] Constructor for the TilesBag class.
         * [Preconditions]: None.
         * [Post-conditions]: The `tiles` bag is filled with all tiles according to the quantities in `TileTypes`.
         * [Invariants]: None.
         */
        public TilesBag() { }

        /**
         * [Info] Draws $n$ random tiles from the bag and removes them.
         * [Preconditions]: None.
         * [Post-conditions]: A list of $n$ tiles (or fewer if the bag is empty) is returned, and they are removed from the bag.
         * [Invariants]: None.
         * @param n The number of tiles to be drawn.
         * @return List of drawn tiles.
         */
        public List<TileTypes> drawTiles(int n) { return null; }

        /**
         * [Info] Finds a tile in the bag based on the name.
         * [Preconditions]: None.
         * [Post-conditions]: The tile is returned if found, otherwise null.
         * [Invariants]: None.
         * @param name The name of the tile.
         * @return The `TileTypes` object or null.
         */
        public TileTypes findBagTileByName(String name) { return null; }
    }


    /**
     * [Class] Player
     * [Info] Represents a player, containing the name, ID, collection areas, collected tiles, and their cards.
     * [Invariants]:
     * - The `id` is unique.
     * - The `playertiles` list is always equal in size to the `Model.inventory` list and contains the player's counts.
     */
    public class Player
    {
        /**
         * [Field]: The name of the player.
         */
        private final String name = "";
        /**
         * [Field]: The id of the player (same as their number).
         */
        private final int id = -1;
        /**
         * [Field]: The area the player chose the current round.
         */
        private String currentArea;
        /**
         * [Field]: The area the player had chosen the last round.
         */
        private String lastArea;
        /**
         * [Field]: An array with all the tiles collected from the board each turn.
         */
        private int[] boardTilesCollected;
        /**
         * [Field]: All the tiles in the players inventory.
         */
        private final List<InventoryTiles> playertiles = new ArrayList<>();
        /**
         * [Field]: All the cards that the player has.
         */
        private final List<CardTiles> playercards = new ArrayList<>();

        /**
         * [Info] Constructor for the Player class.
         * [Preconditions]: None.
         * [Post-conditions]: The player is created, tiles (count=0) and cards (available) are initialized.
         * [Invariants]: As described in the class.
         * @param name The player's name.
         * @param id The player's ID.
         */
        public Player(String name, int id) { }

        /**
         * [Getter]: Get the name of the player.
         * @return The string name.
         */
        public String getName() { return name; }
        /**
         * [Getter]: Get the id (number) of the player.
         * @return The integer id (number).
         */
        public int getId() { return id; }
        /**
         * [Getter]: Get the current area the player chose.
         * @return The sting of the current area.
         */
        public String getCurrentArea() { return currentArea; }
        /**
         * [Setter]: Set the current area the player chose.
         * [Preconditions]: One of the board tiles buttons should have been clicked.
         * @param currentArea The string of the current area.
         */
        public void setCurrentArea(String currentArea) { this.currentArea = currentArea; }
        /**
         * [Setter]: Set the last area the player chose.
         * [Preconditions]: The end turn button should have been clicked.
         * @param lastArea The string of the last area.
         */
        public void setLastArea(String lastArea) { this.lastArea = lastArea; }
        /**
         * [Getter]: Get the last area the player chose.
         * @return The string of the last area.
         */
        public String getLastArea() { return lastArea; }
        /**
         * [Getter]: Get all the tiles the player has collected this round.
         * @return The array of the tiles collected.
         */
        public int[] getBoardTilesCollected() { return boardTilesCollected; }
        /**
         * [Setter]: Set the tiles the player has collected this round.
         * @param array The array containing the integers.
         */
        public void setBoardTilesCollected(int[] array) { this.boardTilesCollected = array; }
        /**
         * [Getter]: Get all the tiles the player has collected so far.
         * @return The list of the ImageTiles objects.
         */
        public List<InventoryTiles> getPlayertiles() { return playertiles; }
        /**
         * [Getter]: Get all the cards the player has.
         * @return The list of CardTiles objects.
         */
        public List<CardTiles> getPlayercards() { return playercards; }

        /**
         * [Info] Collects a tile from the Board. Applies the collection logic (with/without cards).
         * [Preconditions]: `Model.getPlayer()` must not be null. `index` must be a valid board tile index.
         * [Post-conditions]: The tile count on the Board is decreased by 1, the tile count in the player's inventory is increased by 1, and the collection counters (`boardTilesCollected`) and `currentArea` are updated.
         * [Invariants]: None.
         * @param index The index of the tile to be collected.
         */
        public void GetOneTileFromTheBoard(int index) { }

        /**
         * [Info] Helper method for collecting a tile (decrease board count, increase player tile count, increase boardTilesCollected).
         * [Preconditions]: `toChange` must not be null. `arrayIndex` must be a valid index of `boardTilesCollected`.
         * [Post-conditions]: The counts are updated.
         * [Invariants]: None.
         * @param toChange The board tile being changed.
         * @param arrayIndex The index of the `boardTilesCollected` array that corresponds to the collection.
         */
        private void HelperForGetOneTileFromTheBoard(ButtonTiles toChange, int arrayIndex) { }
    }


    /**
     * [Class] PlayTheGame
     * [Info] Contains the game flow logic, such as player switching and tile drawing.
     * [Invariants]:
     * - The `players` list has 4 players (when the game starts).
     */
    public class PlayTheGame
    {
        /**
         * [Field]: An array that stores all the different (4) players playing the game.
         */
        private Player[] players;
        /**
         * [Field]: The current player's index (or number).
         */
        private int currentPlayerIndex;

        /**
         * [Constructor]: The constructor of the class.
         */
        public PlayTheGame() { }

        /**
         * [Info] Selects the current player.
         * [Preconditions]: `cmd` must be "RandomPlayer" or "NextPlayer".
         * [Post-conditions]:
         * - If "RandomPlayer": 4 players are created, and the current player is selected randomly.
         * - If "NextPlayer": The collection counters are reset, it switches to the next player, and cards are reset.
         * [Invariants]: None.
         * @param cmd The selection command ("RandomPlayer" or "NextPlayer").
         */
        public void SelectPlayer(String cmd) { }

        /**
         * [Info] Draws tiles.
         * [Preconditions]: `cmd` must be "RandomDraw" or "PlayerDraw".
         * [Post-conditions]:
         * - If "RandomDraw": 4 random tiles (one from each area) are removed from the bag. Their names are returned.
         * - If "PlayerDraw": 4 random tiles are drawn from the bag. Landslide tiles are immediately added to the player. Their names are returned.
         * [Invariants]: None.
         * @param cmd The drawing command ("RandomDraw" or "PlayerDraw").
         * @return List of the names of the drawn tiles.
         */
        public List<String> DrawTiles(String cmd) { return null; }

        /**
         * [Info] Calculates the total points for each player at the end of the game.
         * [Preconditions]: The `players` list must have 4 players.
         * [Post-conditions]: An array with the total points for the 4 players is returned.
         * [Invariants]: None.
         * @return Array with the total points.
         */
        public int[] CalculatePlayerPointsAtGameOver() { return null; }

        /**
         * [Info] Adds the statue bonus points (Caryatids or Sphinxes).
         * [Preconditions]: The arrays `totalPoints` and `counts` must have size 4.
         * [Post-conditions]: The bonus points (6 for the maximum, 3 for the intermediate, 0 for the minimum) are added to the total points.
         * [Invariants]: None.
         * @param totalPoints The array with the players' total points.
         * @param counts The array with the counts of the specific statue (Caryatid or Sphinx).
         */
        private void addStatuePoints(int[] totalPoints, int[] counts) { }
    }


    /**
     * [Class] Methods
     * [Info] Class containing helper methods for accessing tile information based on the index.
     */
    public class Methods
    {
        /** [Constructor] The constructor of the class ... */
        public Methods() {}

        /**
         * [Info] Searches for a tile in all lists (cards, board, inventory) based on the index.
         * [Preconditions]: None.
         * [Post-conditions]: The tile is returned if found, otherwise null.
         * [Invariants]: None.
         * @param index The index of the tile.
         * @return The `ImageTiles` object or null.
         */
        private InventoryTiles SearchEverywhereForIndex(int index) { return null; }

        /**
         * [Info] Gets the name of a tile based on the index.
         * [Preconditions]: `index` must correspond to a valid tile.
         * [Post-conditions]: The name is returned.
         * [Invariants]: None.
         * @param index The index of the tile.
         * @return The name of the tile or null.
         */
        public String getName(int index) { return null; }

        /**
         * [Info] Gets the count of a tile based on the index.
         * [Preconditions]: `index` must correspond to a valid tile.
         * [Post-conditions]: The count is returned.
         * [Invariants]: None.
         * @param index The index of the tile.
         * @return The count of the tile or -1.
         */
        public int getCount(int index) { return -1; }

        /**
         * [Info] Increases the count of the tiles corresponding to the given indexes by 1.
         * [Preconditions]: The `indexes` list must contain valid indexes.
         * [Post-conditions]: The count of the corresponding tiles is increased by 1.
         * [Invariants]: None.
         * @param indexes The list of tile indexes.
         */
        public void ChangeInventoryCounts(List<Integer> indexes) { }
    }


    /**
     * [Extra]: Temporary method that saves the current game at the current state ...
     * [Preconditions]: ???
     * [Post-conditions]: ???
     * [Invariants]: ???
     * [Parameters]: ???
     * [Returns]: ???
     * [Info]:
     * This method should be called from the controller when the corresponding button from view is pressed (at game screen).
     * This method will probably write all the (model) data into a (.txt) file using "java.io.File;" and "java.util.Scanner;".
     * [ ... ]
     */
    public static void SaveStateAndExit ( ) { }

    /**
     * [Extra]: Temporary method that restores a specific game and its state ...
     * [Preconditions]: ???
     * [Post-conditions]: ???
     * [Invariants]: ???
     * [Parameters]: ???
     * [Returns]: ???
     * [Info]:
     * This method should be called from the controller when the corresponding button from view is pressed (at main menu screen).
     * This method will probably read all the (model) data from a (.txt) file using "java.io.File;" and "java.util.Scanner;".
     * [ ... ]
     */
    public static void ReadFileAndContinueGame ( ) { }

}

/* The End */
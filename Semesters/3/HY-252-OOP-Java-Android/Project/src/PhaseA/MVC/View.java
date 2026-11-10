/* UOC - CSD - HY252 - Project Phase 1 - Java - MVC: View - RIP - csd5748 */

package PhaseA.MVC;

import javax.swing.*;

import java.util.List;

import java.util.ArrayList;

import java.awt.event.ActionListener;

public class View extends JFrame
{
    private static final int height = -1;

    private static final int width = -1;

    private static final int sizes = -1;

    public int getHeight () { return height; }

    public int getWidth () { return width; }

    public int getSizes () { return sizes; }

    static List<CardTilesView> cards = new ArrayList<>();

    static List<ButtonTilesView> board = new ArrayList<>();

    static List<InventoryTilesView> inventory = new ArrayList<>();

    static List<MenuButtons> buttons = new ArrayList<>();

    public JLabel playerlabel;

    public View () { }

    public void MainMenu () { }

    public void GameWindow () { }

    public void GameOver () { }

    public class Methods
    {
        public Methods () {}

        public void UpdateInventoryCountsForPlayer () {}

        public void FindAndUpdateCounts (List<String> names) {}

        public List<Integer> GetLabelIndexFromName (List<String> names) {return null;}
    }

    public static class MenuButtons
    {
        public enum Names { }

        private Names name;

        private final int width = -1;

        private final int height = -1;

        private int x;

        private int y;

        private JButton button;

        private ActionListener actionListener;

        public MenuButtons (Names name, int x, int y) { }

        public void setName (Names name) { this.name = name; }

        public Names getName () { return this.name; }

        public int getWidth () { return this.width; }

        public int getHeight () { return this.height; }

        public void setX (int x) { this.x = x; }

        public int getX () { return this.x; }

        public void setY (int y) { this.y = y; }

        public int getY () { return this.y; }

        public JButton getButton () { return this.button; }

        public void setButton (JButton button) { this.button = button; }

        protected void update (JPanel pane) { }

        public void setAction (ActionListener listener) { }
    }

    public abstract static class LabelWithImageAndText
    {
        private int index;

        private int x;

        private int y;

        private int height;

        private int width;

        private JLabel imgLabel;

        private JLabel countLabel;

        public LabelWithImageAndText (int index, int x, int y) { }

        public void positionCountLabel (int index, int from, int to) { }

        protected abstract void createImageComponent ();

        private void initializeComponents () { }

        public void attach (JPanel panel) { }

        public void update () { }

        public String getName () { return null; }

        public int getCount () { return -1; }

        protected void Extra () { }
    }

    public class InventoryTilesView extends LabelWithImageAndText
    {
        public InventoryTilesView (int index, int x, int y) { super(index, x, y); }

        @Override protected void createImageComponent () { }

        @Override public void update() { }
    }

    public class ButtonTilesView extends InventoryTilesView
    {
        private JButton button;

        public JButton getButton () { return button; }

        public void setButton (JButton btn) { this.button = btn; }

        public ButtonTilesView (int index, int x, int y) { super(index, x, y); }

        @Override protected void createImageComponent () { }

        public void attach (JLayeredPane pane) { }

        public void setAction (ActionListener listener) {}

        @Override public void update () {}

        public void changeLabelCount () { }
    }

    public class CardTilesView extends ButtonTilesView
    {
        public Boolean getAvailable () { return null; }

        public CardTilesView (int index, int x, int y) { super(index, x, y); }

        @Override public void attach (JPanel panel) {}

        @Override public void update () {}
    }
}

/* The End */
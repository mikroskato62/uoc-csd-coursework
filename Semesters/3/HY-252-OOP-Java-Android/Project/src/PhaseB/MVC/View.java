// [!] UOC - CSD - HY252 - Java (MVC) - Project Phase A & B - Amphipolis Board Game - RIP - csd5748 - :)
// [!] Please read the ReadMe.md file !

package PhaseB.MVC;

import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.Arrays;
import java.util.Objects;
import java.util.ArrayList;
import javax.swing.border.LineBorder;

public class View extends JFrame
{
    public View () {}

    private static final int height = 795;
    private static final int width = 1515;
    private static final int sizes = 750;
    public int getHeight() { return height; } public int getWidth() { return width; } public int getSizes() { return sizes; }

    private static final List<CardTilesView> cards = Arrays.asList(
            new CardTilesView("archaeologist", 1, 0, 0, "Archaeologist: you may take two tiles from any area except the selected one ..."),
            new CardTilesView("assistant", 2, 150, 0, "Assistant: you may take only one available tile from any area on the board ..."),
            new CardTilesView("coder", 3, 300, 0, "Coder: you may take exactly two tiles from your selected area in your last round ..."),
            new CardTilesView("digger", 4, 450, 0, "Digger: you may take max two tiles from the same area you chose this round ..."),
            new CardTilesView("professor", 5, 600, 0, "Professor: you may take one tile from each other area than your selected one ...")
    );
    public List<CardTilesView> getCardsView() { return cards; }

    private static final List<ButtonTilesView> board = Arrays.asList(
            new ButtonTilesView("landslide", 6, 348, 410),
            new ButtonTilesView("mosaic_red", 7, 40, 40),
            new ButtonTilesView("mosaic_green", 8, 105, 40),
            new ButtonTilesView("mosaic_yellow", 9, 170, 40),
            new ButtonTilesView("caryatid", 10, 562, 40),
            new ButtonTilesView("sphinx", 11, 626, 40),
            new ButtonTilesView("skeleton_small_top", 12, 562, 567),
            new ButtonTilesView("skeleton_big_top", 13, 626, 567),
            new ButtonTilesView("amphora_brown", 14, 42, 567),
            new ButtonTilesView("amphora_purple", 15, 107, 567),
            new ButtonTilesView("amphora_yellow", 16, 172, 567),
            new ButtonTilesView("amphora_blue", 17, 107, 660),
            new ButtonTilesView("amphora_green", 18, 172, 660),
            new ButtonTilesView("amphora_red", 19, 42, 660),
            new ButtonTilesView("skeleton_small_bottom", 20, 562, 660),
            new ButtonTilesView("skeleton_big_bottom", 21, 626, 660)
    );
    public List<ButtonTilesView> getBoardView() { return board; }

    private static final List<InventoryTilesView> inventory = Arrays.asList(
            new InventoryTilesView("landslide", 22, 125, 488),
            new InventoryTilesView("mosaic_red", 23, 190, 488),
            new InventoryTilesView("mosaic_green", 24, 255, 488),
            new InventoryTilesView("mosaic_yellow", 25, 320, 488),
            new InventoryTilesView("caryatid", 26, 385, 488),
            new InventoryTilesView("sphinx", 27, 450, 488),
            new InventoryTilesView("skeleton_small_top", 28, 515, 488),
            new InventoryTilesView("skeleton_big_top", 29, 580, 488),
            new InventoryTilesView("amphora_brown", 30, 125, 588),
            new InventoryTilesView("amphora_purple", 31, 190, 588),
            new InventoryTilesView("amphora_yellow", 32, 255, 588),
            new InventoryTilesView("amphora_blue", 33, 385, 588),
            new InventoryTilesView("amphora_green", 34, 450, 588),
            new InventoryTilesView("amphora_red", 35, 320, 588),
            new InventoryTilesView("skeleton_small_bottom", 36, 515, 588),
            new InventoryTilesView("skeleton_big_bottom", 37, 580, 588)
    );
    public List<InventoryTilesView> getInventoryView() { return inventory; }

    private static final List<MenuButtons> buttons = Arrays.asList(
            new MenuButtons("Multiplayer", 875, 185, 300, 100),
            new MenuButtons("Singleplayer", 875, 325, 300, 100),
            new MenuButtons("Load & Continue", 875, 465, 300, 100),
            new MenuButtons("Draw Tiles", 142, 387, 150, 50),
            new MenuButtons("End Turn", 305, 387, 150, 50),
            new MenuButtons("Save & Exit", 468, 387, 150, 50),
            new MenuButtons("Main Menu ...", 874, 382, 225, 75),
            new MenuButtons("?", -1, -1, -1, -1),
            new MenuButtons("Go Back ...", 632, 642, 225, 75)
    );
    public MenuButtons getButtons(int index) { return buttons.get(index); }
    public JButton getButton(int index) { return buttons.get(index).getButton(); }
    public JButton getButton(String buttonName) { for (MenuButtons mb : buttons) if (mb.getName().equalsIgnoreCase(buttonName)) return mb.getButton(); return null; }

    private static final List<GameLabels> labels = Arrays.asList(
            new GameLabels("logo", 187, 125, -1, -1, null),
            new GameLabels("player_label", 270, 317, 300, 35, new Font("Calibre", Font.BOLD, 22)),
            new GameLabels("info_label", 5, 675, 650, 75, new Font("Calibre", Font.BOLD, 15)),
            new GameLabels("bag_label", 707, 248, 50, 500, new Font("Arial", Font.PLAIN, 12)),
            new GameLabels("background", 0, -1, -1, -1, null),
            new GameLabels("help", 0, 0, -1, -1, null),
            new GameLabels("tile_back", 187, 115, -1, -1, null),
            new GameLabels("winner_label", 875, 242, 1515, 100, new Font("Calibre", Font.BOLD, 22)),
            new GameLabels("score_label", 875, 292, 1515, 100, new Font("Calibre", Font.BOLD, 22))
    );
    public GameLabels getLabels(int index) { return labels.get(index); }

    public void MainMenu()
    {
        getContentPane().removeAll();
        setTitle("★ RIP - @rapscallion62 - csd5748 ★ Help: ReadMe.txt ★ UoC-CSD: HY252 (Java) ★ Project: \"Amphipolis\" (Greek) Board Game ★ Screen: Main Menu ...");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); setSize(this.getWidth(), this.getHeight()); setLocationRelativeTo(null); setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null); panel.setBounds(0, 0, this.getWidth(), this.getHeight());

        labels.get(0).addTo(panel);
        for (int i = 0; i < 3 && i < buttons.size(); i++) { buttons.get(i).Update(panel); } buttons.get(7).Update(panel);

        add(panel); setVisible(true);
    }

    public void GameWindow(String gameMode)
    {
        getContentPane().removeAll();
        setTitle("★ RIP - @rapscallion62 - csd5748 ★ Help: ReadMe.txt ★ UoC-CSD: HY252 (Java) ★ Project: \"Amphipolis\" (Greek) Board Game ★ Screen: " + gameMode + " Game ...");
        setSize(getWidth(), getHeight()); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); setLocationRelativeTo(null); setResizable(false); setLayout(null);

        JLayeredPane gameboard = new JLayeredPane();
        gameboard.setBounds(0, 0, this.getSizes(), this.getSizes()); gameboard.setLayout(null);
        JPanel sidebar = new JPanel();
        sidebar.setBounds(this.getSizes() + 1, 0, this.getSizes(), this.getSizes()); sidebar.setLayout(null);

        int tempcounter1 = -1, tempcounter2 = -1;
        for (InventoryTilesView t : inventory) { t.update(); t.positionCountLabel(++tempcounter1, 0, 7); t.attach(sidebar); }
        for (ButtonTilesView t : board) { t.update(); t.positionCountLabel(++tempcounter2, 1, 10); t.attach(gameboard); }
        for (CardTilesView c : cards) { c.update(); c.attach(sidebar); sidebar.add(c.getCountLabel()); }
        for (int i = 3; i < buttons.size() - 1; i++) { MenuButtons b = buttons.get(i); b.Update(sidebar); } buttons.get(7).Update(sidebar); buttons.get(7).getButton().setBounds(701, 703, 50, 50);
        for (int i = 1; i < 4; i++) { labels.get(i).addTo(sidebar); } labels.get(4).addTo(gameboard, JLayeredPane.DEFAULT_LAYER);
        labels.get(2).getLabel().setText("<html>" + "[+] Tip: by hovering over a card you can see its effect here ...<br>" + "[?] Note: if you need any help, please left-click the question-mark on the bottom-right ...<br>" + "[!!] Warning: if this window closes while not in the main-menu, all your progress will be lost ..." + "</html>");

        add(gameboard); add(sidebar); setVisible(true);
    }

    public void GameOver(String[] winner)
    {
        getContentPane().removeAll();
        setTitle("★ RIP - @rapscallion62 - csd5748 ★ Help: ReadMe.txt ★ UoC-CSD: HY252 (Java) ★ Project: \"Amphipolis\" (Greek) Board Game ★ Screen: Game Over ...");
        setSize(getWidth(), getHeight()); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); setLocationRelativeTo(null); setResizable(false); setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, getWidth(), getHeight()); panel.setLayout(null);

        labels.get(6).addTo(panel);
        labels.get(7).addTo(panel); labels.get(7).getLabel().setText("[ Winner ]   " + winner[0]);
        labels.get(8).addTo(panel); labels.get(8).getLabel().setText("[  Score  ]   " + winner[1]);
        buttons.get(6).Update(panel); buttons.get(6).getButton().setContentAreaFilled(false);
        buttons.get(7).Update(panel);

        add(panel); setVisible(true);
    }

    public void HelpScreen()
    {
        getContentPane().removeAll();
        setTitle("★ RIP - @rapscallion62 - csd5748 ★ Help: ReadMe.txt ★ UoC-CSD: HY252 (Java) ★ Project: \"Amphipolis\" (Greek) Board Game ★ Screen: Info/Help ...");
        setSize(getWidth(), getHeight()); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); setLocationRelativeTo(null); setResizable(false); setLayout(null);

        JLayeredPane panel = new JLayeredPane();
        panel.setBounds(0, 0, getWidth(), getHeight()); panel.setLayout(null);

        labels.get(5).addTo(panel, JLayeredPane.DEFAULT_LAYER);
        buttons.get(8).Update(panel);

        add(panel); setVisible(true);
    }

    public static class GameLabels
    {
        private final String name;
        private final int x, y;
        private final int w, h;
        private final Font font;
        private JLabel label;
        public JLabel getLabel() { return this.label; }

        public GameLabels(String name, int x, int y, int w, int h, Font font)
        { this.name = name; this.x = x; this.y = y; this.w = w; this.h = h; this.font = font; }

        public void ChangeText(String newText) { this.label.setText(newText); }
        public void addTo(JPanel panel) { createLabel(); panel.add(this.label); }
        public void addTo(JLayeredPane pane, Integer layer) { createLabel(); pane.add(this.label, layer); }
        private void createLabel()
        {
            this.label = new JLabel();
            this.label.setBounds(x, y, w, h);
            if (font == null)
            {
                java.net.URL imgUrl = getClass().getResource("/assets/img/" + name + ".png");
                ImageIcon icon = (imgUrl != null) ? new ImageIcon(imgUrl) : null;
                if (icon == null) return;
                this.label.setIcon(icon);
                if (w == -1 || h == -1) this.label.setSize(icon.getIconWidth(), icon.getIconHeight());
            }
            else
            {
                this.label.setText("");
                this.label.setFont(font);
                if (name.equals("InfoLabel")) this.label.setVerticalAlignment(SwingConstants.TOP);
            }
        }
    }

    public static class MenuButtons
    {
        private final String name;
        private final int x, y;
        private final int w, h;
        private JButton button;

        public String getName() { return this.name; } public JButton getButton() { return this.button; }

        public MenuButtons(String name, int x, int y, int w, int h) { this.name = name; this.x = x; this.y = y; this.w = w; this.h = h; }

        public void ChangeText(String newText) { this.button.setText(newText); }
        protected void Update(JPanel panel) { this.UpdateHelper(); panel.add(getButton()); }
        protected void Update(JLayeredPane pane) { this.UpdateHelper(); pane.add(getButton(), JLayeredPane.PALETTE_LAYER); this.getButton().setContentAreaFilled(false); }
        private void UpdateHelper()
        {
            JButton button = new JButton();
            button.setEnabled(true); button.setFocusPainted(false);
            button.setText(this.name);
            button.setFont(new Font("Calibre", Font.BOLD, 18)); button.setBorder(new LineBorder(Color.BLACK, 2, true));
            button.setBounds(this.x, this.y, this.w, this.h);
            this.button = button;
            if (this.name.equals("?")) { button.setBorder(null); button.setBounds(1452, 703, 50, 50); button.setOpaque(false); button.setContentAreaFilled(false); button.setFont(new Font("Arial", Font.BOLD, 15)); }
        }
    }

    public abstract static class LabelWithImageAndText
    {
        private String name;
        private int index;
        private int x, y;
        private static int height = 50, width = 50;
        private JLabel imgLabel, countLabel;

        public LabelWithImageAndText(String name, int index, int x, int y)
        { setName(name); setIndex(index); setX(x); setY(y); this.countLabel = new JLabel("x0"); this.imgLabel = new JLabel(); initializeComponents(); update(); }

        public void setName(String name) { this.name = name; } public String getName() { return this.name; }
        public void setIndex(int index) {this.index = index; } public int getIndex() { return this.index; }
        public void setX(int x) { this.x = x; } public int getX() { return this.x; }
        public void setY(int y) { this.y = y; } public int getY() { return this.y; }
        public void setHeight(int height) { LabelWithImageAndText.height = height; } public int getHeight() { return LabelWithImageAndText.height; }
        public void setWidth(int width) { LabelWithImageAndText.width = width; } public int getWidth() { return LabelWithImageAndText.width; }
        public JLabel getImgLabel() { return imgLabel; } public void setImgLabel(JLabel lbl) { this.imgLabel = lbl; }
        public JLabel getCountLabel() { return countLabel; } public void setCountLabel(JLabel lbl) { this.countLabel = lbl; }

        protected void positionCountLabel(int index, int from, int to)
        {
            if (index >= from && index <= to) getCountLabel().setBounds((getX() + (getWidth() / 10) - 5), (getY() + (getHeight() / 2) + 9), getWidth(), getHeight());
            else getCountLabel().setBounds((getX() + (getWidth() / 10) - 5), (getY() - (getHeight() / 2) - 10), getWidth(), getHeight());
        }

        protected abstract void createImageComponent();
        protected void initializeComponents() { createImageComponent(); getCountLabel().setHorizontalAlignment(SwingConstants.CENTER); }
        protected void attach(JPanel panel) { panel.add(getImgLabel()); panel.add(getCountLabel()); }

        public void update()
        {
            java.net.URL imgUrl = getClass().getResource("/assets/img/" + this.getName() + ".png");
            ImageIcon icon = (imgUrl != null) ? new ImageIcon(imgUrl) : null;
            if (this instanceof ButtonTilesView) { ButtonTilesView btn = (ButtonTilesView) this; if (btn.getButton() != null) btn.getButton().setIcon(icon); }
            else if (getImgLabel() != null) { getImgLabel().setIcon(icon); }
            getCountLabel().setText("x" + this.getCount());
        }

        public int getCount()
        {
            String text = this.countLabel.getText();
            if (text == null || text.isEmpty()) { return 0; }
            try
            {
                if (text.contains("/")) { return Integer.parseInt(text.split("/")[0].trim()); }
                return Integer.parseInt(text.replace("x", "").trim());
            }
            catch (NumberFormatException e) { return -1; }
        }
    }

    public static class InventoryTilesView extends LabelWithImageAndText
    {
        public InventoryTilesView(String name, int index, int x, int y) { super(name, index, x, y); }

        @Override protected void createImageComponent()
        {
            java.net.URL imgUrl = getClass().getResource("/assets/img/" + this.getName() + ".png");
            ImageIcon icon = (imgUrl != null) ? new ImageIcon(imgUrl) : null;
            setImgLabel(new JLabel(icon));
            getImgLabel().setBounds(getX(), getY(), getWidth(), getHeight()); setCountLabel(new JLabel());
        }
        public void UpdateInventoryCount(int count) { this.getCountLabel().setText("x" + count); }
    }

    public static class ButtonTilesView extends LabelWithImageAndText
    {
        private JButton button;
        public JButton getButton() { return button; } public void setButton(JButton btn) { this.button = btn; }

        public ButtonTilesView(String name, int index, int x, int y) { super(name, index, x, y); }

        @Override protected void createImageComponent() { JButton button = new JButton(); button.setEnabled(true); button.setVisible(true); button.setFocusPainted(false); button.setBounds(getX(), getY(), getWidth(), getHeight()); setButton(button); }
        public void attach(JLayeredPane pane) { pane.add(getButton(), JLayeredPane.PALETTE_LAYER); pane.add(getCountLabel(), JLayeredPane.MODAL_LAYER); }
        public void changeLabelCount(int newCount)
        {
            if (Objects.equals(this.getName(), "landslide")) this.getCountLabel().setText(newCount <= 9 ? " " + newCount + " / 16" : newCount + " / 16");
            else this.getCountLabel().setText("x" + newCount);
        }
    }

    public static class CardTilesView extends ButtonTilesView
    {
        private String effect;
        public void setEffect(String effect) { this.effect = effect; } public String getEffect() { return this.effect; }

        public CardTilesView(String name, int index, int x, int y, String effect) { super(name, index, x, y); setEffect(effect); }

        public void ChangeText(String newText) { this.getCountLabel().setText(newText); }
        public void positionCountLabel() { getCountLabel().setBounds(this.getX() + (getWidth() * 3) / 2 - 32, this.getY() + (getHeight() * 5) + 12, getWidth() * 2, 10); }
        @Override public void attach(JPanel panel) { panel.add(getButton()); }
        @Override public void update()
        {
            java.net.URL imgUrl = getClass().getResource("/assets/img/" + this.getName() + ".png");
            ImageIcon original = (imgUrl != null) ? new ImageIcon(imgUrl) : null;
            getButton().setIcon(original); getButton().setBounds(getX(), getY(), getWidth()*3, getHeight()*5); setCountLabel(new JLabel()); positionCountLabel();
        }
    }

    public List<Integer> GetLabelIndexFromName(List<String> names)
    {
        List<Integer> temp = new ArrayList<>();
        for (String name : names)
        {
            if (name == null) continue;
            ButtonTilesView button = null;
            for (ButtonTilesView tile : board) if (tile != null && tile.getName() != null && tile.getName().equals(name)) button = tile;
            if (button != null) temp.add(button.getIndex());
        }
        return temp;
    }

    public int[] GetAllCountsFromTheBoard()
    {
        int[] all = new int[16];
        for (int i = 0; i < board.size(); i++) all[i] = board.get(i).getCount();
        return all;
    }

    public void ResetView() { for (ButtonTilesView tile : board) tile.changeLabelCount(0); }
}

// [!] The End ...
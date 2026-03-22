package ui;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

import com.formdev.flatlaf.IntelliJTheme;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

// Main application window managing login, save/load, and the tabbed UI layout
@ExcludeFromJacocoGeneratedReport
public class MediaLibraryGUI extends JFrame {

    public static final Font HELDANE = loadFont("resources/fonts/TestHeldaneDisplay-Medium-BF6621e298bc880.otf", 22);
    public static final Font HELDANE_SUB = loadFont(
            "resources/fonts/TestHeldaneDisplay-Medium-BF6621e298bc880.otf", 15);
    public static final Font REGULAR_BOLD = loadFont("resources/fonts/Inter-Bold.otf", 13);
    public static final Font REGULAR = loadFont("resources/fonts/Inter-Medium.otf", 12);
    private static final String SAVE_DIR = "./data/";

    private MediaLibrary mediaLibrary;
    private String username;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private JPanel viewPanel;
    private JPanel statsPanel;
    private JButton addMediaButton;

    // EFFECTS: constructs the GUI and initializes the main window
    public MediaLibraryGUI() {
        super();
        mediaLibrary = new MediaLibrary();
        login();
        init();
    }

    // MODIFIES: this
    // EFFECTS: prompts user for username and prompts to load if file exists
    private void login() {
        promptUsername();
        String path = SAVE_DIR + username + "Library.json";
        jsonReader = new JsonReader(path);
        jsonWriter = new JsonWriter(path);

        // Prompt to load if save file exists
        File saveFile = new File(path);
        if (saveFile.exists()) {
            loadSavedLibrary();
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up the main window with all child panels
    private void init() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setPromptSaveLibrary();
        setSize(800, 600);
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JPanel root = new JPanel(new GridBagLayout());
        JPanel welcomePanel = createWelcomePanel();
        viewPanel = new ViewMediaPanel(this);
        statsPanel = new StatsPanel(this);
        createAddMediaButton();
        JTabbedPane tabbedPane = createTabbedPane();

        placeComponents(root, welcomePanel, tabbedPane, addMediaButton, gbc);
        setContentPane(root);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: shows an input dialog to get the username;
    // sets username to "guest" if input is empty or cancelled
    private void promptUsername() {
        String input = JOptionPane.showInputDialog(
                null,
                "Enter your username:",
                "MediaLibrary",
                JOptionPane.PLAIN_MESSAGE);

        if (input == null || input.trim().isEmpty()) {
            username = "guest";
        } else {
            username = input.trim();
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to load saved library and reads
    // mediaLibrary from file; shows error dialog if read fails
    private void loadSavedLibrary() {
        int choice = JOptionPane.showConfirmDialog(
                null,
                "Welcome back, " + username + "! Load your saved library?",
                "Load Library",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            try {
                mediaLibrary = jsonReader.read();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        "Could not load library: " + e.getMessage(),
                        "Load Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts the user to save before quitting; saves and exits if YES,
    // exits without saving if NO, keeps app open if CANCEL
    private void setPromptSaveLibrary() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                        MediaLibraryGUI.this,
                        "Save your library before quitting?",
                        "Save Library",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (choice == JOptionPane.YES_OPTION) {
                    saveLibrary();
                    System.exit(0);
                } else if (choice == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    // EFFECTS: returns a panel with welcome message
    private JPanel createWelcomePanel() {
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setOpaque(false);

        JLabel iconLabel = new JLabel();
        ImageIcon icon = createImageIcon("resources/icons/image-removebg-preview.png");
        Image scaledImage = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        iconLabel.setIcon(new ImageIcon(scaledImage));
        iconLabel.setAlignmentX(LEFT_ALIGNMENT);

        JLabel welcome = new JLabel();
        welcome.setText("Welcome Home, " + username);
        welcome.setFont(HELDANE.deriveFont(25f));
        welcome.setAlignmentX(LEFT_ALIGNMENT);
        welcome.setForeground(new Color(0xF075AE));
        welcomePanel.add(iconLabel);
        welcomePanel.add(welcome);
        return welcomePanel;
    }

    // MODIFIES: this
    // EFFECTS: on click, opens AddMediaDialog and adds returned media item to
    // mediaLibrary if not null
    private void createAddMediaButton() {
        addMediaButton = new JButton();
        addMediaButton.putClientProperty("JButton.buttonType", "roundRect");
        addMediaButton.setText("+ Add Media");
        addMediaButton.setBackground(new Color(0xFCDEE9));
        addMediaButton.setBorderPainted(false);
        addMediaButton.addActionListener(e -> {
            AddMediaDialog dialog = new AddMediaDialog(this);
            dialog.setLocationRelativeTo(viewPanel);
            dialog.setVisible(true);
            Media newItem = dialog.getResult();
            if (newItem != null) {
                mediaLibrary.addEntry(newItem);
                refreshAll();
            }
        });
    }

    // EFFECTS: returns a JTabbedPane containing the Library and Stats tabs
    private JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setUI(new RoundedTabbedPaneUI());
        tabbedPane.setBounds(10, 10, 400, 400);
        tabbedPane.setFont(REGULAR_BOLD);
        tabbedPane.setTabPlacement(JTabbedPane.TOP);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.add("Library", viewPanel);
        tabbedPane.add("Stats", statsPanel);
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedComponent() == statsPanel) {
                ((StatsPanel) statsPanel).refresh();
            }
        });
        return tabbedPane;
    }

    // MODIFIES: root, gbc
    // EFFECTS: places welcomePanel, tabbedPane, and addMediaButton into root
    // using the given GridBagConstraints
    private void placeComponents(JPanel root, JPanel welcomePanel,
            JTabbedPane tabbedPane, JButton addMediaButton, GridBagConstraints gbc) {
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(20, 20, 0, 0);
        root.add(welcomePanel, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(90, 20, 20, 20);
        root.add(tabbedPane, gbc);

        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(45, 0, 0, 30);
        root.add(addMediaButton, gbc);
    }

    public MediaLibrary getMediaLibrary() {
        return mediaLibrary;
    }

    // MODIFIES: this
    // EFFECTS: refreshes all panels with current library data
    public void refreshAll() {
        ((ViewMediaPanel) viewPanel).refresh();
        ((StatsPanel) statsPanel).refresh();
    }

    // EFFECTS: returns an ImageIcon, or null if the path was invalid
    public static ImageIcon createImageIcon(String path) {
        File file = new File(path);
        if (file.exists()) {
            return new ImageIcon(file.getAbsolutePath());
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    // EFFECTS: returns a message label with an icon and
    // "Nothing to show here..."
    public static JLabel nothingMessage() {
        JLabel empty = new JLabel("Nothing to show here...", JLabel.CENTER);
        Image scaled = MediaLibraryGUI.createImageIcon("resources/icons/sleepy.png").getImage()
                .getScaledInstance(85, 80, Image.SCALE_SMOOTH);
        empty.setIcon(new ImageIcon(scaled));
        empty.setHorizontalTextPosition(JLabel.CENTER);
        empty.setVerticalTextPosition(JLabel.BOTTOM);
        empty.setFont(MediaLibraryGUI.REGULAR_BOLD);
        empty.setForeground(new Color(0xFFFFFF));
        return empty;
    }

    // EFFECTS: loads and returns a font from path at given size;
    // returns a SansSerif font if loading fails
    private static Font loadFont(String path, float size) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT,
                    new File(path));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
            return font.deriveFont(size);
        } catch (Exception e) {
            return new Font("SansSerif", Font.PLAIN, (int) size);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates SAVE_DIR if it does not exist, then writes mediaLibrary
    // to the user's JSON save file; shows error dialog if write fails
    private void saveLibrary() {
        try {
            new File(SAVE_DIR).mkdirs();
            jsonWriter.open();
            jsonWriter.write(mediaLibrary);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this,
                    "Could not save library: " + e.getMessage(),
                    "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: UIManager
    // EFFECTS: sets FlatLaf UIManager properties for tabs, buttons, and fonts
    private static void setupUIManager() {
        Color bg = UIManager.getColor("Panel.background");
        UIManager.put("TabbedPane.selectedBackground", bg);
        UIManager.put("TabbedPane.cardTabSelectionHeight", 0);
        UIManager.put("TabbedPane.underlineColor", bg);
        UIManager.put("TabbedPane.tabSelectionHeight", 0);
        UIManager.put("TabbedPane.hoverColor", bg);
        UIManager.put("TabbedPane.contentAreaColor", bg);
        UIManager.put("Button.foreground", new Color(0x161015));
        UIManager.put("Button.default.foreground", new Color(0x161015));
        UIManager.put("Button.font", MediaLibraryGUI.REGULAR);
        UIManager.put("Button.default.font", MediaLibraryGUI.REGULAR);
        UIManager.put("OptionPane.buttonFont", MediaLibraryGUI.REGULAR);
        UIManager.put("OptionPane.messageFont", MediaLibraryGUI.REGULAR);
        UIManager.put("OptionPane.font", MediaLibraryGUI.REGULAR);
        UIManager.put("TextField.font", MediaLibraryGUI.REGULAR);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                IntelliJTheme.setup(new java.io.FileInputStream("resources/theme/cute_pink_light.theme.json"));
                setupUIManager();
            } catch (Exception e) {
                e.printStackTrace();
            }
            new MediaLibraryGUI();
        });
    }
}

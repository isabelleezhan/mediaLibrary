package ui;

import java.awt.*;
import javax.swing.*;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.MediaLibrary;

// Main application window managing login, save/load, and the tabbed UI layout
@ExcludeFromJacocoGeneratedReport
public class MediaLibraryGUI extends JFrame {

    // EFFECTS: constructs the GUI and initializes the main window
    public MediaLibraryGUI() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: prompts user for username and prompts to load if file exists
    private void login() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: sets up the main window with all child panels
    private void init() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: shows an input dialog to get the username;
    // sets username to "guest" if input is empty or cancelled
    private void promptUsername() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: prompts user to load saved library and reads
    // mediaLibrary from file; shows error dialog if read fails
    private void loadSavedLibrary() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: prompts the user to save before quitting; saves and exits if YES,
    // exits without saving if NO, keeps app open if CANCEL
    private void setPromptSaveLibrary() {
        // stub
    }

    // EFFECTS: returns a panel with welcome message
    private JPanel createWelcomePanel() {
        return new JPanel(); // stub
    }

    // MODIFIES: this
    // EFFECTS: on click, opens AddMediaDialog and adds returned media item to
    // mediaLibrary if not null
    private void createAddMediaButton() {
        // stub
    }

    // EFFECTS: returns a JTabbedPane containing the Library and Stats tabs
    private JTabbedPane createTabbedPane() {
        return new JTabbedPane(); // stub
    }

    // MODIFIES: root, gbc
    // EFFECTS: places welcomePanel, tabbedPane, and addMediaButton into root
    // using the given GridBagConstraints
    private void placeComponents(JPanel root, JPanel welcomePanel,
            JTabbedPane tabbedPane, JButton addMediaButton, GridBagConstraints gbc) {
        // stub
    }

    public MediaLibrary getMediaLibrary() {
        return new MediaLibrary(); // stub
    }

    // MODIFIES: this
    // EFFECTS: refreshes all panels with current library data
    public void refreshAll() {
        // stub
    }

    // EFFECTS: returns an ImageIcon, or null if the path was invalid
    public static ImageIcon createImageIcon(String path) {
        return new ImageIcon(); // stub
    }

    // EFFECTS: returns a message label with an icon and
    // "Nothing to show here..."
    public static JLabel nothingMessage() {
        return new JLabel(); // stub
    }

    // EFFECTS: loads and returns a font from path at given size;
    // returns a SansSerif font if loading fails
    private static Font loadFont(String path, float size) {
        return new Font("SansSerif", Font.PLAIN, (int) size); // stub
    }

    // MODIFIES: this
    // EFFECTS: creates SAVE_DIR if it does not exist, then writes mediaLibrary
    // to the user's JSON save file; shows error dialog if write fails
    private void saveLibrary() {
        // stub
    }

    public static void main(String[] args) {

    }
}

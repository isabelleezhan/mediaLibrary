package ui;

import model.*;
import javax.swing.*;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

// Displays a single media item as a clickable card with cover image and details
@ExcludeFromJacocoGeneratedReport
public class MediaCard extends RoundedPanel {

    private static final int CARD_WIDTH = 130;
    private static final int CARD_HEIGHT = 220;
    private static final int IMG_HEIGHT = 140;

    private final Media media;
    private final MediaLibraryGUI gui;
    private JLabel coverLabel;

    // EFFECTS: constructs a media card displaying the given media item
    public MediaCard(Media media, MediaLibraryGUI gui) {
        this.media = media;
        this.gui = gui;
        setupCard();
        add(buildCoverLabel(), BorderLayout.NORTH);
        add(buildInfoPanel(), BorderLayout.CENTER);
        addMouseListener(buildMouseListener());
        setComponentPopupMenu(buildPopupMenu());
    }

    // MODIFIES: this
    // EFFECTS: sets card and its style properties
    private void setupCard() {
        setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        setMaximumSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        setLayout(new BorderLayout(0, 4));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        putClientProperty("FlatLaf.style", "arc: 16");
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    // MODIFIES: this
    // EFFECTS: creates and returns the cover image label with default cover loaded
    private JLabel buildCoverLabel() {
        coverLabel = new JLabel();
        coverLabel.setPreferredSize(new Dimension(CARD_WIDTH - 30, IMG_HEIGHT));
        coverLabel.setHorizontalAlignment(JLabel.CENTER);
        coverLabel.setVerticalAlignment(JLabel.CENTER);
        loadCover();
        return coverLabel;
    }

    // EFFECTS: returns an info panel with title, type, and status labels
    private JPanel buildInfoPanel() {
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
        info.add(buildTitleLabel());
        info.add(Box.createVerticalStrut(2));
        info.add(buildTypeLabel());
        info.add(Box.createVerticalStrut(2));
        info.add(buildStatusLabel());
        return info;
    }

    // EFFECTS: returns a label displaying the truncated media title
    private JLabel buildTitleLabel() {
        JLabel label = new JLabel(truncate(media.getTitle(), 20));
        label.setFont(MediaLibraryGUI.HELDANE.deriveFont(17f));
        label.setAlignmentX(LEFT_ALIGNMENT);
        return label;
    }

    // EFFECTS: returns a label displaying the media type in pink
    private JLabel buildTypeLabel() {
        JLabel label = new JLabel(media.getMediaType());
        label.setFont(MediaLibraryGUI.REGULAR_BOLD);
        label.setForeground(new Color(0xF075AE));
        label.setAlignmentX(LEFT_ALIGNMENT);
        return label;
    }

    // EFFECTS: returns a label displaying the media status
    private JLabel buildStatusLabel() {
        JLabel label = new JLabel(media.getStatus().getLabel());
        label.setFont(MediaLibraryGUI.REGULAR);
        label.setAlignmentX(LEFT_ALIGNMENT);
        return label;
    }

    // EFFECTS: returns a mouse listener that opens EditMediaDialog on left click
    private MouseAdapter buildMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    openEditDialog();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(UIManager.getColor("TabbedPane.hoverColor"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(UIManager.getColor("Panel.background"));
            }
        };
    }

    // MODIFIES: this
    // EFFECTS: opens EditMediaDialog positioned at this card's location
    private void openEditDialog() {
        EditMediaDialog dialog = new EditMediaDialog(gui, media);
        Point loc = getLocationOnScreen();
        dialog.setLocation(loc.x + getWidth() / 2 - dialog.getWidth() / 2, loc.y);
        dialog.setVisible(true);
        if (dialog.wasConfirmed()) {
            gui.refreshAll();
        }
    }

    // EFFECTS: returns a popup menu with change and remove cover image options
    private JPopupMenu buildPopupMenu() {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem changePhoto = new JMenuItem("Change Cover Image");
        changePhoto.addActionListener(e -> chooseCoverImage());
        JMenuItem removePhoto = new JMenuItem("Remove Cover Image");
        removePhoto.addActionListener(e -> removeCoverImage());
        popup.add(changePhoto);
        popup.add(removePhoto);
        return popup;
    }

    // MODIFIES: this
    // EFFECTS: clears saved cover image path and reloads default cover
    private void removeCoverImage() {
        coverLabel.setIcon(null);
        media.setCoverImagePath(null);
        loadCover();
    }

    // MODIFIES: this
    // EFFECTS: opens native file dialog to pick a cover image;
    // saves path to media and updates coverLabel if file selected
    private void chooseCoverImage() {
        FileDialog fd = new FileDialog(
                (Frame) SwingUtilities.getWindowAncestor(this),
                "Choose Cover Image", FileDialog.LOAD);
        fd.setFilenameFilter((dir, name) -> name.endsWith(".jpg") || name.endsWith(".jpeg")
                || name.endsWith(".png") || name.endsWith(".gif"));
        fd.setVisible(true);
        if (fd.getFile() != null) {
            String path = fd.getDirectory() + fd.getFile();
            media.setCoverImagePath(path);
            loadCoverFromPath(path);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads saved cover image if path exists, otherwise loads default
    private void loadCover() {
        if (media.getCoverImagePath() != null) {
            loadCoverFromPath(media.getCoverImagePath());
        } else {
            loadDefaultCover();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads and scales cover image from path; falls back to default
    // if file does not exist
    private void loadCoverFromPath(String path) {
        File file = new File(path);
        if (file.exists()) {
            Image scaled = new ImageIcon(path).getImage()
                    .getScaledInstance(CARD_WIDTH - 30, IMG_HEIGHT, Image.SCALE_SMOOTH);
            coverLabel.setIcon(new ImageIcon(scaled));
            coverLabel.setText(null);
        } else {
            media.setCoverImagePath(null);
            loadDefaultCover();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads and scales the default icon for this media type
    private void loadDefaultCover() {
        String iconPath;
        if ("Book".equals(media.getMediaType())) {
            iconPath = "../resources/icons/book.png";
        } else if ("Movie".equals(media.getMediaType())) {
            iconPath = "../resources/icons/movie.png";
        } else {
            iconPath = "../resources/icons/tvshow.png";
        }
        Image scaled = MediaLibraryGUI.createImageIcon(iconPath).getImage()
                .getScaledInstance(CARD_WIDTH - 30, IMG_HEIGHT - 40, Image.SCALE_SMOOTH);
        coverLabel.setIcon(new ImageIcon(scaled));
    }

    // EFFECTS: returns s truncated to max characters with ellipsis if over limit
    private String truncate(String s, int max) {
        return s.length() > max ? s.substring(0, max) + "…" : s;
    }
}

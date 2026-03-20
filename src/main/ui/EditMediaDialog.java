package ui;

import model.*;
import javax.swing.*;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import java.awt.*;

// Dialog for viewing and editing an existing media item in the library
@ExcludeFromJacocoGeneratedReport
public class EditMediaDialog extends JDialog {

    private boolean confirmed = false;
    private final Media media;
    private JComboBox<Status> statusCombo;
    private JTextArea reviewArea;
    private StarRatingPanel starPanel;
    private JSpinner numSeasonsSpinner;
    private JPanel ratingReviewPanel;

    // EFFECTS: constructs the Edit Media dialog pre-filled with the given media item
    public EditMediaDialog(JFrame parent, Media media) {
        super(parent, "Edit: " + media.getTitle(), true);
        this.media = media;
        setSize(420, 460);
        setLocationRelativeTo(parent);
        setResizable(false);
        buildUI();
    }

    // MODIFIES: this
    // EFFECTS: builds and lays out the full dialog UI pre-filled with media values
    private void buildUI() {
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));
        addHeaderLabels(main);
        addGenreField(main);
        addStatusCombo(main);
        addTypeSpecificField(main);
        ratingReviewPanel = createRatingReviewPanel();
        ratingReviewPanel.setVisible(media.getStatus() == Status.FINISHED);
        main.add(ratingReviewPanel);
        statusCombo.addActionListener(e -> onStatusChanged());
        addButtonRow(main);
        setContentPane(new JScrollPane(main));
        pack();
    }

    // MODIFIES: main
    // EFFECTS: adds title and media type display labels to main
    private void addHeaderLabels(JPanel main) {
        JLabel titleLabel = new JLabel(media.getTitle());
        titleLabel.setFont(MediaLibraryGUI.HELDANE);
        titleLabel.setForeground(new Color(0xF14874));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        main.add(titleLabel);
        main.add(Box.createVerticalStrut(4));
        JLabel typeLabel = new JLabel(media.getMediaType());
        typeLabel.setFont(MediaLibraryGUI.REGULAR_BOLD);
        typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        main.add(typeLabel);
        main.add(Box.createVerticalStrut(12));
    }

    // MODIFIES: main
    // EFFECTS: adds a non-editable genre field pre-filled with media's genre to main
    private void addGenreField(JPanel main) {
        JTextField genreField = new JTextField(media.getGenre());
        genreField.setFont(MediaLibraryGUI.REGULAR);
        genreField.setEditable(false);
        genreField.setFocusable(false);
        main.add(makeLabeledRow("Genre", genreField));
        main.add(Box.createVerticalStrut(8));
    }

    // MODIFIES: main
    // EFFECTS: adds a status dropdown box pre-filled with media's current status to main
    private void addStatusCombo(JPanel main) {
        statusCombo = new JComboBox<>(Status.values());
        statusCombo.setSelectedItem(media.getStatus());
        statusCombo.setFont(MediaLibraryGUI.REGULAR);
        statusCombo.setRenderer(new StatusRenderer());
        main.add(makeLabeledRow("Status", statusCombo));
        main.add(Box.createVerticalStrut(8));
    }

    // MODIFIES: main
    // EFFECTS: adds the type-specific field (author/director/seasons)
    //          pre-filled with media's values to main
    private void addTypeSpecificField(JPanel main) {
        if (media instanceof Book) {
            JTextField authorField = new JTextField(((Book) media).getAuthor());
            authorField.setEditable(false);
            authorField.setFocusable(false);
            main.add(makeLabeledRow("Author", authorField));
        } else if (media instanceof Movie) {
            JTextField directorField = new JTextField(((Movie) media).getDirector());
            directorField.setEditable(false);
            directorField.setFocusable(false);
            main.add(makeLabeledRow("Director", directorField));
        } else if (media instanceof TVShow) {
            numSeasonsSpinner = new JSpinner(new SpinnerNumberModel(
                    ((TVShow) media).getNumSeasons(), 1, 100, 1));
            main.add(makeLabeledRow("Seasons", numSeasonsSpinner));
        }
        main.add(Box.createVerticalStrut(8));
    }

    // EFFECTS: returns a new panel containing rating and review fields
    //          pre-filled with media's existing rating and review
    private JPanel createRatingReviewPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        starPanel = new StarRatingPanel();
        starPanel.setRating(media.getRating());
        panel.add(makeLabeledRow("Rating", starPanel));
        panel.add(Box.createVerticalStrut(8));
        addReviewArea(panel);
        return panel;
    }

    // MODIFIES: panel
    // EFFECTS: adds a review panel pre-filled with media's review to panel
    private void addReviewArea(JPanel panel) {
        JLabel reviewLabel = new JLabel("Review");
        reviewLabel.setFont(MediaLibraryGUI.HELDANE_SUB);
        reviewLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(reviewLabel);
        panel.add(Box.createVerticalStrut(4));
        reviewArea = new JTextArea(3, 20);
        reviewArea.setFont(MediaLibraryGUI.REGULAR);
        reviewArea.setLineWrap(true);
        reviewArea.setWrapStyleWord(true);
        if (media.getReview() != null) {
            reviewArea.setText(media.getReview());
        }
        JScrollPane reviewScroll = new JScrollPane(reviewArea);
        reviewScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        reviewScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        panel.add(reviewScroll);
        panel.add(Box.createVerticalStrut(8));
    }

    // MODIFIES: main
    // EFFECTS: adds Delete, Cancel, and Save buttons to main
    private void addButtonRow(JPanel main) {
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttonRow.setOpaque(false);
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setForeground(new Color(0xF14874));
        deleteBtn.addActionListener(e -> handleDelete());
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> dispose());
        JButton saveBtn = new JButton("Save");
        saveBtn.putClientProperty("JButton.buttonType", "roundRect");
        saveBtn.addActionListener(e -> handleSave());
        buttonRow.add(deleteBtn);
        buttonRow.add(cancelBtn);
        buttonRow.add(saveBtn);
        buttonRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        main.add(buttonRow);
    }

    // MODIFIES: this
    // EFFECTS: shows or hides ratingReviewPanel based on selected status
    private void onStatusChanged() {
        ratingReviewPanel.setVisible(statusCombo.getSelectedItem() == Status.FINISHED);
        pack();
    }

    // MODIFIES: this
    // EFFECTS: updates media based on selections
    private void handleSave() {
        Status status = (Status) statusCombo.getSelectedItem();
        media.setStatus(status);
        updateSeasonsIfTVShow();
        if (status == Status.FINISHED) {
            applyRatingAndReview();
        } else {
            media.setRating(0);
            media.setReview(null);
        }
        confirmed = true;
        dispose();
    }

    // MODIFIES: this
    // EFFECTS: updates media's number of seasons if media is a TVShow
    //          and numSeasonsSpinner is not null
    private void updateSeasonsIfTVShow() {
       if (media instanceof TVShow && numSeasonsSpinner != null) {
            ((TVShow) media).setNumSeasons((int) numSeasonsSpinner.getValue());
        }
    }

    // MODIFIES: this
    // EFFECTS: sets media's rating and review
    private void applyRatingAndReview() {
        int rating = starPanel.getRating();
        if (rating > 0) {
            media.setRating(rating);
        }
        String review = reviewArea.getText().trim();
        if (!review.isEmpty()) {
            media.setReview(review);
        }
    }

    // EFFECTS: prompts user to confirm deletion; if confirmed, removes media
    //          from library, sets confirmed to true, and closes dialog
    private void handleDelete() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete \"" + media.getTitle() + "\"?",
                "Delete Media", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            ((MediaLibraryGUI) getParent()).getMediaLibrary().deleteEntry(media);
            confirmed = true;
            dispose();
        }
    }

    // EFFECTS: returns a labeled row panel with labelText on the left
    //          and field on the right
    private JPanel makeLabeledRow(String labelText, JComponent field) {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setOpaque(false);
        JLabel label = new JLabel(labelText);
        label.setFont(MediaLibraryGUI.HELDANE_SUB);
        label.setPreferredSize(new Dimension(70, 24));
        row.add(label, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        return row;
    }

    // EFFECTS: returns true if the user saved or deleted
    public boolean wasConfirmed() {
        return confirmed;
    }

    // EFFECTS: renders Status enum values using their display label
    private static class StatusRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Status) {
                setText(((Status) value).getLabel());
            }
            return this;
        }
    }
}

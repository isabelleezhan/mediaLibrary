package ui;

import model.*;
import javax.swing.*;
import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import java.awt.*;

// Dialog for collecting user input to add a new media item to the library
@ExcludeFromJacocoGeneratedReport
public class AddMediaDialog extends JDialog {

    private Media result;

    private JComboBox<String> typeCombo;
    private JTextField titleField;
    private JTextField genreField;
    private JComboBox<Status> statusCombo;
    private JTextArea reviewArea;
    private StarRatingPanel starPanel;

    private JPanel typeSpecificPanel;
    private JTextField authorField;
    private JTextField directorField;
    private JSpinner numSeasonsSpinner;

    // EFFECTS: constructs the Add Media dialog attached to parent
    public AddMediaDialog(JFrame parent) {
        super(parent, "Add Media", true);
        setSize(420, 460);
        setLocationRelativeTo(parent);
        setResizable(false);
        buildUI();
    }

    // MODIFIES: this
    // EFFECTS: builds and lays out the full dialog UI
    private void buildUI() {
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        addCoreFields(main);

        JPanel ratingReviewPanel = createRatingReviewPanel();
        ratingReviewPanel.setVisible(false);
        main.add(ratingReviewPanel);

        statusCombo.addActionListener(e -> {
            boolean isFinished = statusCombo.getSelectedItem() == Status.FINISHED;
            ratingReviewPanel.setVisible(isFinished);
            pack();
        });

        createBtnRow(main);
        refreshTypePanel();
        setContentPane(new JScrollPane(main));
        pack();
    }

    // MODIFIES: main
    // EFFECTS: adds type, title, genre, status, and type-specific fields to main
    private void addCoreFields(JPanel main) {
       createMediaTypeSelector(main);
        createTitleField(main);
        createGenreField(main);
        createStatusBox(main);
        createTypeSpecificPanel(main);
    }

    // MODIFIES: main
    // EFFECTS: adds Cancel and Add buttons to main
    private void createBtnRow(JPanel main) {
       JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttonRow.setOpaque(false);
        JButton cancelBtn = new JButton("Cancel");
        JButton addBtn = new JButton("Add");
        cancelBtn.addActionListener(e -> dispose());
        addBtn.addActionListener(e -> handleInput());
        buttonRow.add(cancelBtn);
        buttonRow.add(addBtn);
        buttonRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        main.add(buttonRow);
    }

    // MODIFIES: ratingReviewPanel
    // EFFECTS: adds a labelled text area for review input to panel
    private void createReviewPanel(JPanel ratingReviewPanel) {
         JLabel reviewLabel = new JLabel("Review");
        reviewLabel.setFont(MediaLibraryGUI.HELDANE_SUB);
        reviewLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ratingReviewPanel.add(reviewLabel);
        ratingReviewPanel.add(Box.createVerticalStrut(4));
        reviewArea = new JTextArea(3, 20);
        reviewArea.setFont(MediaLibraryGUI.REGULAR);
        reviewArea.setLineWrap(true);
        reviewArea.setWrapStyleWord(true);
        JScrollPane reviewScroll = new JScrollPane(reviewArea);
        reviewScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        reviewScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        ratingReviewPanel.add(reviewScroll);
        ratingReviewPanel.add(Box.createVerticalStrut(8));
    }

    // EFFECTS: returns a new panel containing rating and review input fields
    private JPanel createRatingReviewPanel() {
        JPanel ratingReviewPanel = new JPanel();
        ratingReviewPanel.setLayout(new BoxLayout(ratingReviewPanel, BoxLayout.Y_AXIS));
        ratingReviewPanel.setOpaque(false);
        starPanel = new StarRatingPanel();
        ratingReviewPanel.add(makeLabeledRow("Rating", starPanel));
        ratingReviewPanel.add(Box.createVerticalStrut(8));
        createReviewPanel(ratingReviewPanel);
        return ratingReviewPanel;
    }

    // MODIFIES: main
    // EFFECTS: adds an empty type-specific panel placeholder to main
    private void createTypeSpecificPanel(JPanel main) {
       typeSpecificPanel = new JPanel(new BorderLayout());
        typeSpecificPanel.setOpaque(false);
        typeSpecificPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        typeSpecificPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        main.add(typeSpecificPanel);
        main.add(Box.createVerticalStrut(4));
    }

    // MODIFIES: main
    // EFFECTS: adds a dropdown box for status selection to main
    private void createStatusBox(JPanel main) {
        statusCombo = new JComboBox<>(Status.values());
        statusCombo.setFont(MediaLibraryGUI.REGULAR);
        statusCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Status) {
                    setText(((Status) value).getLabel());
                }
                return this;
            }
        });
        main.add(makeLabeledRow("Status", statusCombo));
        main.add(Box.createVerticalStrut(8));
    }

    // MODIFIES: main
    // EFFECTS: adds a text field for genre input to main
    private void createGenreField(JPanel main) {
       genreField = new JTextField();
        genreField.setFont(MediaLibraryGUI.REGULAR);
        main.add(makeLabeledRow("Genre", genreField));
        main.add(Box.createVerticalStrut(8));
    }

    // MODIFIES: main
    // EFFECTS: adds a text field for title input to main
    private void createTitleField(JPanel main) {
        titleField = new JTextField();
        titleField.setFont(MediaLibraryGUI.REGULAR);
        main.add(makeLabeledRow("Title", titleField));
        main.add(Box.createVerticalStrut(8));
    }

    // MODIFIES: main
    // EFFECTS: adds a combo box for media type selection to main
    private void createMediaTypeSelector(JPanel main) {
         typeCombo = new JComboBox<>(new String[] { "Book", "Movie", "TV Show" });
        typeCombo.setFont(MediaLibraryGUI.REGULAR);
        typeCombo.addActionListener(e -> refreshTypePanel());
        main.add(makeLabeledRow("Type", typeCombo));
        main.add(Box.createVerticalStrut(8));
    }

    // MODIFIES: this
    // EFFECTS: swaps the type-specific field in typeSpecificPanel
    // based on currently selected media type
    private void refreshTypePanel() {
        typeSpecificPanel.removeAll();
        String type = (String) typeCombo.getSelectedItem();

        if ("Book".equals(type)) {
            authorField = new JTextField();
            typeSpecificPanel.add(makeLabeledRow("Author", authorField), BorderLayout.CENTER);
        } else if ("Movie".equals(type)) {
            directorField = new JTextField();
            typeSpecificPanel.add(makeLabeledRow("Director", directorField), BorderLayout.CENTER);
        } else if ("TV Show".equals(type)) {
            numSeasonsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
            typeSpecificPanel.add(makeLabeledRow("Seasons", numSeasonsSpinner), BorderLayout.CENTER);
        }

        typeSpecificPanel.revalidate();
        typeSpecificPanel.repaint();
    }

    // MODIFIES: this
    // EFFECTS: validates inputs and constructs a Media object if valid; shows warning if required fields are empty
    private void handleInput() {
        String title = titleField.getText().trim();
        String genre = genreField.getText().trim();
        if (title.isEmpty() || genre.isEmpty()) {
            showMissingFieldMsg("Title and genre");
            return;
        }
        result = buildMediaFromInputs(title, genre);
        if (result == null) {
            return;
        }
        applyRatingAndReview();
        dispose();
    }

    // EFFECTS: constructs and returns a Media object based on selected type
    // and type-specific fields; returns null and shows warning
    // if type-specific required field is empty
    private Media buildMediaFromInputs(String title, String genre) {
        Status status = (Status) statusCombo.getSelectedItem();
        String type = (String) typeCombo.getSelectedItem();
        if ("Book".equals(type)) {
            String author = authorField.getText().trim();
            if (author.isEmpty()) {
                showMissingFieldMsg("Author");
                return null;
            }
            return new Book(title, status, author, genre);
        } else if ("Movie".equals(type)) {
            String director = directorField.getText().trim();
            if (director.isEmpty()) {
                showMissingFieldMsg("Director");
                return null;
            }
            return new Movie(title, status, director, genre);
        } else {
            return new TVShow(title, status, (int) numSeasonsSpinner.getValue(), genre);
        }
    }

    // REQUIRES: result != null
    // MODIFIES: this
    // EFFECTS: sets rating and review on result if provided
    private void applyRatingAndReview() {
        int rating = starPanel.getRating();
        String review = reviewArea.getText().trim();
        if (rating > 0) {
            result.setRating(rating);
        }
        if (!review.isEmpty()) {
            result.setReview(review);
        }
    }

    // EFFECTS: shows a warning dialog indicating fieldName cannot be empty
    private void showMissingFieldMsg(String fieldName) {
       JOptionPane.showMessageDialog(this,
                fieldName + " cannot be empty.", "Missing Fields",
                JOptionPane.WARNING_MESSAGE);
    }

    // EFFECTS: returns a labeled row panel with labelText on the left
    // and field on the right
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

    public Media getResult() {
        return result;
    }
}


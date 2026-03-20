package ui;

import javax.swing.*;
import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import java.awt.event.MouseAdapter;

// Interactive 5-star rating input panel with hover and click support.
@ExcludeFromJacocoGeneratedReport
public class StarRatingPanel extends JPanel {

    // EFFECTS: constructs a star rating panel with 5 clickable stars
    public StarRatingPanel() {
        // stub
    }

    // EFFECTS: returns a new star label at index
    private JLabel buildStar(int index) {
        return new JLabel(); // stub
    }

    // EFFECTS: returns a mouse listener for a star at index that sets rating
    //          on click, highlights on hover, and refreshes on exit
    private MouseAdapter buildStarListener(int index) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: fills stars up to and including index
    private void highlightUpTo(int index) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: fills stars up to rating
    private void refresh() {
        // stub
    }

    // REQUIRES: 0 <= r <= 5
    // MODIFIES: this
    // EFFECTS: sets rating to r and refreshes star display
    public void setRating(int r) {
        // stub
    }

    public int getRating() {
        return 0; // stub
    }
}

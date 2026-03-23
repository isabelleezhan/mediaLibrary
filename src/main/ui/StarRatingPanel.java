package ui;

import javax.swing.*;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Interactive 5-star rating input panel with hover and click support.
@ExcludeFromJacocoGeneratedReport
public class StarRatingPanel extends JPanel {

    private int rating = 0;
    private final JLabel[] stars = new JLabel[5];
    private static final String FILLED = "★";
    private static final String EMPTY = "☆";

    // EFFECTS: constructs a star rating panel with 5 clickable stars
    public StarRatingPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
        setOpaque(false);
        for (int i = 0; i < 5; i++) {
            stars[i] = buildStar(i);
            add(stars[i]);
        }
    }

    // EFFECTS: returns a new star label at index
    private JLabel buildStar(int index) {
        JLabel star = new JLabel(EMPTY);
        star.setFont(new Font("Dialog", Font.PLAIN, 20));
        star.setForeground(Color.decode("#F075AE"));
        star.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        star.addMouseListener(buildStarListener(index));
        return star;
    }

    // EFFECTS: returns a mouse listener for a star at index that sets rating
    // on click, highlights on hover, and refreshes on exit
    private MouseAdapter buildStarListener(int index) {
        return new MouseAdapter() {
            // MODIFIES: this
            // EFFECTS: sets rating to index + 1 and refreshes star display
            @Override
            public void mouseClicked(MouseEvent e) {
                rating = index + 1;
                refresh();
            }

            // MODIFIES: this
            // EFFECTS: highlights all stars up to and including index
            @Override
            public void mouseEntered(MouseEvent e) {
                highlightUpTo(index);
            }

            // MODIFIES: this
            // EFFECTS: refreshes star display to reflect current rating
            @Override
            public void mouseExited(MouseEvent e) {
                refresh();
            }
        };
    }

    // MODIFIES: this
    // EFFECTS: fills stars up to and including index
    private void highlightUpTo(int index) {
        for (int i = 0; i < 5; i++) {
            if (i <= index) {
                stars[i].setText(FILLED);
            } else {
                stars[i].setText(EMPTY);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: fills stars up to rating
    private void refresh() {
        for (int i = 0; i < 5; i++) {
            if (i < rating) {
                stars[i].setText(FILLED);
            } else {
                stars[i].setText(EMPTY);
            }
        }
    }

    // REQUIRES: 0 <= r <= 5
    // MODIFIES: this
    // EFFECTS: sets rating to r and refreshes star display
    public void setRating(int r) {
        this.rating = r;
        refresh();
    }

    public int getRating() {
        return rating;
    }
}

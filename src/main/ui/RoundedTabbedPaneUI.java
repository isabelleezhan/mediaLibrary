package ui;

import com.formdev.flatlaf.ui.FlatTabbedPaneUI;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import java.awt.*;

// Custom FlatLaf tabbed pane UI delegate that paints tabs with fully rounded corners
@ExcludeFromJacocoGeneratedReport
public class RoundedTabbedPaneUI extends FlatTabbedPaneUI {

    // MODIFIES: g
    // EFFECTS: paints a fully rounded tab background in the selected, hover,
    // or default color, then delegates to super to paint text and icon
    @Override
    protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects,
            int tabIndex, Rectangle iconRect, Rectangle textRect) {
        // stub
    }

    // EFFECTS: does nothing; background is handled entirely by paintTab
    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
            int x, int y, int w, int h, boolean isSelected) {
        // stub
    }

    // EFFECTS: surpress tab borders
    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
            int x, int y, int w, int h, boolean isSelected) {
        // stub
    }

    // EFFECTS: suppress focus indicators
    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects,
            int tabIndex, Rectangle iconRect,
            Rectangle textRect, boolean isSelected) {
        // stub
    }

    // EFFECTS: returns false
    private boolean isHover(int tabIndex) {
        return false; // stub
    }
}

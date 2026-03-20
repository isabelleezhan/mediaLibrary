package ui;

import com.formdev.flatlaf.ui.FlatTabbedPaneUI;
import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import java.awt.*;

// Custom tabbed pane UI that paints tabs with fully rounded corners
@ExcludeFromJacocoGeneratedReport
public class RoundedTabbedPaneUI extends FlatTabbedPaneUI {

    // MODIFIES: g
    // EFFECTS: paints a fully rounded tab background in the selected, hover,
    // or default color, then delegates to super to paint text and icon
    @Override
    protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects,
            int tabIndex, Rectangle iconRect, Rectangle textRect) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Rectangle rect = rects[tabIndex];
        boolean isSelected = tabIndex == tabPane.getSelectedIndex();

        if (isSelected) {
            g2.setColor(Color.decode("#FCDEE9")); 
        } else if (isHover(tabIndex)) {
            g2.setColor(Color.decode("#F4B4CC")); 
        } else {
            g2.setColor(tabPane.getBackground());
        }

        g2.fillRoundRect(rect.x + 5, rect.y + 5, rect.width - 10, rect.height - 10, 16, 16);
        g2.dispose();
        super.paintTab(g, tabPlacement, rects, tabIndex, iconRect, textRect);
    }

    // EFFECTS: does nothing; background is handled entirely by paintTab
    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
            int x, int y, int w, int h, boolean isSelected) {
    }

    // EFFECTS: surpress tab borders
    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
            int x, int y, int w, int h, boolean isSelected) {
    }

    // EFFECTS: suppress focus indicators
    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects,
            int tabIndex, Rectangle iconRect,
            Rectangle textRect, boolean isSelected) {
    }

    // EFFECTS: returns false
    private boolean isHover(int tabIndex) {
        return false; 
    }
}

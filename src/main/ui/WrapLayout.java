package ui;

import java.awt.*;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

// Layout that wraps components to new rows when they overflow
@ExcludeFromJacocoGeneratedReport
public class WrapLayout extends FlowLayout {

    // EFFECTS: constructs a WrapLayout with given alignment, horizontal gap,
    // and vertical gap
    public WrapLayout(int align, int hgap, int vgap) {
        super(align, hgap, vgap);
    }

    // EFFECTS: returns the preferred layout size of target
    @Override
    public Dimension preferredLayoutSize(Container target) {
        return layoutSize(target, true);
    }

    // EFFECTS: returns the minimum layout size of target
    @Override
    public Dimension minimumLayoutSize(Container target) {
        return layoutSize(target, false);
    }

    // REQUIRES: target != null
    // EFFECTS: returns the total layout size of target, wrapping components when
    // they exceed
    // the available width
    private Dimension layoutSize(Container target, boolean preferred) {
        synchronized (target.getTreeLock()) {
            int maxWidth = computeMaxWidth(target);
            Dimension dim = new Dimension(0, 0);
            int rowWidth = 0;
            int rowHeight = 0;

            for (Component comp : target.getComponents()) {
                if (!comp.isVisible()) {
                    continue;
                }
                Dimension d = preferred ? comp.getPreferredSize() : comp.getMinimumSize();
                if (rowWidth + d.width > maxWidth && rowWidth > 0) {
                    dim = advanceRow(dim, rowWidth, rowHeight, getVgap());
                    rowWidth = 0;
                    rowHeight = 0;
                }
                rowWidth += d.width + getHgap();
                rowHeight = Math.max(rowHeight, d.height);
            }

            dim.width = Math.max(dim.width, rowWidth);
            Insets insets = target.getInsets();
            dim.height += rowHeight + insets.top + insets.bottom + getVgap() * 2;
            return dim;
        }
    }

    // REQUIRES: target != null
    // EFFECTS: returns the maximum available width for a row in target,
    // accounting for insets and horizontal gaps
    private int computeMaxWidth(Container target) {
        int targetWidth = target.getSize().width;
        if (targetWidth == 0) {
            targetWidth = Integer.MAX_VALUE;
        }
        Insets insets = target.getInsets();
        return targetWidth - (insets.left + insets.right + getHgap() * 2);
    }

    // REQUIRES: rowWidth >= 0, rowHeight >= 0, vgap >= 0
    // EFFECTS: returns a new Dimension with width updated to max of
    // dim.width and rowWidth, and height increased by rowHeight + vgap
    private Dimension advanceRow(Dimension dim, int rowWidth, int rowHeight, int vgap) {
        Dimension next = new Dimension(dim);
        next.width = Math.max(dim.width, rowWidth);
        next.height += rowHeight + vgap;
        return next;
    }
}

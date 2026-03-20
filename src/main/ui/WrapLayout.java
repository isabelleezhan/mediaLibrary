package ui;

import java.awt.*;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

// Layout that wraps components to new rows when they overflow
@ExcludeFromJacocoGeneratedReport
public class WrapLayout extends FlowLayout {

    // EFFECTS: constructs a WrapLayout with given alignment, horizontal gap,
    //          and vertical gap
    public WrapLayout(int align, int hgap, int vgap) {
        // stub
    }

    // EFFECTS: returns the preferred layout size of target
    @Override
    public Dimension preferredLayoutSize(Container target) {
        return new Dimension(); // stub
    }

    // EFFECTS: returns the minimum layout size of target
    @Override
    public Dimension minimumLayoutSize(Container target) {
        return new Dimension(); // stub
    }

    // REQUIRES: target is not null
    // EFFECTS: returns the total layout size of target by summing row
    //          widths and heights, wrapping components when they exceed
    //          the available width; uses preferred sizes if preferred is
    //          true, minimum sizes otherwise
    private Dimension layoutSize(Container target, boolean preferred) {
        return new Dimension(); // stub
    }

    // REQUIRES: target is not null
    // EFFECTS: returns the maximum available width for a row in target,
    //          accounting for insets and horizontal gaps
    private int computeMaxWidth(Container target) {
        return 0; // stub
    }

    // REQUIRES: rowWidth >= 0, rowHeight >= 0, vgap >= 0
    // EFFECTS: returns a new Dimension with width updated to max of
    //          dim.width and rowWidth, and height increased by rowHeight + vgap
    private Dimension advanceRow(Dimension dim, int rowWidth, int rowHeight, int vgap) {
        return new Dimension(); // stub
    }
}

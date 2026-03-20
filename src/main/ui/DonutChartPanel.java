package ui;

import java.awt.*;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

// Custom-painted donut chart for visualizing media status breakdown
@ExcludeFromJacocoGeneratedReport
public class DonutChartPanel extends RoundedPanel {

    // REQUIRES: labels, values, and colors are non-null and have the same length;
    //           values contains non-negative integers
    // EFFECTS: constructs a donut chart panel with the given title, labels,
    //          values, and segment colors
    public DonutChartPanel(String title, String[] labels, int[] values, Color[] colors) {
        // stub
    }

    // MODIFIES: g
    // EFFECTS: paints the donut chart including title, pie segments, donut hole,
    //          total count in center, and legend onto g
    @Override
    protected void paintComponent(Graphics g) {
        // stub
    }

    // MODIFIES: g2
    // EFFECTS: draws the legend with colored squares and labels
    private void paintLegend(Graphics2D g2) {
        // stub
    }

    // REQUIRES: total > 0
    // MODIFIES: g2
    // EFFECTS: draws each donut segment proportional to its value
    private void paintDonut(Graphics2D g2, int total, int chartSize, int x, int y) {
        // stub
    }
}

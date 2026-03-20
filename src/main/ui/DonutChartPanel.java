package ui;

import java.awt.*;
import java.awt.geom.*;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

// Donut chart for visualizing media status breakdown
@ExcludeFromJacocoGeneratedReport
public class DonutChartPanel extends RoundedPanel {

    private final String title;
    private final String[] labels;
    private final int[] values;
    private final Color[] colors;

    private static final int W = 300;
    private static final int H = 220;
    private static final int PAD = 40;
    private static final int HOLE = 65;

    // REQUIRES: labels, values, and colors != null and have the same length;
    //           values >= 0
    // EFFECTS: constructs a donut chart panel with the given title, labels,
    //          values, and segment colors
    public DonutChartPanel(String title, String[] labels, int[] values, Color[] colors) {
        this.title = title;
        this.labels = labels;
        this.values = values;
        this.colors = colors;
        setPreferredSize(new Dimension(W, H));
    }

    // MODIFIES: g
    // EFFECTS: paints the donut chart including title, pie segments, donut hole,
    //          total count in center, and legend onto g
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Title
        g2.setFont(MediaLibraryGUI.HELDANE);
        g2.drawString(title, PAD, PAD);

        int total = 0;
        for (int v : values) {
            total += v;
        }
        int chartSize = W - PAD * 3 - 40; 
        int x = (W - chartSize) / 2 - PAD;
        int y = PAD + 16;
        paintDonut(g2, total, chartSize, x, y);

        // Total in center
        g2.setFont(MediaLibraryGUI.REGULAR_BOLD);
        g2.setColor(new Color(0x161015));
        String totalStr = String.valueOf(total);
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(totalStr,
                x + chartSize / 2 - fm.stringWidth(totalStr) / 2,
                y + chartSize / 2 + fm.getAscent() / 2 - 2);

        paintLegend(g2);
        g2.dispose();
    }

    // MODIFIES: g2
    // EFFECTS: draws the legend with colored squares and labels
    private void paintLegend(Graphics2D g2) {
        int legendX = W - PAD - 60;
        int legendY = PAD + 30;
        g2.setFont(MediaLibraryGUI.REGULAR);
        for (int i = 0; i < labels.length; i++) {
            if (values[i] == 0) {
                continue;
            }
            g2.setColor(colors[i % colors.length]);
            g2.fillRoundRect(legendX, legendY - 10, 10, 10, 4, 4);
            g2.setColor(new Color(0x161015));
            g2.drawString(labels[i], legendX + 14, legendY);
            legendY += 18;
        }
    }

    // REQUIRES: total > 0
    // MODIFIES: g2
    // EFFECTS: draws each donut segment proportional to its value
    private void paintDonut(Graphics2D g2, int total, int chartSize, int x, int y) {
        double startAngle = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] == 0) {
                continue;
            }
            double arc = (values[i] / (double) total) * 360.0;

            g2.setColor(colors[i % colors.length]);
            g2.fill(new Arc2D.Double(x, y, chartSize, chartSize,
                    startAngle, arc, Arc2D.PIE));

            startAngle += arc;
        }
        g2.setColor(getBackground());
        int holeOffset = (chartSize - HOLE) / 2;
        g2.fillOval(x + holeOffset, y + holeOffset, HOLE, HOLE);
    }
}

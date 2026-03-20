package ui;

import java.awt.*;
import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

// Custom-painted vertical bar chart for visualizing media counts 
@ExcludeFromJacocoGeneratedReport
public class BarChartPanel extends RoundedPanel {

    private String title;
    private String[] labels;
    private int[] values;
    private Color[] colors;

    private static final int W = 300;
    private static final int H = 220;
    private static final int PAD = 40;
    private static final int BAR_GAP = 20;

    // EFFECTS: constructs a bar chart panel with the given title, labels,
    // values, and bar colors
    public BarChartPanel(String title, String[] labels, int[] values, Color[] colors) {
        this.title = title;
        this.labels = labels;
        this.values = values;
        this.colors = colors;
        setPreferredSize(new Dimension(W, H));
    }

    // EFFECTS: returns the maximum value in values, or 0 if values is empty
    protected int getMaxValue() {
        int max = 0;
        for (int v : values) {
            max = Math.max(max, v);
        }
        return max;
    }

    // EFFECTS: returns val formatted as a string
    protected String formatValue(int val) {
        return String.valueOf(val);
    }

    // MODIFIES: g
    // EFFECTS: paints the bar chart onto g
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        drawTitle(g2);
        drawBarsAndLabels(g2);
        g2.dispose();
    }

    // MODIFIES: g2
    // EFFECTS: draws all bars, value labels above bars, and x-axis labels
    // below baseline onto g2
    private void drawBarsAndLabels(Graphics2D g2) {
        int maxBarH = 2 * (H - PAD * 2) / 3;
        int chartW = W - PAD * 2;
        int n = labels.length;
        int barWidth = (chartW - BAR_GAP * (n + 1)) / n;
        int maxVal = getMaxValue();
        for (int i = 0; i < n; i++) {
            int barH = maxVal;
            if (barH != 0) {
                barH = (int) ((values[i] / (double) maxVal) * maxBarH);
            }
            int x = PAD + BAR_GAP + i * (barWidth + BAR_GAP);
            int y = H - PAD - barH;

            g2.setColor(colors[i % colors.length]);
            g2.fillRoundRect(x, y, barWidth, barH, 8, 8);
            drawValuesAndLabels(g2, barWidth, i, x, y);
        }
    }

    // MODIFIES: g2
    // EFFECTS: draws the chart title and baseline onto g2
    private void drawTitle(Graphics2D g2) {
        g2.setFont(MediaLibraryGUI.HELDANE);
        g2.drawString(title, PAD, PAD);

        g2.setColor(new Color(0xFFFFFC));
        g2.drawLine(PAD, H - PAD, PAD + W - PAD * 2, H - PAD);
    }

    // MODIFIES: g2
    // EFFECTS: draws the formatted value label centered above bar i
    private void drawValuesAndLabels(Graphics2D g2, int barWidth, int i, int x, int y) {
        g2.setFont(MediaLibraryGUI.REGULAR);
        String valStr = formatValue(values[i]);
        FontMetrics fm = g2.getFontMetrics();
        int valX = x + (barWidth - fm.stringWidth(valStr)) / 2;
        g2.drawString(valStr, valX, y - 4);

        String lbl = labels[i];
        int lblX = x + (barWidth - fm.stringWidth(lbl)) / 2;
        g2.drawString(lbl, lblX, H - PAD + 16);
    }
}

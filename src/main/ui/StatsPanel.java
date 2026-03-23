package ui;

import model.*;
import javax.swing.*;
import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import java.awt.*;

// Displays library statistics and visual charts for the user's media linrary
@ExcludeFromJacocoGeneratedReport
public class StatsPanel extends JPanel {

    private final MediaLibraryGUI gui;

    // EFFECTS: constructs an empty stats panel
    public StatsPanel(MediaLibraryGUI gui) {
        super(new BorderLayout());
        this.gui = gui;
        setOpaque(false);
    }

    // MODIFIES: this
    // EFFECTS: rebuilds the stats panel with current library data
    public void refresh() {
        removeAll();
        MediaLibrary lib = gui.getMediaLibrary();
        if (lib.getAllMedia().isEmpty()) {
            add(MediaLibraryGUI.nothingMessage(), BorderLayout.CENTER);
        } else {
            add(buildScrollPane(buildContent(lib)), BorderLayout.CENTER);
        }
        revalidate();
        repaint();
    }

    // EFFECTS: returns a transparent scroll pane wrapping content
    private JScrollPane buildScrollPane(JPanel content) {
        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    // EFFECTS: returns the full content panel with groups and charts
    private JPanel buildContent(MediaLibrary lib) {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JPanel allRows = new JPanel();
        allRows.setLayout(new BoxLayout(allRows, BoxLayout.Y_AXIS));
        allRows.setOpaque(false);
        allRows.setAlignmentX(LEFT_ALIGNMENT);
        allRows.add(buildGroupsRow(lib));
        allRows.add(Box.createVerticalStrut(20));
        allRows.add(buildChartsRow(lib));
        allRows.setMaximumSize(allRows.getPreferredSize());

        content.add(allRows);
        return content;
    }

    // EFFECTS: returns a row containing Completion and Average Ratings groups
    private JPanel buildGroupsRow(MediaLibrary lib) {
        int total = lib.getTotalNumberItems();
        int finished = lib.getNumFinished();
        double pct = total > 0 ? finished * 100.0 / total : 0;
        double avgBook = lib.getAverageRating(lib.filterByTypeAndStatus("Book", Status.FINISHED));
        double avgMovie = lib.getAverageRating(lib.filterByTypeAndStatus("Movie", Status.FINISHED));
        double avgTV = lib.getAverageRating(lib.filterByTypeAndStatus("TV Show", Status.FINISHED));

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        row.setOpaque(false);
        row.setAlignmentX(LEFT_ALIGNMENT);
        row.add(makeStatGroup("Library Completion",
                new String[] { "Total Items", "Finished", "Completion" },
                new String[] { String.valueOf(total), String.valueOf(finished),
                        String.format("%.1f%%", pct) }));
        row.add(makeStatGroup("Average Ratings",
                new String[] { "Books", "Movies", "TV Shows" },
                new String[] {
                        avgBook > 0 ? String.format("%.1f", avgBook) : "—",
                        avgMovie > 0 ? String.format("%.1f", avgMovie) : "—",
                        avgTV > 0 ? String.format("%.1f", avgTV) : "—" }));
        return row;
    }

    // EFFECTS: returns a row containing bar chart and donut chart
    private JPanel buildChartsRow(MediaLibrary lib) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        row.setOpaque(false);
        row.setAlignmentX(LEFT_ALIGNMENT);
        row.add(buildTypeBarChart(lib));
        row.add(buildStatusDonutChart(lib));
        return row;
    }

    // EFFECTS: returns a bar chart of media counts by type
    private BarChartPanel buildTypeBarChart(MediaLibrary lib) {
        return new BarChartPanel("Media by Type",
                new String[] { "Books", "Movies", "TV Shows" },
                new int[] { lib.filterByType("Book").size(),
                        lib.filterByType("Movie").size(),
                        lib.filterByType("TV Show").size() },
                new Color[] { new Color(0xF14874), new Color(0xF4B4CC), new Color(0xFB9B8F) });
    }

    // EFFECTS: returns a donut chart of media counts by status
    private DonutChartPanel buildStatusDonutChart(MediaLibrary lib) {
        return new DonutChartPanel("Status Breakdown",
                new String[] { "Want to", "In Progress", "Finished", "DNF" },
                new int[] { lib.filterByStatus(Status.WANT_TO).size(),
                        lib.filterByStatus(Status.IN_PROGRESS).size(),
                        lib.filterByStatus(Status.FINISHED).size(),
                        lib.filterByStatus(Status.DNF).size() },
                new Color[] { new Color(0xFCDEE9), new Color(0xF4B4CC),
                        new Color(0xF14874), new Color(0xFB9B8F) });
    }

    // EFFECTS: returns a grouped stat panel with title and sub-stat cards
    private JPanel makeStatGroup(String groupTitle, String[] labels, String[] values) {
        JPanel group = new JPanel() {
            // EFFECTS: paints a rounded rectangle background onto g
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0xFF, 0xF9, 0xF9, 140));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
            }
        };
        group.setLayout(new BoxLayout(group, BoxLayout.Y_AXIS));
        group.setOpaque(false);
        group.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));
        group.add(buildGroupTitleRow(groupTitle));
        group.add(Box.createVerticalStrut(10));
        group.add(buildSubRow(labels, values));
        return group;
    }

    // EFFECTS: returns a centered title row for a stat group
    private JPanel buildGroupTitleRow(String groupTitle) {
        JLabel titleLabel = new JLabel(groupTitle);
        titleLabel.setFont(MediaLibraryGUI.HELDANE);
        JPanel titleRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titleRow.setOpaque(false);
        titleRow.setAlignmentX(LEFT_ALIGNMENT);
        titleRow.add(titleLabel);
        return titleRow;
    }

    // EFFECTS: returns a row of stat cards for the given labels and values
    private JPanel buildSubRow(String[] labels, String[] values) {
        JPanel subRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        subRow.setOpaque(false);
        subRow.setAlignmentX(LEFT_ALIGNMENT);
        for (int i = 0; i < labels.length; i++) {
            subRow.add(makeStatCard(labels[i], values[i]));
        }
        return subRow;
    }

    // EFFECTS: returns a small rounded stat card with value and label
    private JPanel makeStatCard(String label, String value) {
        JPanel card = new RoundedPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(getBackground());
        card.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(MediaLibraryGUI.REGULAR_BOLD.deriveFont(20f));
        valueLabel.setForeground(new Color(0xF075AE));
        valueLabel.setAlignmentX(CENTER_ALIGNMENT);
        JLabel nameLabel = new JLabel(label);
        nameLabel.setFont(MediaLibraryGUI.REGULAR.deriveFont(13f));
        nameLabel.setAlignmentX(CENTER_ALIGNMENT);
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(4));
        card.add(nameLabel);
        return card;
    }

    // MODIFIES: g
    // EFFECTS: paints a vertical gradient background from white to pink
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(new GradientPaint(0, 0, new Color(0xFFFFFC), 0, getHeight(), new Color(0xF075AE)));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
        g2.dispose();
        super.paintComponent(g);
    }
}

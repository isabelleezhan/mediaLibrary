package ui;

import model.Media;
import model.Status;

import javax.swing.*;
import com.formdev.flatlaf.FlatClientProperties;
import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import java.awt.*;
import java.util.List;

// Displays the media library as a filterable grid of media cards
@ExcludeFromJacocoGeneratedReport
public class ViewMediaPanel extends JPanel {

    private final MediaLibraryGUI gui;
    private final JComboBox<String> typeBox;
    private final JComboBox<Object> statusBox;
    private final JPanel gridPanel;

    // EFFECTS: constructs the view panel with filter bar and card grid
    public ViewMediaPanel(MediaLibraryGUI gui) {
        super(new BorderLayout());
        this.gui = gui;
        typeBox = new JComboBox<>(new String[] { "All", "Book", "Movie", "TV Show" });
        statusBox = new JComboBox<>(buildStatusOptions());
        typeBox.setFont(MediaLibraryGUI.REGULAR);
        statusBox.setFont(MediaLibraryGUI.REGULAR);
        gridPanel = new JPanel(new WrapLayout(FlowLayout.LEFT, 12, 12));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel filters = buildFilterBar();

        typeBox.addActionListener(e -> refresh());
        statusBox.addActionListener(e -> refresh());

        JScrollPane scroll = buildScrollPane();

        add(filters, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        setOpaque(false);
        filters.setOpaque(false);
        gridPanel.setOpaque(false);

        refresh();
    }

    // EFFECTS: returns a scroll pane wrapping the grid panel
    private JScrollPane buildScrollPane() {
        JScrollPane scroll = new JScrollPane(gridPanel);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    // EFFECTS: returns a filter bar panel with type and status combo boxes
    private JPanel buildFilterBar() {
        JPanel filters = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filters.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        filters.putClientProperty(FlatClientProperties.STYLE, "arc: 8");
        filters.add(new JLabel("Type"));
        filters.add(typeBox);
        filters.add(new JLabel("Status"));
        filters.add(statusBox);
        return filters;
    }

    // MODIFIES: this
    // EFFECTS: refreshes the card grid based on current filter selections;
    // shows empty state message if no items match the filter
    public void refresh() {
        gridPanel.removeAll();

        String type = (String) typeBox.getSelectedItem();
        Object statusChoice = statusBox.getSelectedItem();
        boolean allTypes = type == null || "All".equals(type);
        boolean allStatuses = !(statusChoice instanceof Status);
        List<Media> toShow;

        if (allTypes && allStatuses) {
            toShow = gui.getMediaLibrary().getAllMedia();
        } else if (allTypes) {
            toShow = gui.getMediaLibrary().filterByStatus((Status) statusChoice);
        } else if (allStatuses) {
            toShow = gui.getMediaLibrary().filterByType(type);
        } else {
            toShow = gui.getMediaLibrary().filterByTypeAndStatus(type, (Status) statusChoice);
        }

        for (Media m : toShow) {
            gridPanel.add(new MediaCard(m, gui));
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    // EFFECTS: returns array of status filter options
    private Object[] buildStatusOptions() {
        Object[] options = new Object[Status.values().length + 1];
        options[0] = "All";
        Status[] statuses = Status.values();
        for (int i = 0; i < statuses.length; i++) {
            options[i + 1] = statuses[i];
        }
        return options;
    }

    // MODIFIES: g
    // EFFECTS: paints a vertical gradient background from white to pink
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(0xFFFFFC),
                0, getHeight(), new Color(0xF075AE));
        g2.setPaint(gradient);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
        g2.dispose();
        super.paintComponent(g);
    }
}

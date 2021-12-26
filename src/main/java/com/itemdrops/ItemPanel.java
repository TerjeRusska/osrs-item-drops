package com.itemdrops;

import net.runelite.client.ui.ColorScheme;
import net.runelite.client.util.AsyncBufferedImage;
import net.runelite.client.util.QuantityFormatter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

class ItemPanel extends JPanel {
    private static final Dimension ICON_SIZE = new Dimension(32, 32);

    ItemPanel(ItemDropsPlugin itemDropsPlugin, AsyncBufferedImage icon, String name, int itemID, int haPrice) {
        BorderLayout layout = new BorderLayout();
        layout.setHgap(5);
        setLayout(layout);
        setToolTipText(name);
        setBackground(ColorScheme.DARKER_GRAY_COLOR);

        Color background = getBackground();
        List<JPanel> panels = new ArrayList<>();
        panels.add(this);

        MouseAdapter itemPanelMouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                for (JPanel panel : panels) {
                    matchComponentBackground(panel, ColorScheme.DARK_GRAY_HOVER_COLOR);
                }
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                for (JPanel panel : panels) {
                    matchComponentBackground(panel, background);
                }
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                itemDropsPlugin.lookupItemDropSources(itemID);
            }
        };

        addMouseListener(itemPanelMouseListener);
        setBorder(new EmptyBorder(5, 5, 5, 0));

        // Icon
        JLabel itemIcon = new JLabel();
        itemIcon.setPreferredSize(ICON_SIZE);
        if (icon != null) {
            icon.addTo(itemIcon);
        }
        add(itemIcon, BorderLayout.LINE_START);

        // Item details panel
        JPanel rightPanel = new JPanel(new GridLayout(3, 1));
        panels.add(rightPanel);
        rightPanel.setBackground(background);

        // Item name
        JLabel itemName = new JLabel();
        itemName.setForeground(Color.WHITE);
        itemName.setMaximumSize(new Dimension(0, 0));        // to limit the label's size for
        itemName.setPreferredSize(new Dimension(0, 0));    // items with longer names
        itemName.setText(name);
        rightPanel.add(itemName);

        JPanel alchPanel = new JPanel(new BorderLayout());
        panels.add(alchPanel);
        alchPanel.setBackground(background);

        // Alch price
        JLabel haPriceLabel = new JLabel();
        haPriceLabel.setText(QuantityFormatter.formatNumber(haPrice) + " alch");
        haPriceLabel.setForeground(ColorScheme.GRAND_EXCHANGE_ALCH);
        alchPanel.add(haPriceLabel, BorderLayout.WEST);
        rightPanel.add(alchPanel);

        add(rightPanel, BorderLayout.CENTER);
    }

    private void matchComponentBackground(JPanel panel, Color color) {
        panel.setBackground(color);
        for (Component c : panel.getComponents()) {
            c.setBackground(color);
        }
    }
}

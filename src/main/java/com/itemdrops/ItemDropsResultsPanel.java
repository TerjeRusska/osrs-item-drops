package com.itemdrops;

import com.google.common.collect.Ordering;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.DynamicGridLayout;
import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.function.Function;

class ItemDropsResultsPanel extends PluginPanel {
    private static final Color ODD_ROW = new Color(44, 44, 44);

    private final JPanel listContainer = new JPanel();

    private ItemDropsResultsHeader nameHeader;
    private ItemDropsResultsHeader combatHeader;
    private ItemDropsResultsHeader quantityHeader;
    private ItemDropsResultsHeader rarityHeader;

    private Order orderIndex = Order.NAME;
    private boolean ascendingOrder = true;

    private final ArrayList<ItemDropsResultsRow> rows = new ArrayList<>();

    ItemDropsResultsPanel() {
        setBorder(null);
        setLayout(new DynamicGridLayout(0, 1));

        JPanel headerContainer = buildHeader();
        listContainer.setLayout(new GridLayout(0, 1));

        add(headerContainer);
        add(listContainer);
    }

    /**
     * Builds the entire table header.
     */
    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        JPanel leftSide = new JPanel(new BorderLayout());
        JPanel rightSide = new JPanel(new BorderLayout());

        nameHeader = new ItemDropsResultsHeader("Name", orderIndex == Order.NAME, ascendingOrder);
        //nameHeader.setPreferredSize(new Dimension(PING_COLUMN_WIDTH, 0));
        nameHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (SwingUtilities.isRightMouseButton(mouseEvent)) {
                    return;
                }
                ascendingOrder = orderIndex != Order.NAME || !ascendingOrder;
                orderBy(Order.NAME);
            }
        });

        combatHeader = new ItemDropsResultsHeader("lvl", orderIndex == Order.COMBAT, ascendingOrder);
        //combatHeader.setPreferredSize(new Dimension(WORLD_COLUMN_WIDTH, 0));
        combatHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (SwingUtilities.isRightMouseButton(mouseEvent)) {
                    return;
                }
                ascendingOrder = orderIndex != Order.COMBAT || !ascendingOrder;
                orderBy(Order.COMBAT);
            }
        });

        quantityHeader = new ItemDropsResultsHeader("Quantity", true, ascendingOrder);
        //quantityHeader.setPreferredSize(new Dimension(PLAYERS_COLUMN_WIDTH, 0));

        rarityHeader = new ItemDropsResultsHeader("Rarity", orderIndex == Order.RARITY, ascendingOrder);
        rarityHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (SwingUtilities.isRightMouseButton(mouseEvent)) {
                    return;
                }
                ascendingOrder = orderIndex != Order.RARITY || !ascendingOrder;
                orderBy(Order.RARITY);
            }
        });

        leftSide.add(combatHeader, BorderLayout.WEST);
        leftSide.add(quantityHeader, BorderLayout.CENTER);

        rightSide.add(rarityHeader, BorderLayout.CENTER);
        rightSide.add(nameHeader, BorderLayout.EAST);

        header.add(leftSide, BorderLayout.WEST);
        header.add(rightSide, BorderLayout.CENTER);

        return header;
    }

    void populate(ArrayList<ItemDrop> itemDrops) {
        rows.clear();
        for (int i = 0; i < itemDrops.size(); i++) {
            ItemDrop itemDrop = itemDrops.get(i);
            rows.add(buildRow(itemDrop, i % 2 == 0));
        }
        updateList();
    }

    private ItemDropsResultsRow buildRow(ItemDrop itemDrop, boolean stripe) {
        ItemDropsResultsRow row = new ItemDropsResultsRow(itemDrop);
        row.setBackground(stripe ? ODD_ROW : ColorScheme.DARK_GRAY_COLOR);
        return row;
    }

    private void orderBy(Order order) {
        nameHeader.highlight(false, ascendingOrder);
        combatHeader.highlight(false, ascendingOrder);
        quantityHeader.highlight(false, ascendingOrder);
        rarityHeader.highlight(false, ascendingOrder);

        switch (order) {
            case NAME:
                nameHeader.highlight(true, ascendingOrder);
                break;
            case COMBAT:
                combatHeader.highlight(true, ascendingOrder);
                break;
            case RARITY:
                rarityHeader.highlight(true, ascendingOrder);
                break;
        }

        orderIndex = order;
        updateList();
    }

    void updateList() {
        rows.sort((r1, r2) ->
        {
            switch (orderIndex) {
                case RARITY:
                    return getCompareValue(r1, r2, row -> row.getItemDrop().getRarity());
                case COMBAT:
                    return getCompareValue(r1, r2, row -> row.getItemDrop().getCombat_level());
                case NAME:
                    return getCompareValue(r1, r2, row -> row.getItemDrop().getName());
                default:
                    return 0;
            }
        });

        listContainer.removeAll();

        for (int i = 0; i < rows.size(); i++) {
            ItemDropsResultsRow row = rows.get(i);
            row.setBackground(i % 2 == 0 ? ODD_ROW : ColorScheme.DARK_GRAY_COLOR);
            listContainer.add(row);
        }

        listContainer.revalidate();
        listContainer.repaint();
    }

    private int getCompareValue(ItemDropsResultsRow row1, ItemDropsResultsRow row2, Function<ItemDropsResultsRow, Comparable> compareByFn) {
        Ordering<Comparable> ordering = Ordering.natural();
        if (!ascendingOrder) {
            ordering = ordering.reverse();
        }
        ordering = ordering.nullsLast();
        return ordering.compare(compareByFn.apply(row1), compareByFn.apply(row2));
    }

    /**
     * Enumerates the multiple ordering options
     */
    private enum Order {
        NAME,
        COMBAT,
        RARITY
    }

}

package com.itemdrops;

import lombok.Getter;
import net.runelite.client.util.LinkBrowser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;

class ItemDropsResultsRow extends JPanel {

    private static final Color MEMBERS = new Color(210, 193, 53);
    private static final Color FREE_WORLD = new Color(200, 200, 200);
    private static final Color NOTED = new Color(133, 177, 178);

    @Getter
    private ItemDrop itemDrop;

    private JLabel nameField;
    private JLabel combatField;
    private JLabel quantityField;
    private JLabel rarityField;

    private Color lastBackground;

    ItemDropsResultsRow(ItemDrop itemDrop) {
        this.itemDrop = itemDrop;

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(2, 0, 2, 0));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                LinkBrowser.browse(itemDrop.getWiki_url());
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                setBackground(getBackground().brighter());
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                setBackground(getBackground().darker());
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                ItemDropsResultsRow.this.lastBackground = getBackground();
                setBackground(getBackground().brighter());
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                setBackground(lastBackground);
            }
        });

        JPanel leftSide = new JPanel(new BorderLayout());
        JPanel rightSide = new JPanel(new BorderLayout());
        leftSide.setOpaque(false);
        rightSide.setOpaque(false);

        JPanel nameField = buildNameField();
        //nameField.setPreferredSize(new Dimension(WORLD_COLUMN_WIDTH, 0));
        nameField.setOpaque(false);

        JPanel combatField = buildCombatField();
        //combatField.setPreferredSize(new Dimension(PING_COLUMN_WIDTH, 0));
        combatField.setOpaque(false);

        JPanel quantityField = buildQuantityField();
        //quantityField.setPreferredSize(new Dimension(PLAYERS_COLUMN_WIDTH, 0));
        quantityField.setOpaque(false);

        JPanel rarityField = buildRarityField();
        //rarityField.setBorder(new EmptyBorder(5, 5, 5, 5));
        rarityField.setOpaque(false);

        if (itemDrop.getMembers()) {
            nameField.setForeground(MEMBERS);
            combatField.setForeground(MEMBERS);
            quantityField.setForeground(MEMBERS);
            rarityField.setForeground(MEMBERS);
        } else {
            nameField.setForeground(FREE_WORLD);
            combatField.setForeground(FREE_WORLD);
            quantityField.setForeground(FREE_WORLD);
            rarityField.setForeground(FREE_WORLD);
        }

        if (itemDrop.getNoted()) {
            quantityField.setForeground(NOTED);
        }

        leftSide.add(nameField, BorderLayout.WEST);
        leftSide.add(combatField, BorderLayout.CENTER);
        rightSide.add(quantityField, BorderLayout.CENTER);
        rightSide.add(rarityField, BorderLayout.EAST);

        add(leftSide, BorderLayout.WEST);
        add(rightSide, BorderLayout.CENTER);
    }

    private JPanel buildNameField() {
        JPanel column = new JPanel(new BorderLayout());
        column.setBorder(new EmptyBorder(0, 5, 0, 5));
        nameField = new JLabel(itemDrop.getName());
        column.add(nameField, BorderLayout.WEST);
        return column;
    }

    private JPanel buildCombatField() {
        JPanel column = new JPanel(new BorderLayout());
        column.setBorder(new EmptyBorder(0, 5, 0, 5));
        combatField = new JLabel(itemDrop.getCombat_level().toString());
        column.add(combatField, BorderLayout.WEST);
        return column;
    }

    private JPanel buildQuantityField() {
        JPanel column = new JPanel(new BorderLayout());
        column.setBorder(new EmptyBorder(0, 5, 0, 5));
        quantityField = new JLabel(itemDrop.getQuantity());
        column.add(quantityField, BorderLayout.CENTER);
        return column;
    }

    private JPanel buildRarityField() {
        JPanel column = new JPanel(new BorderLayout());
        column.setBorder(new EmptyBorder(0, 5, 0, 5));
        NumberFormat defaultFormat = NumberFormat.getPercentInstance();
        defaultFormat.setMinimumFractionDigits(1);
        rarityField = new JLabel(defaultFormat.format(itemDrop.getRarity()));
        column.add(rarityField, BorderLayout.EAST);
        return column;
    }
}

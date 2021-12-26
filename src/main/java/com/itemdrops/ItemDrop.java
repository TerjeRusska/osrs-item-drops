package com.itemdrops;

import lombok.Value;

@Value
public class ItemDrop {
    private final String name;
    private final Integer combat_level;
    private final String wiki_url;
    private final Boolean members;
    private final String quantity;
    private final Boolean noted;
    private final Float rarity;
}

package com.civilization.christmas.modules.daily_gifts.core;

public abstract class ItemChance {
    public enum ItemType { COMMON, RARE }

    public ItemType type;
    public float chance;

    public ItemChance(ItemType type, float chance) {
        this.type = type;
        this.chance = chance;
    }
}

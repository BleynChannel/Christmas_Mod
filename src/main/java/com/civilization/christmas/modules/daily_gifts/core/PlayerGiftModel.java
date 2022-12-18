package com.civilization.christmas.modules.daily_gifts.core;

import java.util.Date;
import java.util.UUID;

public class PlayerGiftModel {
    public UUID uuid;
    public Date lastPick;
    public int countPick;
    public float chance;

    public PlayerGiftModel(UUID uuid, Date lastPick, int countPick, float chance) {
        this.uuid = uuid;
        this.lastPick = lastPick;
        this.countPick = countPick;
        this.chance = chance;
    }
}

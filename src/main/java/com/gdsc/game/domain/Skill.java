package com.gdsc.game.domain;

import java.util.Random;

public class Skill {
    private final String name;
    private final int manaCost;
    private int cooldown;

    public Skill(String name, int manaCost, int cooldown) {
        this.name = name;
        this.manaCost = manaCost;
        this.cooldown = cooldown;
    }

    public int calculateDamage() {
        Random rand = new Random();
        return this.manaCost * (rand.nextInt(10) + 1);
    }

    public boolean isAvailable() {
        return this.cooldown == 0;
    }

    public void useCooldown() {
        Random rand = new Random();
        this.cooldown = rand.nextInt(2);
    }

    public void reduceCooldown() {
        if (this.cooldown > 0) {
            this.cooldown--;
        }
    }

    public String getName() {
        return name;
    }

    public int getManaCost() {
        return manaCost;
    }
}
package com.gdsc.game.domain;

import java.util.Random;

public class Character {
    private final String name;
    private final Job job;
    private int health;
    private int mana;

    public Character(String name, Job job, int level) {
        this.name = name;
        this.job = job;
        if (job == null) {
            throw new IllegalArgumentException("Job cannot be null");
        }
        this.health = job.getBaseHealth() + level * 10;
        this.mana = job.getBaseMana() + level * 5;
    }

    public int attack() {
        Random random = new Random();
        return random.nextInt(10) + 1;
    }

    public int defend() {
        Random random = new Random();
        return random.nextInt(10) + 1;
    }

    public int useSkill(Skill skill) {
        if (skill.isAvailable() && this.mana >= skill.getManaCost()) {
            this.mana -= skill.getManaCost();
            skill.useCooldown();
            return skill.calculateDamage();
        }
        return 0;
    }

    public void decreaseHealth(int damage) {
        this.health -= damage;
    }

    public String getName() {
        return name;
    }

    public Job getJob() {
        return job;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }
}

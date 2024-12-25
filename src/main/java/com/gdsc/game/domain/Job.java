package com.gdsc.game.domain;

public class Job {
    private final String name;
    private final int baseHealth;
    private final int baseMana;
    private final Skill[] skills;

    public Job(String name, int baseHealth, int baseMana, Skill[] skills) {
        this.name = name;
        this.baseHealth = baseHealth;
        this.baseMana = baseMana;
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public int getBaseMana() {
        return baseMana;
    }

    public Skill[] getSkills() {
        return skills;
    }
}

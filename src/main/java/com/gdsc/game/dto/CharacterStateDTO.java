package com.gdsc.game.dto;

import com.gdsc.game.domain.Character;
import com.gdsc.game.domain.Skill;
import java.util.Arrays;
import java.util.Comparator;

public class CharacterStateDTO {
    private final String name;
    private final String jobName;
    private final int health;
    private final int mana;
    private final Skill[] skills;

    public CharacterStateDTO(Character character) {
        this.name = character.getName();
        this.jobName = character.getJob().getName();
        this.health = character.getHealth();
        this.mana = character.getMana();

        // 스킬 복사 및 정렬
        this.skills = Arrays.copyOf(character.getJob().getSkills(), character.getJob().getSkills().length);
        Arrays.sort(this.skills, Comparator.comparing(Skill::getName));
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

    public Skill[] getSkills() {
        return skills;
    }
}
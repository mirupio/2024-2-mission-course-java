package com.gdsc.game.domain;

public class Game implements GameStateProvider{
    private int turnCount;
    private final int maxTurns;
    private final Character player1;
    private final Character player2;

    public Game(Character player1, Character player2, int maxTurns) {
        this.player1 = player1;
        this.player2 = player2;
        this.maxTurns = maxTurns;
        this.turnCount = 0;
    }

    @Override
    public int getTurnCount() {
        return turnCount;
    }

    @Override
    public int getMaxTurns() {
        return maxTurns;
    }

    @Override
    public String getPlayer1Name() {
        return player1.getName();
    }

    @Override
    public String getPlayer2Name() {
        return player2.getName();
    }

    @Override
    public boolean isGameOver() {
        return player1.getHealth() <= 0 || player2.getHealth() <= 0 || turnCount >= maxTurns;
    }

    // 1 turn 당 2 character
    public void nextTurn(int player1Action, Integer player1SkillIndex, int player2Action, Integer player2SkillIndex) {
        if (isGameOver()) {
            return;
        }

        int damage = 0;

        // Player1 행동
        damage = executeAction(player1Action, player1SkillIndex, player1, player2);
        player2.decreaseHealth(damage); // 상대 캐릭터의 체력 감소

        // Player2 행동
        damage = executeAction(player2Action, player2SkillIndex, player2, player1);
        player1.decreaseHealth(damage); // 상대 캐릭터의 체력 감소

        // 쿨다운 감소
        reduceCooldowns();

        turnCount++;
    }

    private int executeAction(int action, int skillIndex, Character current, Character opponent) {
        int damage = 0;
        switch (action) {
            case 1 -> damage = current.attack(); // 일반 공격
            case 2 -> { // 방어
                current.defend(); // 방어 상태 설정
                damage = opponent.attack(); // 공격을 받은 후 피해 계산
                if (damage < 0) damage = 0;
            }
            default -> { // 스킬 사용
                Skill[] skills = current.getJob().getSkills();
                damage = current.useSkill(skills[skillIndex]);
            }
        }
        return damage;
    }

    private void reduceCooldowns() {
        // player1의 스킬 쿨다운 감소
        for (Skill skill : player1.getJob().getSkills()) {
            skill.reduceCooldown();
        }

        // player2의 스킬 쿨다운 감소
        for (Skill skill : player2.getJob().getSkills()) {
            skill.reduceCooldown();
        }
    }
}

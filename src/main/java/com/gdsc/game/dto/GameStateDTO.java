package com.gdsc.game.dto;

import com.gdsc.game.domain.GameStateProvider;

public class GameStateDTO {
    private final int turnCount;
    private final int maxTurns;
    private final String Player1;
    private final String Player2;
    private final boolean isGameOver;

    public GameStateDTO(GameStateProvider gameStateProvider) {
        this.turnCount = gameStateProvider.getTurnCount();
        this.maxTurns = gameStateProvider.getMaxTurns();
        this.Player1 = gameStateProvider.getPlayer1Name();
        this.Player2 = gameStateProvider.getPlayer2Name();
        this.isGameOver = gameStateProvider.isGameOver();
    }
    // Getter 메서드 추가
    public int getTurnCount() {
        return turnCount;
    }

    public int getMaxTurns() {
        return maxTurns;
    }

    public String getPlayer1() {
        return Player1;
    }

    public String getPlayer2() {
        return Player2;
    }

    public boolean isGameOver() {
        return isGameOver;
    }
}
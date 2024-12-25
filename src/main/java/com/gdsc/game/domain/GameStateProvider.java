package com.gdsc.game.domain;

public interface GameStateProvider {
    int getTurnCount();
    int getMaxTurns();
    String getPlayer1Name();
    String getPlayer2Name();
    boolean isGameOver();
}

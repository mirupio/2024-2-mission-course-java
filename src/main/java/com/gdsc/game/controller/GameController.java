package com.gdsc.game.controller;

import com.gdsc.game.dto.CharacterStateDTO;
import com.gdsc.game.dto.GameStateDTO;
import com.gdsc.game.service.GameService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // 1. 게임 상태 조회
    @GetMapping("/state")
    public GameStateDTO getGameState() {
        return gameService.getGameState();
    }

    // 2. 캐릭터 상태 조회
    @GetMapping("/character/{name}")
    public CharacterStateDTO getCharacterState(@PathVariable String name) {
        return gameService.getCharacterState(name);
    }

    // 3. 턴 진행
    @PostMapping("/next-turn")
    public GameStateDTO nextTurn(@RequestParam int player1Action, @RequestParam(required = false) Integer player1SkillIndex,
                                 @RequestParam int player2Action, @RequestParam(required = false) Integer player2SkillIndex) {
        return gameService.nextTurn(player1Action, player1SkillIndex, player2Action, player2SkillIndex);
    }

}
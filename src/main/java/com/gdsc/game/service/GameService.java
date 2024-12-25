/*
package com.gdsc.game.service;

import com.gdsc.game.domain.Character;
import com.gdsc.game.domain.Game;
import com.gdsc.game.domain.Job;
import com.gdsc.game.domain.Skill;
import com.gdsc.game.dto.CharacterStateDTO;
import com.gdsc.game.dto.GameStateDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    private final Game game;
    private final Map<String, Character> characters = new HashMap<>();

    public GameService() {
        Skill doubleStrike = new Skill("2번 베기", 2, 0);
        Skill tripleStrike = new Skill("3번 베기", 3, 0);
        Skill powerStrike = new Skill("쎄게 때리기", 5, 2);
        Skill fireArrow = new Skill("불 화살", 3, 0);
        Skill iceArrow = new Skill("얼음 화살", 3, 1);

        //Character knight = new Character("knight", 50, 30, new Skill[]{doubleStrike, tripleStrike, powerStrike});
        //Character slime = new Character("slime", 30, 20, new Skill[]{fireArrow, iceArrow});


        Job warriorJob = new Job("Warrier", 100, 50, new Skill[]{doubleStrike, tripleStrike, powerStrike});
        Job archerJob = new Job("Archer", 30, 20, new Skill[]{fireArrow, iceArrow});

        Scanner scanner = new Scanner(System.in);
        int warriorLevel = 0;
        int mageLevel = 0;
        try {
            System.out.print("Enter level for Warrior (knight): ");
            warriorLevel = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter level for Mage (slime): ");
            mageLevel = Integer.parseInt(scanner.nextLine());

        } catch (NumberFormatException e) {
            System.out.println("Invalid level input! Please enter a valid number.");
        }

        Character knight = new Character("knight", warriorJob, warriorLevel);
        Character slime = new Character("slime", archerJob, mageLevel);

        characters.put("knight", knight);
        characters.put("slime", slime);

        this.game = new Game(knight, slime, 5);
    }

    // 1. 게임 상태 조회
    public GameStateDTO getGameState() {
        return new GameStateDTO(game);
    }

    // 2. 캐릭터 상태 조회
    public CharacterStateDTO getCharacterState(String name) {
        Character character = characters.get(name.toLowerCase());

        if (character == null) {
            return null;
        }

        //Skill[] sortedSkills = Arrays.copyOf(character.getSkills(), character.getSkills().length);
        //Arrays.sort(sortedSkills, Comparator.comparing(Skill::getName));

        //return new CharacterStateDTO(character, sortedSkills);

        return new CharacterStateDTO(character);
    }

    // 3. 턴 진행
    public GameStateDTO nextTurn(int action, Integer skillIndex) {
        game.nextTurn(action, skillIndex != null ? skillIndex : -1);
        return new GameStateDTO(game);
    }

}
 */

package com.gdsc.game.service;

import com.gdsc.game.domain.Character;
import com.gdsc.game.domain.Game;
import com.gdsc.game.domain.Job;
import com.gdsc.game.domain.Skill;
import com.gdsc.game.dto.CharacterStateDTO;
import com.gdsc.game.dto.GameStateDTO;
import com.gdsc.game.exception.NotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    private final Game game;
    private final Map<String, Character> characters = new HashMap<>();
    private final Map<String, Job> jobs = new HashMap<>();

    public GameService() {
        initializeJobs();
        initializeCharacters();
        int maxTurns = initializeMaxTurns();

        Character firstCharacter = characters.get("firstCharacter"); // 첫 번째 캐릭터
        Character secondCharacter = characters.get("secondCharacter"); // 두 번째 캐릭터

        this.game = new Game(firstCharacter, secondCharacter, maxTurns);
        System.out.println("game start");
    }

    // 직업 초기화
    private void initializeJobs(){
        Skill doubleStrike = new Skill("2번 베기", 2, 0);
        Skill tripleStrike = new Skill("3번 베기", 3, 0);
        Skill powerStrike = new Skill("쎄게 때리기", 5, 2);
        Skill fireArrow = new Skill("불 화살", 3, 0);
        Skill iceArrow = new Skill("얼음 화살", 3, 1);

        Job warriorJob = new Job("Warrior", 70, 30, new Skill[]{doubleStrike, tripleStrike, powerStrike});
        Job archerJob = new Job("Archer", 60, 40, new Skill[]{fireArrow, iceArrow});

        jobs.put("warrior", warriorJob);
        jobs.put("archer", archerJob);
        // System.out.println("initialize jobs");
    }

    // 캐릭터 초기화
    private void initializeCharacters(){
        Scanner scanner = new Scanner(System.in);

        // first character
        // 1) name
        System.out.print("Enter name for first character: ");
        String firstname = scanner.nextLine();

        // 2) job
        String firstJobName = null;
        Job firstJob = null;

        while(firstJob == null){
            System.out.print("Enter job for first character: ");
            firstJobName = scanner.nextLine().toLowerCase();
            try{
                firstJob = getJobByName(firstJobName);
            }
            catch(NotFoundException e){
                System.out.println("404 Not Found: " + e.getMessage());
            }
        }

        // 3) level
        int firstlevel = 0;
        boolean validInput = false;

        while(!validInput) {
            System.out.print("Enter level(1~5) for first character: ");
            try{
                firstlevel = validatelevel(scanner.nextLine());
                validInput = true;
            }
            catch(IllegalArgumentException e){
                System.out.println("400 Bad Request: " + e.getMessage());
            }
        }

        Character firstcharacter = new Character(firstname,firstJob,firstlevel);
        characters.put("firstCharacter", firstcharacter);

        // second character
        // 1) name
        System.out.print("Enter name for second character: ");
        String secondname = scanner.nextLine();

        // 2) job
        String secondJobName = null;
        Job secondJob = null;

        while(secondJob == null){
            System.out.print("Enter job for second character: ");
            secondJobName = scanner.nextLine().toLowerCase();
            try{
                secondJob = getJobByName(secondJobName);
            }
            catch(NotFoundException e){
                System.out.println("404 Not Found: " + e.getMessage());
            }
        }

        // 3) level
        int secondlevel = 0;
        boolean validInput2 = false;

        while(!validInput2) {
            System.out.print("Enter level(1~5) for second character: ");
            try{
                secondlevel = validatelevel(scanner.nextLine());
                validInput2 = true;
            }
            catch(IllegalArgumentException e){
                System.out.println("400 Bad Request: " + e.getMessage());
            }
        }

        Character secondcharacter = new Character(secondname,secondJob,secondlevel);
        characters.put("secondCharacter", secondcharacter);

        // System.out.println(characters);

        // System.out.println("initialize characters");
    }

    // 직업 입력값 검증 및 조회
    private Job getJobByName(String jobName) {
        if(!jobs.containsKey(jobName)) {
            throw new NotFoundException("Job '" + jobName + "' does not exist.");
        }
        return jobs.get(jobName);
    }

    // 레벨 입력값 검증
    private int validatelevel(String input) {
        if (!input.matches("\\d+") || Integer.parseInt(input) <= 0 || Integer.parseInt(input) > 5) {
            throw new IllegalArgumentException("Level must be a 1~5 integer");
        }
        return Integer.parseInt(input);
    }

    // 최대 턴 수 초기화
    private int initializeMaxTurns() {
        Scanner scanner = new Scanner(System.in);
        int maxTurns = 0;
        boolean validInput = false;

        while(!validInput) {
            System.out.print("Enter the maximum number of turns for the game: ");
            try{
                maxTurns = validateTurns(scanner.nextLine());
                // System.out.println("initialize max turns");
                validInput = true;
            }
            catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }

        return maxTurns;
    }

    // 최대 턴 수 입력값 검증
    private int validateTurns(String input) {
        if (!input.matches("\\d+") || Integer.parseInt(input) <= 0) {
            throw new IllegalArgumentException("Max turns must be a positive integer.");
        }
        return Integer.parseInt(input);
    }

    // 1. 게임 상태 조회
    public GameStateDTO getGameState() {
        return new GameStateDTO(game);
    }

    // 2. 캐릭터 상태 조회
    public CharacterStateDTO getCharacterState(String n_character) {
        Character character = characters.get(n_character);

        if (character == null) {
            return null;
        }

        return new CharacterStateDTO(character);
    }

    // 3. 턴 진행
    public GameStateDTO nextTurn(int player1Action, Integer player1SkillIndex, int player2Action, Integer player2SkillIndex) {
        game.nextTurn(player1Action, player1SkillIndex != null ? player1SkillIndex : -1,player2Action, player2SkillIndex != null ? player2SkillIndex : -1);
        return new GameStateDTO(game);

    }
}



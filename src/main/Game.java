package main;

import main.board.Board;
import main.ships.Ship;
import main.teams.AITeam;
import main.teams.HumanTeam;
import main.teams.Team;
import main.teams.proto.PlayerType;
import main.teams.proto.TeamType;
import main.tools.TextColor;
import main.tools.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Game {

    private ArrayList<Team> teams;
    private final Board gameBoard;

    private int winnerId;

    public Game() {
        winnerId = -1;
        gameBoard = new Board(Config.BOARD_SIZE);
        initTeams();
    }

    private void initTeams() {
        ArrayList<TeamType> teamTypes = new ArrayList<>();
        ArrayList<PlayerType> playerTypes = new ArrayList<>();

        for(int i = 0; i < Config.NUM_TEAMS; i++) {
            boolean isMilitary = (i < (int) (Config.NUM_TEAMS * Config.MILITARY_NEUTRAL_RATIO)),
                    isHuman = (i < (int) (Config.NUM_TEAMS * Config.HUMAN_AI_RATIO));

            teamTypes.add( isMilitary ? TeamType.MILITARY : TeamType.NEUTRAL );
            playerTypes.add( isHuman ? PlayerType.HUMAN : PlayerType.AI );
        }

        Collections.shuffle(teamTypes);
        Collections.shuffle(playerTypes);

        teams = new ArrayList<>();
        for(int i = 0; i < Config.NUM_TEAMS; i++) {
            teams.add( switch(playerTypes.get(i)) {
                case HUMAN ->   new HumanTeam(teamTypes.get(i), gameBoard);
                case AI ->      new AITeam(teamTypes.get(i), gameBoard);
            });
        }
    }

    private void checkWinner() {
        boolean loserExists = false;
        Team winningTeam = teams.get(0);

        Util.clearScreen();
        System.out.println("\n\nVérification des gagnants:");
        for(Team t : teams) {
            int numShipsAlive = t.getNumShipsAlive();
            System.out.println("Équipe #" + t.getId() + ": " + t.getNumShipsAlive() + " navires restants");
            if(numShipsAlive == 0) loserExists = true;
            if(numShipsAlive > winningTeam.getNumShipsAlive()) winningTeam = t;
        }

        System.out.println("\n" + (loserExists ? "Il y a un gagnant !" : "Pas de gagnant ..."));

        System.out.println("\n");
        Util.waitForEnter();

        if(loserExists) winnerId = winningTeam.getId();
    }

    public void run() {
        Util.clearScreen();

        ArrayList<String> startOptions = new ArrayList<>();
        startOptions.add("Jouer");
        startOptions.add("Quitter");
        int choice = Util.renderMenu("BATANAVA", startOptions);
        if(choice == 1) System.exit(0);

        // Main game loop
        while(winnerId == -1) {
            for(Team t : teams) t.playRound();
            checkWinner();
        }

        System.out.println(teams.get(winnerId).toString() + " a gagné !");
    }
}

package main.teams;

import main.Config;
import main.board.proto.BoardColoring;
import main.ships.proto.Height;
import main.teams.proto.ActionType;
import main.tools.TextColor;
import main.tools.Util;
import main.board.Board;
import main.board.Location;
import main.ships.Ship;
import main.ships.proto.ShipStatus;
import main.ships.proto.ShipType;
import main.teams.proto.PlayerType;
import main.teams.proto.TeamType;

import java.util.ArrayList;

public abstract class Team {

    private static int numPlayers = 0;

    // Attributes

    private final int id;
    private final PlayerType playerType;

    protected final TeamType teamType;
    protected final Board board;
    protected ArrayList<Ship> ships;

    public Team(PlayerType playerType, TeamType teamType, Board board) {
        this.id = ++numPlayers;
        this.playerType = playerType;
        this.teamType = teamType;
        this.board = board;

        this.initFleet();
    }

    // Fleet creation

    private void initFleet() {
        ships = new ArrayList<>();
        for(int i = 0; i < Config.NUM_SHIPS_PER_TEAM; i++) {

            // STEP 1: Creating the ship
            ShipType shipType = switch (teamType) {
                case NEUTRAL -> ShipType.TRAWLER;
                case MILITARY -> (Math.random() > 0.5) ? ShipType.DESTROYER : ShipType.SUBMARINE; // TODO: Make less random
            };
            Ship newShip = new Ship(shipType, id);
            ships.add( newShip );

            // STEP 2: Add it to the board
            int boardX, boardY;
            Location potentialSpace;
            do {
                boardX = Util.randInt(board.getSize());
                boardY = Util.randInt(board.getSize());
                potentialSpace = board.getSpace(boardX, boardY);
            } while(potentialSpace.isOccupied(shipType.getHeight()));
            potentialSpace.addShip(newShip);
        }
    }

    // Round playing

    public void playRound() {
        if(getNumShipsAlive() == 0) return;

        // Select current ship from list
        printHeader(ship -> (ships.contains(ship)) ? TextColor.GREEN : TextColor.RESET);
        Ship shipToMove = selectShip();

        // Choose action to perform
        printHeader(ship -> (ship == shipToMove) ? TextColor.GREEN : TextColor.RESET);

        ArrayList<String> shipOptions = new ArrayList<>();
        if(shipToMove.getStatus() != ShipStatus.DAMAGED) shipOptions.add(ActionType.MOVE.toString());
        shipOptions.add(switch (teamType) {
            case MILITARY -> ActionType.ATTACK.toString();
            case NEUTRAL -> ActionType.FISH.toString();
        });

        if(shipToMove.getStatus() == ShipStatus.DAMAGED)
            System.out.println("Ce navire est immobilisé, impossible de se déplacer");
        int actionChoice = selectAction(shipOptions);
        ActionType action = ActionType.fromDesc(shipOptions.get(actionChoice));

        // Try performing action
        ArrayList<Ship> shipsToAttack = new ArrayList<>();

        if(action != ActionType.FISH) {
            printHeader(ship -> {
                if (ship == shipToMove) {
                    return TextColor.GREEN;
                } else if (action == ActionType.ATTACK && shipToMove.canAttack(ship) && !ships.contains(ship)) {
                    shipsToAttack.add(ship);
                    return TextColor.RED;
                } else {
                    return TextColor.RESET;
                }
            });
        }

        String resultString = switch (action) {
            case MOVE -> movePlayer(shipToMove);
            case ATTACK -> launchAttack(shipsToAttack);
            case FISH -> fishForShips(shipToMove);
        };

        printHeader(ship -> (ship == shipToMove) ? TextColor.GREEN : TextColor.RESET);
        System.out.println(resultString);

        Util.waitForEnter();
    }

    protected void printHeader(BoardColoring bci) {
        Util.clearScreen();
        System.out.println(this.getShortDesc() + "\n");
        board.render(bci);
        System.out.println("");
    }

    protected abstract Ship selectShip();
    protected abstract int selectAction(ArrayList<String> shipOptions);

    protected abstract String movePlayer(Ship s);
    protected abstract String launchAttack(ArrayList<Ship> ships);

    protected String fishForShips(Ship s) {
        Location curSpace = s.getLocation();
        if(curSpace.isOccupied(Height.DEPTH)) {
            curSpace.getOccupant(Height.DEPTH).damage();
            return "Le sous-marin en case " + curSpace.getCoordsStr() + " a été immobilisé.";
        } else {
            return "Pas de navires à pêcher.";
        }
    }

    // Getters

    public int getNumShipsAlive() {
        int total = 0;
        for(Ship s : ships)
            if(s.getStatus() != ShipStatus.SUNK) total++;
        return total;
    }

    public int getId() {
        return id;
    }

    // Class functions

    public String getShortDesc() {
        return "Player " + id + " (" + playerType + " - " + teamType + ")";
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getShortDesc());

        for(Ship ship : ships) {
            builder.append("\n");
            builder.append(ship.toString());
        }

        return builder.toString();
    }
}

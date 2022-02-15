package main.teams;

import main.board.Board;
import main.board.Location;
import main.ships.Ship;
import main.ships.proto.Height;
import main.ships.proto.ShipStatus;
import main.teams.proto.PlayerType;
import main.teams.proto.TeamType;
import main.tools.Util;

import java.util.ArrayList;

public class AITeam extends Team {

    public AITeam(TeamType teamType, Board board) {
        super(PlayerType.AI, teamType, board);
    }


    @Override
    protected Ship selectShip() {
        Ship selected;
        int selectedId;
        do {
            selectedId = Util.randInt(0, ships.size()-1);
            selected = ships.get(selectedId);
        } while (selected.getStatus() == ShipStatus.SUNK);
        return selected;
    }

    @Override
    protected int selectAction(ArrayList<String> shipOptions) {
        return Util.randInt(0, shipOptions.size()-1);
    }

    @Override
    protected String movePlayer(Ship s) {
        int newRow, newCol;
        Location space;
        do {
            newCol = Util.randInt(1, board.getSize()) - 1;
            newRow = Util.randInt(1, board.getSize()) - 1;
            space = board.getSpace(newRow, newCol);
        } while(space.isOccupied(s.getHeight()) || !s.canMove(space));

        s.getLocation().removeOccupant(s.getHeight());
        space.addShip(s);

        return "Déplacement effectué au " + space.getCoordsStr();
    }

    @Override
    protected String launchAttack(ArrayList<Ship> shipsToAttack) {
        if(shipsToAttack.isEmpty())
            return "Pas de navires à attaquer.";

        int shipToAttackIndex = Util.randInt(0, shipsToAttack.size()-1);
        Ship shipToAttack = shipsToAttack.get(shipToAttackIndex);
        shipToAttack.sink();

        return "Le bateau en position " + shipToAttack.getLocation().getCoordsStr() + " à été coulé";
    }
}

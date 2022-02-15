package main.teams;

import main.board.Board;
import main.board.Location;
import main.ships.Ship;
import main.ships.proto.Height;
import main.ships.proto.ShipStatus;
import main.ships.proto.ShipType;
import main.teams.proto.ActionType;
import main.teams.proto.PlayerType;
import main.teams.proto.TeamType;
import main.tools.TextColor;
import main.tools.Util;

import java.util.ArrayList;

public class HumanTeam extends Team {

    public HumanTeam(TeamType teamType, Board board) {
        super(PlayerType.HUMAN, teamType, board);
    }

    @Override
    protected Ship selectShip() {
        ArrayList<String> shipDescs = new ArrayList<>();
        ArrayList<Ship> aliveShips = new ArrayList<>();

        for(Ship s : ships) {
            if (s.getStatus() != ShipStatus.SUNK) {
                shipDescs.add(s.toString());
                aliveShips.add(s);
            }
        }

        int shipToMoveId = Util.renderMenu("Choisir un bateau", shipDescs);
        return aliveShips.get(shipToMoveId);
    }

    @Override
    protected int selectAction(ArrayList<String> shipOptions) {
        return Util.renderMenu("Action à réaliser", shipOptions);
    }

    @Override
    protected String movePlayer(Ship s) {
        int newRow, newCol;
        Location space;

        System.out.println("Vous pouvez vous déplacer de " + s.getType().getSpeed() + " cases.");
        do {
            newCol = Util.readInt("Colonne de la nouvelle case : ", 1, board.getSize()) - 1;
            newRow = Util.readInt("Rangée de la nouvelle case : ", 1, board.getSize()) - 1;
            space = board.getSpace(newRow, newCol);

            if(space.isOccupied(s.getHeight()))
                System.out.println("Case occupée !");
            else if(!s.canMove(space))
                System.out.println("Case trop loin !");
        } while(space.isOccupied(s.getHeight()) || !s.canMove(space));

        s.getLocation().removeOccupant(s.getHeight());
        space.addShip(s);

        return "Déplacement réussi au " + space.getCoordsStr();
    }

    @Override
    protected String launchAttack(ArrayList<Ship> shipsToAttack) {
        if(shipsToAttack.isEmpty())
            return "Pas de navires à attaquer.";

        ArrayList<String> menuItems = new ArrayList<>();
        for(Ship as : shipsToAttack) menuItems.add(as.toString());

        int shipToAttackIndex = Util.renderMenu("Bateau à attaquer", menuItems);
        Ship shipToAttack = shipsToAttack.get(shipToAttackIndex);

        shipToAttack.sink();

        return "Le bateau en position " + shipToAttack.getLocation().getCoordsStr() + " à été coulé";
    }

}

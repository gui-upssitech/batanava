package main.board;

import main.board.proto.BoardColoring;
import main.ships.Ship;
import main.ships.proto.Height;
import main.tools.TextColor;
import main.tools.Util;

public class Location {

    public static final int RENDER_SIZE = 5;

    private final int x, y;
    private Ship surfaceOccupant, depthOccupant;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;

        this.surfaceOccupant = null;
        this.depthOccupant = null;
    }

    public void addShip(Ship ship) {
        ship.setLocation(this);
        switch (ship.getHeight()) {
            case SURFACE -> surfaceOccupant = ship;
            case DEPTH ->   depthOccupant = ship;
        }
    }

    public void removeOccupant(Height height) {
        switch (height) {
            case SURFACE -> surfaceOccupant = null;
            case DEPTH ->   depthOccupant = null;
        }
    }

    public Ship getOccupant(Height height) {
        return switch (height) {
            case SURFACE -> surfaceOccupant;
            case DEPTH -> depthOccupant;
        };
    }

    public boolean isOccupied(Height height) {
        return switch (height) {
            case SURFACE -> surfaceOccupant != null;
            case DEPTH ->   depthOccupant != null;
        };
    }

    public boolean contains(Ship ship) {
        // On vérifie que les références des objets sont identiques, on peut donc se permettre de ne pas faire equals()
        return depthOccupant == ship || surfaceOccupant == ship;
    }

    public int distanceTo(Location l) {
        return Math.max(
                Math.abs(x - l.x),
                Math.abs(y - l.y)
        );
    }

    public String getCoordsStr() {
        return "(" + (this.x+1) + "," + (this.y+1) + ")";
    }

    public String render(BoardColoring bci) {
        String surfaceStr = (surfaceOccupant == null) ? "  " : surfaceOccupant.render(bci);
        String depthStr = (depthOccupant == null) ? "  " : depthOccupant.render(bci);
        return surfaceStr + Util.genColorString("•", TextColor.BLUE) + depthStr;
    }
}

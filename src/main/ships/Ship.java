package main.ships;

import main.board.Location;
import main.board.proto.BoardColoring;
import main.ships.proto.ShipType;
import main.ships.proto.ShipStatus;
import main.ships.proto.Height;
import main.tools.TextColor;
import main.tools.Util;

public class Ship {

    private final ShipType type;
    private ShipStatus status;
    private Location location;

    private final int teamId;

    public Ship(ShipType type, int teamId) {
        this.type = type;
        this.teamId = teamId;
        this.status = ShipStatus.OK;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void damage() {
        this.status = ShipStatus.DAMAGED;
    }

    public void sink() {
        this.status = ShipStatus.SUNK;
    }

    public ShipType getType() { return type; }

    public ShipStatus getStatus() {
        return status;
    }

    public String render(BoardColoring bci) {
        return Util.genColorString(
                type.getChar() + "" + teamId,
                (status == ShipStatus.SUNK) ? TextColor.WHITE : bci.colorShip(this)
        );
    }

    public boolean canAttack(Ship s) {
        return location.distanceTo(s.location) <= type.getRange();
    }
    public boolean canMove(Location l) {
        return location.distanceTo(l) <= type.getSpeed();
    }

    public Height getHeight() {
        return type.getHeight();
    }

    public String toString() {
        String locationDesc = (this.location == null) ? "null" : this.location.getCoordsStr();
        return "[" + this.type + "] - " + locationDesc + " - " + this.status;
    }
}

package main.board.proto;

import main.ships.Ship;
import main.tools.TextColor;

public interface BoardColoring {
    TextColor colorShip(Ship ship);
}

package main.ships.proto;

public enum ShipType {

    // ELEMENTS

    DESTROYER("Destroyer", 'D', Height.SURFACE, 1, 2),
    SUBMARINE("Sous-marin", 'S', Height.DEPTH, 2, 1),
    TRAWLER("Chalutier", 'C', Height.SURFACE, 0, 3);

    // CLASS DATA

    private final String name;
    private final char desc;
    private final int range, speed;
    private final Height height;

    ShipType(String name, char desc, Height height, int range, int speed) {
        this.name = name;
        this.desc = desc;
        this.height = height;
        this.range = range;
        this.speed = speed;
    }

    public char getChar() {
        return desc;
    }

    public int getRange() {
        return range;
    }

    public int getSpeed() {
        return speed;
    }

    public Height getHeight() {
        return height;
    }

    public String toString() {
        return name;
    }
}

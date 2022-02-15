package main.tools;

// Source : https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

public enum TextColor {
    RESET       (0),

    BLACK       (30),
    RED         (31),
    GREEN       (32),
    YELLOW      (33),
    BLUE        (34),
    PURPLE      (35),
    CYAN        (36),
    WHITE       (37),

    BG_BLACK    (40),
    BG_RED      (41),
    BG_GREEN    (42),
    BG_YELLOW   (43),
    BG_BLUE     (44),
    BG_PURPLE   (45),
    BG_CYAN     (46),
    BG_WHITE    (47);


    private final int id;

    TextColor(int id) {
        this.id = id;
    }

    public String toString() {
        return "\u001B[" + id + "m";
    }
}

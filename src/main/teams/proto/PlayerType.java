package main.teams.proto;

public enum PlayerType {

    // ELEMENTS

    HUMAN("Humain"),
    AI("Robot-kun");

    // CLASS DATA

    private String str;

    PlayerType(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}

package main.teams.proto;

public enum TeamType {
    // ELEMENTS

    MILITARY("Militaires"),
    NEUTRAL("Civils");

    // CLASS DATA

    private String str;

    TeamType(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}

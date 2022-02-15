package main.teams.proto;

public enum ActionType {
    MOVE("DÉPLACER"),
    ATTACK("ATTAQUER"),
    FISH("PÊCHER");

    private String desc;
    ActionType(String desc) {
        this.desc = desc;
    }

    public static ActionType fromDesc(String desc) {
        for(ActionType a : ActionType.values()) {
            if(a.toString().equals(desc)) return a;
        }
        return MOVE;
    }

    public String toString() {
        return desc;
    }
}

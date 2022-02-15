package main.ships.proto;

public enum ShipStatus {
    OK("OK"),
    DAMAGED("DAMAGED"),
    SUNK("SUNK");


    private String desc;
    ShipStatus(String desc) {
        this.desc = desc;
    }

    public String toString(){
        return desc;
    }
}

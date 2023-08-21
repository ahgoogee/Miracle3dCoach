package com.miracle3d.coach.monitor.enums;


public enum PlaySide {
    LEFT("left"),
    RIGHT("right"),
    UNKNOWN("unknown");

    private final String playSideString;

    private PlaySide(String playSideString) {
        this.playSideString = playSideString;
    }

    public static PlaySide parsePlaySide(String playSideString) {
        PlaySide[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            PlaySide side = var1[var3];
            if (playSideString.equalsIgnoreCase(side.playSideString)) {
                return side;
            }
        }

        return UNKNOWN;
    }

    public String getName() {
        return this.playSideString;
    }

    public PlaySide getOpposite() {
        switch (this) {
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                return UNKNOWN;
        }
    }
}

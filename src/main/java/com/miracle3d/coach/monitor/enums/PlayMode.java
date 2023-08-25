package com.miracle3d.coach.monitor.enums;

public enum PlayMode {
    BEFORE_KICK_OFF("BeforeKickOff"),
    KICK_OFF_LEFT("KickOff_Left"),
    KICK_OFF_RIGHT("KickOff_Right"),
    PLAY_ON("PlayOn"),
    KICK_IN_LEFT("KickIn_Left"),
    KICK_IN_RIGHT("KickIn_Right"),
    CORNER_KICK_LEFT("corner_kick_left"),
    CORNER_KICK_RIGHT("corner_kick_right"),
    GOAL_KICK_LEFT("goal_kick_left"),
    GOAL_KICK_RIGHT("goal_kick_right"),
    OFFSIDE_LEFT("offside_left"),
    OFFSIDE_RIGHT("offside_right"),
    GAME_OVER("GameOver"),
    GOAL_LEFT("Goal_Left"),
    GOAL_RIGHT("Goal_Right"),
    FREE_KICK_LEFT("free_kick_left"),
    FREE_KICK_RIGHT("free_kick_right"),
    DIRECT_FREE_KICK_LEFT("direct_free_kick_left"),
    DIRECT_FREE_KICK_RIGHT("direct_free_kick_right"),
    NONE("NONE"),
    SET("Set");

    private final String name;

    PlayMode(String name) {
        this.name = name;
    }

    public static PlayMode parsePlayMode(String playModeString) {
        PlayMode[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            PlayMode mode = var1[var3];
            if (playModeString.equalsIgnoreCase(mode.name)) {
                return mode;
            }
        }

        return NONE;
    }

    public boolean isBeamingAllowed() {
        return this == BEFORE_KICK_OFF || this == GOAL_LEFT || this == GOAL_RIGHT;
    }

    public String getServerString() {
        return this.name;
    }
}

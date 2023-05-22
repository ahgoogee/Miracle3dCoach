package monitor.enums;


public class Foul {
    public final float time;
    public final int index;
    public final FoulType type;
    public final PlaySide playSide;
    public final int agentID;

    public Foul(float time, int index, FoulType type, int team, int agentID) {
        this.time = time;
        this.index = index;
        this.type = type;
        this.agentID = agentID;
        this.playSide = this.getPlaySide(team);
    }

    private PlaySide getPlaySide(int team) {
        switch (team) {
            case 1:
                return PlaySide.LEFT;
            case 2:
                return PlaySide.RIGHT;
            default:
                return PlaySide.UNKNOWN;
        }
    }

    public static enum FoulType {
        CROWDING(0, "crowding"),
        TOUCHING(1, "touching"),
        ILLEGAL_DEFENCE(2, "illegal defence"),
        ILLEGAL_ATTACK(3, "illegal attack"),
        INCAPABLE(4, "incapable"),
        ILLEGAL_KICK_OFF(5, "illegal kickoff"),
        CHARGING(6, "charging");

        private int index;
        private String name;

        private FoulType(int index, String name) {
            this.index = index;
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        public String getName() {
            return this.name;
        }
    }
}

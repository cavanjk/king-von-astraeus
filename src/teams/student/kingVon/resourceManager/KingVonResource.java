package teams.student.kingVon.resourceManager;

import objects.resource.Resource;
import player.Player;

public class KingVonResource extends Resource {
    private final Player player;
    boolean targeted;

    public KingVonResource(float x, float y, Player player) {
        super(x, y);
        this.player = player;
    }

    public void setTargeted(boolean bool) {
        targeted = bool;
    }

    public boolean isTargeted() {
        return targeted;
    }
}

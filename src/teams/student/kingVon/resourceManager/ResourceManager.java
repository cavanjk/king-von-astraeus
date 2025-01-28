package teams.student.kingVon.resourceManager;

import objects.entity.unit.Unit;
import objects.resource.Resource;
import player.Player;
import teams.student.kingVon.KingVon;

import java.util.ArrayList;

public class ResourceManager {

    private final Player player;
    private ArrayList<KingVonResource> resources;

    public ResourceManager(Unit unit) {
        this.player = unit.getPlayer();
        resources = new ArrayList<>();
    }

    public void registerResources() {
        for (Resource r : player.getAllResources()) {
            resources.add(new KingVonResource(r.getX(), r.getY()));
        }
    }

    public ArrayList<KingVonResource> getResources() {
        return resources;
    }
}

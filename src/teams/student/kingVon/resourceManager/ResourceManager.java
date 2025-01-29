package teams.student.kingVon.resourceManager;

import objects.entity.unit.Unit;
import objects.resource.Resource;
import player.Player;
import teams.student.kingVon.KingVon;

import java.util.ArrayList;

public class ResourceManager {

    private static KingVon player;
    private static final ArrayList<KingVonResource> resources = new ArrayList<>();

    public static void initialize(KingVon player) {
        ResourceManager.player = player;
    }

    public static void registerResources() {
        if (player == null) {
            throw new IllegalStateException("ResourceManager not initialized.");
        }
        resources.clear();
        for (Resource r : player.getAllResources()) {
            resources.add(new KingVonResource(r.getX(), r.getY(), player));
        }
    }

    public static ArrayList<KingVonResource> getResources() {
        return resources;
    }
}

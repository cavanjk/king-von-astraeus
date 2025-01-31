package teams.student.kingVon.resourceManager;

import objects.entity.unit.Unit;
import objects.resource.Resource;
import player.Player;
import teams.student.kingVon.KingVon;
import teams.student.kingVon.units.Gatherer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ResourceManager {

    private static KingVon player;
    private static final HashMap<Resource, Gatherer> resourceMap = new HashMap<>();
    private static final ArrayList<Resource> resources = new ArrayList<>();
    private static final ArrayList<Gatherer> gatherers = new ArrayList<>();

    public static void initialize(KingVon player) {
        ResourceManager.player = player;
    }

    public static void registerResources() {
        if (player == null) {
            throw new IllegalStateException("ResourceManager not initialized.");
        }
        resourceMap.clear();
    }

    public static HashMap<Resource, Gatherer> getResourceMap() {
        return resourceMap;
    }

    public static ArrayList<Resource> getResources() {
        return resources;
    }

    public static ArrayList<Gatherer> getGatherers() {
        return gatherers;
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}

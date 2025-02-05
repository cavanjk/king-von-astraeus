package teams.student.kingVon.resourceManager;

import objects.entity.unit.Unit;
import objects.resource.Resource;
import teams.student.kingVon.KingVon;
import teams.student.kingVon.units.Gatherer;

import java.util.*;

public class ResourceManager {

    private static KingVon player;
    private static final HashMap<Resource, Gatherer> resourceMap = new HashMap<>();
    private static final HashMap<Resource, Boolean> resourceGathererHashMap = new HashMap<>();
    private static final ArrayList<Gatherer> gatherers = new ArrayList<>();
    static boolean init = false;

    public static void initialize(KingVon player) {
        ResourceManager.player = player;
    }

    public static void registerResources() {
        if (player == null) {
            throw new IllegalStateException("error: resource managr  is not initialized.");
        }
        resourceMap.clear();
    }

    public static HashMap<Resource, Gatherer> getResourceMap() {
        return resourceMap;
    }

    public static HashMap<Resource, Boolean> getResourceGathererHashMap() {
        return resourceGathererHashMap;
    }

    public static ArrayList<Gatherer> getGatherers() {
        return gatherers;
    }

    public static ArrayList<Resource> getSortedResources(Unit homeBase) {
        ArrayList<Resource> sortedResources = new ArrayList<>(objects.resource.ResourceManager.getResources());
        sortedResources.sort(Comparator.comparingDouble(homeBase::getDistance));
        if (!init) {
            for (Resource r : sortedResources) {
                getResourceGathererHashMap().put(r, false);
            }
            init = true;
        }
        return sortedResources;
    }
}

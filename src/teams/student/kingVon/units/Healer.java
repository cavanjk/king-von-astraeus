package teams.student.kingVon.units;

import components.mod.healing.ApolloMod;
import components.upgrade.Shield;
import components.weapon.utility.RepairBeam;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import player.Player;
import teams.student.kingVon.KingVonUnit;
import teams.student.kingVon.units.Armorer;
import teams.student.kingVon.units.Fighter;

import java.util.ArrayList;

public class Healer extends KingVonUnit {

    private static final float SAFE_DISTANCE = 100f;

    public Healer(Player p) {
        super(p);
    }

    public void design() {
        setFrame(Frame.MEDIUM);
        setModel(Model.CRUISER);
        setStyle(Style.ROCKET);
        add(Shield.class);
        add(ApolloMod.class);
        add(RepairBeam.class);
    }

    public void action() {
        ArrayList<Unit> fighters = getPlayer().getMyUnits(Fighter.class);
        ArrayList<Unit> armorers = getPlayer().getMyUnits(Armorer.class);

        Unit healTarget = getLowestHealthUnit(armorers.isEmpty() ? fighters : armorers);
        if (healTarget != null) {
            moveTo(healTarget.getCenterX() - SAFE_DISTANCE, healTarget.getCenterY());
            getWeaponOne().use(healTarget);
        }
    }

    private Unit getLowestHealthUnit(ArrayList<Unit> units) {
        Unit lowestHealthUnit = null;
        float minHealth = Float.MAX_VALUE;
        for (Unit u : units) {
            float health = u.getCurEffectiveHealth();
            if (health < minHealth) {
                minHealth = health;
                lowestHealthUnit = u;
            }
        }
        return lowestHealthUnit;
    }
}

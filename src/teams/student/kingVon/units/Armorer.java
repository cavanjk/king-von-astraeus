package teams.student.kingVon.units;

import components.mod.offense.NyxMod;
import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.explosive.Missile;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.kingVon.KingVon;
import teams.student.kingVon.KingVonUnit;

public class Armorer extends KingVonUnit {
    private KingVon p;

    public Armorer(KingVon p) {
        super(p);
        this.p = p;
    }

    public void design() {
        setFrame(Frame.HEAVY);
        setModel(Model.BASTION);
        setStyle(Style.WEDGE);
        add(Missile.class);
        add(Plating.class);
        add(Shield.class);
        add(NyxMod.class);
    }

    public void action() {
        Unit enemy = getNearestEnemy();
        if (enemy != null) {
            moveTo(enemy);
            getWeaponOne().use(enemy);
        }
    }

}

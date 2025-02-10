package teams.student.kingVon.units;

import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.economy.Collector;
import components.weapon.energy.Laser;
import components.weapon.kinetic.Autocannon;
import engine.Main;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;
import player.Player;
import teams.student.kingVon.KingVon;

import java.util.ArrayList;

public class Tank extends Unit {
    private boolean isInFormation = true;
    private static final float DEFEND_RANGE = 1000f;
    private static final float ORBIT_RADIUS = 500f;
    int timer;
    private KingVon p;
    int loc = 4;

    public Tank(KingVon p) {
        super(p);
        this.p = p;
        timer = 0;
    }

    public void design() {
        setFrame(Frame.HEAVY);
        setModel(Model.BASTION);
        setStyle(Style.WEDGE);
        add(Plating.class);
        add(Plating.class);
        add(Collector.class);
    }

    @Override
    public void draw(Graphics g) {

    }

    public void action() {

        for (Tank t : p.getTanks()) {
            if (t == this) {
                switch (loc) {
                    case 4:
                        moveTo((float) getHomeBase().getCenterX() + 500, (float) getHomeBase().getCenterY() + 500);
                        if (loc < 4) {
                            turnTo((float) getHomeBase().getCenterX() + 520, (float) getHomeBase().getCenterY() + 500);
                            move();
                        }
                    case 3:
                        moveTo((float) getHomeBase().getCenterX() + 500, (float) getHomeBase().getCenterY() + 200);
                        if (loc < 3) {
                            turnTo((float) getHomeBase().getCenterX() + 520, (float) getHomeBase().getCenterY() + 200);
                            move();
                        }
                    case 2:
                        moveTo((float) getHomeBase().getCenterX() + 500, (float) getHomeBase().getCenterY() - 200);
                        if (loc < 2) {
                            turnTo((float) getHomeBase().getCenterX() + 520, (float) getHomeBase().getCenterY() - 200);
                            move();
                        }
                    case 1:
                        moveTo((float) getHomeBase().getCenterX() + 500, (float) getHomeBase().getCenterY() - 500);
                        if (loc < 1) {
                            turnTo((float) getHomeBase().getCenterX() + 520, (float) getHomeBase().getCenterY() - 500);
                            move();
                        }
                }
                p.getTanks().remove(this);
                loc--;
            }
        }
    }

    public void movement()
    {
        if(!isInFormation) {
            Unit target = getLowestHealthEnemyInRadius(getMaxRange());
            if (target != null) {
                float angle = getAngleToward(target.getCenterX(), target.getCenterY());
                float orbitX = target.getCenterX() + ORBIT_RADIUS * (float) Math.cos(Math.toRadians(angle) + (float) ((this.hashCode() % 100) *3.6) );
                float orbitY = target.getCenterY() + ORBIT_RADIUS * (float) Math.sin(Math.toRadians(angle) + (float) ((this.hashCode() % 100)*3.6)) ;

                moveTo(orbitX, orbitY);
            }
            else
            {
                moveTo(getHomeBase());
            }

        }

    }


    private Unit getLowestHealthEnemyInRadius(float radius)
    {
        ArrayList<Unit> enemies = getEnemiesInRadius(radius, Unit.class);
        if(enemies.isEmpty())
        {
            return null;
        }
        Unit lowestHealthUnit = null;
        float minHealth = Float.MAX_VALUE;
        for(Unit u : enemies)
        {
            float health = u.getCurEffectiveHealth();
            if(health < minHealth)
            {
                minHealth = health;
                lowestHealthUnit = u;
            }
        }
        return lowestHealthUnit;
    }
}

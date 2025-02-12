package teams.student.kingVon.units;

import components.upgrade.Plating;
import components.weapon.economy.Collector;
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
    private boolean hasReachedTarget = false;
    private static final float[] TARGET_Y = {2500, 0, -2500};
    private static final float TARGET_X = -300;
    private static final float Y_ADJUSTMENT = 50;
    private static final float ORBIT_RADIUS = 100;
    private static final float RETREAT_DISTANCE = 300;
    private int tankIndex;
    private KingVon p;
    private boolean retreating = false;
    private Unit targetEnemy = null;

    public Tank(KingVon p, int index) {
        super(p);
        this.p = p;
        this.tankIndex = index % TARGET_Y.length;
    }

    public void design() {
        setFrame(Frame.MEDIUM);
        setModel(Model.BASTION);
        setStyle(Style.ORB);
        add(Plating.class);
        add(Plating.class);
        add(Collector.class);
    }

    @Override
    public void draw(Graphics g) {
    }

    public void action() {
        float targetY = TARGET_Y[tankIndex] + ((this.hashCode() % 2 == 0) ? Y_ADJUSTMENT : -Y_ADJUSTMENT);
        if (!hasReachedTarget) {
            moveTo(TARGET_X, targetY);
            if (getDistance(TARGET_X, targetY) < 10) {
                hasReachedTarget = true;
            }
        } else {
            if (retreating) {
                moveTo(getCenterX() + RETREAT_DISTANCE, getCenterY());
                if (getDistance(targetEnemy) > RETREAT_DISTANCE * 2) {
                    retreating = false;
                    targetEnemy = null;
                }
            } else if (targetEnemy != null) {
                moveTo(targetEnemy.getCenterX(), targetEnemy.getCenterY());
                retreating = true;
            } else {
                targetEnemy = nearestEnemy();
                if (targetEnemy == null) {
                    orbitAroundPoint(TARGET_X, targetY);
                }
            }
        }
    }

    private void orbitAroundPoint(float x, float y) {
        float angle = getAngleToward(x, y) + 90;
        float orbitX = x + ORBIT_RADIUS * (float) Math.cos(Math.toRadians(angle));
        float orbitY = y + ORBIT_RADIUS * (float) Math.sin(Math.toRadians(angle));
        moveTo(orbitX, orbitY);
    }

    private Unit nearestEnemy() {
        ArrayList<Unit> enemies = getEnemiesInRadius(1000, Unit.class);
        Unit nearest = null;
        float minDist = Float.MAX_VALUE;
        for (Unit u : enemies) {
            float dist = getDistance(u);
            if (dist < minDist) {
                minDist = dist;
                nearest = u;
            }
        }
        return nearest;
    }
}

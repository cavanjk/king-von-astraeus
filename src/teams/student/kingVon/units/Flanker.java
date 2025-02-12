package teams.student.kingVon.units;

import components.upgrade.Shield;
import components.weapon.economy.Collector;
import components.weapon.kinetic.Autocannon;
import engine.states.Game;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.geom.Point;
import player.Player;
import teams.student.kingVon.KingVonUnit;

import java.util.ArrayList;

public class Flanker extends KingVonUnit {

    private static final float MIN_DISTANCE_FROM_HOME = 1200f;
    private boolean isInFormation = true;
    private static final float RALLY_POINT_X = 0;
    private static final float RALLY_POINT_Y = Game.getMapBottomEdge() * 0.4f;
    private boolean isAtRallyPoint = false;
    private boolean isMovingToRally = false;
    private boolean isSneaking = false;
    private boolean isAttacking = false;
    private static final float MIN_ALLY_DISTANCE = 120f;


    public Flanker(Player p) {
        super(p);
    }

    public void design() {
        setFrame(Frame.LIGHT);
        setModel(Model.STRIKER);
        setStyle(Style.DAGGER);

        add(Autocannon.class);
        add(Shield.class);
    }

    public void action() {
        if (!isAtRallyPoint) {
            if (isInFormation) {
                if (getPlayer().countMyUnits(Flanker.class) >= 15) {
                    isInFormation = false;
                    isMovingToRally = true;
                } else {
                    moveTo(getHomeBase());
                    return;
                }
            }
            if (isMovingToRally && (int) getDistance(new Point(RALLY_POINT_X, RALLY_POINT_Y)) > 50) {
                Point adjustedRallyPoint = getSpace(RALLY_POINT_X, RALLY_POINT_Y);
                if(adjustedRallyPoint != null){
                    moveTo(RALLY_POINT_X + adjustedRallyPoint.getX(), RALLY_POINT_Y + adjustedRallyPoint.getY());
                } else {
                    moveTo(new Point(RALLY_POINT_X, RALLY_POINT_Y));
                }

                return;
            } else if (isMovingToRally) {
                isAtRallyPoint = true;
                isSneaking = true;
            } else {
                moveTo(getHomeBase());
                return;
            }
        }

        if (isSneaking) {
            sneakAlongBorder();
        } else if (isAttacking) {
            attackCollectors();
        }
    }

    private void sneakAlongBorder() {
        float targetX = getCenterX() + 50;
        float targetY = Game.getMapBottomEdge() * 0.4f;

        if (targetX >= Game.getMapRightEdge() * 0.4f) {
            isSneaking = false;
            isAttacking = true;
            return;
        }

        Point adjustedTarget = getSpace(targetX, targetY);
        if(adjustedTarget != null){
            moveTo(targetX + adjustedTarget.getX(), targetY + adjustedTarget.getY());
        } else {
            moveTo(targetX, targetY);
        }
    }

    private void attackCollectors() {
        Unit target = getNearestDistantGatherer();

        if (target == null) {
            target = getEnemyBase();
            if (target == null) {
                move();
                return;
            }

            if (getDistance(target) > getMaxRange() * .75f) {
                Point adjustedTargetPoint = getSpace(target.getCenterX(), target.getCenterY());
                if(adjustedTargetPoint != null){
                    moveTo(target.getCenterX() + adjustedTargetPoint.getX(), target.getCenterY() + adjustedTargetPoint.getY());
                } else {
                    moveTo(target);
                }

            } else {
                turnTo(target);
                move();
            }
        } else {
            if (getDistance(target) > getMaxRange() * .75f) {
                Point adjustedTargetPoint = getSpace(target.getCenterX(), target.getCenterY());
                if(adjustedTargetPoint != null){
                    moveTo(target.getCenterX() + adjustedTargetPoint.getX(), target.getCenterY() + adjustedTargetPoint.getY());
                } else {
                    moveTo(target);
                }
            } else {
                turnTo(target);
                move();
            }
        }

        if (!getWeaponOne().onCooldown() && getDistance(target) <= getMaxRange()) {
            getWeaponOne().use(target);
        }
    }

    private Unit getNearestDistantGatherer() {
        ArrayList<Unit> enemies = getEnemies();

        if (enemies.isEmpty()) {
            return null;
        }

        Unit nearestGatherer = null;
        float minDistance = Float.MAX_VALUE;

        for (Unit u : enemies) {
            if (u.hasWeapon(Collector.class) && getHomeBase().getDistance(u) > MIN_DISTANCE_FROM_HOME) {
                float distance = getDistance(u);
                if (distance < minDistance) {
                    nearestGatherer = u;
                    minDistance = distance;
                }
            }
        }
        return nearestGatherer;
    }


    private Point getSpace(float targetX, float targetY) {
        ArrayList<Unit> allies = getFriendlyUnitsInRadius(MIN_ALLY_DISTANCE * 2, Flanker.class);
        float spaceX = 0;
        float spaceY = 0;
        int closeAllies = 0;

        for (Unit ally : allies) {
            if (ally != this) { // Don't check its self
                float distance = getDistance(ally);
                if (distance < MIN_ALLY_DISTANCE) {
                    closeAllies++;
                    float angleAway = getAngleAway(ally.getCenterX(), ally.getCenterY());
                    spaceX += (float) Math.cos(Math.toRadians(angleAway));
                    spaceY += (float) Math.sin(Math.toRadians(angleAway));
                }
            }
        }

        if (closeAllies > 0) {
            float magnitude = (float) Math.sqrt(spaceX * spaceX + spaceY * spaceY);
            if (magnitude > 0) {
                spaceX /= magnitude;
                spaceY /= magnitude;

                spaceX *= MIN_ALLY_DISTANCE * 0.5f; // Apply the avoidance factor here.
                spaceY *= MIN_ALLY_DISTANCE * 0.5f;

                return new Point(spaceX, spaceY);
            }
        }

        return null;
    }

    private ArrayList<Unit> getFriendlyUnitsInRadius(float radius, Class<? extends Unit> unitType) {
        ArrayList<Unit> friendlyUnits = new ArrayList<>();
        for (Unit unit : getPlayer().getMyUnits()) {
            if (unitType.isInstance(unit) && getDistance(unit) <= radius && unit != this) {
                friendlyUnits.add(unit);
            }
        }
        return friendlyUnits;
    }
}

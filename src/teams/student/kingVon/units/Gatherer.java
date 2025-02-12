package teams.student.kingVon.units;

import components.weapon.economy.Collector;
import components.weapon.energy.Laser;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import teams.student.kingVon.KingVon;
import teams.student.kingVon.KingVonUnit;
import teams.student.kingVon.resourceManager.ResourceManager;

public class Gatherer extends KingVonUnit
{
	int timer;
	private Resource target;

	public Gatherer(KingVon p)
	{
		super(p);
		timer = 0;
		target = null;
	}

	public void design()
	{
		setFrame(Frame.LIGHT);
		setModel(Model.TRANSPORT);
		setStyle(Style.BUBBLE);
		if (timer > 45000)
		{
			add(Laser.class);
		}
		else
		{
			add(Collector.class);
		}
	}

	public void action()
	{
		timer++;
		if (timer > 45000)
		{
			endGameStrategy();
		}
		else
		{
			returnResources();
			gatherResources();
		}
	}

	public void returnResources() {
		if (isFull()) {
			if (timer > 25000) {
				Resource r = getNearestResource();
				if(r != null) {
					moveTo(r);
					((Collector) getWeaponOne()).use(r);
				}
            } else {
				if (target != null) {
					ResourceManager.getResourceGathererHashMap().put(target, false);
					target = null;
				}
				moveTo(getHomeBase());
				deposit();
			}
		}
	}

	public void gatherResources() {
		if (hasCapacity()) {
			if (timer > 25000) {
				getPlayer().addMessage("default gatherter");
				Resource r = getNearestResource();
				if(r != null) {
					moveTo(r);
					((Collector) getWeaponOne()).use(r);
				}
				return;
			} else {
				getPlayer().addMessage("not default gatherter");
				Resource closestAvailable = getClosestAvailableResource();
				if (closestAvailable != null && (target == null || shouldSwitchTarget(closestAvailable))) {
					if (target != null) {
						releaseResource(target);
					}
					target = closestAvailable;
					ResourceManager.getResourceMap().put(target, this);
					ResourceManager.getResourceGathererHashMap().put(target, true);
				}
				if (target != null) {
					moveTo(target);
					((Collector) getWeaponOne()).use(target);
				}
			}
		}
	}

	private boolean shouldSwitchTarget(Resource newTarget) {
		Gatherer assignedGatherer = ResourceManager.getResourceMap().get(newTarget);
		if (assignedGatherer == null) {
			return true;
		}
		double myDistance = getDistance(newTarget);
		double otherDistance = assignedGatherer.getDistance(newTarget);
		return myDistance < otherDistance;
	}

	private Resource getClosestAvailableResource() {
		double minDistance = Double.MAX_VALUE;
		Resource closest = null;
		for (Resource r : objects.resource.ResourceManager.getResources()) {
			if (doesResourceExist(r)) {
				Gatherer assignedGatherer = ResourceManager.getResourceMap().get(r);
				double distance = getDistance(r);
				if (assignedGatherer == null || distance < assignedGatherer.getDistance(r)) {
					if (distance < minDistance) {
						minDistance = distance;
						closest = r;
					}
				}
			}
		}
		return closest;
	}

	private void releaseResource(Resource r) {
		if (r != null) {
			ResourceManager.getResourceGathererHashMap().put(r, false);
			ResourceManager.getResourceMap().remove(r);
		}
	}


	private boolean doesResourceExist(Resource r)
	{
		return objects.resource.ResourceManager.getResources().contains(r);
	}

	public Resource getTarget()
	{
		return target;
	}


	public void endGameStrategy()
	{
		if (getPlayer().countMyUnits() >= getPlayer().getMaxFleetSize())
		{
			die();
		}
		else
		{
			Unit enemy = getNearestEnemy();

			if(enemy != null && getWeaponOne() != null)
			{
				getWeaponOne().use(enemy);
			}

			if(enemy != null)
			{
				if(getDistance(enemy) > getMaxRange())
				{
					moveTo(enemy);
				}
				else
				{
					turnTo(enemy);
					turnAround();
					move();
				}
			}
		}
	}
}

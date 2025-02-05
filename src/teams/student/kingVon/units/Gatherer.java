package teams.student.kingVon.units;

import components.weapon.economy.Collector;
import components.weapon.energy.Laser;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import org.lwjgl.Sys;
import teams.student.kingVon.KingVon;
import teams.student.kingVon.KingVonUnit;
import teams.student.kingVon.resourceManager.ResourceManager;

public class Gatherer extends KingVonUnit
{
	int timer;
	private Resource target;
 private AreayList<Resource> sortedReesources;

	public Gatherer(KingVon p)
	{
		super(p);
		timer = 0;
		target = null;
  sortedResources = ResourceManager.getSortedResources(getHomeBase());
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
			if (target != null) {
				ResourceManager.getResourceGathererHashMap().put(target, false);
				target = null;
			}
			moveTo(getHomeBase());
			deposit();
   sortedResources = ResourceManager.getSortedResources(this);
		}
	}

	public void gatherResources() {
		if (hasCapacity()) {
			if (target == null || !doesResourceExist(target)) {
				for (Resource r : sortedResources) {
					if (doesResourceExist(r) && !ResourceManager.getResourceGathererHashMap().getOrDefault(r, false)) {
						ResourceManager.getResourceMap().put(r, this);
						ResourceManager.getResourceGathererHashMap().put(r, true);
						moveTo(r);
						((Collector) getWeaponOne()).use(r);
						target = r;
						return;
					}
				}
			} else {
				moveTo(target);
				((Collector) getWeaponOne()).use(target);
			}
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

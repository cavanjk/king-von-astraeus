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

	public Gatherer(KingVon p)
	{
		super(p);
		timer = 0;
		boolean isGathering = false;
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

	public void returnResources()
	{
		if(isFull())
		{
			moveTo(getHomeBase());
			deposit();
		}
	}

	public void gatherResources()
	{
		if(hasCapacity())
		{
			Resource r = getNearestResource();
			ResourceManager.getResources().add(r);
			if(r != null) {
				if (!isResourceBeingGathered(r)) {
					ResourceManager.getResourceMap().put(r, this);
					moveTo(r);
					((Collector) getWeaponOne()).use(r);
					if (getDistance(getHomeBase()) < 100 && getTarget() != null) {
						ResourceManager.getResourceMap().remove(r, this);
					}
				}
			}
		}
	}


	private boolean isResourceBeingGathered(Resource r)
	{
		for (Gatherer gatherer : ResourceManager.getGatherers())
		{
			if (gatherer.getTarget() == r)
			{
				return true;
			}
		}
		return false;
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

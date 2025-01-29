package teams.student.kingVon.units;

import components.weapon.economy.Collector;
import components.weapon.energy.Laser;
import engine.states.Game;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import objects.resource.ResourceManager;
import teams.student.kingVon.KingVon;
import teams.student.kingVon.KingVonUnit;
import teams.student.kingVon.resourceManager.KingVonResource;

public class Gatherer extends KingVonUnit
{
	int timer;
	public Gatherer(KingVon p)
	{
		super(p);
		timer = 0;
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
			KingVonResource r = (KingVonResource) getNearestResource();
			if(r != null)
			{
				if (!r.isTargeted()) {
					moveTo(r);
					((Collector) getWeaponOne()).use(r);
				} else {
					gatherResources();
				}
			}
		}
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

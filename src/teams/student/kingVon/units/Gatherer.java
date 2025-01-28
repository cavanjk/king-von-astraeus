package teams.student.kingVon.units;


import components.weapon.economy.Collector;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.resource.Resource;
import teams.student.kingVon.KingVon;
import teams.student.kingVon.KingVonUnit;

public class Gatherer extends KingVonUnit
{

	public Gatherer(KingVon p)
	{
		super(p);
	}
	
	public void design()
	{
		setFrame(Frame.LIGHT);
		setModel(Model.TRANSPORT);
		setStyle(Style.BUBBLE);
		add(Collector.class);
	}

	public void action() 
	{
		returnResources();
		gatherResources();
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
			if(r != null)
			{
				moveTo(r);
				((Collector) getWeaponOne()).use(r);
			}
		}
	}


}

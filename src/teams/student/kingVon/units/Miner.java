package teams.student.kingVon.units;


import components.weapon.Weapon;
import components.weapon.economy.Drillbeam;
import objects.entity.node.Node;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.kingVon.KingVon;
import teams.student.kingVon.KingVonUnit;

public class Miner extends KingVonUnit
{
	
	public Miner(KingVon p)
	{
		super(p);
	}
	
	public void design()
	{
		setFrame(Frame.LIGHT);
		setModel(Model.ARTILLERY);
		setStyle(Style.BOXY);
		add(Drillbeam.class);
	}

	public void action() 
	{
		harvest(unifiedNode, getWeaponOne());
		updateUnifiedNode(getNearestNode());
	}

	public void harvest(Node n, Weapon w)
	{
		// Approach the node
		if(getDistance(n) > w.getMaxRange() * .5f)
		{
			moveTo(n);
		}
		// Back up if I'm close to my minimum range
		else if(getDistance(n) < w.getMinRange() * 1.5f)
		{
			turnTo(n);
			turnAround();
			move();
		}
		w.use(n);
	}
	
}

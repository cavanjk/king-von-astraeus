package teams.student.simple.units;


import components.weapon.Weapon;
import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import objects.entity.node.Node;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.simple.Simple;
import teams.student.simple.SimpleUnit;

public class Miner extends SimpleUnit
{
	
	public Miner(Simple p)
	{
		super(p);
	}
	
	public void design()
	{
		setFrame(Frame.LIGHT);
		setModel(Model.DESTROYER);
		setStyle(Style.BOXY);
		add(Drillbeam.class);
	}

	public void action() 
	{
		harvest(getNearestNode(), getWeaponOne());
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

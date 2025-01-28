package teams.student.kingVon.units;

import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.energy.Laser;
import components.weapon.kinetic.Autocannon;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.kingVon.KingVon;
import teams.student.kingVon.KingVonUnit;

public class Fighter extends KingVonUnit
{
	
	public Fighter(KingVon p)
	{
		super(p);
	}
	
	public void design()
	{	
		setFrame(Frame.HEAVY);
		setModel(Model.CRUISER);
		setStyle(Style.ARROW);

		add(Autocannon.class);
		add(Laser.class);
		add(Shield.class);
		add(Plating.class);

	}



}

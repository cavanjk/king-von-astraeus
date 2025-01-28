package teams.student.kingVon;

import components.weapon.economy.Collector;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import org.newdawn.slick.Graphics;
import player.Player;
import teams.student.kingVon.units.*;


public class KingVon extends Player
{
	public void setup()
	{		
		setName("KingVon");
		setTeamImage("src/teams/student/KingVon/teamLogo.png");
		setTitle("KingVon Team");

		setColorPrimary(170, 170, 170);
		setColorSecondary(200, 200, 50);
		setColorAccent(255, 255, 255);
	}
	
	public void strategy() 
	{
//		if(getFleetValueUnitPercentage(Thrower.class) < .4f)
//		{
//			buildUnit(new Thrower(this));
//		}
//		else if(getFleetValueUnitPercentage(CloseGatherer.class) < .3f)
//		{
//			buildUnit(new CloseGatherer(this));
//		}
//		else if(getFleetValueUnitPercentage(Miner.class) < .3f)
//		{
//			buildUnit(new Miner(this));
//		}
//		else
//		{
//			buildUnit(new Fighter(this));
//		}
		float throwerPercent = getFleetValueUnitPercentage(Thrower.class);
		float closePercent = getFleetValueUnitPercentage(CloseGatherer.class);
		float minerPercent = getFleetValueUnitPercentage(Miner.class);
		float flankerPercent = getFleetValueUnitPercentage(Flanker.class);
		float fighterPercent = getFleetValueUnitPercentage(Fighter.class);


		if (getMyUnits(Fighter.class).size() < 2 )
		{
			buildUnit(new Fighter(this));
			return;
		}
		if (throwerPercent < 0.2f)
		{
			buildUnit(new Thrower(this));
			return;
		}
		else if (closePercent < 0.1f)
		{
			buildUnit(new CloseGatherer(this));
			return;
		}
		else if (minerPercent < 0.1f)
		{
			buildUnit(new Miner(this));
			return;
		}

		if (!anyEnemyHasCollector()) {
			if (fighterPercent < .8f) {
				buildUnit(new Fighter(this));
			}
			return;
		}
		else
		{
			if (flankerPercent < .8f) {
				buildUnit(new Flanker(this));
			}
			else {
				buildUnit(new Fighter(this));
			}

		}

	}

	private boolean anyEnemyHasCollector()
	{
		for (Unit u : getEnemyUnits())
		{
			if (u.hasComponent(Collector.class))
			{
				return true;
			}
		}
		return false;
	}
			
	public void draw(Graphics g) 
	{
		
	}
	
}

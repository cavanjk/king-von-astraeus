package teams.student.kingVon;

import components.weapon.economy.Collector;
import engine.Main;
import engine.states.Game;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import player.Player;
import teams.student.kingVon.resourceManager.ResourceManager;
import teams.student.kingVon.units.*;

import java.util.ArrayList;


public class KingVon extends Player
{
	int timer;
	private ArrayList<Tank> tanks = new ArrayList<>();

	public void setup()
	{
		setName("King Von");
		setTeamImage("src/teams/student/KingVon/teamLogo.png");
		setTitle("King Von Team");
		timer = 0;

		setColorPrimary(170, 170, 170);
		setColorSecondary(200, 200, 50);
		setColorAccent(255, 255, 255);

		ResourceManager.initialize(this);
		ResourceManager.registerResources();
	}

	// timer < 10,000			early
	// 10,000 < timer < 20,000	early middle
	// 20,000 < timer < 30,000	middle
	// 30,000 < timer < 40,000 	late middle
	// 45,000 < timer			endgame


	public void strategy()
	{
		timer++;
		float gathererPercent = getFleetValueUnitPercentage(Gatherer.class);
		float minerPercent = getFleetValueUnitPercentage(Miner.class);
		float flankerPercent = getFleetValueUnitPercentage(Flanker.class);
		float fighterPercent = getFleetValueUnitPercentage(Fighter.class);

		if (timer < 10000)
		{
			if (gathererPercent < 0.4)
			{
				Gatherer g = new Gatherer(this);
				ResourceManager.getGatherers().add(g);
				buildUnit(g);
			}
			else if (minerPercent < 0.4)
			{
				buildUnit(new Miner(this));
			}
			else
			{
				if (anyEnemyHasCollector())
				{
					buildUnit(new Flanker(this));
				}
				else
				{
					buildUnit(new Fighter(this));
				}
			}
			if (getMyUnits(Tank.class).size() < 3) {
				Tank t = new Tank(this, getMyUnits(Tank.class).size());
				tanks.add(t);
				buildUnit(t);
			}

		}
		else if (timer < 30000)
		{
			if (getMyUnits(Fighter.class).size() <= 2 )
			{
				buildUnit(new Fighter(this));
			}
			else if (gathererPercent < 0.15)
			{
				buildUnit(new Gatherer(this));
			}
			else if (minerPercent < 0.15)
			{
				buildUnit(new Miner(this));
			}
			else
			{
				buildUnit(new Fighter(this));
			}
		}
		else if (timer > 30000)
		{
			buildUnit(new Fighter(this));
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
		g.setColor(Color.white);
		g.drawLine(getMyBase().getX(), getMyBase().getY(), -300, 2500);
		g.drawLine(getMyBase().getX(), getMyBase().getY(), -300, 0);
		g.drawLine(getMyBase().getX(), getMyBase().getY(), -300, -2500);
		if (timer > 25000) {
			addMessage("Timer > 25,000");
		}
	}


	public int getTimer() {
		return timer;
	}

	public ArrayList<Tank> getTanks() {
		return tanks;
	}




//	float throwerPercent = getFleetValueUnitPercentage(Thrower.class);
//	float closePercent = getFleetValueUnitPercentage(CloseGatherer.class);
//	float minerPercent = getFleetValueUnitPercentage(Miner.class);
//	float flankerPercent = getFleetValueUnitPercentage(Flanker.class);
//	float fighterPercent = getFleetValueUnitPercentage(Fighter.class);
//
//
//		if (getMyUnits(Fighter.class).size() < 2 )
//	{
//		buildUnit(new Fighter(this));
//		return;
//	}
//		if (throwerPercent < 0.2f)
//	{
//		buildUnit(new Thrower(this));
//		return;
//	}
//		else if (closePercent < 0.1f)
//	{
//		buildUnit(new CloseGatherer(this));
//		return;
//	}
//		else if (minerPercent < 0.1f)
//	{
//		buildUnit(new Miner(this));
//		return;
//	}
//
//		if (!anyEnemyHasCollector()) {
//	if (fighterPercent < .8f) {
//		buildUnit(new Fighter(this));
//	}
//	return;
//}
//		else
//	{
//		if (flankerPercent < .8f) {
//			buildUnit(new Flanker(this));
//		}
//		else {
//			buildUnit(new Fighter(this));
//		}
//
//	}

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
}

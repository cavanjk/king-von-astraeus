package teams.student.kingVon;

import org.newdawn.slick.Graphics;
import player.Player;
import teams.student.kingVon.units.Fighter;
import teams.student.kingVon.units.Gatherer;
import teams.student.kingVon.units.Miner;

public class KingVon extends Player
{	
	public void setup()
	{		
		setName("My Team");
		setTeamImage("src/teams/student/myTeam/teamLogo.png");
		setTitle("Newbie Team");

		setColorPrimary(170, 170, 170);
		setColorSecondary(200, 200, 50);
		setColorAccent(255, 255, 255);
	}
	
	public void strategy() 
	{
		if(getFleetValueUnitPercentage(Gatherer.class) < .2f)
		{
			buildUnit(new Gatherer(this));
		}
		else if(getFleetValueUnitPercentage(Miner.class) < .2f)
		{
			buildUnit(new Miner(this));
		}
		else
		{
			buildUnit(new Fighter(this));
		}		
	}
			
	public void draw(Graphics g) 
	{
		
	}
	
}

package teams.student.kingVon;

import components.weapon.Weapon;
import objects.entity.node.Node;
import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;
import player.Player;

public abstract class KingVonUnit extends Unit
{
	public Node unifiedNode;

	public KingVonUnit(Player p)
	{
		super(p);
		unifiedNode = getHomeBase().getNearestNode();
	}
	
	public KingVon getPlayer()
	{
		return (KingVon) super.getPlayer();
	}

	public void updateUnifiedNode(Node a)
	{
		unifiedNode = a;
	}
	
	public void action() 
	{
		attack(getWeaponOne());
		attack(getWeaponTwo());
		movement();
	}
		
	public void attack(Weapon w)
	{
		Unit enemy = getNearestEnemy();

		if(enemy != null && w != null)
		{
			w.use(enemy);	
		}
	}
		
	public void movement()
	{
		Unit enemy = getNearestEnemy();

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
	
	public void draw(Graphics g) 
	{

	}
}

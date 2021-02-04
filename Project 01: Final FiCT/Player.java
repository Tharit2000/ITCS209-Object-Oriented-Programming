//Tharit Chantanalertvilai 6188068 Sec.2

import java.util.Arrays;

public class Player {

	public enum PlayerType {Healer, Tank, Samurai, BlackMage, Phoenix, Cherry};
	
	private PlayerType type; 	//Type of this player. Can be one of either Healer, Tank, Samurai, BlackMage, or Phoenix
	private double maxHP;		//Max HP of this player
	private double currentHP;	//Current HP of this player 
	private double atk;			//Attack power of this player
	private int numSpecialTurns;
	private int internalTurns;
	private Arena.Team team;
	private Arena.Row row;
	private int position;
	private boolean taunt;
	private boolean cursed;
	private boolean sleep;
	private Player cursedtarget;
	
	/**
	 * Constructor of class Player, which initializes this player's type, maxHP, atk, numSpecialTurns, 
	 * as specified in the given table. It also reset the internal turn count of this player. 
	 * @param _type
	 */
	public Player(PlayerType _type)
	{	
		//INSERT YOUR CODE HERE
		type = _type;
		switch(type)
		{
			case Healer:
			{
				type = PlayerType.Healer;
				currentHP = 4790;
				maxHP = 4790;
				atk = 238;
				numSpecialTurns = 4;
				internalTurns = 0;
				break;
			}
			case Tank:
			{
				type = PlayerType.Tank;
				currentHP = 5340;
				maxHP = 5340;
				atk = 255;
				numSpecialTurns = 4;
				internalTurns = 0;
				break;
			}
			case Samurai:
			{
				type = PlayerType.Samurai;
				currentHP = 4005;
				maxHP = 4005;
				atk = 368;
				numSpecialTurns = 3;
				internalTurns = 0;
				break;
			}
			case BlackMage:
			{
				type = PlayerType.BlackMage;
				currentHP = 4175;
				maxHP = 4175;
				atk = 303;
				numSpecialTurns = 4;
				internalTurns = 0;
				cursedtarget = null;
				break;
			}
			case Phoenix:
			{
				type = PlayerType.Phoenix;
				currentHP = 4175;
				maxHP = 4175;
				atk = 209;
				numSpecialTurns = 8;
				internalTurns = 0;
				break;
			}
			case Cherry:
			{
				type = PlayerType.Cherry;
				currentHP = 3560;
				maxHP = 3560;
				atk = 198;
				numSpecialTurns = 4;
				internalTurns = 0;
				break;
			}
		}
	}
	
	/**
	 * Returns the current HP of this player
	 * @return
	 */
	public double getCurrentHP()
	{
		//INSERT YOUR CODE HERE
		return this.currentHP;
	}
	
	/**
	 * Returns type of this player
	 * @return
	 */
	public Player.PlayerType getType()
	{
		//INSERT YOUR CODE HERE
		return this.type;
	}
	
	/**
	 * Returns max HP of this player. 
	 * @return
	 */
	public double getMaxHP()
	{
		//INSERT YOUR CODE HERE
		return this.maxHP;
	}
	
	/**
	 * Returns whether this player is sleeping.
	 * @return
	 */
	public boolean isSleeping()
	{
		//INSERT YOUR CODE HERE
		return this.sleep;
	}
	
	/**
	 * Returns whether this player is being cursed.
	 * @return
	 */
	public boolean isCursed()
	{
		//INSERT YOUR CODE HERE
		return this.cursed;
	}
	
	/**
	 * Returns whether this player is alive (i.e. current HP > 0).
	 * @return
	 */
	public boolean isAlive()
	{
		//INSERT YOUR CODE HERE
		if(this.currentHP > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Returns whether this player is taunting the other team.
	 * @return
	 */
	public boolean isTaunting()
	{
		//INSERT YOUR CODE HERE
		return this.taunt;
	}
	
	
	public void attack(Player target)
	{	
		//INSERT YOUR CODE HERE
		target.currentHP -= this.atk;
		if(target.currentHP < 0)
		{
			target.currentHP = 0;
			target.taunt = false;
			target.cursed = false;
			target.sleep = false;
		}
	}
	
	public void useSpecialAbility(Player[][] myTeam, Player[][] theirTeam)
	{	
		//INSERT YOUR CODE HERE
		Player target;
		switch(this.type)
		{
			case Healer:
			{
				target = lowestPercentageHP(myTeam);
				if(target.isCursed() == false && target.isAlive())
				{
					System.out.println("# " + this.playerInfo() + " Heals " + lowestPercentageHP(myTeam).playerInfo());
					if(target.currentHP * 1.25 > target.maxHP)
					{
						target.currentHP = target.maxHP;
					}
					else
					{
						target.currentHP += 0.25 * target.maxHP;
					}
				}
				break;
			}
			case Tank:
			{
				System.out.println("# " + this.playerInfo() + " is Taunting");
				this.taunt = true;
				break;
			}
			case Samurai:
			{
				target = findTarget(theirTeam);
				if(target != null)
				{
					System.out.println("# " + this.playerInfo() + " Double-Slashes " + target.playerInfo());
					attack(target); attack(target);
				}
				break;
			}
			case BlackMage:
			{
				if(findTarget(theirTeam) != null && findTarget(theirTeam).isAlive())
				{	
					this.cursedtarget = findTarget(theirTeam);
					this.cursedtarget.cursed = true;
					System.out.println("# " + this.playerInfo() + " Curses " + this.cursedtarget.playerInfo());
				}
				break;
			}
			case Phoenix:
			{
				target = deadAlly(myTeam);
				if(target != null)
				{	
					System.out.println("# " + this.playerInfo() + " Revives " + target.playerInfo());
					target.internalTurns = 0;
					target.taunt = false;
					target.cursed = false;
					target.sleep = false;
					target.currentHP += target.maxHP * 0.3;
				}
				break;
			}
			case Cherry:
			{
				for(int i=0; i<2; i++)
				{
					for(int j=0; j<theirTeam[0].length; j++)
					{
						if(theirTeam[i][j].isAlive())
						{
							theirTeam[i][j].sleep = true;
							System.out.println("# " + this.playerInfo() + " Feeds a Fortune Cookie to " + theirTeam[i][j].playerInfo());
						}
					}
				}
				break;
			}
		}
	}
	
	
	/**
	 * This method is called by Arena when it is this player's turn to take an action. 
	 * By default, the player simply just "attack(target)". However, once this player has 
	 * fought for "numSpecialTurns" rounds, this player must perform "useSpecialAbility(myTeam, theirTeam)"
	 * where each player type performs his own special move. 
	 * @param arena
	 */
	public void takeAction(Arena arena)
	{	
		//INSERT YOUR CODE HERE
		Player target;
		if(this.type == PlayerType.Tank)
		{
			this.taunt = false;
		}
		if(this.type == PlayerType.BlackMage && this.cursedtarget != null)
		{
			this.cursedtarget.cursed = false;
		}
		if(!this.isSleeping() && this.isAlive())
		{
			this.internalTurns++;
			switch(this.team)
			{
				case A:
				{
					if(this.type == PlayerType.Cherry)
					{
						for(int i=0; i<2; i++)
						{
							for(int j=0; j<arena.getTeam(Arena.Team.B)[0].length; j++)
							{
								if(arena.getTeam(Arena.Team.B)[i][j].isAlive())
								{
									arena.getTeam(Arena.Team.B)[i][j].sleep = false;
								}
							}
						}
					}
					if(this.internalTurns == this.numSpecialTurns)
					{
						useSpecialAbility(arena.getTeam(Arena.Team.A), arena.getTeam(Arena.Team.B));
						this.internalTurns = 0;
					}
					else
					{
						target = findTarget(arena.getTeam(Arena.Team.B));
						if(target != null)
						{
							System.out.println("# " + this.playerInfo() + " Attacks " + target.playerInfo());
							attack(target);	
						}
					}
					break;
				}
				case B:
				{
					if(this.type == PlayerType.Cherry)
					{
						for(int i=0; i<2; i++)
						{
							for(int j=0; j<arena.getTeam(Arena.Team.A)[0].length; j++)
							{
								if(arena.getTeam(Arena.Team.A)[i][j].isAlive())
								{
									arena.getTeam(Arena.Team.A)[i][j].sleep = false;
								}
							}
						}
					}
					if(this.internalTurns == this.numSpecialTurns)
					{
						useSpecialAbility(arena.getTeam(Arena.Team.B), arena.getTeam(Arena.Team.A));
						this.internalTurns = 0;
					}
					else
					{
						target = findTarget(arena.getTeam(Arena.Team.A));
						if(target != null)
						{
							System.out.println("# " + this.playerInfo() + " Attacks " + target.playerInfo());
							attack(target);	
						}
					}
					break;
				}
			}
		}
	}

	public void setTeam(Arena.Team team)
	{
		this.team = team;
	}

	public void setRow(Arena.Row row)
	{
		this.row = row;
	}

	public void setPosition(int position)
	{
		this.position = position;
	}

	public String playerInfo()
	{
		return this.team.toString() + "[" + this.row.toString() + "][" + this.position + "] {" + this.type.toString() + "}";
	}

	public double getPercentageHP(Player player)
	{
		return (player.getCurrentHP()/player.getMaxHP())*100;
	}

	public Player lowestPercentageHP(Player[][] player)
	{
		double[] playersPercentHP = new double[player[0].length + player[1].length];
		for(int i=0; i<player[0].length; i++)
		{
			if(player[0][i].isAlive())
			{
				playersPercentHP[i] = getPercentageHP(player[0][i]);
			}
			else
			{
				playersPercentHP[i] = 99999;
			}
		}
		for(int i=player[0].length,j=0; i<player[0].length*2; i++,j++)
		{
			if(player[1][j].isAlive())
			{
				playersPercentHP[i] = getPercentageHP(player[1][j]);
			}
			else
			{
				playersPercentHP[i] = 99999;
			}
		}
		Arrays.sort(playersPercentHP);
		for(int i=0; i<2; i++)
		{
			for(int j=0; j<player[0].length; j++)
			{
				if(playersPercentHP[0] == getPercentageHP(player[i][j]))
				{
					return player[i][j];
				}
			}
		}
		return null;
	}

	public boolean isFrontAlive(Player[][] player)
	{
		double sum = 0;
		for(int i=0; i<player[0].length; i++)
		{
			sum += player[0][i].currentHP;
		}
		if(sum == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public Player lowestEnemyHP(Player[][] enemy)
	{
		double[] enemiesHP = new double[enemy[0].length];
		if(isFrontAlive(enemy))
		{
			for(int i=0; i<enemy[0].length; i++)
			{
				if(enemy[0][i].isAlive())
				{
					enemiesHP[i] = enemy[0][i].getCurrentHP();
				}
				else
				{
					enemiesHP[i] = 99999;
				}
			}
		}
		else
		{
			for(int i=0; i<enemy[0].length; i++)
			{
				if(enemy[1][i].isAlive())
				{
					enemiesHP[i] = enemy[1][i].getCurrentHP();
				}
				else
				{
					enemiesHP[i] = 99999;
				}
			}
		}

		Arrays.sort(enemiesHP);
		for(int i=0; i<2; i++)
		{
			for(int j=0; j<enemy[0].length; j++)
			{
				if(enemiesHP[0] == enemy[i][j].getCurrentHP())
				{
					return enemy[i][j];
				}
			}
		}
		return null;
	}

	public Player findTarget(Player[][] enemy)
	{
		for(int i=0; i<2; i++)
		{
			for(int j=0; j<enemy[0].length; j++)
			{
				if(enemy[i][j].isAlive() && enemy[i][j].isTaunting())
				{
					return enemy[i][j];
				}
			}
		}
		return lowestEnemyHP(enemy);
	}

	public Player deadAlly(Player[][] player)
	{
		for(int i=0; i<2; i++)
		{
			for(int j=0; j<player[0].length; j++)
			{
				if(player[i][j].isAlive() == false)
				{
					return player[i][j];
				}
			}
		}
		return null;
	}


	/**
	 * This method overrides the default Object's toString() and is already implemented for you. 
	 */
	@Override
	public String toString()
	{
		return "["+this.type.toString()+" HP:"+this.currentHP+"/"+this.maxHP+" ATK:"+this.atk+"]["
				+((this.isCursed())?"C":"")
				+((this.isTaunting())?"T":"")
				+((this.isSleeping())?"S":"")
				+"]";
	}
}

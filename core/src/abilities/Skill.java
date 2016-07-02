package abilities;

import java.util.ArrayList;

import battle.BattleButton;
import battle.BattleQueue;
import party.Schmuck;
import states.BattleState;

public class Skill {
	
	public String name;								//Name of a skill
	public String descr;							//Description of a skill visible in the menu
	private int id;									//Skill id. Not used right now
	public int cost;								//Mp cost required to cast a skill
	
	public int targetType = 0;
	
	public Skill(String name, String descr, int id, int cost, int target){
		this.name = name;
		this.descr = descr;
		this.id = id;
		this.cost = cost;
		this.targetType = target;
	}
	
	public void use(Schmuck user, Schmuck target, BattleState bs){
		
	}

	public ArrayList<BattleButton> getTargets(BattleQueue bq){
		return bq.actionq;
	}
	
	
	public String getName() {
		return name;
	}

	public String getDescr() {
		return descr;
	}

	public int getId() {
		return id;
	}

	public int getCost() {
		return cost;
	}

	public int getTargetType() {
		return targetType;
	}
	
}

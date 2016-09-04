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
	public double init;								//Bonus Initiation chance
	
	public int targetType;
	public int numTargets;
	
	public Skill(String name, String descr, int id, int cost, double init, int target, int numTargets){
		this.name = name;
		this.descr = descr;
		this.id = id;
		this.cost = cost;
		this.init = init;
		this.targetType = target;
		this.numTargets = numTargets;
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
	}

	public ArrayList<BattleButton> getTargets(BattleButton user, BattleQueue bq){
		return new ArrayList<BattleButton>();
	}
	
	public String useText(Schmuck user){
		return user.getName()+" used "+this.getName()+"!";
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

	//0: Select a set number of schmucks.
	//1: Automatically targets a set number of schumucks
	public int getTargetType() {
		return targetType;
	}
	
}

package abilities;

import java.util.ArrayList;

import battle.BattleButton;
import battle.BattleQueue;
import party.Schmuck;
import states.BattleState;

public class CandelaFlare extends Skill{

	public final static String name = "Candela Flare";
	public final static String descr = "TEMP";
	public final static int id = 2;
	public final static int cost = 6;
	public final static double init = 1;
	public final static int target = 0;
	public final static int numTargets = 1;

	public CandelaFlare() {
		super(name, descr, id, cost, init, target, numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		if(bs.party.contains(user)){
		}
		else{
		}
	}
	
	public ArrayList<BattleButton> getTargets(BattleButton user, BattleQueue bq){
		return bq.actionq;
	}

}

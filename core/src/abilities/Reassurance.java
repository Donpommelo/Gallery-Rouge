package abilities;

import java.util.ArrayList;

import battle.BattleButton;
import battle.BattleQueue;
import party.Schmuck;
import states.BattleState;

public class Reassurance extends Skill{

	public final static String name = "Reassureance";
	public final static String descr = "TEMP";
	public final static int id = 4;
	public final static int cost = 5;
	public final static double init = 1;
	public final static int target = 0;
	public final static int numTargets = 1;

	public Reassurance() {
		super(name, descr, id, cost, init, target, numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		for(Schmuck s : target){
			bs.em.hpChange(bs, user, s,user.getSpec(bs), 3);
			bs.em.mpChange(bs, user, s,user.getSpec(bs), 3);
		}
	}
	
	public ArrayList<BattleButton> getTargets(BattleButton user, BattleQueue bq){
		return bq.actionq;
	}

}

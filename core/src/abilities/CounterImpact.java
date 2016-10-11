package abilities;

import java.util.ArrayList;

import party.Schmuck;
import states.BattleState;
import status.Countering;

public class CounterImpact extends Skill{

	public final static String name = "Counter Impact";
	public final static String descr = "TEMP";
	public final static int id = 0;
	public final static int cost = 3;
	public final static double init = .90;
	public final static int target = 0;
	public final static int numTargets = 0;

	public CounterImpact() {
		super(name, descr, id, cost, init, target, numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		bs.stm.addStatus(user, user, new Countering(1,user, user));
	}
	
}

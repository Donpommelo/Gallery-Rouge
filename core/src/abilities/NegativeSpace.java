package abilities;

import java.util.ArrayList;

import party.Schmuck;
import states.BattleState;
import status.abilitySpecific.NegativeSpaceStatus;

public class NegativeSpace extends Skill{

	public final static String name = "Negative Space";
	public final static String descr = "TEMP";
	public final static int id = 4;
	public final static int cost = 5;
	public final static double init = 0.0;
	public final static int target = 0;
	public final static int numTargets = 0;

	public NegativeSpace() {
		super(name, descr, id, cost, init, target, numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		bs.stm.addStatus(user, bs.fieldDummy, new NegativeSpaceStatus(16, user, bs.fieldDummy));
	}
	
}

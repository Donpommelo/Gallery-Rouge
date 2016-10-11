package abilities;

import java.util.ArrayList;

import party.Schmuck;
import states.BattleState;
import status.abilitySpecific.ObserveEternityStatus;

public class ObserveEternity extends Skill{

	public final static String name = "Observe Eternity";
	public final static String descr = "TEMP";
	public final static int id = 4;
	public final static int cost = 9;
	public final static double init = 0.0;
	public final static int target = 0;
	public final static int numTargets = 0;

	public ObserveEternity() {
		super(name, descr, id, cost, init, target, numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		bs.stm.addStatus(user, bs.fieldDummy, new ObserveEternityStatus(6, user, bs.fieldDummy));
	}
	
}

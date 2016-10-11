package abilities;

import java.util.ArrayList;

import party.Schmuck;
import states.BattleState;
import status.ActionQStatAddAura;

public class WaxingLight extends Skill{

	public final static String name = "Waxing Light";
	public final static String descr = "TEMP";
	public final static int id = 4;
	public final static int cost = 6;
	public final static double init = 0.0;
	public final static int target = 0;
	public final static int numTargets = 0;

	public WaxingLight() {
		super(name, descr, id, cost, init, target, numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		bs.stm.addStatus(user, bs.fieldDummy, new ActionQStatAddAura(25, 8, 5, true, false, user, bs.fieldDummy));
		bs.stm.addStatus(user, bs.fieldDummy, new ActionQStatAddAura(25, 9, 5, true, false, user, bs.fieldDummy));
		bs.stm.addStatus(user, bs.fieldDummy, new ActionQStatAddAura(25, 10, 5, true, false, user, bs.fieldDummy));
	}
	
}

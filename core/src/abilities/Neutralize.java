package abilities;

import java.util.ArrayList;

import party.Schmuck;
import states.BattleState;
import status.SingleStatChangeMult;

public class Neutralize extends Skill{

	public final static String name = "Neutralize";
	public final static String descr = "TEMP";
	public final static int id = 1;
	public final static int cost = 3;
	public final static double init = 0.75;
	public final static int target = 1;
	public final static int numTargets = 0;

	public Neutralize() {
		super(name, descr, id, cost, init, target, numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		bs.em.hpChange(bs, user, bs.bq.getOpposingActor(user).getSchmuck(),-user.getPhys(bs)/4, 0);
		bs.stm.addStatus(user, bs.bq.getOpposingActor(user).getSchmuck(), new SingleStatChangeMult(10, 8, .90, false, true,
				true, true, user, bs.bq.getOpposingActor(user).getSchmuck()));
		bs.stm.addStatus(user, bs.bq.getOpposingActor(user).getSchmuck(), new SingleStatChangeMult(10, 11, .90, false, true,
				true, true, user, bs.bq.getOpposingActor(user).getSchmuck()));
	}
}

package abilities;

import java.util.ArrayList;

import party.Schmuck;
import states.BattleState;
import status.SingleStatChangeAdd;

public class BreakBlade extends Skill{

	public final static String name = "Break Blade";
	public final static String descr = "TEMP";
	public final static int id = 1;
	public final static int cost = 6;
	public final static double init = 0.0;
	public final static int target = 1;
	public final static int numTargets = 0;

	public BreakBlade() {
		super(name, descr, id, cost, init, target, numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		bs.em.hpChange(bs, user, bs.bq.getOpposingActor(user).getSchmuck(),-user.getPhys(bs), 0);
		bs.stm.addStatus(user, bs.bq.getOpposingActor(user).getSchmuck(), new SingleStatChangeAdd(25, 14, -50, false, true,
				true, true, user, bs.bq.getOpposingActor(user).getSchmuck()));
	}
}

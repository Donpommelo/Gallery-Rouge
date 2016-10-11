package abilities;

import java.util.ArrayList;

import party.Schmuck;
import states.BattleState;
import status.Bleed;

public class StainedGlass extends Skill{

	public final static String name = "Stained Glass";
	public final static String descr = "TEMP";
	public final static int id = 1;
	public final static int cost = 8;
	public final static double init = 0.0;
	public final static int target = 1;
	public final static int numTargets = 0;

	public StainedGlass() {
		super(name, descr, id, cost, init, target,numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		bs.em.hpChange(bs, user, bs.bq.getOpposingActor(user).getSchmuck(),-user.getPhys(bs), 0);
		bs.stm.addStatus(user, bs.bq.getOpposingActor(user).getSchmuck(), new Bleed(10,user,bs.bq.getOpposingActor(user).getSchmuck()));
	}
}

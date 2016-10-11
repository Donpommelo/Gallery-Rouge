package abilities;

import java.util.ArrayList;

import party.Schmuck;
import states.BattleState;

public class StandardAttack extends Skill{

	public final static String name = "Attack";
	public final static String descr = "TEMP";
	public final static int id = 1;
	public final static int cost = 0;
	public final static double init = 0.0;
	public final static int target = 1;
	public final static int numTargets = 0;

	public StandardAttack() {
		super(name, descr, id, cost, init, target,numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		switch(user.getElemAlign(bs)){
		case 0:
			bs.em.hpChange(bs, user, bs.bq.getOpposingActor(user).getSchmuck(),-user.getPhys(bs), 0);
			break;
		case 1:
			bs.em.hpChange(bs, user, bs.bq.getOpposingActor(user).getSchmuck(),-user.getSpec(bs), 1);
			break;
		case 2:
			bs.em.hpChange(bs, user, bs.bq.getOpposingActor(user).getSchmuck(),-user.getAbstr(bs), 2);
			break;
		}
	}
}

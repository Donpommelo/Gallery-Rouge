package abilities;

import java.util.ArrayList;

import battle.BattleButton;
import battle.BattleQueue;
import party.Schmuck;
import states.BattleState;
import status.SingleStatChange;

public class BreakBlade extends Skill{

	public final static String name = "Break Blade";
	public final static String descr = "TEMP";
	public final static int id = 1;
	public final static int cost = 6;
	public final static double init = 1;
	public final static int target = 1;
	public final static int numTargets = 0;

	public BreakBlade() {
		super(name, descr, id, cost, init, target,numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		if(bs.bq.getOpposingActor(user) == null){
			bs.bt.addScene("The Attack Failed!");
		}
		else{
			bs.em.hpChange(bs, user, bs.bq.getOpposingActor(user).getSchmuck(),-user.getPhys(bs), 0);
			bs.stm.addStatus(user, bs.bq.getOpposingActor(user).getSchmuck(), new SingleStatChange(25, 14, -50, true, false, user, bs.bq.getOpposingActor(user).getSchmuck()));
		}
	}
	
	public ArrayList<BattleButton> getTargets(BattleButton user, BattleQueue bq){
		ArrayList<BattleButton> targets = new ArrayList<BattleButton>();
		if(bq.getOpposingActor(user.getSchmuck()) != null){
			targets.add(bq.getOpposingActor(user.getSchmuck()));
		}
		return targets;
	}

}

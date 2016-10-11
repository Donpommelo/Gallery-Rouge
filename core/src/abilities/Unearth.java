package abilities;

import java.util.ArrayList;

import battle.BattleButton;
import party.Schmuck;
import states.BattleState;

public class Unearth extends Skill{

	public final static String name = "Unearth";
	public final static String descr = "TEMP";
	public final static int id = 4;
	public final static int cost = 11;
	public final static double init = 0.0;
	public final static int target = 0;
	public final static int numTargets = 0;

	public Unearth() {
		super(name, descr, id, cost, init, target, numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		Schmuck victim = user;
		
		for (BattleButton bb : bs.bq.actionq) {
			if (bb.getSchmuck().getAbstrRes(bs) < victim.getAbstrRes(bs)) {
				victim = bb.getSchmuck();
			}
		}
		
		bs.bt.addScene("Unearthed shadows grasp at " + victim.getName()+"!", true);
		
		bs.em.hpChange(bs, user, victim, (int)(-user.getAbstr(bs) * 1.2), 2);
	}
	
}

package abilities;

import java.util.ArrayList;

import party.Schmuck;
import states.BattleState;
import status.Status;

public class CleansingSpray extends Skill{

	public final static String name = "Cleansing Spray";
	public final static String descr = "TEMP";
	public final static int id = 1;
	public final static int cost = 9;
	public final static double init = 0.0;
	public final static int target = 1;
	public final static int numTargets = 0;

	public CleansingSpray() {
		super(name, descr, id, cost, init, target, numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		ArrayList<Status> remove = new ArrayList<Status>();
		
		for (Status st : bs.bq.getOpposingActor(user).getSchmuck().statuses) {
			if (!st.perm) {
				remove.add(st);
			}
		}
		
		for (Status st : remove) {
			bs.stm.removeStatus(bs, bs.bq.getOpposingActor(user).getSchmuck(), st);
		}
		
		bs.em.hpChange(bs, user, bs.bq.getOpposingActor(user).getSchmuck(),
				-user.getSpec(bs) * (remove.size() + 1) / 3, 1);
	}
}

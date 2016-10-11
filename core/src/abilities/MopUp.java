package abilities;

import java.util.ArrayList;

import party.Schmuck;
import states.BattleState;
import status.Status;

public class MopUp extends Skill{

	public final static String name = "Mop Up";
	public final static String descr = "TEMP";
	public final static int id = 1;
	public final static int cost = 8;
	public final static double init = 0.25;
	public final static int target = 0;
	public final static int numTargets = 0;

	public MopUp() {
		super(name, descr, id, cost, init, target,numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		ArrayList<Status> remove = new ArrayList<Status>();
		
		for (Status st : bs.fieldDummy.statuses) {
			if (!st.perm) {
				remove.add(st);
			}
		}
		
		for (Status st : remove) {
			bs.stm.removeStatus(bs, bs.fieldDummy, st);
		}
	}

}

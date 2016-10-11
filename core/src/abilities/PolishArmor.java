package abilities;

import java.util.ArrayList;

import party.Schmuck;
import states.BattleState;
import status.SingleStatChangeAdd;
import status.Status;

public class PolishArmor extends Skill{

	public final static String name = "Polish Armor";
	public final static String descr = "TEMP";
	public final static int id = 0;
	public final static int cost = 3;
	public final static double init = 0.0;
	public final static int target = 0;
	public final static int numTargets = 0;

	public PolishArmor() {
		super(name, descr, id, cost, init, target, numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		
		ArrayList<Status> toRemove = new ArrayList<Status>();
		
		for (Status st : user.statuses){
			if (!st.perm) {	toRemove.add(st);}
		}
		
		for (Status st : toRemove){
			bs.stm.removeStatus(bs, user, st);
		}
		
		bs.stm.addStatus(user, user, new SingleStatChangeAdd(25, 9, 12, false, true, true, true, user, user));
	}
	
}

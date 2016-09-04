package abilities;

import java.util.ArrayList;

import party.Enforcer;
import party.Schmuck;
import states.BattleState;

public class CallforBackup extends Skill{

	public final static String name = "Call for Backup";
	public final static String descr = "TEMP";
	public final static int id = 1;
	public final static int cost = 6;
	public final static double init = 1;
	public final static int target = 0;
	public final static int numTargets = 0;

	public CallforBackup() {
		super(name, descr, id, cost, init, target,numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		bs.bq.initSchmuck(new Enforcer(), bs.bq.toq.size(),	bs.bq.team1.contains(user.getButton()));
		bs.bq.adjustButtons();
	}

}

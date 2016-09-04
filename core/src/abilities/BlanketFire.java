package abilities;

import java.util.ArrayList;

import party.Schmuck;
import states.BattleState;

public class BlanketFire extends Skill{

	public final static String name = "Blanket Fire";
	public final static String descr = "TEMP";
	public final static int id = 4;
	public final static int cost = 7;
	public final static double init = 1;
	public final static int target = 0;
	public final static int numTargets = 0;

	public BlanketFire() {
		super(name, descr, id, cost, init, target, numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		if(bs.bq.actionAllies.contains(user.getButton()) && !bs.bq.actionEnemy.isEmpty()){
			
		}
		else if(bs.bq.actionEnemy.contains(user.getButton()) && !bs.bq.actionAllies.isEmpty()){

			
		}
		else{
			
		}
	}
	
}

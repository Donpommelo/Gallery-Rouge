package abilities;

import java.util.ArrayList;

import battle.BattleButton;
import party.Schmuck;
import states.BattleState;

public class BlanketFire extends Skill{

	public final static String name = "Blanket Fire";
	public final static String descr = "TEMP";
	public final static int id = 4;
	public final static int cost = 9;
	public final static double init = 0.0;
	public final static int target = 0;
	public final static int numTargets = 0;

	public BlanketFire() {
		super(name, descr, id, cost, init, target, numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		
		int numally = 0;
		int numenem = 0;
		
		for (BattleButton b : bs.bq.getAllyTeam(user)) {
			if (bs.bq.actionq.contains(b)) {
				numally++;
			}
		}
		
		for (BattleButton b : bs.bq.getEnemyTeam(user)) {
			if (bs.bq.actionq.contains(b)) {
				numenem++;
			}
		}
		
		if (numenem != 0) {
			for (BattleButton b : bs.bq.getEnemyTeam(user)) {
				if (bs.bq.actionq.contains(b)) {
					bs.em.hpChange(bs, user, b.getSchmuck(), -user.getPhys(bs)*2/3 * (numally/numenem), 0);
				}
			}
		}
	}
}

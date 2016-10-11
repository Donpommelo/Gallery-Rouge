package abilities;

import java.util.ArrayList;

import battle.BattleButton;
import party.Schmuck;
import states.BattleState;

public class CandelaFlare extends Skill{

	public final static String name = "Candela Flare";
	public final static String descr = "TEMP";
	public final static int id = 2;
	public final static int cost = 6;
	public final static double init = 0.0;
	public final static int target = 0;
	public final static int numTargets = 1;

	public CandelaFlare() {
		super(name, descr, id, cost, init, target, numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		for (Schmuck s : target) {
			bs.em.hpChange(bs, user, s, -user.getSpec(bs)*2/3, 1);
		}
	}
	
	public ArrayList<BattleButton> getTargets(BattleButton user, BattleState bs){
		ArrayList<BattleButton> targets = new ArrayList<BattleButton>();
		for (BattleButton b : bs.bq.actionq) {
			if (bs.bq.getEnemyTeam(user.getSchmuck()).contains(b)){
				targets.add(b);
			}
		}
		return targets;
	}

}

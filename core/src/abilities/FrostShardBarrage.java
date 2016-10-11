package abilities;

import java.util.ArrayList;

import battle.BattleButton;
import party.Schmuck;
import states.BattleState;

public class FrostShardBarrage extends Skill{

	public final static String name = "Frost Shard Barrage";
	public final static String descr = "TEMP";
	public final static int id = 3;
	public final static int cost = 9;
	public final static double init = 0.0;
	public final static int target = 0;
	public final static int numTargets = 1;

	public FrostShardBarrage() {
		super(name, descr, id, cost, init, target, numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		for(Schmuck s : target){
			bs.em.hpChange(bs, user, s,-user.getPhys(bs)/3, 0);
			int index = bs.bq.toq.indexOf(s.getButton());
			if (index > 0) {
				bs.em.hpChange(bs, user, bs.bq.toq.get(index - 1).getSchmuck(), -user.getPhys(bs)/3, 0);
			}
			
			if (index < bs.bq.toq.size()) {
				bs.em.hpChange(bs, user, bs.bq.toq.get(index + 1).getSchmuck(), -user.getPhys(bs)/3, 0);
			}
		}
	}
	
	public ArrayList<BattleButton> getTargets(BattleButton user, BattleState bs){
		ArrayList<BattleButton> targets = new ArrayList<BattleButton>();
		for(BattleButton b : bs.bq.toq){
			targets.add(b);
		}
		return targets;
	}

}

package abilities;

import java.util.ArrayList;

import battle.BattleButton;
import party.Schmuck;
import states.BattleState;

public class Swap extends Skill{

	public final static String name = "Swap";
	public final static String descr = "TEMP";
	public final static int id = 1;
	public final static int cost = 0;
	public final static double init = 0.0;
	public final static int target = 0;
	public final static int numTargets = 2;

	public Swap() {
		super(name, descr, id, cost, init, target, numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		BattleButton b = target.get(0).getButton();
		int i = bs.bq.toq.indexOf(target.get(1).getButton());
		bs.bq.toq.set(bs.bq.toq.indexOf(target.get(0).getButton()), target.get(1).getButton());
		bs.bq.toq.set(i, b);
		bs.bq.adjustButtons();

	}
	
	public ArrayList<BattleButton> getTargets(BattleButton user, BattleState bs){
		if (bs.curAction.targets.isEmpty()) {
			return bs.bq.toq;
		} else {
			ArrayList<BattleButton> targets = new ArrayList<BattleButton>();
			int index = bs.bq.toq.indexOf(bs.curAction.targets.get(0).getButton());
			
			for (BattleButton b : bs.bq.toq) {
				if (Math.abs(bs.bq.toq.indexOf(b) - index) < 3 ) {
					targets.add(b);
				}
			}
			
			return targets;
		}
	}

}

package abilities;

import java.util.ArrayList;

import battle.BattleButton;
import party.Schmuck;
import states.BattleState;

public class Reclaim extends Skill{

	public final static String name = "Reclaim";
	public final static String descr = "TEMP";
	public final static int id = 1;
	public final static int cost = 5;
	public final static double init = 0.35;
	public final static int target = 0;
	public final static int numTargets = 1;

	public Reclaim() {
		super(name, descr, id, cost, init, target, numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		BattleButton b = target.get(0).getButton();
		int i = bs.bq.toq.indexOf(b);
		bs.bq.toq.remove(i);
		bs.bq.toq.add(i/2, b);
		bs.bq.adjustButtons();
		
		bs.bt.addScene(b.getSchmuck().getName() + " was brought to the front of the Turn Order Queue!", true);
	}
	
	public ArrayList<BattleButton> getTargets(BattleButton user, BattleState bs){
		return bs.bq.toq;
	}

}

package abilities;

import java.util.ArrayList;

import battle.BattleButton;
import battle.BattleQueue;
import party.Schmuck;
import states.BattleState;

public class Swap extends Skill{

	public final static String name = "Swap";
	public final static String descr = "TEMP";
	public final static int id = 1;
	public final static int cost = 0;
	public final static double init = 1;
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
	
	public ArrayList<BattleButton> getTargets(BattleButton user, BattleQueue bq){
		return bq.toq;
	}

}

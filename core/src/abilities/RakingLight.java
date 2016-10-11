package abilities;

import java.util.ArrayList;

import battle.BattleButton;
import party.Schmuck;
import states.BattleState;
import status.Petrification;

public class RakingLight extends Skill{

	public final static String name = "Raking Light";
	public final static String descr = "TEMP";
	public final static int id = 2;
	public final static int cost = 7;
	public final static double init = 0.0;
	public final static int target = 0;
	public final static int numTargets = 1;

	public RakingLight() {
		super(name, descr, id, cost, init, target, numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		for (Schmuck s : target) {
			bs.stm.addStatus(user, s, new Petrification(10, user, s));
		}
	}
	
	public ArrayList<BattleButton> getTargets(BattleButton user, BattleState bs){
		return bs.bq.actionq;
	}
}

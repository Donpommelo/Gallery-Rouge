package abilities;

import java.util.ArrayList;

import party.Schmuck;
import states.BattleState;
import status.SingleStatChange;

public class PolishArmor extends Skill{

	public final static String name = "Polish Armor";
	public final static String descr = "TEMP";
	public final static int id = 0;
	public final static int cost = 3;
	public final static double init = 1;
	public final static int target = 0;
	public final static int numTargets = 0;

	public PolishArmor() {
		super(name, descr, id, cost, init, target, numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		bs.stm.addStatus(user, user, new SingleStatChange(25, 9, 12, true, false, user, user));
	}
	
}

package abilities;

import party.Schmuck;
import states.BattleState;

public class StandardAttack extends Skill{

	public final static String name = "Attack";
	public final static String descr = "TEMP";
	public final static int id = 1;
	public final static int cost = 0;
	public final static int target = 0;

	public StandardAttack() {
		super(name, descr, id, cost, target);
	}
	
	public void use(Schmuck user, Schmuck target, BattleState bs){
		if(bs.party.contains(user)){
		}
		else{
		}
	}

}

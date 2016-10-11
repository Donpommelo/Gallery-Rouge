package abilities;

import java.util.ArrayList;

import battle.BattleButton;
import party.Schmuck;
import states.BattleState;
import status.abilitySpecific.FeetofClayStatus;

public class FeetofClay extends Skill{

	public final static String name = "Feet of Clay";
	public final static String descr = "TEMP";
	public final static int id = 3;
	public final static int cost = 5;
	public final static double init = 0.0;
	public final static int target = 0;
	public final static int numTargets = 1;

	public FeetofClay() {
		super(name, descr, id, cost, init, target, numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		for(Schmuck s : target){
			bs.stm.addStatus(user, s, new FeetofClayStatus(6,user,s));
		}
	}
	
	public ArrayList<BattleButton> getTargets(BattleButton user, BattleState bs){
		ArrayList<BattleButton> targets = new ArrayList<BattleButton>();
		for(BattleButton b : bs.bq.actionq){
			if (bs.bq.getEnemyTeam(user.getSchmuck()).contains(b)) {
				targets.add(b);
			}
		}
		for(BattleButton b : bs.bq.toq){
			if (bs.bq.getEnemyTeam(user.getSchmuck()).contains(b)) {
				targets.add(b);
			}
		}
		return targets;
	}

}

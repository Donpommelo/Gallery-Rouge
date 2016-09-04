package abilities;

import java.util.ArrayList;

import battle.BattleButton;
import party.Schmuck;
import states.BattleState;

public class AssembletheArms extends Skill{

	public final static String name = "Assemble the Arms";
	public final static String descr = "TEMP";
	public final static int id = 1;
	public final static int cost = 7;
	public final static double init = 1;
	public final static int target = 0;
	public final static int numTargets = 0;

	public AssembletheArms() {
		super(name, descr, id, cost, init, target,numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		for(BattleButton b : bs.bq.toq){
			
			int index = bs.bq.toq.indexOf(b);

			if(bs.bq.getAllyTeam(user).contains(b) && index != 0){
				bs.bq.toq.set(index, bs.bq.toq.get(index-1));
				bs.bq.toq.set(index-1, b);
			}
		}
		bs.bq.adjustButtons();
	}

}

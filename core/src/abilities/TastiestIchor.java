package abilities;

import java.util.ArrayList;

import party.Schmuck;
import states.BattleState;
import status.abilitySpecific.TastiestIchorStatus;

public class TastiestIchor extends Skill{

	public final static String name = "Tastiest Ichor";
	public final static String descr = "TEMP";
	public final static int id = 1;
	public final static int cost = 8;
	public final static double init = 0.4;
	public final static int target = 1;
	public final static int numTargets = 0;

	public TastiestIchor() {
		super(name, descr, id, cost, init, target, numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		bs.stm.addStatus(user, bs.bq.getOpposingActor(user).getSchmuck(), 
				new TastiestIchorStatus(1, user , bs.bq.getOpposingActor(user).getSchmuck()));
	}
}

package abilities;

import java.util.ArrayList;

import party.Schmuck;
import states.BattleState;
import status.abilitySpecific.CuttingFloorStatus;

public class CuttingFloor extends Skill{

	public final static String name = "Cutting Floor";
	public final static String descr = "TEMP";
	public final static int id = 4;
	public final static int cost = 13;
	public final static double init = 0.0;
	public final static int target = 0;
	public final static int numTargets = 0;

	public CuttingFloor() {
		super(name, descr, id, cost, init, target, numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		bs.stm.addStatus(user, bs.fieldDummy, new CuttingFloorStatus(5, user, bs.fieldDummy));
	}
	
}

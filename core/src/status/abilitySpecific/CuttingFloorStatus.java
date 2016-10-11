package status.abilitySpecific;

import battle.BattleAction;
import party.Schmuck;
import states.BattleState;
import status.Status;

public class CuttingFloorStatus extends Status{
	
	final static int id = 2;
	final static String name = "Cut Floor";
	final static boolean perm = false;
	final static boolean vis= true;
	final static boolean end = true;
	final static boolean dec = true;

	Schmuck target;
	
	public CuttingFloorStatus(int duration,Schmuck p,Schmuck v) {
		super(id, duration, name, perm, vis, end, dec, p, v);
	}
	
	public void onAction(BattleState bs, BattleAction ba){
		if (bs.bq.getEnemyTeam(perp).contains(ba.user.getButton())) {
			bs.bt.addScene(ba.getUser().getName()+" was cut by broken glass!", this);
			target = ba.getUser();
		}
	}

	public void postAnimRun(BattleState bs){
		bs.em.hpChange(bs, perp, target, -perp.getPhys(bs) / 2, 0);
	}

	public String inflictText(Schmuck s){
		return "The floor was covered in broken glass!";
	}

	public String cureText(Schmuck s){
		return "The floor was cleared of broken glass!";
	}
}

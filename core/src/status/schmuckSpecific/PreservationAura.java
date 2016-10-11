package status.schmuckSpecific;

import battle.BattleButton;
import party.Schmuck;
import states.BattleState;
import status.Status;

public class PreservationAura extends Status{
	
	final static int id = 2;
	final static String name = "Preservation Aura";
	final static int dura = 1;
	final static boolean perm = true;
	final static boolean vis= false;
	final static boolean end = false;
	final static boolean dec = false;

	public PreservationAura(Schmuck p,Schmuck v) {
		super(id, dura, name, perm, vis, end, dec, p, v);
	}
	
	public void postDelegation(BattleState bs){
		if (bs.bq.actionq.contains(vic.getButton())) {
			bs.bt.addScene(vic.getName()+"'s Preservation Aura restores Hp to allies!", this);
		}
	}
	
	public void postAnimRun(BattleState bs){
		for (BattleButton b : bs.bq.getAllyTeam(vic)) {
			if (bs.bq.actionq.contains(b)) {
				bs.em.hpChange(bs, vic, b.getSchmuck(), b.getSchmuck().getMaxHp(bs)/20, 2);
			}
		}
	}
	
}

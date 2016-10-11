package status.special;

import party.Schmuck;
import states.BattleState;
import status.Incapacitation;
import status.Status;

public class StartOffKO extends Status{
	
	final static int id = 2;
	final static String name = "Graveborn";
	final static int dura = 1;
	final static boolean perm = true;
	final static boolean vis= false;
	final static boolean end = false;
	final static boolean dec = false;

	public StartOffKO(Schmuck p,Schmuck v) {
		super(id, dura, name, perm, vis, end, dec, p, v);
	}
	
	public void fightStart(BattleState bs){
		bs.bt.addScene(vic.getName() + " finds " + vic.getPronoun(1) + " way to the Grave!", this);
	}
	
	public void postAnimRun(BattleState bs){
		bs.stm.addStatus(vic, vic, new Incapacitation(vic, vic));
		bs.bq.adjustButtons();
	}
}

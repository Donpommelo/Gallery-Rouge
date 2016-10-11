package status.schmuckSpecific;

import party.Enforcer;
import party.Schmuck;
import states.BattleState;
import status.Status;

public class Reinforced extends Status{
	
	final static int id = 2;
	final static String name = "Reinforced";
	final static int dura = 1;
	final static boolean perm = true;
	final static boolean vis= false;
	final static boolean end = false;
	final static boolean dec = false;

	public Reinforced(Schmuck p,Schmuck v) {
		super(id, dura, name, perm, vis, end, dec, p, v);
	}
	
	public void fightStart(BattleState bs){
		bs.bt.addScene(vic.getName()+" is calling for Reinforcements!", this);
	}
	
	public void postAnimRun(BattleState bs){
		int position = bs.bq.toq.indexOf(vic.getButton());

		bs.bq.initSchmuck(new Enforcer(), position,	bs.bq.team1.contains(vic.getButton()));

		bs.bq.initSchmuck(new Enforcer(), position,	bs.bq.team1.contains(vic.getButton()));
		
		bs.bq.adjustButtons();
	}

	
}

package status;

import battle.BattleAction;
import party.Schmuck;
import states.BattleState;

public class Marked extends Status{
	
	final static int id = 2;
	final static String name = "Marked";
	final static boolean perm = false;
	final static boolean vis= true;
	final static boolean end = true;
	final static boolean dec = true;

	public Marked(int duration,Schmuck p,Schmuck v) {
		super(id, duration, name, perm, vis, end, dec, p, v);
	}
	
	public void onTargetAcquire(BattleState bs, BattleAction ba){
		if(!bs.targetable.contains(this.vic.getButton())){
			bs.targetable.add(this.vic.getButton());
		}
	}

	public String inflictText(Schmuck s){
		return s.getName()+" was Marked!";
	}

	public String cureText(Schmuck s){
		return s.getName()+"'s Mark wore off!";
	}
}

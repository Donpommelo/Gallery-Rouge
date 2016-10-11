package status;

import party.Schmuck;
import states.BattleState;

public class Ablaze extends Status{
	
	final static int id = 2;
	final static String name = "Ablaze";
	final static boolean perm = false;
	final static boolean vis= true;
	final static boolean end = true;
	final static boolean dec = true;

	
	public Ablaze(int duration,Schmuck p,Schmuck v) {
		super(id, duration, name, perm, vis, end, dec, p, v);
	}
	
	public void postRound(BattleState bs){
		bs.bt.addScene(vic.getName()+" took damage from being Ablaze!", this);
		bs.em.hpChange(bs, perp, vic, -3, 0);
	}


	public String inflictText(Schmuck s){
		return s.getName()+" was set Ablaze!";
	}

	public String cureText(Schmuck s){
		return s.getName()+"'s Ablaze was extinguished!";
	}
}

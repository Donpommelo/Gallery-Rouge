package status;

import party.Schmuck;
import states.BattleState;

public class Bleed extends Status{
	
	final static int id = 2;
	final static String name = "Bleed";
	final static boolean perm = false;
	final static boolean vis= true;
	final static boolean end = true;
	final static boolean dec = true;

	int position;
	
	public Bleed(int duration,Schmuck p,Schmuck v) {
		super(id, duration, name, perm, vis, end, dec, p, v);
		position = 999;
	}
	
	public void postRound(BattleState bs){
		
		if (position != 999) {
			int damage = Math.abs(position - bs.bq.toq.indexOf(vic.getButton()));
			if (damage != 0) {
				bs.bt.addScene(vic.getName()+" took damage from Bleeding!", this);
				bs.em.hpChange(bs, perp, vic, -damage, 0);
			}
		}
		
		position = bs.bq.toq.indexOf(vic.getButton());
	}


	public String inflictText(Schmuck s){
		return s.getName()+" is Bleeding!";
	}

	public String cureText(Schmuck s){
		return s.getName()+"'s Bleeding stopped!";
	}
}

package status.abilitySpecific;

import party.Schmuck;
import states.BattleState;
import status.Status;

public class FeetofClayStatus extends Status{
	
	final static int id = 2;
	final static String name = "Clay Feet";
	final static boolean perm = false;
	final static boolean vis= true;
	final static boolean end = true;
	final static boolean dec = true;

	
	public FeetofClayStatus(int duration,Schmuck p,Schmuck v) {
		super(id, duration, name, perm, vis, end, dec, p, v);
	}
	
	public void postRound(BattleState bs){
		if (bs.bq.toq.contains(vic.getButton())) {
			int index = bs.bq.toq.indexOf(vic.getButton());
			if (index >= bs.bq.toq.size() - 3) {
				bs.bq.toq.remove(vic.getButton());
				bs.bq.toq.add(vic.getButton());
			} else {
				bs.bq.toq.remove(vic.getButton());
				bs.bq.toq.add(index + 2, vic.getButton());
			}
		}
	}

	public String inflictText(Schmuck s){
		return s.getName() + "'s feet became Clay!";
	}

	public String cureText(Schmuck s){
		return s.getName() + "'s feet are no longer Clay!";
	}
}

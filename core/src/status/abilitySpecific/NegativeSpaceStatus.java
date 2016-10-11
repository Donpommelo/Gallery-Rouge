package status.abilitySpecific;

import party.Schmuck;
import states.BattleState;
import status.Status;

public class NegativeSpaceStatus extends Status{
	
	final static int id = 2;
	final static String name = "Negative Space";
	final static boolean perm = false;
	final static boolean vis= true;
	final static boolean end = true;
	final static boolean dec = true;

	public NegativeSpaceStatus(int duration, Schmuck p,Schmuck v) {
		super(id, duration, name, perm, vis, end, dec, p, v);
	}
	
	public int statChange(BattleState bs, Schmuck vic, int amount, int statNum){
		if (bs.bq.actionq.contains(vic.getButton()) && statNum == 11) {
			return -amount;
		}
		return amount;
	}

	public String inflictText(Schmuck s){
		return "The Battlefield was bathed in Negative Space!";
	}

	public String cureText(Schmuck s){
		return perp.getName() + "'s Negative Space faded!";
	}
	
}

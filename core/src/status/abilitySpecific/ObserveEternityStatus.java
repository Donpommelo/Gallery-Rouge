package status.abilitySpecific;

import party.Schmuck;
import states.BattleState;
import status.Status;

public class ObserveEternityStatus extends Status{
	
	final static int id = 2;
	final static String name = "Eye of Eternity";
	final static boolean perm = false;
	final static boolean vis= true;
	final static boolean end = true;
	final static boolean dec = true;

	public ObserveEternityStatus(int duration, Schmuck p,Schmuck v) {
		super(id, duration, name, perm, vis, end, dec, p, v);
	}
	
	public void onStatusInflict(BattleState bs, Schmuck perp, Schmuck vic, Status st){
		if (bs.bq.getAllyTeam(this.perp).contains(perp) && bs.bq.actionq.contains(this.vic)
				&& !st.perm && st.decay) {
			bs.bt.addScene(this.vic.getName() + "'s Eye of Eternity extends the duration of the status!", true);
			st.duration += 2;
		}
	}
	
	public String inflictText(Schmuck s){
		return this.perp.getName() + " is Observing Eternity!";
	}

	public String cureText(Schmuck s){
		return perp.getName() + "'s Eye of Eternity faded!";
	}
	
}

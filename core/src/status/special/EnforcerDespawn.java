package status.special;

import battle.BattleButton;
import party.Schmuck;
import states.BattleState;
import status.Status;

public class EnforcerDespawn extends Status{
	
	final static int id = 0;
	final static String name = "TEMP";
	final static int dura = 1;
	final static boolean perm = true;
	final static boolean vis= false;
	final static boolean end = true;
	final static boolean dec = false;

	public EnforcerDespawn(Schmuck p,Schmuck v) {
		super(id, dura, name, perm, vis, end, dec, p, v);
	}

	public void onDeath(BattleState bs, Schmuck perp,Schmuck vic){
		if(vic == this.vic){
			boolean last = true;
			
			for(BattleButton b : bs.bq.getAllyTeam(vic)){
				if(b.getSchmuck().getName() == "Enforcer"){
					last = false;
				}
			}
			
			if(!last){
				bs.bq.removeSchmuck(vic);
			}
		}
		
	}	

}

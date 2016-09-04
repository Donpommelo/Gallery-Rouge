package status;

import party.Schmuck;
import states.BattleState;

public class DespawnonDeath extends Status{
	
	final static int id = 0;
	final static String name = "Despawn on Death";
	final static int dura = 1;
	final static boolean perm = true;
	final static boolean vis= false;
	final static boolean end = true;
	final static boolean dec = false;

	public DespawnonDeath(Schmuck p,Schmuck v) {
		super(id, dura, name, perm, vis, end, dec, p, v);
	}

	public void onDeath(BattleState bs, Schmuck perp,Schmuck vic){
		if(vic == this.vic){
			bs.bq.removeSchmuck(vic);
		}
	}	

}

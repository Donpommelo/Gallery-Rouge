package status.schmuckSpecific;

import party.Schmuck;
import states.BattleState;
import status.Status;

public class MachineVigil extends Status{
	
	final static int id = 2;
	final static String name = "Machine Vigil";
	final static int dura = 1;
	final static boolean perm = true;
	final static boolean vis= false;
	final static boolean end = false;
	final static boolean dec = false;

	public MachineVigil(Schmuck p,Schmuck v) {
		super(id, dura, name, perm, vis, end, dec, p, v);
	}
	
	public int onHpChange(BattleState bs, Schmuck perp, Schmuck vic, int damage, int elem){
		
		if (bs.bq.toq.contains(this.vic.getButton()) && bs.bq.toq.contains(vic.getButton()) && 
				bs.bq.getAllyTeam(this.vic).contains(vic) && damage < 0) {
			int index = bs.bq.toq.indexOf(this.vic.getButton());
				
			if (Math.abs(bs.bq.toq.indexOf(vic.getButton()) - index) < 2) {
				
				bs.bt.addScene(this.vic.getName()+"'s Machine Vigil protected adjacent allies!", true);
				
				return 0;
			}
		}
		
		return damage;
		
	}
	
}

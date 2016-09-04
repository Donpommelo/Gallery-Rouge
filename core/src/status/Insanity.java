package status;

import party.Schmuck;
import states.BattleState;

public class Insanity extends Status{
	
	final static int id = 1;
	final static String name = "Insanity";
	final static int dura = 1;
	final static boolean perm = true;
	final static boolean vis= true;
	final static boolean end = true;
	final static boolean dec = false;

	double turns;
	
	public Insanity(Schmuck p,Schmuck v) {
		super(id, dura, name, perm, vis, end, dec, p, v);
	}
	
	public int statChange(BattleState bs,Schmuck vic, int amount,int statNum){
		if(vic == this.vic){
			if(statNum == 2 || statNum == 3 || statNum == 4){
				amount *= turns;
			}
		}
		
		return amount;
	}

	
	public void onStatusInflict(BattleState bs,Schmuck perp,Schmuck vic,Status st){
		if(st.getId() == this.getId() && vic == this.vic){
			
			for(Schmuck s : bs.bq.all){
				bs.stm.statusProcTime(11, bs, null, s, perp, vic, 0, 0, true, null);
			}
			bs.stm.statusProcTime(11, bs, null, bs.fieldDummy, perp, vic, 0, 0, true, null);
		}
		
		turns = 1.5;
	}
	
	public void postRound(BattleState bs){
		turns -= .1;
	}
	
	public String inflictText(Schmuck s){
		return s.getName()+" went Insane!";
	}

	public String cureText(Schmuck s){
		return s.getName()+"'s Sanity returned!";
	}
}

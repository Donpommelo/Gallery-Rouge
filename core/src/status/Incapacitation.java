package status;

import battle.BattleAction;
import party.Schmuck;
import states.BattleState;

public class Incapacitation extends Status{
	
	final static int id = 0;
	final static String name = "Incapacitation";
	final static int dura = 1;
	final static boolean perm = true;
	final static boolean vis= true;
	final static boolean end = true;
	final static boolean dec = false;

	public Incapacitation(Schmuck p,Schmuck v) {
		super(id, dura, name, perm, vis, end, dec, p, v);
	}
	
	public void onStatusInflict(BattleState bs,Schmuck perp,Schmuck vic,Status st){		
		if(st.getId() == this.getId() && vic == this.vic){

			bs.bq.toq.remove(vic.getButton());
			bs.bq.actionq.remove(vic.getButton());
			bs.bq.ko.add(vic.getButton());
			
			for(Schmuck s : bs.bq.all){
				bs.stm.statusProcTime(10, bs, null, s, perp, vic, 0, 0, true, null);
			}
			bs.stm.statusProcTime(10, bs, null, bs.fieldDummy, perp, vic, 0, 0, true, null);

			Boolean teamded = true;
			
			for(Schmuck s : bs.bq.party){
				if(!bs.stm.checkStatus(s, this)){
					teamded = false;
				}
			}
			
			if(teamded){
				bs.endFight(!bs.bq.party.contains(vic));
			}
		}
	}
	
	public void onStatusCure(BattleState bs,Schmuck perp,Schmuck vic,Status st){
		if(st.getId() == this.getId() && vic == this.vic){
			bs.bq.ko.remove(vic.getButton());
			bs.bq.toq.add(vic.getButton());
		}
	}
	
	public void preAction(BattleState bs, BattleAction ba){
		if(ba.getUser() == this.vic){
			//replace action with empty action
		}
	}

	
	public String inflictText(Schmuck s){
		return s.getName()+" was Incapacitated!";
	}

	public String cureText(Schmuck s){
		return s.getName()+" was Revived!";
	}
}

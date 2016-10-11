package status;

import party.Schmuck;
import states.BattleState;

public class SingleStatChangeAdd extends Status{
	
	final static int id = 0;
	final static String name = "Stats Changed";
	
	public int statChanged;
	public int amount;
	
	public SingleStatChangeAdd(int duration,int stat, int amount, boolean perm, boolean vis, boolean dec, boolean end,
			Schmuck p, Schmuck v) {
		super(id, duration, name, perm, vis, end, dec, p, v);
		this.statChanged = stat;
		this.amount = amount;
	}
	
	public int statChange(BattleState bs, Schmuck vic, int a, int statNum){
		if(statNum == statChanged && vic.equals(this.vic)){
			a += amount;
		}
		return a;
	}

	public int stackingEffect(){
		return 1;
	}
	
	public String inflictText(Schmuck s){
		String stat = super.getStat(statChanged);
		String change = "";
		if (amount >= 0) {
			change = "increas";
		} else {
			change = "decreas";
		}
		
		return vic.getName()+"'s "+stat+" was "+change+"ed!";

	}

	public String cureText(Schmuck s){
		return "";
	}
}

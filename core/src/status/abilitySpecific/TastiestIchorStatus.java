package status.abilitySpecific;

import battle.BattleAction;
import party.Schmuck;
import states.BattleState;
import status.Status;

public class TastiestIchorStatus extends Status{
	
	final static int id = 2;
	final static String name = "Ichorous Skin";
	final static boolean perm = false;
	final static boolean vis= true;
	final static boolean end = true;
	final static boolean dec = false;

	int stacks;
	
	public TastiestIchorStatus(int duration,Schmuck p,Schmuck v) {
		super(id, duration, name, perm, vis, end, dec, p, v);
		stacks = 0;
	}
	
	public int statChange(BattleState bs, Schmuck vic, int amount, int statNum){
		if (statNum == 10 && vic.equals(this.vic)) {
			return amount - 20 * stacks; 
		} else {
			return amount;
		}
	}


	public void onAction(BattleState bs, BattleAction ba){
		if (ba.user.equals(vic)) {
			bs.bt.addScene("Ichor seeps into " + vic.getName() + "'s skin!", true);
			bs.bt.addScene(vic.getName() + "'s Abstract Resistance was reduced!", true);
			stacks++;
		}
	}	

	public String inflictText(Schmuck s){
		return s.getName()+" was covered in caustic ichor!";
	}

	public String cureText(Schmuck s){
		return s.getName()+" was cleaned of caustic ichor!";
	}
}

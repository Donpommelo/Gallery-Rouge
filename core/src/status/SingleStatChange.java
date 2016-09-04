package status;

import party.Schmuck;
import states.BattleState;

public class SingleStatChange extends Status{
	
	final static int id = 0;
	final static String name = "Stats Changed";
	final static boolean perm = false;
	final static boolean vis= true;
	final static boolean end = true;
	final static boolean dec = true;

	public int statChanged;
	public int amount;
	public boolean teamAffected;
	public boolean enemyAffected;
	
	public SingleStatChange(int duration,int stat, int amount, boolean team, boolean enemy,
			Schmuck p,Schmuck v) {
		super(id, duration, name, perm, vis, end, dec, p, v);
		this.statChanged = stat;
		this.amount = amount;
		this.teamAffected = team;
		this.enemyAffected = enemy;
	}
	
	public int statChange(BattleState bs,Schmuck vic, int a,int statNum){
		if(statNum == statChanged && vic.equals(this.vic)){
			a += amount;
		}
		return a;
	}

	public int stackingEffect(){
		return 1;
	}
	
	public String inflictText(Schmuck s){
		return "Temp Text";
	}

	public String cureText(Schmuck s){
		return " ";
	}
}

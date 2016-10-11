package status;

import party.Schmuck;
import states.BattleState;

public class ActionQStatAddAura extends Status{
	
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
	
	public ActionQStatAddAura(int duration,int stat, int amount, boolean team, boolean enemy,
			Schmuck p,Schmuck v) {
		super(id, duration, name, perm, vis, end, dec, p, v);
		this.statChanged = stat;
		this.amount = amount;
		this.teamAffected = team;
		this.enemyAffected = enemy;
	}
	
	public int statChange(BattleState bs,Schmuck vic, int a,int statNum){
		if(statNum == statChanged && bs.bq.actionq.contains(vic.getButton())){

			if(teamAffected && bs.bq.getAllyTeam(perp).contains(vic.getButton())){
				a += amount;
			}
			if(enemyAffected && !bs.bq.getAllyTeam(perp).contains(vic.getButton())){
				a += amount;
			}
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
		
		if (teamAffected && enemyAffected) {
			return perp.getName()+" is emenating a(n) "+stat+" "+change+"ing aura for all characters!";
		} else if (teamAffected) {
			return perp.getName()+" is emenating a(n) "+stat+" "+change+"ing aura for allies!";
		} else if (enemyAffected) {
			return perp.getName()+" is emenating a(n) "+stat+" "+change+"ing aura for enemies!";
		}
		
		//This should never run
		return "A "+stat+" "+change+"ing aura that affects nobody was created.";
	}

	public String cureText(Schmuck s){
		String stat = super.getStat(statChanged);
		String change = "";
		if (amount >= 0) {
			change = "increas";
		} else {
			change = "decreas";
		}
		
		return perp.getName()+"'s "+stat+" "+change+"ing aura disappeared!";

	}
}

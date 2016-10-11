package status;

import battle.BattleAction;
import party.Schmuck;
import states.BattleState;

public class Status {
	public int id = 0;
	public int duration = 0;			//The amount of turns of the status remaining
	public String name;					//The name of the status
	public Boolean perm = false;		//Whether the status is purgeable by generic status removal.
	public Boolean visible = true;		//Whether the status is visible in the UI
	public Boolean removedEnd = true;	//Whether the status is removed at the end of combat.
	public Boolean decay = true;		//Whether the status's duration decreases at the end of each round.
	public Schmuck perp, vic;			//Schmuck who inflicted this status, Schmuck afflicted
	
	public Status(int i, int dura, String n, Boolean perm, Boolean vis, Boolean end, Boolean dec, Schmuck p, Schmuck v){
		this.id = i;
		this.duration=dura;
		this.name = n;
		this.perm = perm;
		this.visible = vis;
		this.removedEnd = end;
		this.decay = dec;
		this.perp = p;
		this.vic = v;
	}
	
	public void postAnimRun(BattleState bs){}
	
	//Called whenever any buffed stat is checked. BattleState is checked if in Battle.
	public int statChange(int amount, int statNum){return amount;}
	
	public int statChange(BattleState bs, Schmuck vic, int amount, int statNum){return amount;}
	
	//Called when this status is inflicted
	public void onStatusInflict(BattleState bs, Schmuck perp, Schmuck vic, Status st){}
	
	//Called when this status is cured
	public void onStatusCure(BattleState bs, Schmuck perp, Schmuck vic, Status st){}

	//Called when a fight begins
	public void fightStart(BattleState bs){}
	
	//Called when a fight ends
	public void fightEnd(BattleState bs){}
	
	//Called after each round after combat processing
	public void postRound(BattleState bs){}
	
	//Called after being selected to be in the Action Group
	public void postDelegation(BattleState bs){}
	
	//Called before an action is performed
	public void preAction(BattleState bs, BattleAction ba){}
		
	//Called after an action is performed
	public void onAction(BattleState bs, BattleAction ba){}
		
	//Called when a schmuck's hp changes
	public int onHpChange(BattleState bs, Schmuck perp, Schmuck vic, int damage, int elem){return damage;}
	
	//Called when a schmuck's mp changes
	public int onMpChange(BattleState bs, Schmuck perp, Schmuck vic, int damage, int elem){return damage;}
		
	//Called when a schmuck is incapacitated
	public void onDeath(BattleState bs, Schmuck perp, Schmuck vic){}	
	
	//Called when a schmuck goes insane
	public void onInsanity(BattleState bs, Schmuck perp, Schmuck vic){}
	
	public void onTargetAcquire(BattleState bs, BattleAction ba){}

	
	public int getId(){
		return id;
	}
	
	public String inflictText(Schmuck s){
		return "";
	}

	public String cureText(Schmuck s){
		return "";
	}
	
	public String getName(){
		return name;
	}
	
	//What will happen if you gain this status while already having a status with the same name?
	//0: Nothing happens. (Most statuses)
	//1: The duration of the status is increased. (Poison)
	//2: You gain a new instance of the status. (Stat changes, Status Immunity, etc)
	//3: The new status replaces the old one. (Statuses with some numeric modifier like Vampirism or Damage Reflect)
	//4: Both statuses are removed. (Misaligned)
	//5: The status' stack variable increases by 1 (Intrusive Thought)
	public int stackingEffect(){
		return 0;
	}
	
	//Generic status curing stuff removes statuses if this returns true.
	public Boolean isBad(){
		return false;
	}
	
	//Does this status run if its owner is incapacitated?
	public Boolean runWhenDead(){
		return false;
	}
	
	//Is this status a basic stat-changer?
	public Boolean isStat(){
		return false;
	}
	
	public String getStat(int statChanged){
		String stat = "";
		switch (statChanged) {
		case 0:
			stat = "Hp";
			break;
		case 1:
			stat = "Mp";
			break;
		case 2:
			stat = "Physical Alignment";
			break;
		case 3:
			stat = "Special Alignment";
			break;
		case 4:
			stat = "Abstract Alignment";
			break;
		case 5:
			stat = "Physical Damage";
			break;
		case 6:
			stat = "Special Damage";
			break;
		case 7:
			stat = "Abstract Damage";
			break;
		case 8:
			stat = "Physical Resistance";
			break;
		case 9:
			stat = "Special Resistance";
			break;
		case 10:
			stat = "Abstract Resistance";
			break;
		case 11:
			stat = "Initiative";
			break;	
		case 12:
			stat = "Hp Regeneration";
			break;
		case 13:
			stat = "Mp Regeneration";
			break;
		case 14:
			stat = "Damage Amplification";
			break;
		case 15:
			stat = "Damage Resistance";
			break;	
		case 16:
			stat = "Elemental Alignment";
			break;
		case 17:
			stat = "Mp Costs";
			break;
		}
		return stat;
	}
	
	public int getExtraVar(){
		return 0;
	}
}

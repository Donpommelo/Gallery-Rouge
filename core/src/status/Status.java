package status;

import party.Schmuck;

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
	
	//Called whenever any buffed stat is checked/.
	public int statChange(int statNum){
		return vic.getBaseStat(statNum);
	}
	
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
	
	
}

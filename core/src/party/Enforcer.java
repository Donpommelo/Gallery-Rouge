package party;


import abilities.BlanketFire;
import abilities.CallforBackup;
import abilities.Neutralize;
import abilities.Skill;
import abilities.StandardAttack;
import status.schmuckSpecific.Reinforced;

public class Enforcer extends Schmuck{

	public static final String name = "Enforcer";
	public static final int pronoun = 1;
	public static final String bioShort = "TEMP";
	public static final String bioLong = "TEMP";
	
	public static final int[] start = {11,12,8,7,5,0,0,0,0,0,0,0,0,0,0,0,0};
	public static final Skill[] skills = {new StandardAttack(), new CallforBackup(), new BlanketFire(),
			new Neutralize()};
	
	public static String icon = "EnforcerA.png";
	public static String sprite = "";
	
	public Enforcer() {
		super(name, pronoun, bioShort, bioLong, start, skills, icon, sprite);
	}
	
	public void addIntrinsics(){
		this.statuses.add(new Reinforced(this,this));
//		this.statuses.add(new EnforcerDespawn(this,this));
	}
}

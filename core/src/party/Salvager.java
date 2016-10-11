package party;


import abilities.Reclaim;
import abilities.Salvage;
import abilities.Skill;
import abilities.StandardAttack;
import abilities.Unearth;

public class Salvager extends Schmuck{

	public static final String name = "Salvager";
	public static final int pronoun = 2;
	public static final String bioShort = "TEMP";
	public static final String bioLong = "TEMP";
	
	public static final int[] start = {18,10,10,11,16,0,0,0,0,0,0,0,0,0,0,0,0};
	public static final Skill[] skills = {new StandardAttack(), new Reclaim(), new Salvage(), new Unearth()};
	
	public static String icon = "Pluot.png";
	public static String sprite = "";
	
	public Salvager() {
		super(name, pronoun, bioShort, bioLong, start, skills, icon, sprite);
	}
	
}

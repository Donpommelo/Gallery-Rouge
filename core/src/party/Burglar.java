package party;


import abilities.Skill;
import abilities.StandardAttack;

public class Burglar extends Schmuck{

	public static final String name = "Burglar";
	public static final int pronoun = 1;
	public static final String bioShort = "TEMP";
	public static final String bioLong = "TEMP";
	
	public static final int[] start = {20,16,12,12,8,0,0,0,0,0,0,0,0,0,0,0,0};
	public static final Skill[] skills = {new StandardAttack()};
	
	public static String icon = "Bhurglemeister.png";
	public static String sprite = "";
	
	public Burglar() {
		super(name, pronoun, bioShort, bioLong, start, skills, icon, sprite);
	}
	
}

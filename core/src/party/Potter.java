package party;


import abilities.CeramicGrenade;
import abilities.FeetofClay;
import abilities.Skill;
import abilities.StandardAttack;
import abilities.Swap;

public class Potter extends Schmuck{

	public static final String name = "Potter";
	public static final int pronoun = 1;
	public static final String bioShort = "TEMP";
	public static final String bioLong = "TEMP";
	
	public static final int[] start = {13,16,14,14,9,0,0,0,0,0,0,0,0,0,0,0,0};
	public static final Skill[] skills = {new StandardAttack(), new FeetofClay(), new CeramicGrenade(),
			new Swap()};
	
	public static String icon = "Bhurglemeister.png";
	public static String sprite = "";
	
	public Potter() {
		super(name, pronoun, bioShort, bioLong, start, skills, icon, sprite);
	}
	
}

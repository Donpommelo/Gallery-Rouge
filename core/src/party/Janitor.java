package party;


import abilities.CleansingSpray;
import abilities.MopUp;
import abilities.Purify;
import abilities.Skill;
import abilities.StandardAttack;

public class Janitor extends Schmuck{

	public static final String name = "Janitor";
	public static final int pronoun = 1;
	public static final String bioShort = "TEMP";
	public static final String bioLong = "TEMP";
	
	public static final int[] start = {17,21,9,15,10,0,0,0,0,0,0,0,0,0,0,0,0};
	public static final Skill[] skills = {new StandardAttack(), new Purify(), new CleansingSpray(),
			new MopUp()};
	
	public static String icon = "Pluot.png";
	public static String sprite = "";
	
	public Janitor() {
		super(name, pronoun, bioShort, bioLong, start, skills, icon, sprite);
	}
	
}

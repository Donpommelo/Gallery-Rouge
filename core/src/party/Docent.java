package party;


import abilities.AssembletheArms;
import abilities.Reassurance;
import abilities.Skill;
import abilities.SlakeWonder;
import abilities.StandardAttack;

public class Docent extends Schmuck{

	public static final String name = "Docent";
	public static final int pronoun = 2;
	public static final String bioShort = "TEMP";
	public static final String bioLong = "TEMP";
	
	public static final int[] start = {18,23,9,15,10,0,0,0,0,0,0,0,0,0,0,0,1};
	public static final Skill[] skills = {new StandardAttack(),	new SlakeWonder(), new Reassurance(), 
			new AssembletheArms()};
	
	public static String icon = "Pluot.png";
	public static String sprite = "";
	
	public Docent() {
		super(name, pronoun, bioShort, bioLong, start, skills, icon, sprite);
	}

}

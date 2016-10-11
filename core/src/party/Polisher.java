package party;


import abilities.BreakBlade;
import abilities.CounterImpact;
import abilities.PolishArmor;
import abilities.Skill;
import abilities.StandardAttack;

public class Polisher extends Schmuck{

	public static final String name = "Polisher";
	public static final int pronoun = 2;
	public static final String bioShort = "TEMP";
	public static final String bioLong = "TEMP";
	
	public static final int[] start = {20,15,18,10,7,0,0,0,0,0,0,0,0,0,0,0,0};
	public static final Skill[] skills = {new StandardAttack(), new CounterImpact(), new PolishArmor(),
			new BreakBlade()};
	
	public static String icon = "Bhurglemeister.png";
	public static String sprite = "";
	
	public Polisher() {
		super(name, pronoun, bioShort, bioLong, start, skills, icon, sprite);
	}
	
}

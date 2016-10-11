package party;


import abilities.NegativeSpace;
import abilities.ObserveEternity;
import abilities.ScaleUp;
import abilities.Skill;
import abilities.StandardAttack;

public class Dioramist extends Schmuck{

	public static final String name = "Dioramist";
	public static final int pronoun = 1;
	public static final String bioShort = "TEMP";
	public static final String bioLong = "TEMP";
	
	public static final int[] start = {16,21,5,10,19,0,0,0,0,0,0,0,0,0,0,0,0};
	public static final Skill[] skills = {new StandardAttack(), new ObserveEternity(), new NegativeSpace(),
			new ScaleUp()};
	
	public static String icon = "Pluot.png";
	public static String sprite = "";
	
	public Dioramist() {
		super(name, pronoun, bioShort, bioLong, start, skills, icon, sprite);
	}
	
}

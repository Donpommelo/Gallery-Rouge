package party;


import abilities.CandelaFlare;
import abilities.Skill;
import abilities.StandardAttack;
import abilities.Swap;
import abilities.WaxingLight;

public class Candleman extends Schmuck{

	public static final String name = "Candleman";
	public static final int pronoun = 1;
	public static final String bioShort = "Candleman";
	public static final String bioLong = "Candleman";
	
	public static final int[] start = {20,16,12,12,8,0,0,0,0,0,0,0,0,0,0,0,0};
	public static final Skill[] skills = {new StandardAttack(), new CandelaFlare(), new WaxingLight(),
			new Swap()};
	
	public static String icon = "Candleman.png";
	public static String sprite = "";
	
	public Candleman() {
		super(name, pronoun, bioShort, bioLong, start, skills, icon, sprite);
	}
	
}

package party;

import abilities.Skill;

public class Candleman extends Schmuck{

	public static final String name = "Candleman";
	public static final String bioShort = "Candleman";
	public static final String bioLong = "Candleman";
	
	public static final int[] start = {20,20,15,15,15};
	public static Skill[] skills = {};
	
	public static String icon = "charIcons/Bad Egg.png";
	public static String sprite = "";
	
	public Candleman() {
		super(name, bioShort, bioLong, start, skills, icon, sprite);
		// TODO Auto-generated constructor stub
	}

}

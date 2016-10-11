package party.enemy;


import abilities.Skill;
import abilities.StandardAttack;
import party.Schmuck;

public class Vandal extends Schmuck{

	public static final String name = "Vandal";
	public static final int pronoun = 0;
	public static final String bioShort = "TEMP";
	public static final String bioLong = "TEMP";
	
	public static final int[] start = {17,21,15,5,6,0,0,0,0,0,0,0,0,0,0,0,0};
	public static final Skill[] skills = {new StandardAttack()};
	
	public static String icon = "Bandito.png";
	public static String sprite = "";
	
	public Vandal() {
		super(name, pronoun, bioShort, bioLong, start, skills, icon, sprite);
	}
	
}

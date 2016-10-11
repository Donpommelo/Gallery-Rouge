package party.enemy;


import abilities.Skill;
import abilities.StandardAttack;
import party.Schmuck;

public class GalleryGremlin extends Schmuck{

	public static final String name = "Gallery Gremlin";
	public static final int pronoun = 0;
	public static final String bioShort = "TEMP";
	public static final String bioLong = "TEMP";
	
	public static final int[] start = {12,21,10,11,7,0,0,0,0,0,0,0,0,0,0,0,0};
	public static final Skill[] skills = {new StandardAttack()};
	
	public static String icon = "HatGremlin.png";
	public static String sprite = "";
	
	public GalleryGremlin() {
		super(name, pronoun, bioShort, bioLong, start, skills, icon, sprite);
	}
	
}

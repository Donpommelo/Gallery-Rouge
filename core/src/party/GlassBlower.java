package party;


import abilities.CuttingFloor;
import abilities.FrostShardBarrage;
import abilities.Skill;
import abilities.StainedGlass;
import abilities.StandardAttack;

public class Glassblower extends Schmuck{

	public static final String name = "Glassblower";
	public static final int pronoun = 1;
	public static final String bioShort = "TEMP";
	public static final String bioLong = "TEMP";
	
	public static final int[] start = {13,18,17,9,8,0,0,0,0,0,0,0,0,0,0,0,0};
	public static final Skill[] skills = {new StandardAttack(), new StainedGlass(), new FrostShardBarrage(),
			new CuttingFloor()};
	
	public static String icon = "Candleman.png";
	public static String sprite = "";
	
	public Glassblower() {
		super(name, pronoun, bioShort, bioLong, start, skills, icon, sprite);
	}
}

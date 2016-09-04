package party;


import abilities.CallforBackup;
import abilities.CandelaFlare;
import abilities.Skill;
import abilities.StandardAttack;
import abilities.WaxingLight;

public class Polisher extends Schmuck{

	public static final String name = "Polisher";
	public static final String bioShort = "TEMP";
	public static final String bioLong = "TEMP";
	
	public static final int[] start = {20,16,12,12,8,0,0,0,0,0,0,0,0,0,0,0,0};
	public static final Skill[] skills = {new StandardAttack(), new CandelaFlare(), new WaxingLight(),
			new CallforBackup()};
	
	public static String icon = "Candleman.png";
	public static String sprite = "";
	
	public Polisher() {
		super(name, bioShort, bioLong, start, skills, icon, sprite);
	}
	
}

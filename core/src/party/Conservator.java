package party;


import abilities.NewFleshWeave;
import abilities.RakingLight;
import abilities.Skill;
import abilities.StandardAttack;
import abilities.TastiestIchor;
import status.schmuckSpecific.PreservationAura;

public class Conservator extends Schmuck{

	public static final String name = "Conservator";
	public static final int pronoun = 2;
	public static final String bioShort = "TEMP";
	public static final String bioLong = "TEMP";
	
	public static final int[] start = {14,21,8,10,17,0,0,0,0,0,0,0,0,0,0,0,0};
	public static final Skill[] skills = {new StandardAttack(), new NewFleshWeave(), new RakingLight(),
			new TastiestIchor()};
	
	public static String icon = "Pluot.png";
	public static String sprite = "";
	
	public Conservator() {
		super(name, pronoun, bioShort, bioLong, start, skills, icon, sprite);
	}
	
	public void addIntrinsics(){
		this.statuses.add(new PreservationAura(this,this));
	}
}

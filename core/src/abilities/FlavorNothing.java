package abilities;

import party.Schmuck;

public class FlavorNothing extends Skill{

	public final static String name = "Nothing";
	public final static String descr = "TEMP";
	public final static int id = 0;
	public final static int cost = 0;
	public final static double init = 1;
	public final static int target = 0;
	public final static int numTargets = 0;

	public String flavorText;
	
	public FlavorNothing(String text) {
		super(name, descr, id, cost, init, target, numTargets);
		this.flavorText = text;
	}
		
	public String useText(Schmuck user){
		return flavorText;
	}

}

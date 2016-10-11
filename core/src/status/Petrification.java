package status;

import java.util.ArrayList;

import abilities.FlavorNothing;
import battle.BattleAction;
import party.Schmuck;
import states.BattleState;

public class Petrification extends Status{
	
	final static int id = 2;
	final static String name = "Petrification";
	final static boolean perm = false;
	final static boolean vis= true;
	final static boolean end = true;
	final static boolean dec = true;

	
	public Petrification(int duration,Schmuck p,Schmuck v) {
		super(id, duration, name, perm, vis, end, dec, p, v);
	}
	
	public void preAction(BattleState bs, BattleAction ba){		
		if (ba.getUser().equals(this.vic)) {
			BattleAction fail = new BattleAction(this.vic, new ArrayList<Schmuck>(),
					new FlavorNothing(ba.getUser().getName() + "'s Petrification prevents " + vic.getPronoun(3) +
					" from performing an action!"));
			bs.bt.skillReplace(0, fail);
		}
	}

	public int onHpChange(BattleState bs, Schmuck perp, Schmuck vic, int damage, int elem){
		if (vic.equals(this.vic)) {
			bs.bt.addScene(vic.getName()+"'s Petrification blocked damage!", false);
			return 0;
		} else {
			return damage;
		}
	}

	public String inflictText(Schmuck s){
		return s.getName()+" was Petrified!";
	}

	public String cureText(Schmuck s){
		return s.getName()+" is no longer Petrified!";
	}
}

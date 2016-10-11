package status.abilitySpecific;

import java.util.ArrayList;

import battle.BattleAction;
import battle.BattleButton;
import party.Schmuck;
import states.BattleState;
import status.Status;

public class ScaleUpStatus extends Status{
	
	final static int id = 2;
	final static String name = "Scaled Up";
	final static boolean perm = false;
	final static boolean vis= true;
	final static boolean end = true;
	final static boolean dec = true;

	public ScaleUpStatus(int duration, Schmuck p,Schmuck v) {
		super(id, duration, name, perm, vis, end, dec, p, v);
	}
	
	public void preAction(BattleState bs, BattleAction ba){
		if (bs.bq.getAllyTeam(this.perp).contains(ba.getUser().getButton()) &&
				ba.getSkill().getTargetType() == 0 && ba.getTarget().size() == 1 &&
				bs.bq.actionq.contains(ba.getTarget().get(0).getButton())) {
			
			ArrayList<Schmuck> targets = new ArrayList<Schmuck>();
			
			for (BattleButton bb : bs.bq.actionq) {
				if (bs.bq.getAllyTeam((ba.getTarget().get(0))).contains(bb)) {
					targets.add(bb.getSchmuck());
				}
			}
			
			ba.setTarget(targets);
			
			bs.bt.addScene(this.perp.getName() + "'s Scale Up amplified the ability!", true);
			
		}
		
	}

	public String inflictText(Schmuck s){
		return this.perp.getName() + " Scaled Up allis!";
	}

	public String cureText(Schmuck s){
		return perp.getName() + "'s allies are no longer Scaled Up!";
	}
	
}
